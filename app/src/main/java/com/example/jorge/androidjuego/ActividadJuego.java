package com.example.jorge.androidjuego;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class ActividadJuego extends AppCompatActivity {

    private Juego j;
    private MediaPlayer mainMusica;
    private Control control;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        j =  new Juego(this);
        control = new Control(this);
        control.hideSystemUI(); //llamamos al método de la clase control para que se oculte la barra de acción
        setContentView(j); //hacemos que ejecute la clase juego
        mainMusica = MediaPlayer.create(this, R.raw.juego);
        mainMusica.start(); //hacemos que se reproduzca la canción mientras está la pantalla de juego abierta
    }


    //estos dos métodos para la música cuando se sale de la actividad
    @Override
    public void onPause() {
        super.onPause();
        mainMusica.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainMusica.stop();
    }
}
