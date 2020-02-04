package com.example.idealhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.sax.Element;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listado extends AppCompatActivity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1 ;
    int valorminimobarra=0;
    TextView textobarra,txtmin,txtmax;
    String operation="";
    Button BtnSearch;
    public List<ElementList> ListaViviendas= null;
    private RecyclerView rvViviendas;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptador mAdapter;
    int min;
    int max;
    private Context context;
    String token;
    ElementList elemento = new ElementList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
       // this.context=this;
        min=0;
        max=500000000;
        txtmin = findViewById(R.id.txtmin);
        txtmax = findViewById(R.id.txtmax);
        BtnSearch =findViewById(R.id.BtnSearch);
        rvViviendas = findViewById(R.id.recycler_view);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                operation= null;
            } else {
                operation= extras.getString("Action");
            }
        } else {
            operation= (String) savedInstanceState.getSerializable("Action");
        }
        ListaViviendas= MainActivity.db.obtenerAnuncios(Integer.parseInt(operation),min,max);
        if(operation.equals("0")){
        ListaViviendas.add(elemento);
        //ListaViviendas.add( ElementList().ElementList2())

        }

        mAdapter = new MiAdaptador(this, ListaViviendas);
        rvViviendas.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        rvViviendas.setLayoutManager(layoutManager);

/*
        AccountManager am = AccountManager.get(this);
        //am.addAccount()
                Bundle options = new Bundle();


        am.getAuthToken(
                myAccount_,                     // Account retrieved using getAccountsByType()
                "Manage your tasks",            // Auth scope
                options,                        // Authenticator-specific options
                this,                           // Your activity
                new OnTokenAcquired(),          // Callback called when a token is successfully acquired
                new Handler(new OnError()));    // Callback called if an error occurs
*/
        BtnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BuscarAnunciosIdealista();
            }

            private void BuscarAnunciosIdealista() {
                if(!txtmin.getText().toString().equals("")){
                    min = Integer.parseInt(txtmin.getText().toString());
                }else{
                    min=0;
                }

                if(!txtmax.getText().toString().equals("")){
                    max = Integer.parseInt(txtmax.getText().toString());
                }else{
                   max= 500000000;
                }



                ListaViviendas= MainActivity.db.obtenerAnuncios(Integer.parseInt(operation),min,max);
                if(elemento.getPrice()>= min && elemento.getPrice()<=max && operation.equals("0")){
                    ListaViviendas.add(new ElementList());
                }
                mAdapter = new MiAdaptador(Listado.this
                        , ListaViviendas);
                rvViviendas.setAdapter(mAdapter);
                layoutManager = new LinearLayoutManager(Listado.this);
                rvViviendas.setLayoutManager(layoutManager);
            }
        });




        textobarra= findViewById(R.id.TextoBarra);
        SeekBar mSeekbar = (SeekBar)findViewById(R.id.barraDistancia);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = valorminimobarra;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = valorminimobarra+ progress;
                textobarra.setText(String.valueOf(progressChanged)+" Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        checkAndRequestPermissions();


    }


    private  boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            // Get the result of the operation from the AccountManagerFuture.
            Bundle bundle = null;
            try {
                bundle = result.getResult();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            }

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

        }
    }

}




