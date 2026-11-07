package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends  Empleado {
    private int capacidadLote=30;
    private int minutosPreparacionBandeja=20;
    private int tiempoPanTradicional=30;
    private int tiempoPanIntegral=40;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutosDisponibles, int capacidadLote, int minutosPreparacionBandeja, int tiempoPanTradicional, int tiempoPanIntegral) {
        super(nombre, documento, codigo, minutosDisponibles);
        this.capacidadLote = capacidadLote;
        this.minutosPreparacionBandeja = minutosPreparacionBandeja;
        this.tiempoPanTradicional = tiempoPanTradicional;
        this.tiempoPanIntegral = tiempoPanIntegral;
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
        int cantidad = pedido.getCantidadUnidades();
        TipoPan tipo = pedido.getTipoPan();

        int lotes = (int) Math.ceil((double) cantidad / capacidadLote);

        if (tipo == TipoPan.TRADICIONAL) {
            return minutosPreparacionBandeja + (lotes * tiempoPanTradicional);
        } else if (tipo == TipoPan.INTEGRAL) {
            return minutosPreparacionBandeja + (lotes * tiempoPanIntegral);
        }
        return 0;
    }
}
