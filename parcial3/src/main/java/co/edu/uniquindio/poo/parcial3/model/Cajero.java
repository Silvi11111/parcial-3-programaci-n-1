package co.edu.uniquindio.poo.parcial3.model;

public class Cajero extends Empleado{
    private String cajaAsignada;

    public Cajero(String nombre, String documento, String codigo, int minutosDisponibles, String cajaAsignada) {
        super(nombre, documento, codigo, minutosDisponibles);
        this.cajaAsignada = cajaAsignada;
    }

    public String getCajaAsignada() {
        return cajaAsignada;
    }
    public void setCajaAsignada(String cajaAsignada) {
        this.cajaAsignada = cajaAsignada;
    }

    @Override
    public int calcularTiempoProduccion(Pedido pedido) {
        return 0;
    }
}
