package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.pacman.BaseDeDatos;
import com.pacman.JuegoPrincipal;

public class PantallaFinDelJuego extends PantallaBase {
    //Pantalla que indica que finalizo el juego y da la posibilidad de reiniciar la partida

    private TextButton reiniciar;
    private Label titulo, palabraPuntaje, puntaje, palabraJugador, nombreJugador;
    private Table tabla;

    public PantallaFinDelJuego(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //Metodo que se ejecuta cuando se muestra por primera vez la pantalla
        //Se inicializan todos los elementos que vaya a utilizar la pantalla
        String[] datosPartida = juego.getDatosPartida();
        super.show();
        this.titulo = new Label(traductor.get("pantallaFinDelJuego.fin"), skin);
        this.titulo.setFontScale(1.5f);
        this.palabraJugador = new Label(traductor.get("pantallaFinDelJuego.jugador"), skin);
        this.nombreJugador = new Label(datosPartida[0], skin);
        this.palabraPuntaje = new Label(traductor.get("pantallaFinDelJuego.puntaje"), skin);
        this.puntaje = new Label(datosPartida[1], skin);
        this.reiniciar = new TextButton(traductor.get("pantallaFinDelJuego.reiniciar"), skin);
        this.reiniciar.setSize(152, 50);
        this.reiniciar.setPosition((anchoEnPx / 2) - (this.reiniciar.getWidth() / 2), (altoEnPx / 2) - (this.reiniciar.getWidth() / 2));
        //Funcionalidad al Boton reiniciar
        this.reiniciar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.getPantallaJuegoPrincipal());
            }
        });

        //Se agregan los elementos a mostrar en la tabla que los contiene para ser mostrados en pantalla
        tabla = new Table();
        tabla.align(Align.center);
        tabla.setFillParent(true);
        tabla.add(titulo).align(Align.center).colspan(2).padBottom(30);
        tabla.row();
        tabla.add(palabraJugador);
        tabla.add(nombreJugador);
        tabla.row();
        tabla.add(palabraPuntaje);
        tabla.add(puntaje);
        tabla.row();
        tabla.add(reiniciar).width(200).padTop(30).align(Align.center).colspan(2);

        this.escenario.addActor(this.tabla);
        this.escenario.addActor(retroceso);
        this.retroceso.setPosition(10, altoEnPx - 10 - retroceso.getHeight());

        //Se crea la ventana de dialogo si hubo un nuevo record
        if (this.juego.actualizarPuntaje(datosPartida[0], Integer.parseInt(datosPartida[1]))) {
            System.out.println("Nuevo Record---------------------");
            Dialog ventana = new Dialog("", this.skin);
            TextButton boton = new TextButton("Ok", this.skin);
            ventana.button(boton);
            ventana.text(traductor.get("pantallaFinDelJuego.record"));
            ventana.show(this.escenario);
        }
        Gdx.input.setInputProcessor(this.escenario);
    }

    @Override
    public void render(float delta) {
        //Metodo que se ejecuta en cada frame del juego
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.escenario.act();
        this.escenario.draw();
    }
}
