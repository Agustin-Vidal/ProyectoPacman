package com.pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BotonSonidoListener extends InputListener {

    //Clase que extiende InputListener para manejar el comportamiento del boton del sonido

    private JuegoPrincipal juego;
    private ImageButton boton;

    private Texture textura;
    private TextureRegion regionTextura;
    private TextureRegionDrawable drawable;
    private Texture texturaMute;
    private TextureRegion regionMute;
    private TextureRegionDrawable drawableMute;

    public BotonSonidoListener(JuegoPrincipal juego, ImageButton boton) {
        this.juego = juego;
        this.boton = boton;
        this.textura = new Texture("buttons/speaker.png");
        this.regionTextura = new TextureRegion(textura);
        this.drawable = new TextureRegionDrawable(regionTextura);
        this.texturaMute = new Texture("buttons/speaker_muted.png");
        this.regionMute = new TextureRegion(texturaMute);
        this.drawableMute = new TextureRegionDrawable(regionMute);
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //Metodo que establece el comportamiento del boton cuando este es presionado
        //Cambia la imagen del boton y la configaracion de sonido del juego
        if (this.juego.getPantallaJuegoPrincipal().cambiarConfigSonido()) {
            boton.getStyle().imageUp = drawable;
        } else {
            boton.getStyle().imageUp = drawableMute;
        }
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }
}
