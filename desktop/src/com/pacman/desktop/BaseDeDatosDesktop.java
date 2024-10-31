package com.pacman.desktop;

import com.badlogic.gdx.Gdx;
import com.pacman.BaseDeDatos;

import java.util.ArrayList;

public class BaseDeDatosDesktop implements BaseDeDatos {

//Clase que se encargaria de controlar la base de datos si SQLite funcionara en escritorio

    @Override
    public void inicializar() {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }

    @Override
    public boolean crearJugador(String nombre, String contrasena, int puntos) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public boolean logIn(String nombre, String contrasena) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public boolean actualizarPuntaje(String nombre, int puntos) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public void mostrarDatos() {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }

    @Override
    public ArrayList obtenerDatos() {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return new ArrayList();
    }
}
