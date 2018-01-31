package com.example.jorge.androidjuego;

import android.view.SurfaceHolder;

public class BucleJuego extends Thread {

    private Juego j;
    private SurfaceHolder sh;
    private final static int MAX_FPS = 45;
    private final static int MAX_FRAMES_SALTADOS = 5;
    private final static int TIEMPO_POR_FRAME = 1000 / MAX_FPS;

    private boolean ejecutando = true;

    public void fin() {
        ejecutando = false;
    }

    public BucleJuego(SurfaceHolder sh, Juego j) {
        this.j = j;
        this.sh = sh;
    }

    public void run() {
        long empieza;
        long diferencia;
        int dormido = 0;
        int jumpFrames;

        while (ejecutando)
            synchronized (sh) {
                empieza = System.currentTimeMillis();
                jumpFrames = 0;
                j.actualizar(); //actualiza el juego
                j.renderizar(); //dibuja el juego
                //calcula el tiempo que ha tardado en actualizar y en renderizar
                diferencia = System.currentTimeMillis() - empieza;
                //calcula lo que debe dormir el thread hasta ejecurtarlo de nuevo
                dormido = (int) (TIEMPO_POR_FRAME - diferencia);
                //si el thread tiene que dormir, duerme
                if (dormido > 0) {
                    try {
                        sleep(dormido);
                    } catch (InterruptedException e) {

                    }
                }

                //si el thread ha ido lento actualiza sin renderizar hasta sincronizarse
                while (dormido < 0 && jumpFrames < MAX_FRAMES_SALTADOS) {
                    j.actualizar();
                    dormido += TIEMPO_POR_FRAME;
                    jumpFrames++;
                }
            }
    }

}
