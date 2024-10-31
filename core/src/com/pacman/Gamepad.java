package com.pacman;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.pacman.Actores.PacMan;

public class Gamepad extends Touchpad {
    //Clase utilizada para implementar el gamepad
    private PacMan pac;

    public Gamepad(float deadzoneRadius, Skin skin, PacMan pac) {
        super(deadzoneRadius, skin);
        this.pac = pac;
    }

    @Override
    public void act(float delta) {
        //Metodo que se llama en cada frame del juego
        //Captura la posicion del knob, determinando la direccion en la que se debe mover el PacMan
        //y luego se lo notifica
        super.act(delta);
        //System.out.println("X" + this.getKnobX() + "---j---" + this.getKnobY() + "Y");
        //System.out.println(delta + "delta de touch");
        if (isTouched()) {
            //se verifica en caso de tocarse el touchpad, si fue un lugar de direccion habil????????????????
            if (this.getKnobX() >= 55 && this.getKnobX() <= 86 && this.getKnobY() >= 85) {
                pac.setEstado("arriba");
                //System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
            } else if (this.getKnobX() >= 55 && this.getKnobX() <= 86 && this.getKnobY() <= 55) {
                pac.setEstado("abajo");
                //System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
            } else if (this.getKnobX() <= 55 && this.getKnobY() >= 55 && this.getKnobY() <= 86) {
                pac.setEstado("izquierda");
                //System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
            } else if (this.getKnobX() >= 85 && this.getKnobY() >= 55 && this.getKnobY() <= 86) {
                pac.setEstado("derecha");
                //System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
            }
        } else {
            pac.setEstado("quieto");
        }
    }
}