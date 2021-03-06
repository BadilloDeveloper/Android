package com.example.asteroidesv2;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        RunAnimation();




    }


    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.myanimacion);
        a.reset();
        Button tv = (Button) findViewById(R.id.btnplay);
        tv.clearAnimation();
        tv.startAnimation(a);
    }


    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.btnplay:
                Intent i = new Intent(v.getContext(),Juego.class);
                startActivity(i);
                break;
            case R.id.btnabout:
                Intent ia = new Intent(v.getContext(),AcercaDe.class);
                startActivity(ia);
                break;
            case R.id.btnscore:
                Intent ib = new Intent(v.getContext(),Puntuaciones.class);
                startActivity(ib);
                break;
            case R.id.btnconfiguracion:
                Intent ic = new Intent(v.getContext(),Preferencias.class);
                startActivity(ic);
                break;
        }
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

        if(id==R.id.acercaDe){
            Intent i = new Intent (this,AcercaDe.class);
            startActivity(i);

        }

        if(id == R.id.action_settings){
            Intent i = new Intent(this,Preferencias.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}
