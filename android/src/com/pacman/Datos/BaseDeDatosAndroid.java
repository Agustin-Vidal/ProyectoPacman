package com.pacman.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pacman.BaseDeDatos;

import java.util.ArrayList;

public class BaseDeDatosAndroid implements BaseDeDatos {

    //Clase que implementa todas las consultas a la base de datos necesarias para el funcionamiento del juego

    private final ServicioBD basedeDatos;

    public BaseDeDatosAndroid(Context contexto) {
        this.basedeDatos = new ServicioBD(contexto);
    }

    public void inicializar() {
        //Metodo que ingresa algunos datos iniciales a la base de datos
        this.crearJugador("Agustin", "reina", 1000);
        this.crearJugador("Gaston", "1234", 2500);
        this.crearJugador("Yaupe", "123987456", 750);
    }

    @Override
    public boolean crearJugador(String nombre, String contraseña, int puntos) {
        //Metodo que ingresa un nuevo jugador a la base de datos siempre y cuando no se repitan nombres de usuario
        SQLiteDatabase db = this.basedeDatos.getWritableDatabase();
        Cursor cursorJugador = this.getJugadorByUsuario(nombre);
        boolean resultado = false;
        if (!cursorJugador.moveToNext()) {
            Jugador jugador = new Jugador(nombre, contraseña, puntos);
            if (db.insert(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, jugador.toContentValues()) != (-1)) {
                //System.out.println("---------------------------------------Jugador " + nombre + " creado exitosamente--------------------------------");
                resultado = true;
            } else {
                //System.out.println("--------------------------------------Ocurrio un error al crear al jugador " + nombre + " -----------------------------");
            }
        } else {
            //System.out.println("----------------------------El jugador ya se encuentra en la base de datos-----------------------------------------");
        }
        return resultado;
    }

    @Override
    public boolean logIn(String nombre, String contraseña) {
        //Metodo que verifica si el nombre ingresado por parametro se encuentra en la base de datos y que la contrasena concuerde
        boolean resultado = false;
        Cursor cursorJugador = this.getJugadorByUsuario(nombre);
        if (cursorJugador.moveToNext()) {
            if (this.verificarContraseña(cursorJugador, contraseña)) {
                //System.out.println("-------------------------------------Bienvenido " + nombre + "------------------------------------------");
                resultado = true;
            } else {
                //System.out.println("----------------------------------------Contraseña incorrecta-----------------------------------------");
            }
        } else {
            //System.out.println("----------------------------------------El jugador " + nombre + " no existe-----------------------------------");
        }
        return resultado;
    }

    @Override
    public boolean actualizarPuntaje(String nombre, int puntos) {
        //Metodo que actualiza el puntaje de un jugador si el nuevo puntaje supera al anterior
        boolean resultado = false;
        SQLiteDatabase db = this.basedeDatos.getWritableDatabase();
        Cursor cursorJugador = getJugadorByUsuario(nombre);
        cursorJugador.moveToNext();
        if (puntos > cursorJugador.getInt(cursorJugador.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE))) {
            ContentValues valores = new ContentValues();
            valores.put(JugadorContract.JugadorEntry.PUNTAJE, puntos);
            db.update(JugadorContract.JugadorEntry.NOMBRE_TABLA, valores, JugadorContract.JugadorEntry.USUARIO + " Like ?", new String[]{nombre});
            resultado = true;
            //System.out.println("-----------------------------Puntaje del jugador " + nombre + " actualizado a " + puntos + " puntos----------------------------------");
        } else {
            //System.out.println("------------------------------------Error al actualizar el puntaje de " + nombre + "----------------------------------------");
        }
        return resultado;
    }

    @Override
    public void mostrarDatos() {
        //Metodo que muestra por consola todos los datos almacenados en la base de datos
        //Metodo para debug
        Cursor cursor = this.basedeDatos.getReadableDatabase().query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, null, null, null, null, JugadorContract.JugadorEntry.PUNTAJE + " DESC");
        System.out.println("----------------------------------------------BASE DE DATOS PACMAN----------------------------------------");
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndex(JugadorContract.JugadorEntry.USUARIO));
            System.out.println("Nombre de usuario: " + nombre);
            String contra = cursor.getString(cursor.getColumnIndex(JugadorContract.JugadorEntry.CONTRASEÑA));
            System.out.println("Contraseña: " + contra);
            int puntos = cursor.getInt(cursor.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE));
            System.out.println("Puntaje: " + puntos);
            System.out.println("---------------------------------------------------------------------");
        }
    }

    @Override
    public ArrayList obtenerDatos() {
        //Metodo que retorna una lista ordenada con los nombres de todos los jugadores y sus puntajes
        ArrayList lista = new ArrayList();
        Cursor cursor = this.basedeDatos.getReadableDatabase().query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, null, null, null, null, JugadorContract.JugadorEntry.PUNTAJE + " DESC");
        while (cursor.moveToNext()) {
            lista.add(cursor.getString(cursor.getColumnIndex(JugadorContract.JugadorEntry.USUARIO)));
            lista.add(cursor.getInt(cursor.getColumnIndex(JugadorContract.JugadorEntry.PUNTAJE)));
        }
        return lista;
    }

    private Cursor getJugadorByUsuario(String usuario) {
        //Metodo que realiza una consulta a la base de datos y retorna su resultado
        SQLiteDatabase db = this.basedeDatos.getReadableDatabase();
        Cursor c = db.query(JugadorContract.JugadorEntry.NOMBRE_TABLA, null, JugadorContract.JugadorEntry.USUARIO + " LIKE ?", new String[]{usuario},
                null, null, null);
        return c;
    }

    private boolean verificarContraseña(Cursor cursorJugador, String contraseña) {
        //Metodo que compara la contraseña ingresada por parametro con la contraseña de un jugador de la base de datos
        return contraseña.equals(cursorJugador.getString(cursorJugador.getColumnIndex(JugadorContract.JugadorEntry.CONTRASEÑA)));
    }
}

