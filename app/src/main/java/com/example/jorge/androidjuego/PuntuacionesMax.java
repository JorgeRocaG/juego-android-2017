package com.example.jorge.androidjuego;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PuntuacionesMax extends AppCompatActivity {

    private ListView listaPuntuaciones;
    private DBManager db;
    private Context context;
    private Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        control = new Control(this);
        control.hideSystemUI();
        setContentView(R.layout.activity_puntuaciones_max);

        context = getApplicationContext();

        listaPuntuaciones = (ListView) findViewById(R.id.listaPuntuaciones);
        db = new DBManager(context);

        listar();
    }

    public void listar() {
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();



        db.listar(context, lista, listaPuntuaciones);

        adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_fila, lista);
        listaPuntuaciones.setAdapter(adaptador);
    }

    public void onMenu(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onBorrar(View v) {
        db.borrarPuntuaciones(context);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Se han borrado las puntuaciones", Toast.LENGTH_SHORT).show();
    }
}

