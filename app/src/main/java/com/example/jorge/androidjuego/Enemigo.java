package com.example.jorge.androidjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemigo {

    private Bitmap bitmap;
    private int x, y;
    private int velocidad = 0;
    private int yMax;
    private int yMin;
    private int xMax;
    private int xMin;
    private Rect colision;
    private boolean techo = false;

    public Enemigo(Context context, int pantallaX, int pantallaY) {

        //cargamos al bitmap el sprite
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.alien);

        //marcamos las dimesiones de la pantalla en las siguientes varibales
        xMax = pantallaX;
        yMax = pantallaY;
        xMin = 0;
        yMin = 0;

        //generamos una velocidad aleatoria para el enemigo
        Random generador = new Random();
        velocidad = generador.nextInt(6) + 10;

        //marcamos las coordenadas conde va a aparecer el enemigo
        x = pantallaX;
        y = generador.nextInt(yMax) - bitmap.getHeight();

        //creamos la hitbox
        colision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void actualizar() {

        //hacemos que se mueva "velocidad" pixeles cada vez que llamemeos a este metodo
        x -= velocidad;

        //si se genera fuera de los maximos de la pantalla lo generamos dentro
        if (x < xMin - bitmap.getWidth()) {
            Random generador = new Random();
            velocidad = generador.nextInt(10) + 10;
            x = xMax;
            y = generador.nextInt(yMax) - bitmap.getHeight();
        }

        //el enemigo va rebotando cuando toca el techo de la pantalla
        if (y > yMax) {
            techo = true;
        }

        if (y < yMin)
            techo = false;

        if (!techo)
            y += velocidad;

        if (techo)
            y -= velocidad;


        //asiganamos las coordenadas de la hitbox actualmente
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

