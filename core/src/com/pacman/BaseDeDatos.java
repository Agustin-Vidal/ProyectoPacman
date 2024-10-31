package com.pacman;

import java.util.ArrayList;

public interface BaseDeDatos {

    //Interfaz de la base de datos, utilizada para hacer el vinculo con la base de datos creada para el dispositivo android
    //Esta interfaz establece los metodos que se van a poder utilizar sobre la base de datos

    public void inicializar();

    public boolean crearJugador(String nombre, String contraseña, int puntos);

    public boolean logIn(String nombre, String contraseña);

    public boolean actualizarPuntaje(String nombre, int puntos);

    public void mostrarDatos();

    public ArrayList obtenerDatos();
}