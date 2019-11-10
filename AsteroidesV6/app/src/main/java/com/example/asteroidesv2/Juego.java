package com.example.asteroidesv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Juego extends AppCompatActivity {

    int pos;
    private VistaJuego vistaJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_juego);
        vistaJuego = findViewById(R.id.VistaJuego);

    }


    @Override protected void onPause() {
        vistaJuego.getThread().pausar();
        vistaJuego.desactivarSensores();
        super.onPause();
    }
    @Override protected void onResume() {
        super.onResume();
        vistaJuego.getThread().reanudar();
        vistaJuego.activarSensores();
    }
    @Override protected void onDestroy() {
        vistaJuego.getThread().detener();
        super.onDestroy();
    }


}
