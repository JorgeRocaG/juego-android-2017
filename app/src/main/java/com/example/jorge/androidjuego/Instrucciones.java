package com.example.jorge.androidjuego;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Instrucciones extends AppCompatActivity {

    private Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ocultamos la barra de accion y ponemos la actividad en pantalla completa
        control = new Control(this);
        control.hideSystemUI();
        setContentView(R.layout.activity_instrucciones);

        //creamos la videoView y le asginamos el video que quermaos que se reproduzca
        VideoView videoView = (VideoView) findViewById(R.id.videoView2);
        Uri path = Uri.parse("android.resource://com.example.jorge.androidjuego/" + R.raw.instrucciones);

        /*
        creamos un mediaControler para tener los controles de reproduccion y se lo asignamos a la
        videoView antes creada. */
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(path);
        videoView.start();

        //hacemos que cuando se acabe el video vaya automaticamente al menu
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    //cuando se pulse el botón de salir nos lleva al menu principal
    public void onSalir(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
