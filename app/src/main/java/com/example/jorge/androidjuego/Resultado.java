package com.example.jorge.androidjuego;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Resultado extends AppCompatActivity {

    private TextView tvPuntuacion;
    private int puntuacion;
    private DBManager db;
    private Context context;
    private EditText tdNombre;
    private Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        control = new Control(this);
        control.hideSystemUI(); //llamamos al metodo que oculta la barra de actividad
        setContentView(R.layout.activity_resultado);

        tvPuntuacion = (TextView) findViewById(R.id.tvPuntuacion);
        tdNombre = (EditText) findViewById(R.id.etNombre);
        context = getApplicationContext();

        //recogemos la puntuacion del intent
        Bundle extras = getIntent().getExtras();
        puntuacion = extras.getInt("puntuacion");

        tvPuntuacion.setText("Tu puntuacion es: " + puntuacion);

        db = new DBManager(context);
    }

    public void onMainMenu(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onGuardarPuntuacion(View v) {

        //comprobamos si el nombre que ha introducido es demasiado largo o demasiado corto
        if(tdNombre.getText().toString().length() == 0)
            Toast.makeText(getApplicationContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
        else if (tdNombre.getText().toString().length() > 8)
            Toast.makeText(getApplicationContext(), "Nombre demasiado largo", Toast.LENGTH_SHORT).show();

        else {
            //calculamos el tamaño del nombre y rellenamos con blancos hasta tener 8 caracteres
            String nombre = tdNombre.getText().toString();
            int num;
            num = 8 - tdNombre.getText().toString().length();
            for(int i = 0; i < num; i++)
                nombre += " ";
            db.insertarPuntuacion(context, nombre, puntuacion); //insertamos la puntuacion
            Intent i = new Intent(this, MainActivity.class); //nos vamos al menu
            startActivity(i);
        }
    }
}


