package com.example.asteroidesv2;

import android.app.Activity;
import android.os.Bundle;

public class Preferencias extends Activity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit(); }
}
