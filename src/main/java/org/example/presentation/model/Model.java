package org.example.presentation.model;

import java.util.Observer;

public class Model extends java.util.Observable{
    public Model() {
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
