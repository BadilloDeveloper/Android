package com.example.idealhouse;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder>
    {
        private LayoutInflater inflador;
        private List<ElementList> lista;
        private Context context;


        public MiAdaptador(Context context, List<ElementList> lista)
        {
            this.context=context;
            this.lista = lista;
            inflador = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE); }



        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView titulo, subtitulo,telef,metr,preciom;
            public ImageView icon;
            ViewHolder(View itemView) { super(itemView);
                titulo = itemView.findViewById(R.id.titulo);
                subtitulo = itemView.findViewById(R.id.subtitulo);
                icon = itemView.findViewById(R.id.icono);
                telef = itemView.findViewById(R.id.telefono);
                metr = itemView.findViewById(R.id.metros);
                preciom = itemView.findViewById(R.id.preciometro);
                }
        }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflador.inflate(R.layout.elemento_layout, parent, false);

            return new ViewHolder(v);
        }


        @Override public void onBindViewHolder(final ViewHolder holder, int i)
        {

        if(lista.get(i).getPropertyCode()!=null ){
            ElementList o = lista.get(i);
            holder.titulo.setText(lista.get(i).getPrice()+" €");
            String subtitulo = o.getAddress()+" " +o.getProvince()+ " "+o.getMunicipality();
            holder.subtitulo.setText(subtitulo);
            holder.metr.setText(String.valueOf(o.getSize())+" m");
            holder.preciom.setText(String.valueOf(o.getPriceByArea())+" €/m");


        }else{
                ElementList a = lista.get(i);
                holder.titulo.setText(a.getDescription());
                holder.subtitulo.setText(String.valueOf(a.getPrice())+" €");
                holder.preciom.setText(String.valueOf(a.getPrice()/a.getSize()) + " €/m");
                holder.telef.setText(a.getTelephone()+" - "+a.getName());
                holder.metr.setText(String.valueOf(a.getSize())+" m");


            }
            if(lista.get(i).getThumbnail()!=null) {
                Glide.with(this.context)
                        .load(lista.get(i).getThumbnail())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                holder.icon.setImageBitmap(resource);
                            }
                        });
        }else if(lista.get(i).getImage()!=null){


                    Bitmap bitmap = BitmapFactory.decodeByteArray(lista.get(i).getImage() , 0,lista.get(i).getImage().length);
                    holder.icon.setImageBitmap(bitmap);


            }



        }






        @Override public int getItemCount() {
            if(lista!=null){
                return lista.size();
            }else {return 0;}

        }



    }



