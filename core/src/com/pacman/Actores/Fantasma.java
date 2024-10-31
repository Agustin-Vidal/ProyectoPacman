package com.pacman.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pacman.Aleatorio;
import com.pacman.Mundo;

public class Fantasma extends Personaje {

    private int fantasmaId, idUltimoCambio;
    private static final float duracionFrame = 0.15f;
    private Animation animMuerto, animDebilitado;
    private boolean debilitado = false;

    public Fantasma(Texture texturas, Rectangle respawn, int id, Mundo mundo) {
        super(respawn, mundo);
        //Se agregan los estados propios del fantasma
        this.estados.add("debilitado");
        this.estados.add("muerto");
        this.estados.add("finDebilitado");
        int ejeY = 20, aumento = 16;                       //Bases para obtener las animaciones
        //Por default el id del fantasma es 0
        switch (id) {
            case 1:
                this.fantasmaId = 1;                       //La posicion del fantasma con id 1 no se modifica
                //Se situan los limites en la entrada de la guarida
                break;
            case 2:
                this.fantasmaId = 2;
                //Se situan los limites a la derecha de la entrada de la guarida
                //Se debe mover al fantasma 0.1px para que no colisione con el cambio de direccion que no le corresponde
                this.limites.setPosition(this.limites.getX() + (this.limites.getHeight() + 0.1f), this.limites.getY());
                break;
            case 3:
                this.fantasmaId = 3;
                //Se situan los limites a la izquierda de la entrada de la guarida
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                break;
            default:
                this.fantasmaId = 0;
                //Se situan los limites a la izquierda de la entrada de la guarida
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                break;
        }
        this.setPosition(this.limites.getX(), this.limites.getY());               //Establezco la posicion del Actor fantasma donde corresponde
        establecerAnimaciones(texturas, ejeY + (aumento * this.fantasmaId));
        this.animActual = animArriba;
        this.estadoActual = 5;                                                    //Se etablece el estado arriba
    }


    public boolean setEstado(String estado) {
        //Metodo que cambia al fantasma de estado y establece la animacion correspondiente al estado
        //El metodo retorna true si se pudo cambiar el estado, false en caso contrario
        boolean exito = true;
        int pos = estados.indexOf(estado), estadoAnterior = this.estadoActual;
        //System.out.println(pos);
        if (pos != -1 && this.estadoActual != pos && !this.inicioAnimMuerto) {
            this.estadoActual = pos;
            if (getEstado().equals("finDebilitado")) {
                this.debilitado = false;
                this.estadoActual = estadoAnterior;
            }
            //Si el fantasma esta debilitado entonces no se debe cambiar la animacion
            //solo cambia el estado para el movimiento
            if (!this.debilitado) {
                switch (this.estadoActual) {
                    case 0:
                        this.animActual = this.animIzq;
                        break;
                    case 1:
                        this.animActual = this.animDer;
                        break;
                    case 2:
                        this.animActual = this.animArriba;
                        break;
                    case 3:
                        this.animActual = this.animAbajo;
                        break;
                    case 4:
                        //System.out.println("Se establecio como debilitado");
                        this.animActual = this.animDebilitado;
                        this.debilitado = true;
                        break;
                }
            } else if (getEstado().equals("muerto")) {
                //Si el fantasma esta debilitado y se establecio el estado muerto, se carga la animacion de muerte
                this.animActual = this.animMuerto;
            }
        } else {
            exito = false;
        }
        return exito;
    }

    protected void revivir() {
        //Metodo que se ejecuta cuando termina la animacion de muerte del Fantasma
        super.revivir();
        this.debilitado = false;
        this.setEstado("arriba");
    }

