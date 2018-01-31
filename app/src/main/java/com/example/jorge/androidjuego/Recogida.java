package com.example.jorge.androidjuego;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class Recogida {

    private int anchoSprite;
    private int altoSprite;
    private final int NUM_IMG = 5; //numero de imagenes diferentes
    public int x, y;
    private Juego j;
    private int estado;

    public Recogida(Juego j, int x, int y) {
        this.x = x;
        this.y = y;
        this.j = j;
        anchoSprite = j.recogida.getWidth() / NUM_IMG; //dividimos en partes iguales
        altoSprite = j.recogida.getHeight();
        estado = -1;
    }

    public void actualizarEstado() {
        estado++; //pasamos de una imagen recortada a otra
    }

    public void renderizar(Canvas canvas, Paint paint) {
        int posicion = estado * anchoSprite; //asignamos la posicion

        //si todavia no hemos terminado de dibujar las 5 imagenes de la animacion
        if (!terminado()) {
            Rect origen = new Rect(posicion, 0, posicion + anchoSprite, altoSprite);
            Rect destino = new Rect(x, y, x + anchoSprite, y + j.recogida.getHeight());
            canvas.drawBitmap(j.recogida, origen, destino, paint);
        }
    }

    //comprueba si ya se han reproducido las 5 imagenes
    public boolean terminado() {
        return estado >= NUM_IMG;
    }


}
