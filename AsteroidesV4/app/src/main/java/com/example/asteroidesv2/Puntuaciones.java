package com.example.asteroidesv2;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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


        final GestureDetector mGestureDetector = new GestureDetector(Puntuaciones.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        rvPuntuaciones.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);

                        Toast.makeText(Puntuaciones.this,"El elemento esta en la posición: "+ position ,Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });


    }









}
