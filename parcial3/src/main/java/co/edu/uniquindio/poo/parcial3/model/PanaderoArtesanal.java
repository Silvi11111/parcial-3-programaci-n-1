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

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutosDisponibles, int capacidadBandeja, int tiempoPanTradicional, int tiempoPanIntegral) {
        super(nombre, documento, codigo, minutosDisponibles);
        this.capacidadBandeja = capacidadBandeja;
        this.tiempoPanTradicional = tiempoPanTradicional;
        this.tiempoPanIntegral = tiempoPanIntegral;
    }

    @Override
    public int calcularTiempoProduccion(Pedido pedido) {
        int cantidad = pedido.getCantidadUnidades();
        TipoPan tipo = pedido.getTipoPan();

        int bandejas = (int) Math.ceil((double) cantidad / capacidadBandeja);

        if (tipo == TipoPan.TRADICIONAL) {
            return bandejas * tiempoPanTradicional;
        } else if (tipo == TipoPan.INTEGRAL) {
            return bandejas * tiempoPanIntegral;
        }
        return 0;
    }
}
