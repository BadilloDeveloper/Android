package com.example.asteroidesv2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

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
        Drawable drawableNave, drawableAsteroide, drawableMisil;
        drawableAsteroide = context.getResources().getDrawable( R.drawable.asteroide1);
        drawableNave= context.getResources().getDrawable(R.drawable.nave);
        asteroides = new Vector<Grafico>();
        nave= new Grafico(this,drawableNave);

        for (int i = 0; i < numAsteroides; i++){
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
    }


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




