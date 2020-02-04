package com.example.idealhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBuy, btnShare,btnRent,btnFind,btnnew,btnlogout;
    ImageView Imgusuario;
    static final int Insert_AD = 0;
    public static DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DataBaseHelper(this);
        btnBuy = findViewById(R.id.btnBuy);
        btnShare = findViewById(R.id.btnshare);
        btnRent = findViewById(R.id.btnrent);
        btnFind = findViewById(R.id.btnfind);
        btnnew = findViewById(R.id.btnnew);
        Imgusuario= findViewById(R.id.logouser);
        btnlogout= findViewById(R.id.action_logout);

        btnBuy.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnRent.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        btnnew.setOnClickListener(this);


        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if(usuario!=null){
            String nombre = usuario.getDisplayName();
            String correo = usuario.getEmail();
            String telefono = usuario.getPhoneNumber();
            Uri urlFoto = usuario.getPhotoUrl();
            new DownloadImageTask((ImageView) findViewById(R.id.logouser))
                    .execute(urlFoto.toString());
            String uid = usuario.getUid();
            String proveedores="";
            for (UserInfo proveedor: usuario.getProviderData()){
                proveedores += proveedor.getProviderId() +", "; }


        }


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnBuy:
                Intent i = new Intent(v.getContext(), Listado.class);
                String strName = "0";
                i.putExtra("Action", strName);
                startActivity(i);
                break;

            case R.id.btnrent:
                Intent in = new Intent(v.getContext(), Listado.class);
                String strName1 = "1";
                in.putExtra("Action", strName1);
                startActivity(in);
                break;

            case R.id.btnshare:
                Intent it = new Intent(v.getContext(), Listado.class);
                String strName2 = "2";
                it.putExtra("Action", strName2);
                startActivity(it);
                break;

            case R.id.btnfind:
                Intent ig = new Intent(v.getContext(), Listado.class);
                String strName3 = "3";
                ig.putExtra("Action", strName3);
                startActivity(ig);
                break;


            case R.id.btnnew:
                Intent ih = new Intent(v.getContext(), NewAd.class);
                startActivityForResult(ih,Insert_AD);
               // startActivity(ih);


            default:
                break;
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
               // openSearch();
                AuthUI.getInstance().signOut(this) .addOnCompleteListener(new OnCompleteListener<Void>() { @Override
                public void onComplete(@NonNull Task<Void> task) { Intent i = new Intent(MainActivity.this,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i);
                MainActivity.this.finish();
                } });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
