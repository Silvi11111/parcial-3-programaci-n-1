package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends  Empleado {
    private int capacidadLote=30;
    private int minutosPreparacionBandeja=20;
    private int tiempoPanTradicional=30;
    private int tiempoPanIntegral=40;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    public int getCapacidadLote() {
        return capacidadLote;
    }

    public void setCapacidadLote(int capacidadLote) {
        this.capacidadLote = capacidadLote;
    }

    public int getMinutosPreparacionBandeja() {
        return minutosPreparacionBandeja;
    }

    public void setMinutosPreparacionBandeja(int minutosPreparacionBandeja) {
        this.minutosPreparacionBandeja = minutosPreparacionBandeja;
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

    @Override
    public int calcularTiempoProduccion(Pedido pedido) {
        if (pedido == null || pedido.getTipoPan() == null) {
            return Integer.MAX_VALUE;
        }
        int cantidad = pedido.getCantidadUnidades();
        int lotes = (int) Math.ceil((double) cantidad / 30);
        if (pedido.getTipoPan() == TipoPan.TRADICIONAL) {
            return 20 + (lotes * 25);
        } else if (pedido.getTipoPan() == TipoPan.INTEGRAL) {
            return 20 + (lotes * 35);
        }
        return Integer.MAX_VALUE;
    }
}
