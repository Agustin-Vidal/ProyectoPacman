package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.pacman.JuegoPrincipal;

import java.util.ArrayList;

public class PantallaRanking extends PantallaBase {

    private Table tabla;
    private Label cabecera;
    private ScrollPane panel;

    public PantallaRanking(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //Metodo que se ejecuta cuando se muestra por primera vez la pantalla
        //Se inicializan todos los elementos que vaya a utilizar la pantalla
        super.show();
        this.tabla = new Table(this.skin);
        this.tabla.align(Align.center);
        this.cabecera = new Label(traductor.get("pantallaRankings.titulo"), skin);
        this.tabla.add(cabecera).colspan(2);
        this.tabla.row();
        this.panel = new ScrollPane(tabla, skin);

        //Se obtienen a todos los usuarios registrados en la base de datos
        ArrayList listaJugadores = this.juego.obtenerDatos();
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (i % 2 == 0) {
                this.tabla.add(listaJugadores.get(i).toString()).height(50).width(100);
            } else {
                this.tabla.add(listaJugadores.get(i).toString()).height(50).width(50);
                this.tabla.row();
            }
        }

        //Se agregan los elementos a mostrar en la tabla que los contiene para ser mostrados en pantalla
        Table container = new Table(this.skin);
        container.setFillParent(true);
        System.out.println();
        container.add(this.panel).expand().fill();
        container.row();
        this.escenario.addActor(container);
        Gdx.input.setInputProcessor(this.escenario);
        this.retroceso.remove();
        this.escenario.addActor(retroceso);
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
