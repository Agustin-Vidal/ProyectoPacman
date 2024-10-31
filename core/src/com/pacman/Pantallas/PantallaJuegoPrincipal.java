package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.pacman.Actores.PacMan;
import com.pacman.Gamepad;
import com.pacman.JuegoPrincipal;
import com.pacman.Mundo;

public class PantallaJuegoPrincipal extends PantallaBase {
    //Pantalla que se encarga de crear la partida que se va a jugar,
    //estableciendo la camara, los sonidos, el gamepad y el elemento que muestra el puntaje por pantalla

    private Mundo mundo;

    //Elementos Visuales
    private TextArea puntajePantalla;
    private Texture sprites;

    private Gamepad touch;

    private boolean configSonido = true;
    private Music sonidoJuego;
    private AssetManager manager;

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //Metodo que se ejecuta cuando se muestra por primera vez la pantalla
        //Se inicializan todos los elementos que vaya a utilizar la pantalla
        super.show();
        establecerSonido();             //Se establecen los sonidos del juego
        //System.out.println("Mapa" + widthEnPx + "//" + heightEnPx);

        this.mundo = new Mundo(this.mapa, this.escenario, this.manager, this.configSonido);

        //Se establece el gamePad
        Gdx.input.setInputProcessor(escenario);
        this.touch = new Gamepad(15, this.skin, this.mundo.getPacman());
        this.touch.setBounds(76, 0, 140, 140);
        this.escenario.addActor(this.touch);

        //Se inicia la reproduccion del sonido del juego
        if (this.configSonido) {
            this.sonidoJuego = this.manager.get("sounds/pac-mans-park-block-plaza-super-smash-bros-3ds.ogg");
            this.sonidoJuego.setLooping(true);
            this.sonidoJuego.play();
        }

        //Se establece el elemento que muestra el puntaje por pantalla
        this.puntajePantalla = new TextArea("Score: ", skin);
        this.puntajePantalla.setPosition(200, 336);
        this.escenario.addActor(puntajePantalla);

        //Se obtienen los sprites para mostrar las vidas
        this.sprites = new Texture("personajes/actors.png");

        //Se modifica la posicion del boton de retroceso
        this.retroceso.setPosition(10, altoEnPx - retroceso.getHeight());
    }

    public void actualizarScore() {
        //Metodo que establece el texto del area de teto que muestr el puntaje por pantalla
        this.puntajePantalla.setText(traductor.get("pantallaJuegoPrincipal.puntaje") + this.mundo.getPuntaje());
        //System.out.println(this.puntajePantalla.getText());
    }

    @Override
    protected void establecerCamara() {
        //Metodo que utiliza al meotodo de la clase padre y agrega al renderizador del mapa, para que se muestre por pantalla
        super.establecerCamara();
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(mapa, 1 / 16f, this.batch);
    }

    private void establecerSonido() {
        //Metodo que carga los sonidos que se van a usar en el juego
        this.manager = new AssetManager();
        this.manager.load("sounds/big_pill.ogg", Sound.class);
        this.manager.load("sounds/clear.ogg", Sound.class);
        this.manager.load("sounds/ghost_die.ogg", Sound.class);
        this.manager.load("sounds/pacman_die.ogg", Sound.class);
        this.manager.load("sounds/pill.ogg", Sound.class);
        this.manager.load("sounds/pac-mans-park-block-plaza-super-smash-bros-3ds.ogg", Music.class);
        this.manager.finishLoading();
    }

    @Override
    public void render(float delta) {
        //Metodo que se ejecuta en cada frame del juego
        //Es el encargado de verificar si el juego termino o no, ademas
        //de hacer que los elementos en el escenario actuen y se dibujen por pantalla
        this.camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.tiledMapRenderer.setView(this.camera);
        this.tiledMapRenderer.render();
        int cantVidas = this.mundo.getCantVidas();
        Batch batch = this.escenario.getBatch();
        batch.begin();
        for (int i = 0; i < cantVidas; i++) {
            batch.draw(new TextureRegion(sprites, 179, 58, 14, 14), 8f + i, 21, 1, 1);
        }
        batch.end();
        //Si el juego finalizo (estado 0 o 1), se establece la transicion a la pantalla de fin del juego
        //caso contrario continua con la ejecucuion del juego
        if (this.mundo.getEstadoJuego() != -1) {
            juego.setPuntajeActual(this.mundo.getPuntaje());
            escenario.addAction(Actions.sequence(
                    Actions.delay(0.30f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            juego.setScreen(juego.getPantallaFinDelJuego());
                        }
                    })
            ));
        }
        actualizarScore();
        this.escenario.act();
        this.escenario.draw();
    }

    @Override
    public void hide() {
        //Metodo que se ejecuta cuando la pantalla ya no es la pantalla que se visualiza
        if (configSonido) {
            sonidoJuego.stop();
        } else {
            configSonido = true;
        }
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        //Metodo que se llama cuando las dimensiones de la pantalla cambian
        super.resize(width, height);
        this.viewport.update(width, height);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose() {
        //Metodo que se ejecuta cuando la pantalla debe eliminar los recursos
        //o cuando la pantalla actual se debe eliminar, porque ya no es la pantalla mostrada
        super.dispose();
        this.tiledMapRenderer.dispose();
        this.sprites.dispose();
    }

    public boolean cambiarConfigSonido() {
        this.configSonido = !this.configSonido;
        return configSonido;
    }
}