    protected void mover(float delta) {
        //Metodo que determina la direccion en la que debe moverse el Fantasma segun el estado
        //luego traslada al Fantasma y verifica si ocurrieron colisiones con paredes o si debe cambiar de direccion
        Rectangle pared;
        switch (this.estadoActual) {
            case 0:
                this.direccion = new Vector2(-delta, 0);
                //System.out.println("Estado izquierda");
                break;
            case 1:
                this.direccion = new Vector2(delta, 0);
                //System.out.println("Estado derecha");
                break;
            case 2:
                this.direccion = new Vector2(0, delta);
                //System.out.println("Estado arriba");
                break;
            case 3:
                this.direccion = new Vector2(0, -delta);
                //System.out.println("Estado abajo");
                break;
            case 4:
                //Si el fantasma esta debilitado debe reiniciar la direccion que poseia, para que el vector se rescale correctamente
                float deltaX, deltaY;
                if (this.direccion.x > 0) {
                    deltaX = delta;
                } else if (this.direccion.x < 0) {
                    deltaX = -delta;
                } else {
                    deltaX = 0;
                }
                if (this.direccion.y > 0) {
                    deltaY = delta;
                } else if (this.direccion.y < 0) {
                    deltaY = -delta;
                } else {
                    deltaY = 0;
                }
                this.direccion = new Vector2(deltaX, deltaY);
                //System.out.println("Estado Debilitado");
                break;
            case 5:
                this.direccion = new Vector2(0, 0);
                //System.out.println("Estado muerto");
                break;
        }
        this.direccion.scl(VELOCIDAD);
        setXY(getX() + this.direccion.x, getY() + this.direccion.y);
        pared = this.mundo.verificarColisionPared(this);
        if (pared != null) {
            reacomodar(pared);
        } else {
            //Si no hay colision con paredes, se verifica si esta en un cambio de direccion y elige una nueva dentro de las disponibles
            MapObject cambioDireccion = this.mundo.verificarCambioDireccion(this);
            if (cambioDireccion != null) {
                elegirDireccion(cambioDireccion);
            }
        }
    }

    private void elegirDireccion(MapObject cambioDireccion) {
        //Metodo que recibe una posicion del mapa, obtiene las direcciones posibles
        //luego elige aleatoriamente la siguiente direccion, dentro de las que esten disponibles en la posicion actual
        //(solo hay cuatro tipos de posiciones de cambio, de una sola direccion, de dos, de 3 o de 4 direcciones posibles)
        String tipoCambio = cambioDireccion.getProperties().get("type", String.class);
        String direccionesPosibles = cambioDireccion.getProperties().get("direcciones", String.class);
        int idCambio = cambioDireccion.getProperties().get("id", Integer.class);

        //Vuelve a analizar la posicion de cambio, solo si es una nueva posicion
        if (idCambio != this.idUltimoCambio) {
            /*System.out.println("IdCambio" + idCambio);
            System.out.println("Rectangulo" + cambioDireccion.getProperties().get("x", Float.class) + "//" + cambioDireccion.getProperties().get("y", Float.class));
            System.out.println("Fantasma x" + this.getLimites().getX() + "//" + this.getLimites().getY());
            System.out.println("Direcciones posibles" + direccionesPosibles);
            System.out.println("Estado Anterior" + this.getEstado());*/
            this.idUltimoCambio = idCambio;
            if (tipoCambio.equals("unaDir")) {
                this.setEstado(direccionesPosibles);
            } else if (tipoCambio.equals("dosDir")) {
                //System.out.println("2dir");
                String[] direccion = new String[2];
                direccion[0] = direccionesPosibles.substring(0, direccionesPosibles.indexOf("/"));
                direccion[1] = direccionesPosibles.substring(direccionesPosibles.indexOf("/") + 1);
                int opcionElegida = Aleatorio.intAleatorio(-1, 2);
                //System.out.println(opcionElegida);
                this.setEstado(direccion[opcionElegida]);
            } else if (tipoCambio.equals("tresDir")) {
                //System.out.println("3dir");
                //System.out.println(direccionesPosibles);
                String[] direccion = new String[3];
                direccion[0] = direccionesPosibles.substring(0, direccionesPosibles.indexOf("/"));
                int primerIndice = direccionesPosibles.indexOf("/") + 1;
                int segundoIndice = direccionesPosibles.indexOf("/", primerIndice);
                direccion[1] = direccionesPosibles.substring(primerIndice, segundoIndice);
                direccion[2] = direccionesPosibles.substring(segundoIndice + 1);
                int opcionElegida = Aleatorio.intAleatorio(-1, 3);
                //System.out.println(opcionElegida);
                this.setEstado(direccion[opcionElegida]);
            } else if (tipoCambio.equals("cuatroDir")) {
                //System.out.println("4dir");
                //No se leen las direcciones, porque las posiciones de cuatro direcciones, solo tienen una posibilidad
                String[] direccion = {"arriba", "abajo", "izquierda", "derecha"};
                int opcionElegida = Aleatorio.intAleatorio(-1, 4);
                //System.out.println(opcionElegida);
                this.setEstado(direccion[opcionElegida]);
            }
            //System.out.println("Estado Siguiente" + this.getEstado());
        }
    }

