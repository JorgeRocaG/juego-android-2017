package com.example.jorge.androidjuego;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        control = new Control(this);
        control.hideSystemUI(); //utilizamos este metodo para ocultar la barra de accion
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.menu); //iniciamos la musica del menu
        mp.start();
    }

    //paramos la musica si se cierra o pausa la actividad
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp.stop();
    }

    //onClick listeners para ir a otras actividades del juego

    public void onJugar(View v) {
        Intent i = new Intent(this, ActividadJuego.class);
        startActivity(i);
    }

    public void onResultados(View v) {
        Intent i = new Intent(this, PuntuacionesMax.class);
        startActivity(i);
    }

    public void onInfo(View v) {
        Intent i = new Intent(this, Instrucciones.class);
        startActivity(i);
    }



}
