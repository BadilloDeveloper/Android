package com.example.idealhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.sax.Element;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NewAd extends AppCompatActivity {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    static final int Insert_AD = 0;

    EditText txtPrecio,txtMetros, txtTelefono, txtDescripcion,TipoOperacion,txtName;
    Spinner spinner ;
    LocationManager locManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);
        Button BtnPublicar = findViewById(R.id.BtnPublicar);
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        txtPrecio = findViewById(R.id.txtprize);
        txtMetros = findViewById(R.id.txtmeters);
        txtTelefono =findViewById(R.id.txttelefon);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtName = findViewById(R.id.txtNombre);
        spinner = (Spinner) findViewById(R.id.TipoAnuncio);
        String[] data= {"Sell","Rent","Share","Find roommate"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewAd.this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        BtnPublicar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Addpublicacion();
            }

        });

        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }

    }
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    private void Addpublicacion(){

        boolean escorrecto=true;
        String Error="";
        ElementList elemento =  new ElementList();
        if(!TextUtils.isEmpty(txtPrecio.getText())&& !txtPrecio.getText().equals("0")){
            elemento.setPrice(Long.parseLong(txtPrecio.getText().toString()));
        }else{
            escorrecto=false;
            Error+= " Enter Precio\n";
        }
        if(!TextUtils.isEmpty(txtMetros.getText()) && !txtMetros.getText().equals("0")){
            elemento.setSize(Long.parseLong(txtMetros.getText().toString()));
        }else{
            escorrecto=false;
            Error+= " Enter Meters\n";
        }
        if(!TextUtils.isEmpty(txtTelefono.getText())){
            elemento.setTelephone(txtTelefono.getText().toString());
        }else{
            escorrecto=false;
            Error+=" Enter phone\n";
        }
        if(!TextUtils.isEmpty(txtDescripcion.getText())){
            elemento.setDescription(txtDescripcion.getText().toString());
        }else{
            escorrecto=false;
            Error+=" Enter description\n";
        }
        if(!TextUtils.isEmpty(txtName.getText())){
            elemento.setName(txtName.getText().toString());
        }else{
            escorrecto=false;
            Error+=" Enter Name\n";
        }

        if(!spinner.getSelectedItem().toString().equals("")){
            String SpinString = spinner.getSelectedItem().toString();
            int seleccion=0;
            switch (SpinString){
                case "Sell":
                    seleccion=0;
                    break;
                case "Rent":
                    seleccion=1;
                    break;
                case "Share":
                    seleccion=2;
                    break;
                case "Find roommate":
                    seleccion=3;
                    break;
                default: break;

            }
            elemento.setTipoAnuncio(seleccion);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            elemento.setLatitude(loc.getLatitude());
            elemento.setLongitude(loc.getLongitude());
            return;
        }

        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        if(imageInByte.length>0)
            elemento.setImage(imageInByte);

        if(escorrecto) {
            Toast t = Toast.makeText(getApplicationContext(), "Add Successful", Toast.LENGTH_LONG);
            t.show();
            MainActivity.db.insertarAnuncio(elemento);
            setResult(Insert_AD);
            finish();
        }else{
            Toast t = Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_LONG);
            t.show();
        }
    }





    //obtener imagen de galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }
}
