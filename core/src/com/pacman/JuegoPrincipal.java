package com.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.pacman.Pantallas.PantallaFinDelJuego;
import com.pacman.Pantallas.PantallaIngreso;
import com.pacman.Pantallas.PantallaJuegoPrincipal;
import com.pacman.Pantallas.PantallaMenu;
import com.pacman.Pantallas.PantallaRanking;
import com.pacman.Pantallas.PantallaRegistro;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JuegoPrincipal extends Game {
    //Clase principal sobre la que se establece el juegp
    //En esta se crean las diferentes pantallas y recibe la base de datos con la que se operara en el juego
    public final BaseDeDatos baseDeDatos;
    private PantallaJuegoPrincipal pantallaJuegoPrincipal;
    private PantallaFinDelJuego pantallaFinDelJuego;
    private PantallaIngreso pantallaIngreso;
    private PantallaRegistro pantallaRegistro;
    private PantallaMenu pantallaMenu;
    private PantallaRanking pantallaRanking;
    private String nombreJugador = "Vacio";
    private int puntaje = 0;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        //Metodo que se ejecuta cuando se crea la aplicacion, momento en el que se crean los elementos que se utilizaran en la aplicacion
        Gdx.input.setCatchBackKey(true);  //Se bloquea el boton back, para que no se cierre la aplicacion
        //Metodo que se llama cuando la aplicación es creada (antes de iniciar el game loop)
        //En este se inicializan las pantallas y se establece la pantalla principal
        this.baseDeDatos.inicializar();
        this.pantallaJuegoPrincipal = new PantallaJuegoPrincipal(this);
        this.pantallaFinDelJuego = new PantallaFinDelJuego(this);
        this.pantallaIngreso = new PantallaIngreso(this);
        this.pantallaRegistro = new PantallaRegistro(this);
        this.pantallaMenu = new PantallaMenu(this);
        this.pantallaRanking = new PantallaRanking(this);
        setScreen(this.pantallaMenu);
    }

    public PantallaRanking getPantallaRanking() {
        return pantallaRanking;
    }

    public PantallaMenu getPantallaMenu() {
        return pantallaMenu;
    }

    public PantallaIngreso getPantallaIngreso() {
        return this.pantallaIngreso;
    }

    public PantallaRegistro getPantallaRegistro() {
        return this.pantallaRegistro;
    }

    public PantallaFinDelJuego getPantallaFinDelJuego() {
        return this.pantallaFinDelJuego;
    }

    public PantallaJuegoPrincipal getPantallaJuegoPrincipal() {
        return this.pantallaJuegoPrincipal;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setPuntajeActual(int puntajeActual) {
        this.puntaje = puntajeActual;
    }

    public String[] getDatosPartida() {
        String[] dato = {this.nombreJugador, Integer.toString(this.puntaje)};
        return dato;
    }

    public ArrayList obtenerDatos() {
        //metodo que obtiene los puntajes maximos de todos los usuarios
        return this.baseDeDatos.obtenerDatos();
    }

    public boolean actualizarPuntaje(String nombre, int puntos) {
        //metodo que actualiza el puntaje recibido por parametro, del jugador recibido por parametro
        return this.baseDeDatos.actualizarPuntaje(nombre, puntos);
    }

    public BaseDeDatos getBD() {
        //metodo que devuelve la instancia de la base de datos del juego
        return this.baseDeDatos;
    }
}