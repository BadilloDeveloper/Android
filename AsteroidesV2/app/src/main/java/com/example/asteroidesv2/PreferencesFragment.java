package com.example.asteroidesv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferencesFragment extends PreferenceFragment {


        @Override public void onCreate(Bundle savedInstanceState)
        { super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences); }

}
