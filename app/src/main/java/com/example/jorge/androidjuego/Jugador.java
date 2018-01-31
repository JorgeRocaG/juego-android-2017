package com.example.jorge.androidjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.sax.RootElement;

public class Jugador {

    private Bitmap bitmap;
    private int x, y;
    private int velocidad = 0;
    private boolean volando;
    private final int GRAVEDAD = -12;
    private int yMax;
    private int yMin;
    private int xMax;
    private final int VEL_MIN = 1;
    private final int VEL_MAX = 30;
    private Rect colision;

    public Jugador(Context context, int pantallaX, int pantallaY) {
        //Creamos el jugador en la parte superios de la pantalla
        x = 100;
        y = 100;
        velocidad = 20;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bot);
        volando = false; //hacemos que al comenzar no vuele
        //marcamos lo máximo y lo minimo que se va a poder mover en el eje Y
        yMax = pantallaY - bitmap.getHeight();
        yMin = 0;

        //Creamos suhitbox
        colision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        xMax = pantallaX - bitmap.getWidth();
    }

    public void actualizar() {

        //hacemos que el jugado suba mas rapido y que la gravedad tire mas
        if (volando) {
            velocidad += 5;
        } else {
            velocidad -= 3;
        }

        if (velocidad > VEL_MAX) {
            velocidad = VEL_MAX;
        }

        if (velocidad < VEL_MIN) {
            velocidad = VEL_MIN;
        }

        //al final la velocidad total le añariremos la gravedad para que caiga si no vuela
        y -= velocidad + GRAVEDAD;

        //si tocamos el suelo o el techo no deja subir o bajar mas
        if (y < yMin) {
            y = yMin;
        }
        if (y > yMax) {
            y = yMax;
        }

        //actualizamos la posicion del hitbox
        colision.left = x;
        colision.top = y;
        colision.right = x + bitmap.getWidth();
        colision.bottom = y + bitmap.getHeight();
    }

    //este metodo hace que nuestro robot crezca 5px más que los que tenía previamente
    public Bitmap agrandar() {

        Bitmap nuevoBm = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() + 5, bitmap.getHeight() + 5, true);
        return nuevoBm; //devolvemos el bitmap reescalado
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVolando() {
        volando = true;
    }

    public void paraVolando() {
        volando = false;
    }

    public Rect getColision() {
        return colision;
    }
}
