package com.example.asteroidesv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.prefs.Preferences;

public class ScrollingActivity extends AppCompatActivity {

    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Button btnAcercaDe =  findViewById(R.id.btnabout);
        Button btnPuntuacion = findViewById(R.id.btnscore);
        Button btnconfig= findViewById(R.id.btnconfiguracion);
        Button btnplay = findViewById(R.id.btnplay);

       // Animation animacion = AnimationUtils.loadAnimation(this , R.transition.tran);
        //btnplay.startAnimation(animacion);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),Juego.class);
                startActivity(i);
            }
        });


        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),AcercaDe.class);
                startActivity(i);
            }
        });


        btnPuntuacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), Puntuaciones.class);
                    startActivity(i);

            }
        });

        btnconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), Preferencias.class);
                startActivity(i);

            }
        });

       // AlmacenPuntuacionesList almacen2 = new AlmacenPuntuacionesList();
       // almacen=almacen2.listaPuntuaciones(10);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_settings){
            Intent i = new Intent(this,AcercaDe.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
