package org.example.presentation.controller;

import org.example.presentation.model.Model;
import org.example.presentation.view.View;

public class Controller {

    private Model model;
    private View view;

    public Controller() {
    }

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        view.setModel(model);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
