package org.example.data;

import jakarta.xml.bind.annotation.*;
import org.example.logic.Ciudad;
import org.example.logic.Vuelo;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    @XmlElementWrapper(name = "ciudades")
    @XmlElement(name = "ciudad")
    private List<Ciudad> ciudades;

    @XmlElementWrapper(name = "vuelos")
    @XmlElement(name = "vuelo")
    private List<Vuelo> vuelos;

    public Data() {
        ciudades = new ArrayList<>();
        vuelos = new ArrayList<>();
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }
}
