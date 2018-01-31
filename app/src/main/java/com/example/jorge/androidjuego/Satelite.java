package com.example.jorge.androidjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Satelite {

    private Bitmap bitmap;
    private int x, y;
    private int velocidad = 0;
    private int yMax;
    private int yMin;
    private int xMax;
    private int xMin;
    private Rect colision;

    public Satelite(Context context, int pantallaX, int pantallaY) {

        //le asignamos el sprite al bitmap del satelite
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sat);

        //ajustmaos las dimensiones de la pantalla
        xMax = pantallaX;
        yMax = pantallaY;
        xMin = 0;
        yMin = 0;

        //veneramos la velocidad aleatoria que tendra
        Random generador = new Random();
        velocidad = generador.nextInt(6) + 10;

        //utilizando el mismo generador fijamos las coordenadas donde aparecerá
        //y será aleatoria
        x = pantallaX;
        y = generador.nextInt(yMax) - bitmap.getHeight();

        //le asignamos la hitbox al bitmap
        colision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void actualizar() {
        //hacemos que se mueva de derecha a izquierda por la pantalla
        x -= velocidad;

        //si se genera fuera de los limites hacemos que se cree de nuevo
        if (x < xMin - bitmap.getWidth()) {
            Random generador = new Random();
            velocidad = generador.nextInt(10) + 10;
            x = xMax;
            y = generador.nextInt(yMax) - bitmap.getHeight();
        }

        //actualizamos la posicion del hitbox
        colision.left = x;
        colision.top = y;
        colision.right = x + bitmap.getWidth();
        colision.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getColision() {
        return colision;
    }

    public void setX(int x) {
        this.x = x;
    }

}
