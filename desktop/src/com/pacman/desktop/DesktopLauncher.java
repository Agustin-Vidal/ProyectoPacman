package com.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pacman.JuegoPrincipal;

public class DesktopLauncher {
    public static void main(String[] arg) {
        System.setProperty("user.name", "gasty");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new JuegoPrincipal(new BaseDeDatosDesktop()), config);
    }
}
