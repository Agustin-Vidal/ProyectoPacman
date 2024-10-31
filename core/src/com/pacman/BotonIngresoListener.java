package com.pacman;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.I18NBundle;

public class BotonIngresoListener extends InputListener {

    //Clase que extiende InputListener para manejar el comportamiento del boton de ingreso de usuario

    private Stage escenario;
    private Skin skin;
    private BaseDeDatos bd;
    private TextField usuarioTextField;
    private TextField contrasenaTextField;
    private JuegoPrincipal juego;
    private I18NBundle traductor;

    public BotonIngresoListener(Stage escenario, Skin skin, BaseDeDatos bd, TextField campoUsuario, TextField campoContra, JuegoPrincipal juego, I18NBundle traductor) {
        this.escenario = escenario;
        this.skin = skin;
        this.bd = bd;
        this.usuarioTextField = campoUsuario;
        this.contrasenaTextField = campoContra;
        this.juego = juego;
        this.traductor = traductor;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //Metodo que establece el comportamiento del boton cuando este es presionado
        //Realiza una consulta a la base para verificar los datos ingresados por pantalla
        //Muestra un cartel por pantalla para indicar si la operacion tuvo exito o no
        Dialog ventana = new Dialog("", this.skin);
        TextButton boton = new TextButton("Ok", this.skin);
        ventana.button(boton);
        if (this.bd.logIn(this.usuarioTextField.getText(), this.contrasenaTextField.getText())) {
            ventana.text(traductor.get("botonIngreso.exito") + this.usuarioTextField.getText());
            ventana.show(this.escenario);
            juego.setNombreJugador(this.usuarioTextField.getText());
            juego.setScreen(juego.getPantallaJuegoPrincipal());
        } else {
            ventana.text(traductor.get("botonIngreso.error"));
            ventana.show(this.escenario);
        }
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }
}
