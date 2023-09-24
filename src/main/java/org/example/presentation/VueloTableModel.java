package org.example.presentation;

import javax.swing.table.AbstractTableModel;

import org.example.logic.Ciudad;
import org.example.logic.Vuelo;

import java.util.ArrayList;
import java.util.List;

public class VueloTableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    private final List<Vuelo> vuelos;

    public VueloTableModel(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
        initColNames();
    }

    @Override
    public int getRowCount() {
        return vuelos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vuelo vuelo = vuelos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> vuelo.getNumero();
            case 1 -> vuelo.getCiudadOrigen().getNombre();
            case 2 -> vuelo.getHoraSalida();
            case 3 -> vuelo.getCiudadDestino().getNombre();
            case 4 -> vuelo.getHoraLlegada();
            case 5 -> calcularDuracionCiudades(vuelo.getCiudadOrigen(), vuelo.getCiudadDestino());
            default -> null;
        };
    }
    public static int calcularDuracionCiudades(Ciudad ciudadOrigen, Ciudad ciudadDestino) {
        int gmtOrigen = ciudadOrigen.getGmt();
        int gmtDestino = ciudadDestino.getGmt();

        return gmtDestino - gmtOrigen;
    }
    public String[] columnNames = new String[6];
    private void initColNames() {
        columnNames[0] = "Numero";
        columnNames[1] = "Origen";
        columnNames[2] = "Salida";
        columnNames[3] = "Destino";
        columnNames[4] = "Llegada";
        columnNames[5] = "Duracion";
    }
}
