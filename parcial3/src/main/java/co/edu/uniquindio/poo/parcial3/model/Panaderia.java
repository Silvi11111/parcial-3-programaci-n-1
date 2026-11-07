package co.edu.uniquindio.poo.parcial3.model;

import java.util.List;
import java.util.Optional;

public class Panaderia {
    private String nombre;
    private String direccion;
    private List<Empleado> listaEmpleados;
    private List<Pedido> listaPedidos;

    public Panaderia(String nombre, String direccion, List<Empleado> listaEmpleados, List<Pedido> listaPedidos) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaEmpleados = listaEmpleados;
        this.listaPedidos = listaPedidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    //registrar pedido
    public String registrarPedido(Pedido pedido) {
        Optional<Pedido> pedido1 = buscarPedido(pedido.getCodigoPedido());
        if (pedido1.isPresent()) {
            return "Ya existe un pedido con el mismo código";
        } else listaPedidos.add(pedido);
        return "Pedido registrado exitosamente, pronto se le asignará un panadero";
    }

    private Optional<Pedido> buscarPedido(String codigo) {
        return listaPedidos
                .stream()
                .filter(e -> e.getCodigoPedido()
                        .equals(codigo))
                .findFirst();
    }

    //asignar pedido a produccion
    public void asignarPedidoProduccion(Pedido pedido) {
        Pedido pedido1 = buscarPedido(pedido.getCodigoPedido())
                .orElseThrow(() -> new IllegalArgumentException("El código del pedido no existe."));

        if (pedido.getEstadoPedido() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido ya está asignado o cancelado.");
        }

        Empleado empleadoSeleccionado = null;
        int tiempoMinimo = Integer.MAX_VALUE;

        for (Empleado empleado : listaEmpleados) {
            if (empleado instanceof Cajero) {
                continue;
            }
            int tiempoRequerido = empleado.calcularTiempoProduccion(pedido);
            if (empleado.getMinutosDisponibles() >= tiempoRequerido) {
                if (tiempoRequerido < tiempoMinimo) {
                    tiempoMinimo = tiempoRequerido;
                    empleadoSeleccionado = empleado;
                }
            }
        }
        if (empleadoSeleccionado == null) {
            throw new IllegalStateException("Ningún panadero tiene tiempo suficiente para el pedido, espere hasta que alguno esté disponible.");
        }

        empleadoSeleccionado.quitarMinutosDisponibles(tiempoMinimo);

        pedido.asignarEmpleado(empleadoSeleccionado, tiempoMinimo);
    }
    //cancelar pedido
    public void cancelarPedido(String codigoPedido) {
        Pedido pedido1 = listaPedidos.stream()
                .filter(e -> e.getCodigoPedido().equals(codigoPedido))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El código del pedido no existe.")); //
        pedido1.cancelar();
    }
}
