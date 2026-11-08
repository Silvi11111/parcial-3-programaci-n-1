package co.edu.uniquindio.poo.parcial3.controller;

import co.edu.uniquindio.poo.parcial3.model.Panaderia;

public class InicioGestorController {

    Panaderia panaderia;
    public InicioGestorController(Panaderia panaderia){
        this.panaderia=panaderia;
    }
    //para entrar a la app de gestión
    public boolean entrarAlGestor(){
        return true;
    }
}
