package co.edu.uniquindio.poo.parcial3.model;

public abstract class Empleado {
    private String nombre;
    private String documento;
    private String codigo;
    private int minutosDisponibles;

    public Empleado(String nombre, String documento, String codigo, int minutosDisponibles) {
        this.nombre = nombre;
        this.documento = documento;
        this.codigo = codigo;
        this.minutosDisponibles = minutosDisponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getMinutosDisponibles() {
        return minutosDisponibles;
    }
    public void setMinutosDisponibles(int minutosDisponibles) {
        this.minutosDisponibles = minutosDisponibles;
    }

    public void quitarMinutosDisponibles(int minutos) {
        this.minutosDisponibles -= minutos;
    }

    public void agregarMinutosDisponibles(int minutos) {
        this.minutosDisponibles += minutos;
    }

    public abstract int calcularTiempoProduccion(Pedido pedido);

}