    public int getId() {
        return this.fantasmaId;
    }

    public boolean estaDebilitado() {
        return this.debilitado;
    }

    public boolean estaMuerto() {
        //Metodo que inidica que el fantasma esta muerto (retorna true) si su animacion de muerte se esta reproduciendo
        //false en caso contrario
        return this.inicioAnimMuerto;
    }

    private void establecerAnimaciones(Texture texturas, int ejeY) {
        //Metodo que incializa las animaciones del Fantasma segun el valor recibido por parametro (ejeY),
        //el cual es determinado por el id del fantasma, en el constructor
        animIzq = new Animation<Sprite>(duracionFrame, getAnimIzq(texturas, ejeY), Animation.PlayMode.LOOP);
        animDer = new Animation<Sprite>(duracionFrame, getAnimDer(texturas, ejeY), Animation.PlayMode.LOOP);
        animArriba = new Animation<Sprite>(duracionFrame, getAnimArriba(texturas, ejeY), Animation.PlayMode.LOOP);
        animAbajo = new Animation<Sprite>(duracionFrame, getAnimAbajo(texturas, ejeY), Animation.PlayMode.LOOP);
        animDebilitado = new Animation<Sprite>(duracionFrame, getAnimDebilitado(texturas), Animation.PlayMode.LOOP);
        animMuerto = new Animation<Sprite>(this.duracionFrame, getAnimMuerto(texturas), Animation.PlayMode.NORMAL);
    }

    private Array<Sprite> getAnimIzq(Texture texturas, int ejeY) {
        //Metodo que devuelve un arreglo con los frames de la animacion de movimiento izquierda
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 34, ejeY, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 50, ejeY, 14, 13)));
        return animacion;
    }

    private Array<Sprite> getAnimDer(Texture texturas, int ejeY) {
        //Metodo que devuelve un arreglo con los frames de la animacion de movimiento derecha
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 2, ejeY, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 18, ejeY, 14, 13)));
        return animacion;
    }

    private Array<Sprite> getAnimArriba(Texture texturas, int ejeY) {
        //Metodo que devuelve un arreglo con los frames de la animacion de movimiento arriba
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 66, ejeY, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 82, ejeY, 14, 13)));
        return animacion;
    }

    private Array<Sprite> getAnimAbajo(Texture texturas, int ejeY) {
        //Metodo que devuelve un arreglo con los frames de la animacion de movimiento abajo
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 98, ejeY, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 114, ejeY, 14, 13)));
        return animacion;
    }

    private Array<Sprite> getAnimDebilitado(Texture texturas) {
        //Metodo que devuelve un arreglo con los frames de la animacion de debilitamiento
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 2, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 18, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 34, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 50, 84, 14, 13)));
        return animacion;
    }

    private Array<Sprite> getAnimMuerto(Texture texturas) {
        //Metodo que devuelve un arreglo con los frames de la animacion de muerte
        Array<Sprite> animacion = new Array<Sprite>();
        animacion.add(new Sprite(new TextureRegion(texturas, 66, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 82, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 98, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 114, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 66, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 82, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 98, 84, 14, 13)));
        animacion.add(new Sprite(new TextureRegion(texturas, 114, 84, 14, 13)));
        return animacion;
    }
}
