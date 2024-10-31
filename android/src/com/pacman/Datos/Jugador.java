package com.pacman.Datos;

import android.content.ContentValues;

//Clase que almacena todos los datos de un jugador

public class Jugador {

    private String usuario;
    private String contraseña;
    private int maxPuntaje;

    public Jugador(String nombre, String contraseña, int puntos) {
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.maxPuntaje = puntos;
    }

    public ContentValues toContentValues() {
        //Metodo que convierte los datos de un jugador en ContentValues para realizar consultas a la base de datos
        ContentValues valores = new ContentValues();
        valores.put(JugadorContract.JugadorEntry.USUARIO, this.usuario);
        valores.put(JugadorContract.JugadorEntry.CONTRASEÑA, this.contraseña);
        valores.put(JugadorContract.JugadorEntry.PUNTAJE, this.maxPuntaje);
        return valores;
    }
}
