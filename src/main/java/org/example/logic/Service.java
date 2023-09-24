package org.example.logic;

import org.example.data.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service {
    private List<Ciudad> ciudades;
    private List<Vuelo> vuelos;

    private static Service theInstance;

    private Data data;

    private Service() {
        ciudades = new ArrayList<>();
        vuelos = new ArrayList<>();
        data = new Data();
        try {
            data = XmlPersister.instance().load();
        }catch (Exception ex){
            data = new Data();
        }

    }

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public void agregarVuelo(Vuelo vuelo) throws Exception {
        if (!existeVuelo(vuelo)) {
            data.getVuelos().add(vuelo);
            saveData();
        }
    }

    public List<Vuelo> buscarVuelosPorCiudad(String textoBusqueda) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();
        for (Vuelo vuelo : vuelos) {
            if (vuelo.getCiudadOrigen().getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                    vuelo.getCiudadDestino().getNombre().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                vuelosEncontrados.add(vuelo);
            }
        }
        return vuelosEncontrados;
    }

    public boolean actualizarVuelo(Vuelo vuelo) throws Exception {

        for (Vuelo vuelo1: data.getVuelos()) {
            if(Objects.equals(vuelo.getNumero(), vuelo1.getNumero())){
                data.getVuelos().remove(vuelo1);
                saveData();
                return true;
            }
        }
        return false;
    }

    public boolean existeVuelo(Vuelo vuelo) {
        return vuelos.contains(vuelo);
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }


    public void saveData() throws Exception {
        XmlPersister.instance().store(this.data);
    }

    public Data getData() {
        return data;
    }
}

