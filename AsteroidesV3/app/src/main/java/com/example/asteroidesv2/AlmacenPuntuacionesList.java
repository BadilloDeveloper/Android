package com.example.asteroidesv2;

import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesList implements AlmacenPuntuaciones {

    private List<String> puntuaciones;


    public AlmacenPuntuacionesList() {
        puntuaciones = new ArrayList<String>();
        puntuaciones.add("123000 Javier Badillo");
        puntuaciones.add("111000 guada");
    }
    @Override public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        puntuaciones.add(0, puntos + " " + nombre);
    }
    @Override
    public List<String> listaPuntuaciones(int cantidad) {
        return puntuaciones;
    }
}
