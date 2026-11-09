package co.edu.uniquindio.poo.parcial3.controller;

import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;

import java.util.List;

public class PanaderiaController {
    Panaderia panaderia;
    public PanaderiaController(Panaderia panaderia){
        this.panaderia=panaderia;
    }

    //para hacer las funciones que pide el programa

    public void registroDePedidos(String codigo, String documento, String nombre, TipoPan tipoPan, int cantidad){
         panaderia.registrarPedido(codigo,documento,nombre,tipoPan,cantidad);}

    public void asignacionDePedidosAProduccion(Pedido pedido){
        panaderia.asignarPedidoProduccion(pedido);
    }

    public void cancelarUnPedido(String codigo){
        panaderia.cancelarPedido(codigo);
    }

    public List<Pedido> obtenerListaPedidos() {
        return panaderia.getListaPedidos();
    }

    public Pedido buscarPedido(String codigo) {
        return panaderia.buscarPedido(codigo).orElse(null);
    }
    public List<Empleado> obtenerListaEmpleados() {
        return panaderia.getListaEmpleados();
    }
}
