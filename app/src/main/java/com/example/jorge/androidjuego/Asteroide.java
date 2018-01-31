package com.example.jorge.androidjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Asteroide {

    private Bitmap bitmap;
    private int x, y;
    private int velocidad = 0;
    private int yMax;
    private int yMin;
    private int xMax;
    private int xMin;
    private Rect colision;

    public Asteroide(Context context, int pantallaX, int pantallaY) {

        //asignamos el sprite que queramos al bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid0);

        //guardamos el tamaño de la pantalla en las siguientes variables
        xMax = pantallaX;
        yMax = pantallaY;
        xMin = 0;
        yMin = 0;

        //se genera la velocidad aleatoria a la que va a moverse
        Random generador = new Random();
        velocidad = generador.nextInt(6) + 10;

        //marcamos las coordenadas donde va a aparecer
        x = pantallaX;
        y = generador.nextInt(yMax) - bitmap.getHeight();

        //le asignamos una hitbox al bitmap
        colision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    public void actualizar() {
        //cada vez que se llama a este método el asteriode se mueve
        x -= velocidad;

        //si se genera más arriba del límite de la pantalla lo generamos abajo
        if (x < xMin - bitmap.getWidth()) {
            Random generador = new Random();
            velocidad = generador.nextInt(10) + 10;
            x = xMax;
            y = generador.nextInt(yMax) - bitmap.getHeight();
        }

        //asiganmos las coordenadas de la hitbox en ese momento
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

}
