package com.example.asteroids_badilloj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Tab4 extends Fragment {
    String vacio="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v= inflater.inflate(R.layout.activity_tab4,container,false);

        Button btn = v.findViewById(R.id.btnconvert);
        final  TextView resultado = v.findViewById(R.id.Resultado);
        final EditText euros = v.findViewById(R.id.Euros);
        final EditText pesetas = v.findViewById(R.id.Pesetas);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double resultadonumero =0 ;
                String pesetastring= pesetas.getText().toString();


                String eurosstring =euros.getText().toString();

                if(pesetastring.equals(vacio)){
                    double n1 = Double.parseDouble(euros.getText().toString());
                    pesetas.setText("");
                    resultadonumero = n1 * 166.386;

                    resultado.setText("Resultado :"+ resultadonumero + " Pesetas");


                }
                if(eurosstring.equals(vacio)){

                    double n2 = Double.parseDouble(pesetas.getText().toString());
                    euros.setText("");
                    resultadonumero =  n2* 0.006;
                    resultado.setText("Resultado :"+ resultadonumero + " Euros");
                }
            }
        });

        return v;
   }
    
}
