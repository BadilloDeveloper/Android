package com.example.asteroidesv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder>
{
        private LayoutInflater inflador;
        private List<String> lista;



        public MiAdaptador(Context context, List<String> lista)
        {
            this.lista = lista; inflador = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE); }



            public class ViewHolder extends RecyclerView.ViewHolder {
                public TextView titulo, subtitulo; public ImageView icon; ViewHolder(View itemView) { super(itemView);
                    titulo = (TextView)itemView.findViewById(R.id.titulo);
                    subtitulo = (TextView)itemView.findViewById(R.id.subtitulo);
                    icon = (ImageView)itemView.findViewById(R.id.icono); }
            }

            @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = inflador.inflate(R.layout.elementolista, parent, false);

                return new ViewHolder(v);
            }


            @Override public void onBindViewHolder(ViewHolder holder, int i)
            { holder.titulo.setText(lista.get(i)); switch (Math.round((float)Math.random()*3))
                { case 0: holder.icon.setImageResource(R.drawable.asteroide1); break;
                    case 1: holder.icon.setImageResource(R.drawable.asteroide2); break;
                    case 2: holder.icon.setImageResource(R.drawable.asteroide3); break;
                }



            }




            @Override public int getItemCount() {
            return lista.size();
            }



        }

