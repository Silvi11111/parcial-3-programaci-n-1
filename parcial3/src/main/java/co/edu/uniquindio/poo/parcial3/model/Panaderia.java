package co.edu.uniquindio.poo.parcial3.model;

import java.util.ArrayList;
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
        this.listaEmpleados = new ArrayList<>();;
        this.listaPedidos = new ArrayList<>();;
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
    public void registrarPedido(String codigo, String documentoCliente, String nombreCliente, TipoPan tipoPan, int cantidadUnidades) {
        if (cantidadUnidades <= 0) {
            throw new IllegalArgumentException("La cantidad de panes debe ser mayor que cero.");
        }
        if (buscarPedido(codigo).isPresent()) {
            throw new IllegalArgumentException("El código del pedido ya existe."); //
        }
        Pedido nuevoPedido = new Pedido(codigo, documentoCliente, nombreCliente, tipoPan, cantidadUnidades,null,0,EstadoPedido.PENDIENTE);
        listaPedidos.add(nuevoPedido);
    }

    public Optional<Pedido> buscarPedido(String codigo) {
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
            int tiempoRequerido = empleado.calcularTiempoProduccion(pedido);

            if (tiempoRequerido != Integer.MAX_VALUE && empleado.getMinutosDisponibles() >= tiempoRequerido) {
                if (tiempoRequerido < tiempoMinimo) {
                    tiempoMinimo = tiempoRequerido; // <-- ¡SI FALTA ESTA LÍNEA, EL ALGORITMO SE ROMPE!
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
