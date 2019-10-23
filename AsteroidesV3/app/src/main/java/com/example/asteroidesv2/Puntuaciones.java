package com.example.asteroidesv2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class Puntuaciones extends AppCompatActivity {


    private RecyclerView rvPuntuaciones;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptador mAdapter;
    @Override protected void onCreate
            (Bundle savedInstanceState) { super.onCreate(savedInstanceState);
            setContentView(R.layout.puntuaciones);
            rvPuntuaciones = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new MiAdaptador(this, ScrollingActivity.almacen.listaPuntuaciones(10));
            rvPuntuaciones.setAdapter(mAdapter); layoutManager = new LinearLayoutManager(this);
            rvPuntuaciones.setLayoutManager(layoutManager);
    }





}
