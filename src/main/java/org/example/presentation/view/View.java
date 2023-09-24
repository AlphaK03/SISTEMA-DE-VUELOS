package org.example.presentation.view;

import org.example.logic.Ciudad;
import org.example.logic.Service;
import org.example.logic.Vuelo;
import org.example.presentation.VueloTableModel;
import org.example.presentation.controller.Controller;
import org.example.presentation.model.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

public class View implements Observer {

    private Controller controller;
    private Model model;
    private JPanel panel1;
    private JTextField textFieldNumero;
    private JComboBox<String> comboBoxOrigen;
    private JComboBox<Integer> comboBoxHoraSalida;
    private JComboBox<String> comboBoxDestino;
    private JComboBox<Integer> comboBoxHoraLlegada;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JTextField textFieldBuscarVuelos;
    private JButton buscarButton;
    private JTable listVuelos;
    private JPanel vueloPanel;

    public JPanel getPanel1() {
        return panel1;
    }

    public View() {
        initComponents();
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                comboBoxOrigen.setSelectedItem("San Jose");
                comboBoxDestino.setSelectedItem("San Jose");
                comboBoxHoraSalida.setSelectedItem(1);
                comboBoxHoraLlegada.setSelectedItem(1);

            }
        });
    }


    private void initComponents() {
        initTable();
        initComboBoxes();
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los datos del formulario y crea un objeto Vuelo
                String numero = textFieldNumero.getText();
                String origen = (String) comboBoxOrigen.getSelectedItem();
                String destino = (String) comboBoxDestino.getSelectedItem();
                String horaSalida = Objects.requireNonNull(comboBoxHoraSalida.getSelectedItem()).toString();
                String horaLlegada = Objects.requireNonNull(comboBoxHoraLlegada.getSelectedItem()).toString();

                Ciudad ciudadOrigen = new Ciudad();
                Ciudad ciudadDestino = new Ciudad();

                List<Ciudad> ciudades = new ArrayList<>();
                ciudades = Service.instance().getData().getCiudades();

                for (Ciudad ciudad : ciudades) {
                    if(Objects.equals(destino, Objects.requireNonNull(ciudad.getNombre()))){
                        ciudadDestino = ciudad;
                    };
                }

                for (Ciudad ciudad : ciudades) {
                    if(Objects.equals(origen, Objects.requireNonNull(ciudad.getNombre()))){
                        ciudadOrigen = ciudad;
                    };
                }

                Vuelo nuevoVuelo = new Vuelo(numero, ciudadOrigen, ciudadDestino, horaSalida, horaLlegada);
                try {
                    if (controller.agregarVuelo(nuevoVuelo) && textFieldNumero.isEnabled()) {

                        textFieldNumero.setEnabled(true);
                    } else
                    if(!textFieldNumero.isEnabled()){
                        controller.guardarEdicionVuelo(nuevoVuelo);
                    }
                    else {
                        JOptionPane.showMessageDialog(panel1, "El vuelo ya existe", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                    actualizarTablaVuelos();
                    limpiarCampos();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoBusqueda = textFieldBuscarVuelos.getText();
                List<Vuelo> vuelosEncontrados = new ArrayList<>();

                // Itera a través de los vuelos y verifica si coinciden con la búsqueda
                List<Vuelo> todosLosVuelos = Service.instance().getData().getVuelos();
                for (Vuelo vuelo : todosLosVuelos) {
                    String nombreOrigen = vuelo.getCiudadOrigen().getNombre();
                    String nombreDestino = vuelo.getCiudadDestino().getNombre();

                    if (nombreOrigen.contains(textoBusqueda) || nombreDestino.contains(textoBusqueda)) {
                        vuelosEncontrados.add(vuelo);
                    }
                }

                VueloTableModel tableModel = new VueloTableModel(vuelosEncontrados);
                listVuelos.setModel(tableModel);
                tableModel.fireTableDataChanged();
            }
        });

        listVuelos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = listVuelos.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Obtener los datos del vuelo seleccionado
                        String numero = (String) listVuelos.getValueAt(selectedRow, 0);
                        String origen = (String) listVuelos.getValueAt(selectedRow, 1);
                        String horaSalida = (String) listVuelos.getValueAt(selectedRow, 2);
                        String destino = (String) listVuelos.getValueAt(selectedRow, 3);
                        String horaLlegada = (String) listVuelos.getValueAt(selectedRow, 4);

                        // Establece las selecciones en los ComboBox
                        comboBoxOrigen.setSelectedItem(origen);
                        comboBoxDestino.setSelectedItem(destino);
                        comboBoxHoraSalida.setSelectedItem(Integer.parseInt(horaSalida));
                        comboBoxHoraLlegada.setSelectedItem(Integer.parseInt(horaLlegada));
                        textFieldNumero.setText(numero);
                        textFieldNumero.setEnabled(false);

                    }
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoBusqueda = textFieldBuscarVuelos.getText();
                List<Vuelo> vuelosEncontrados = controller.buscarVuelos(textoBusqueda);
                limpiarCampos();
                textFieldNumero.setEnabled(true);
            }
        });

        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);

            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
       initTable();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void initTable(){
        try {
            listVuelos.setModel(new VueloTableModel(Service.instance().getData().getVuelos()));
            listVuelos.setRowHeight(30);
            TableColumnModel columnModel = listVuelos.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(200);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.panel1.revalidate();
    }


    public void initComboBoxes(){

        List<Ciudad> ciudades = Service.instance().getData().getCiudades();


        comboBoxOrigen.removeAllItems();

        for (Ciudad ciudad : ciudades) {
            comboBoxOrigen.addItem(ciudad.getNombre());
        }

        comboBoxDestino.removeAllItems();

        for (Ciudad ciudad : ciudades) {
            comboBoxDestino.addItem(ciudad.getNombre());
        }

        comboBoxHoraLlegada.removeAllItems();
        comboBoxHoraSalida.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            comboBoxHoraLlegada.addItem(i);
            comboBoxHoraSalida.addItem(i);

        }

    }
    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    private void actualizarTablaVuelos() {
        model.setChanged();
    }

    public void limpiarCampos() {
        textFieldNumero.setText("");
        textFieldNumero.setEnabled(true);
    }

}
