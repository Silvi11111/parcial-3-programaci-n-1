package co.edu.uniquindio.poo.parcial3.model;

public class Pedido {
    private String codigoPedido;
    private String documentoCliente;
    private String nombreCliente;
    private TipoPan tipoPan;
    private int cantidadUnidades;
    private Empleado empleadoAsignado;
    private int minutosReservados;
    private EstadoPedido estadoPedido;

    public Pedido(String codigoPedido, String documentoCliente, String nombreCliente, TipoPan tipoPan, int cantidadUnidades, Empleado empleadoAsignado, int minutosReservados, EstadoPedido estadoPedido) {
        this.codigoPedido = codigoPedido;
        this.documentoCliente = documentoCliente;
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidadUnidades = cantidadUnidades;
        this.empleadoAsignado = empleadoAsignado;
        this.minutosReservados = minutosReservados;
        this.estadoPedido = estadoPedido;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public TipoPan getTipoPan() {
        return tipoPan;
    }

    public void setTipoPan(TipoPan tipoPan) {
        this.tipoPan = tipoPan;
    }

    public int getCantidadUnidades() {
        return cantidadUnidades;
    }

    public void setCantidadUnidades(int cantidadUnidades) {
        this.cantidadUnidades = cantidadUnidades;
    }

    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(Empleado empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public int getMinutosReservados() {
        return minutosReservados;
    }

    public void setMinutosReservados(int minutosReservados) {
        this.minutosReservados = minutosReservados;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void asignarEmpleado(Empleado empleado, int minutos) {
        this.empleadoAsignado = empleado;
        this.minutosReservados = minutos;
        this.estadoPedido = EstadoPedido.ASIGNADO;
    }

    public void cancelar() {
        if (this.estadoPedido == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido ya está cancelado.");
        }
        if (this.estadoPedido == EstadoPedido.ASIGNADO && this.empleadoAsignado != null) {
            this.empleadoAsignado.agregarMinutosDisponibles(this.minutosReservados);
        }
        this.estadoPedido = EstadoPedido.CANCELADO;
    }
}
