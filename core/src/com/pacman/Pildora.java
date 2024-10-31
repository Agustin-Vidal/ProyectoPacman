package com.pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pildora extends Actor {

    private TextureRegion imagen;
    private Rectangle limites;
    private boolean esGrande;
    private boolean valida;

    public Pildora(Texture imagen, Rectangle limites, boolean esGrande) {
        this.valida = true;
        if (esGrande) {
            this.imagen = new TextureRegion(imagen, 147, 39, 16, 16);
        } else {
            this.imagen = new TextureRegion(imagen, 131, 39, 16, 16);
        }
        this.esGrande = esGrande;
        this.limites = limites;
        this.setPosition(this.limites.getX(), this.limites.getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imagen, getX(), getY());
    }

    public void esComida() {
        //Metodo que establece que la pildora fue comida por el PacMan
        if (this.valida) {
            //System.out.println("La pildora fue comida");
            this.valida = false;
            this.remove();                                 //Se quita a la pildora de la pantalla
        }
    }

    public boolean esValida() {
        //Metodo que indica si la pildora es valida para ser comida o no
        return this.valida;
    }

    public boolean esGrande() {
        return (this.esGrande && this.valida);
    }

    public Rectangle getLimites() {
        return this.limites;
    }
}
