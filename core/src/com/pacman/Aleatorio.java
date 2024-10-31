package com.pacman;

public class Aleatorio {

    public static double doubleAleatorio(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    public static int intAleatorio(int min, int max) {
        return (int) doubleAleatorio(min, max);
    }

    public static char charAleatorio() {
        char a;
        if (Math.random() >= 0.5) {
            a = (char) intAleatorio(97, 123);
        } else {
            a = (char) intAleatorio(65, 91);
        }
        return a;
    }

    public static String stringAleatorio(int tam) {
        String res = "";
        for (int i = 0; i < tam; i++) {
            res = res + charAleatorio();
        }
        return res;
    }
}
