package com.example.asteroidesv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.Vector;

public class VistaJuego extends View
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


    public VistaJuego(Context context, AttributeSet attrs) {

        super(context, attrs);

       // String tipograficos = ObtenerPreferenciaGraficos(context);
        Drawable drawableNave, drawableAsteroide, drawableMisil;

        //if(tipograficos=="1"){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        if(pref.getString("graficos","1").equals("0")){


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




                ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(pathAsteroide,1,1));
                dAsteroide.getPaint().setColor(Color.WHITE);
                dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
                dAsteroide.setIntrinsicHeight(50);
                dAsteroide.setIntrinsicWidth(50);
                drawableAsteroide=dAsteroide;
                setBackgroundColor(Color.BLACK);

                Path pathnave = new Path();
                pathnave.moveTo((float )0,(float)0);
                pathnave.lineTo((float)1, (float)0.5);
                pathnave.lineTo((float)0, (float)1);
                pathnave.lineTo((float)0, (float)0);

                ShapeDrawable dnave = new ShapeDrawable(new PathShape(pathnave,1,1));

                dnave.getPaint().setColor(Color.WHITE);
                dnave.getPaint().setStyle(Paint.Style.STROKE);
                dnave.setIntrinsicHeight(50);
                dnave.setIntrinsicWidth(50);
                drawableNave=dnave;

                setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }else{

        drawableAsteroide = context.getResources().getDrawable( R.drawable.asteroide1);
        drawableNave= context.getResources().getDrawable(R.drawable.nave);
            setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }

        nave= new Grafico(this,drawableNave);
        asteroides = new Vector<Grafico>();

        nave.setAngulo(90);

        for (int i = 0; i < numAsteroides; i++){
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
       // }
    }

    public String ObtenerPreferenciaGraficos(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String s =  pref.getString("graficos","1");
        //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        return s;

    };


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
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Grafico asteroide: asteroides) {
            asteroide.dibujaGrafico(canvas);
            nave.dibujaGrafico(canvas);
        }
    }

}




