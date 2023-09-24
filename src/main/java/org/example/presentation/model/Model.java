package org.example.presentation.model;

import org.example.logic.Ciudad;
import org.example.logic.Vuelo;

import java.util.Observable;
import java.util.List;
import java.util.Observer;

public class Model extends Observable {
    private List<Vuelo> vuelos;

    public Model() {
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
        setChanged();
        notifyObservers(vuelos);
    }

    // Otras funciones para administrar datos en el modelo

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public Ciudad getCiudadById(String id) {
        // Implementa la lógica para obtener una ciudad por su ID
        return null;
    }

    public Vuelo getVueloByNumero(String numero) {
        // Implementa la lógica para obtener un vuelo por su número
        return null;
    }
}
