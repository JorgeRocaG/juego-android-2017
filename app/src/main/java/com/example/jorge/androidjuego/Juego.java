package com.example.jorge.androidjuego;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class Juego extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    public  BucleJuego bucle;
    private Activity actividad;

    private Jugador jugador;
    private Enemigo enemigo;
    private Satelite satelite;
    private Asteroide asteroide;
    private Asteroide asteroide1;
    private Asteroide asteroide2;

    private Paint paint;
    private Canvas canvas;

    public int altoPantalla;
    public int anchoPantalla;

    private Bitmap fondo;

    private ArrayList<Recogida> lista_recogida = new ArrayList<Recogida>();
    public Bitmap recogida;

    //creamos el array de las imagenes que usaremos para la animacion
    private static final int IMAGENES_FONDO = 3;
    Bitmap imagenes[] = new Bitmap[IMAGENES_FONDO];
    int recursos_imagenes[]= {
            R.drawable.bg0,
            R.drawable.bg1,
            R.drawable.bg2,
    };


    private int puntos = 0;



    private int yImgActual  = 0, yImgSiguiente = 0;
    private int img_actual = 0, img_siguiente = 1;

    private MediaPlayer sonidoRecogida;

    public Juego(Activity context) {
        super(context);
        actividad = context;
        holder = getHolder();
        holder.addCallback(this);

        calcularTamanoPantalla();

        yImgActual=-1;
        yImgSiguiente=-altoPantalla-1;

        cargarBackground();
        paint = new Paint();

        //Creamos un mediaplayer con la pista 'punto'
        sonidoRecogida = MediaPlayer.create(actividad, R.raw.punto);
        sonidoRecogida.setVolume(0.5f, 0.5f);

        //iniciamos las clases creadas a sus constructores
        jugador = new Jugador(context, anchoPantalla, altoPantalla);
        satelite = new Satelite(context, anchoPantalla, altoPantalla);
        enemigo = new Enemigo(context, anchoPantalla, altoPantalla);
        asteroide = new Asteroide(context, anchoPantalla, altoPantalla);
        asteroide1 = new Asteroide(context, anchoPantalla, altoPantalla);
        asteroide2 = new Asteroide(context, anchoPantalla, altoPantalla);

        //le asignamos un sprite al bitmap que usaremos para la animacion creada cuando
        //recojamos un satelite
        recogida = BitmapFactory.decodeResource(getResources(), R.drawable.recogida);

    }

    //metodo que nos calcula la dimension de la pantalla en pixeles
    private void calcularTamanoPantalla() {
        Display display = actividad.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        anchoPantalla = size.x;
        altoPantalla = size.y;
    }

    //metodo que se encarga de dibujar en el canvas
    public void renderizar() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0)); //dibujamos un color de fondo
            //Dibujamos el fondo con la imagen que deberia de estar ahora reproduciendose y
            //con la que viene a continuación
            canvas.drawBitmap(imagenes[img_actual],0,yImgActual,null);
            canvas.drawBitmap(imagenes[img_siguiente],0,yImgSiguiente,null);
            //dibujamos los sprites de cada elemento del juego
            canvas.drawBitmap(jugador.getBitmap(), jugador.getX(), jugador.getY(), paint);
            canvas.drawBitmap(asteroide.getBitmap(), asteroide.getX(), asteroide.getY(), paint);
            canvas.drawBitmap(asteroide1.getBitmap(), asteroide1.getX(), asteroide1.getY(), paint);
            canvas.drawBitmap(asteroide2.getBitmap(), asteroide2.getX(), asteroide2.getY(), paint);
            canvas.drawBitmap(enemigo.getBitmap(), enemigo.getX(), enemigo.getY(), paint);
            canvas.drawBitmap(satelite.getBitmap(), satelite.getX(), satelite.getY(), paint);

            //dibuja la animacion de recogida
            for(Recogida rec: lista_recogida){
                rec.renderizar(canvas, paint);
            }

            //dibuja el testo donde se mostrará la puntuación actual
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.argb(255,255,255,255));
            paint.setTextSize(50);
            canvas.drawText(String.valueOf("SATÉLITES RECOGIDOS: " + puntos),200, 100,paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void actualizar() {

        actualiza_fondo();

        //comprobamos las colisiones con el rect del jugador con el satelite
        if(Rect.intersects(jugador.getColision(), satelite.getColision())) {
            satelite.setX(-100); //hacemos que el satelite desaparezca
            puntos ++; //Sumamos un punto
            jugador.setBitmap(jugador.agrandar()); //llamamos al metodo que agranda el sprite
            sonidoRecogida.start(); //hacemos el sonido de recogida
            //ejecutamos la animacion de recogida en las coordenadas donde ha colisionado
            lista_recogida.add(new Recogida(this, jugador.getX(), jugador.getY()));
        }

        //comprobamos las colisiones con el rect del jugador con el asteriode
        if(Rect.intersects(jugador.getColision(), asteroide.getColision())) {
            bucle.fin(); //cerramos el bucle
            intent(); //llamamos al metodo que nos lleva a la ventana del resultado
        }

        if(Rect.intersects(jugador.getColision(), asteroide1.getColision())) {
            bucle.fin();
            intent();
        }

        if(Rect.intersects(jugador.getColision(), asteroide2.getColision())) {
            bucle.fin();
            intent();
        }

        //igual que con el asteroide, comprobamos con el enemigo
        if(Rect.intersects(jugador.getColision(), enemigo.getColision())) {
            bucle.fin();
            intent();
        }

        //realizamos la animacion de recogida
        for(Iterator<Recogida> it_recogida = lista_recogida.iterator();
                it_recogida.hasNext();) {
            Recogida rec = it_recogida.next();
            rec.actualizarEstado();
            if(rec.terminado())
                it_recogida.remove(); //cuando acaba retiramos la animacion
        }

        //actualizamos los movimientos de cada bitmap
        jugador.actualizar();
        asteroide.actualizar();
        asteroide1.actualizar();
        asteroide2.actualizar();
        enemigo.actualizar();
        satelite.actualizar();
    }

    public void cargarBackground() {
        for(int i = 0; i<3; i++) {
            fondo = BitmapFactory.decodeResource(getResources(), recursos_imagenes[i]);
            if(imagenes[i] == null) //se rellena el fondo con los 3 fondos que queremos
                imagenes[i] = fondo.createScaledBitmap(fondo, anchoPantalla, altoPantalla, true);
            fondo.recycle();
        }

    }

    public void actualiza_fondo(){
        yImgActual++;
        yImgSiguiente++;
        if(yImgActual>altoPantalla){

            if(img_actual==IMAGENES_FONDO-1)
                img_actual=0;
            else
                img_actual++;

            if(img_siguiente==IMAGENES_FONDO-1)
                img_siguiente=0;
            else
                img_siguiente++;

            yImgActual=0;
            yImgSiguiente=-altoPantalla;
        }
    }

    //intent que lleva a la actividad de resultados y le pasa los puntos conseguidos
    public void intent() {
        Intent i = new Intent(actividad, Resultado.class);
        i.putExtra("puntuacion", puntos);
        actividad.startActivity(i);
    }


    //cuando se crea la interfaz comienza el bucle juego
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        getHolder().addCallback(this);

        bucle = new BucleJuego(getHolder(), this);

        setFocusable(true);

        bucle.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    //cada vez que se pulsa en la pantalla se manda
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP: //si no se pulsa la pantalla cae
                jugador.paraVolando();
                break;
            case MotionEvent.ACTION_DOWN: //si se pulsa vuela
                jugador.setVolando();
                break;
        }

        return true;
    }
}
