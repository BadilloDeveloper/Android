package com.example.asteroids_badilloj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class Tab3 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


       View v= inflater.inflate(R.layout.activity_tab3,container,false);

      // Button btn = v.findViewById(R.id.imagebut);

       ImageButton btn = v.findViewById(R.id.imagebut);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri = Uri.parse("https://instagram.com/javibadillo"); // missing 'http://' will cause crashed
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);

           }
       });

      // btn.set
        return v;
   }
    
}
