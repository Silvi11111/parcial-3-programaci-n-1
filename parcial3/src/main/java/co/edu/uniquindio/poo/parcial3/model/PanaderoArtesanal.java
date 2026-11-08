package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoArtesanal extends Empleado{
    private int capacidadBandeja=12;
    private int tiempoPanTradicional=30;
    private int tiempoPanIntegral=40;

    public int getCapacidadBandeja() {
        return capacidadBandeja;
    }

    public void setCapacidadBandeja(int capacidadBandeja) {
        this.capacidadBandeja = capacidadBandeja;
    }

    public int getTiempoPanTradicional() {
        return tiempoPanTradicional;
    }

    public void setTiempoPanTradicional(int tiempoPanTradicional) {
        this.tiempoPanTradicional = tiempoPanTradicional;
    }

    public int getTiempoPanIntegral() {
        return tiempoPanIntegral;
    }

    public void setTiempoPanIntegral(int tiempoPanIntegral) {
        this.tiempoPanIntegral = tiempoPanIntegral;
    }

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);

    }

    @Override
    public int calcularTiempoProduccion(Pedido pedido) {
        if (pedido == null || pedido.getTipoPan() == null) {
            return Integer.MAX_VALUE;
        }

        int cantidad = pedido.getCantidadUnidades();
        int bandejas = (int) Math.ceil((double) cantidad / 12);

        if (pedido.getTipoPan() == TipoPan.TRADICIONAL) {
            return bandejas * 30;
        } else if (pedido.getTipoPan() == TipoPan.INTEGRAL) {
            return bandejas * 40;
        }
        return Integer.MAX_VALUE;
    }
}
