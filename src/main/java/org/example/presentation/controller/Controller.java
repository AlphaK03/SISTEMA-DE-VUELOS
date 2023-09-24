package org.example.presentation.controller;

import org.example.logic.Ciudad;
import org.example.logic.Service;
import org.example.logic.Vuelo;
import org.example.presentation.model.Model;
import org.example.presentation.view.View;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import java.util.List;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        model.init(Service.instance().getData().getVuelos());
        this.model = model;
        this.view = view;// Inicializa el servicio

        view.setController(this);
        view.setModel(model);
    }

    // Función para agregar un vuelo
    public boolean agregarVuelo(Vuelo vuelo) throws Exception {
        // Implementa la lógica para agregar un vuelo

        // Verifica si el vuelo ya existe
        if (!existeVuelo(vuelo)) {
            Service.instance().agregarVuelo(vuelo);
            model.setChanged();
            model.notifyObservers("Vuelo agregado correctamente.");
        } else {
            Service.instance().getData().getVuelos().remove(vuelo);
            model.setChanged();
            model.notifyObservers("El vuelo ya existe.");
        }
        return false;
    }

    // Función para buscar vuelos por ciudad de origen o destino
    public List<Vuelo> buscarVuelos(String textoBusqueda) {
        // Implementa la lógica para buscar vuelos
        List<Vuelo> vuelosEncontrados = Service.instance().buscarVuelosPorCiudad(textoBusqueda);
        model.setChanged();
        model.notifyObservers(vuelosEncontrados);
        return vuelosEncontrados;
    }

    // Función para editar un vuelo
 /*   public void editarVuelo(Vuelo vuelo) {
        // Implementa la lógica para editar un vuelo
        view.mostrarDatosEdicion(vuelo);
    }*/

    // Función para guardar la edición de un vuelo
    public void guardarEdicionVuelo(Vuelo vuelo) throws Exception {
        // Implementa la lógica para guardar la edición de un vuelo
        if (Service.instance().actualizarVuelo(vuelo)) {
            model.setChanged();
            model.notifyObservers("Vuelo actualizado correctamente.");
        } else {
            model.setChanged();
            model.notifyObservers("Error al actualizar el vuelo.");
        }
    }

    // Función para limpiar la pantalla
    public void limpiarPantalla() {
        // Implementa la lógica para limpiar la pantalla
        view.limpiarCampos();
    }

    // Función auxiliar para verificar si un vuelo ya existe
    private boolean existeVuelo(Vuelo vuelo) {
        // Implementa la lógica para verificar si el vuelo ya existe
        return Service.instance().existeVuelo(vuelo);
    }

    public void cargarDatosDesdeXML() {
    }

    public List<Vuelo> obtenerVuelos(){
        return Service.instance().getVuelos();
    }
}
