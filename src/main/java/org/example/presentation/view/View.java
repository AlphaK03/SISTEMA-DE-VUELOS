package org.example.presentation.view;

import org.example.presentation.controller.Controller;
import org.example.presentation.model.Model;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private Controller controller;
    private Model model;
    private JPanel panel1;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JTextField textField2;
    private JButton buscarButton;
    private JTable table1;

    public JPanel getPanel1() {
        return panel1;
    }

    public View() {
    }

    @Override
    public void update(Observable o, Object arg) {

        this.panel1.revalidate();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

}
