package org.example;
/*
Universidad Nacional de Costa Rica
Estudiante: Keylor Josué Cortés Cascante 504350713
Profesor: Jose Sanchez
Ciclo II 2023
*/

import org.example.data.Data;
import org.example.logic.Ciudad;
import org.example.logic.Service;
import org.example.logic.Vuelo;
import org.example.logic.XmlPersister;
import org.example.presentation.controller.Controller;
import org.example.presentation.model.Model;
import org.example.presentation.view.View;

import javax.swing.*;
import java.util.Objects;

public class Application {
    public static void main(String[] args) throws Exception {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }
        ;

        JFrame window = new JFrame();

        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(model, view);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Vuelos", view.getPanel1());
        window.getContentPane().add(tabbedPane);

        window.setSize(900, 450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Application.class.getResource("/vuelos/presentation/icons/globe.png")));
        window.setIconImage(icon.getImage());
        window.setTitle("SIVU: SISTEMA DE VUELOS");
        window.setVisible(true);
    }
}