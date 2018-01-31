package com.example.jorge.androidjuego;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.List;

public class DBManager {

    SQLiteDatabase db;

    //creamos la base de datos y la tabla para las puntuaciones
    public DBManager(Context context) {
        db = context.openOrCreateDatabase("Marcadores", context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS puntuaciones (nombre VARCHAR, puntuacion INTEGER)");
    }

    //metodo para insertar puntuaciones nuevas
    public void insertarPuntuacion(Context context, String nombre, int puntos) {
        db = context.openOrCreateDatabase("Marcadores", context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO puntuaciones VALUES ('" + nombre + "', '" + puntos + "')");
        db.close();
    }

    //metodo que lista cargan las puntuaciones en una lista
    public void listar(Context context, List<String> lista, ListView listaPuntuaciones) {

        db = context.openOrCreateDatabase("Marcadores", context.MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT DISTINCT * FROM puntuaciones ORDER BY puntuacion DESC", null);

        if (c.getCount() == 0) {
            lista.add("No hay puntuaciones, juega una partida");
        } else {
            while (c.moveToNext())
                lista.add((c.getString(0) + "\t\t\t\t\t" + c.getString(1)));
        }

        db.close();
    }

    //metodo para borrar todas las puntuaciones
    public void borrarPuntuaciones(Context context) {
        db = context.openOrCreateDatabase("Marcadores", context.MODE_PRIVATE, null);
        db.execSQL("DELETE FROM puntuaciones");
        db.close();
    }

}
