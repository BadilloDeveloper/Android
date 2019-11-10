package com.example.asteroidesv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VistaJuego extends View implements SensorEventListener
{
    private Vector<Grafico> asteroides;
    // Vector con los Asteroides
    private int numAsteroides = 5;// Número inicial de asteroides
    private int numFragmentos = 3; // Fragmentos en que se divide
    private Grafico nave;
    private int giroNave ; // Incremento de dirección
    private float aceleracionNave ; // aumento de velocidad
    // Incremento estándar de giro y aceleración
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;
    public MiThread thread = new MiThread();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    private static int MAX_VELOCIDAD_NAVE=10;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;
//MISIL
    private ArrayList<Grafico> misiles;
    //private Grafico misil;
    private static int PASO_VELOCIDAD_MISIL = 12;
    //private boolean misilActivo = false;
    //private int tiempoMisil;
    Drawable drawableMisil;
    private ArrayList<Integer> tiempoMisiles;
    long ahora;
    //Sensores
    private SensorManager mSensorManager;
    private Sensor orientationSensor;
    private Sensor acelerometer;

    //SOONIDO MISIL
    // //// MULTIMEDIA //////
    SoundPool soundPool;
    int idDisparo, idExplosion;

    //////////////////////CONSTRUCTOR///////////////////////////////////////
    public VistaJuego(Context context, AttributeSet attrs) {

        super(context, attrs);


        Drawable drawableNave, drawableAsteroide;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        if(pref.getString("graficos","1").equals("0")){
            ///////////GRAFICOS VECTORIALES/////////////
                setBackgroundColor(Color.BLACK);
                Path pathAsteroide = CrearAsteroidesVector();
                Path pathnave = CrearNaveVector();

                ShapeDrawable dAsteroide = CrearShapeAsteroides(pathAsteroide);
                drawableAsteroide=dAsteroide;

                ShapeDrawable dnave =CrearShapeNave(pathnave);
                drawableNave=dnave;

                ShapeDrawable dMisil = CrearShapeMisil();
                drawableMisil = dMisil;
                //aceleracion por software
                setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }else{
            ///////////GRAFICOS BITMAP/////////////
            drawableAsteroide = context.getResources().getDrawable( R.drawable.asteroide1);
            drawableNave= context.getResources().getDrawable(R.drawable.nave);
            drawableMisil=context.getResources().getDrawable(R.drawable.misil1);

            setLayerType(View.LAYER_TYPE_HARDWARE,null);//aceleracion por hardware
        }

        nave= new Grafico(this,drawableNave);
        //misil=new Grafico(this,drawableMisil);
        asteroides = new Vector<Grafico>();
        nave.setAngulo(90);
        misiles=new ArrayList<Grafico>();
        tiempoMisiles= new ArrayList<Integer>();
        for (int i = 0; i < numAsteroides; i++){
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
        //SENSOR
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors = mSensorManager.getSensorList(
                Sensor.TYPE_ORIENTATION);
        if (!listSensors.isEmpty()) {
             orientationSensor = listSensors.get(0);
            acelerometer =listSensors.get(1);
            mSensorManager.registerListener(this, orientationSensor,
                    SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this,acelerometer,SensorManager.SENSOR_ACCELEROMETER);
        }
        //SONIDOS
        soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
        idDisparo = soundPool.load(context, R.raw.disparo, 0);
        idExplosion = soundPool.load(context, R.raw.explosion, 0);

    }


    //SENSORES

    public void desactivarSensores (){
        mSensorManager.unregisterListener(this);
    }
    public void activarSensores(){
        mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_ACCELEROMETER);
    }


    ////////////////LOGICA MOVIMIENTO Y ACCIONES//////////////////////

    @Override protected void onSizeChanged(int ancho, int alto, int ancho_anter, int alto_anter) {
        super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
        // Una vez que conocemos nuestro ancho y alto.

        nave.setCenX((int)ancho/2);
        nave.setCenY((int)alto/2);

        for (Grafico asteroide: asteroides) {


            do{
                asteroide.setCenX((int) (Math.random()*ancho));
                asteroide.setCenY((int) (Math.random()*alto));
            }while(asteroide.distancia(nave)< (ancho+alto)/5);


        }
        ultimoProceso = System.currentTimeMillis();
        thread.start();

    }


    @Override protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized(asteroides){
            for (Grafico asteroide: asteroides) {
                asteroide.dibujaGrafico(canvas);

            }
            nave.dibujaGrafico(canvas);
        }
        if (!misiles.isEmpty()) {

            for(int i=0; i <misiles.size();i++){
                misiles.get(i).dibujaGrafico(canvas);
            }

        }

    }

    public MiThread getThread() {
        return  this.thread;
    }

    public Sensor getOrientationSensor(){
        return orientationSensor;
    }

    public class MiThread extends Thread {
        private boolean corriendo;
        private boolean pausa;

        @Override public void run() {
            corriendo = true;
            while (corriendo) {
                actualizaFisica();
                synchronized (this) {
                    while (pausa) {
                        try {
                            wait();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }




        public synchronized void pausar() {
            pausa = true;
        }

        public synchronized void reanudar() {
            pausa = false;
            notify();
        }

        public synchronized void detener() {
            corriendo = false;
            if (pausa) reanudar();
        }



    }

    private void activaMisil() {
        soundPool.play(idDisparo, 1, 1, 1, 0, 1);
        Grafico misil= new Grafico(this,drawableMisil);

        misil.setCenX(nave.getCenX());
        misil.setCenY(nave.getCenY());
        misil.setAngulo(nave.getAngulo());
        misil.setIncX(Math.cos(Math.toRadians(nave.getAngulo())) * PASO_VELOCIDAD_MISIL);
        misil.setIncY(Math.sin(Math.toRadians(nave.getAngulo())) * PASO_VELOCIDAD_MISIL);
        misiles.add(misil);
        tiempoMisiles.add((int)ahora+60);

    }

    private void destruyeAsteroide(int i) {
        soundPool.play(idExplosion, 1, 1, 1, 0, 1);
        asteroides.remove(i);
        //misilActivo = false;
        misiles.remove(i);
        this.postInvalidate();
    }

    private synchronized void actualizaFisica() {

        ahora = System.currentTimeMillis();
        // No hagas nada si el período de proceso no se ha cumplido.
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
            return;
        }
        // Para una ejecución en tiempo real calculamos retardo
        double factorMov= (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora;
        // Actualizamos posición nave
        nave.setAngulo((int) (nave.getAngulo() + giroNave * factorMov));
        double nIncX = nave.getIncX() + aceleracionNave * Math.cos(Math.toRadians(nave.getAngulo())) * factorMov;
        double nIncY = nave.getIncY() + aceleracionNave * Math.sin(Math.toRadians(nave.getAngulo())) * factorMov;


        if (Math.hypot(nIncX,nIncY)
                <=MAX_VELOCIDAD_NAVE){
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }
        nave.incrementaPos(factorMov);

        synchronized (asteroides){
            for (Grafico asteroide : asteroides)
                asteroide.incrementaPos(factorMov);
        }




        if (misiles.size()>0) {
            for (int i=0;i<misiles.size();i++){

                misiles.get(i).incrementaPos(factorMov);
                tiempoMisiles.set(i, (tiempoMisiles.get(i)-(int)factorMov));

                if (tiempoMisiles.get(i) < 0) {
                    misiles.remove(i);
                } else {
                    for (int j = 0; j < asteroides.size(); j++)
                        if (misiles.get(i).verificaColision(asteroides.get(j))) {
                            destruyeAsteroide(j);
                            break;
                        }
                }
            }

        }

    }

    ////////////////////////////////////////////
    ///////////GRAFICOS VECTORIALES/////////////
    ////////////////////////////////////////////
    private ShapeDrawable CrearShapeNave(Path pathnave) {
        ShapeDrawable dnave= new ShapeDrawable(new PathShape(pathnave,1,1));
        dnave.getPaint().setColor(Color.WHITE);
        dnave.getPaint().setStyle(Paint.Style.STROKE);
        dnave.setIntrinsicHeight(50);
        dnave.setIntrinsicWidth(50);
        return dnave;
    }

    private ShapeDrawable CrearShapeAsteroides(Path pathAsteroide) {
        ShapeDrawable dAsteroide =new ShapeDrawable(new PathShape(pathAsteroide,1,1));
        dAsteroide.getPaint().setColor(Color.WHITE);
        dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
        dAsteroide.setIntrinsicHeight(50);
        dAsteroide.setIntrinsicWidth(50);
        return dAsteroide;
    }

    private ShapeDrawable CrearShapeMisil() {
        ShapeDrawable dMisil =new ShapeDrawable(new RectShape());
        dMisil.getPaint().setColor(Color.WHITE);
        dMisil.getPaint().setStyle(Paint.Style.STROKE);
        dMisil.setIntrinsicWidth(15);
        dMisil.setIntrinsicHeight(3);
        return dMisil;
    }

    private Path CrearNaveVector() {
        Path pathnave = new Path();
        pathnave.moveTo((float )0,(float)0);
        pathnave.lineTo((float)1, (float)0.5);
        pathnave.lineTo((float)0, (float)1);
        pathnave.lineTo((float)0, (float)0);
        return pathnave;
    }

    private Path CrearAsteroidesVector() {
        Path pathAsteroide = new Path();
        pathAsteroide.moveTo((float )0.3,(float)0.0);
        pathAsteroide.lineTo((float)0.6, (float)0.0);
        pathAsteroide.lineTo((float)0.6, (float)0.3);
        pathAsteroide.lineTo((float)0.8, (float)0.2);
        pathAsteroide.lineTo((float)1, (float)0.4);
        pathAsteroide.lineTo((float)0.8, (float)0.6);
        pathAsteroide.lineTo((float)0.9, (float)0.9);
        pathAsteroide.lineTo((float)0.8, (float)1);
        pathAsteroide.lineTo((float)0.4, (float)1);
        pathAsteroide.lineTo((float)0.0, (float)0.6);
        pathAsteroide.lineTo((float)0.0, (float)0.2);
        pathAsteroide.lineTo((float)0.3, (float)0);
        return pathAsteroide;
    }




    /////////////////////////////////////////////////////////////////
//////////////////////////teclado///////////////////////////////
/////////////////////////////////////////////////////////////////
    @Override
    public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(pref.getString("Movimiento","1").equals("0")){
        super.onKeyDown(codigoTecla, evento);
        // Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = +PASO_ACELERACION_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                giroNave = -PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = +PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                activaMisil();
                break;
            default:
        // Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
        }else{
            return false;
        }
    }

    @Override public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(pref.getString("Movimiento","1").equals("0")){
        super.onKeyUp(codigoTecla, evento);
    // Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave=0 ;
                break;
            default:
            // Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
        }else{
            return false;
        }
    }
    /////////////////////////////////////////////////////////////////
//////////////////////////teclado///////////////////////////////
/////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////
//////////////////////////TACTIL///////////////////////////////
/////////////////////////////////////////////////////////////////
    private float mX=0, mY=0;
    private boolean disparo=false;
    @Override
    public boolean onTouchEvent (MotionEvent event) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(pref.getString("Movimiento","1").equals("1")){
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy<6 && dx>6){
                    giroNave = Math.round((x - mX) / 2);
                    disparo = false;
                } else if (dx<6 && dy>6){
                    aceleracionNave = Math.round((mY - y) / 25);
                    disparo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave = 0;
                aceleracionNave = 0;
                if (disparo){
                    activaMisil();
                }
                break;
        }
        mX=x; mY=y;
        return true;
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////
//////////////////////////TACTIL///////////////////////////////
/////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////
//////////////////////////SENSORES///////////////////////////////
/////////////////////////////////////////////////////////////////
    private boolean hayValorInicial = false;
    private float valorInicial;
    @Override
    public void onSensorChanged(SensorEvent event) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(pref.getString("Movimiento","1").equals("2")){
            int eType = event.sensor.getType();
            if (eType == Sensor.TYPE_ORIENTATION) {
                float valor = event.values[1];
                if (!hayValorInicial){
                    valorInicial = valor;
                    hayValorInicial = true;
                }
                giroNave=(int) (valor-valorInicial)/3 ;
            }
            /*
            else if(eType== Sensor.TYPE_ACCELEROMETER){

                        float mSensorX = event.values[0];
                        float mSensorY = event.values[1];

                       // float angulomovil = (float) Math.toDegrees(Math.atan2(mSensorX, mSensorY));

                        float modulo = mSensorX % mSensorY;
                        aceleracionNave = modulo;

            }*/


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {



    }
    /////////////////////////////////////////////////////////////////
//////////////////////////SENSORES///////////////////////////////
/////////////////////////////////////////////////////////////////






}




