package com.pacman.Datos;

import android.provider.BaseColumns;

public class JugadorContract {

    //Clase que guarda los nombres de la table y sus columnas en un solo lugar para acceder a ellos facilmente

    public static abstract class JugadorEntry implements BaseColumns {

        public static final String NOMBRE_TABLA = "jugador";
        public static final String USUARIO = "usuario";
        public static final String CONTRASEÑA = "contraseña";
        public static final String PUNTAJE = "puntajemaximo";
    }
}
