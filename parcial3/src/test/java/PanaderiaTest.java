/**
 * Clase para probar los metodos de la panaderia
 * @author Silvana López
 * Licencia GNU/GPL V3.0
 */
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.logging.Logger;

import co.edu.uniquindio.poo.parcial3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PanaderiaTest {
    private Panaderia panaderia;
    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("Trigal", "en el local");
    }
    @Test
    //Registrar P01 por 25 panes tradicionales.
    public void registroDePedidos1() {

        String codigoPedido1="P01";
        String documentoCliente1="123";
        String nombreCliente1="Juanito Peritas";
        TipoPan tipopan1=TipoPan.TRADICIONAL;
        int cantidad1=25;
        panaderia.registrarPedido(codigoPedido1,documentoCliente1,nombreCliente1,tipopan1,cantidad1);
        Pedido pedido = panaderia.buscarPedido(codigoPedido1).get();
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstadoPedido());
        //El pedido debe iniciar en estado PENDIENTE
    }

    @Test
    //Registrar P01, cancelarlo e intentar registrar otro pedido con el mismo código.
    public void registroDePedidos2() {

        String codigoPedido1="P01";
        String documentoCliente1="123";
        String nombreCliente1="Juanito Peritas";
        TipoPan tipopan1=TipoPan.TRADICIONAL;
        int cantidad1=25;
        panaderia.registrarPedido(codigoPedido1,documentoCliente1,nombreCliente1,tipopan1,cantidad1);
        Pedido pedido = panaderia.buscarPedido(codigoPedido1).get();

        assertThrows(IllegalArgumentException.class, () -> {
            panaderia.registrarPedido("P01", "99999", "María López", TipoPan.INTEGRAL, 10);
        });
        //Debe lanzar IllegalArgumentException por intentar usar un código que ya existe en el historial
    }

    //Intentar registrar un pedido de cero panes con el historial vacío.
    @Test
    public void registroDePedidos3() {
        assertThrows(IllegalArgumentException.class, () -> {
            panaderia.registrarPedido("P01", "12345", "Carlos Gómez", TipoPan.TRADICIONAL, 0);
        }, "Debe lanzar IllegalArgumentException si la cantidad es menor o igual a cero.");

        assertTrue(panaderia.getListaPedidos().isEmpty(), "El historial debe continuar completamente vacío.");
        //Debe lanzar IllegalArgumentException por haber puesto 0 panes
        }

    @Test
    //Registrar en este orden a Ana y a Diana, ambas artesanales con 80 minutos, y a Carlos, industrial con 54 minutos.
    // Asignar un pedido de 13 panes integrales.
    public void asignacionDePedidos1() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 45);

        panaderia.getListaEmpleados().add(ana);
        panaderia.getListaEmpleados().add(carlos);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.TRADICIONAL, 25);

        Pedido pedido = panaderia.buscarPedido("P01").orElse(null);
        panaderia.asignarPedidoProduccion(pedido);
        assertNotNull(pedido);
        assertEquals(EstadoPedido.ASIGNADO, pedido.getEstadoPedido(), "El estado del pedido pasa a ASIGNADO.");
        assertEquals(carlos, pedido.getEmpleadoAsignado(), "Debe asignarse a Carlos por tardar menos (45 min vs 90 min).");
        assertEquals(45, pedido.getMinutosReservados(), "Se deben reservar 45 minutos.");
        assertEquals(0, carlos.getMinutosDisponibles(), "La disponibilidad de Carlos pasa de 45 a 0 minutos.");
        assertEquals(100, ana.getMinutosDisponibles(), "Ana conserva sus 100 minutos intactos.");
    }

    @Test
    //Registrar a Carlos, industrial con 44 minutos, y a Lucía, cajera con 120 minutos. Intentar asignar un pedido de
    //25 panes tradicionales.
    public void asignacionDePedidos2() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 80);
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "102", "E02", 80);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "103", "E03", 54);

        panaderia.getListaEmpleados().add(ana);
        panaderia.getListaEmpleados().add(diana);
        panaderia.getListaEmpleados().add(carlos);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.INTEGRAL, 13);
        Pedido pedido1 = panaderia.buscarPedido("P01").orElse(null);
        panaderia.asignarPedidoProduccion(pedido1);


        assertNotNull(pedido1);
        assertEquals(ana, pedido1.getEmpleadoAsignado(), "Ante empate de tiempo, se asigna a Ana por orden de registro.");
        assertEquals(0, ana.getMinutosDisponibles(), "Ana consume sus 80 minutos.");
        assertEquals(80, diana.getMinutosDisponibles(), "Diana conserva sus 80 minutos.");
        assertEquals(54, carlos.getMinutosDisponibles(), "Carlos conserva sus 54 minutos al no ser seleccionado.");
    }

    @Test
    //Registrar a Carlos, industrial con 44 minutos, y a Lucía, cajera con 120 minutos. Intentar asignar un pedido de
    //25 panes tradicionales.
    public void asignacionDePedidos3() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "101", "E01", 44);
        Cajero lucia = new Cajero("Lucía", "102", "E02", 120, "Caja 1");

        panaderia.getListaEmpleados().add(carlos);
        panaderia.getListaEmpleados().add(lucia);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.TRADICIONAL, 25);

        Pedido pedido1 = panaderia.buscarPedido("P01").orElse(null);

        assertThrows(IllegalStateException.class, () -> {
            panaderia.asignarPedidoProduccion(pedido1);
        }, "Debe lanzar IllegalStateException cuando ningún panadero dispone de tiempo.");

        assertNotNull(pedido1);
        assertEquals(EstadoPedido.PENDIENTE, pedido1.getEstadoPedido(), "El pedido debe mantenerse en estado PENDIENTE.");
        assertEquals(44, carlos.getMinutosDisponibles(), "Carlos debe conservar sus 44 minutos.");
        assertEquals(120, lucia.getMinutosDisponibles(), "Lucía debe conservar sus 120 minutos.");
    }
    @Test
    //Asignar a Carlos, industrial con 120 minutos, dos pedidos de 25 panes tradicionales.
    // Luego cancelar solo el primero.
    public void cancelacionDePedidos1() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "101", "E01", 120);
        panaderia.getListaEmpleados().add(carlos);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.TRADICIONAL, 25);
        panaderia.registrarPedido("P02", "67890", "Cliente 2", TipoPan.TRADICIONAL, 25);

        Pedido p01 = panaderia.buscarPedido("P01").orElse(null);
        Pedido p02 = panaderia.buscarPedido("P02").orElse(null);

        panaderia.asignarPedidoProduccion(p01);
        panaderia.asignarPedidoProduccion(p02);

        assertEquals(30, carlos.getMinutosDisponibles());

        panaderia.cancelarPedido("P01");

        assertEquals(75, carlos.getMinutosDisponibles(), "Carlos debe recuperar 45 min y quedar con 75 min disponibles.");
        assertEquals(EstadoPedido.CANCELADO, p01.getEstadoPedido(), "El pedido P01 debe pasar a estado CANCELADO.");
        assertEquals(EstadoPedido.ASIGNADO, p02.getEstadoPedido(), "El pedido P02 debe mantenerse en estado ASIGNADO.");
    }

    @Test
    //Cancelar un pedido pendiente cuando hay un panadero con 100 minutos disponibles.
    public void cancelacionDePedidos2() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        panaderia.getListaEmpleados().add(ana);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.TRADICIONAL, 25);

        panaderia.cancelarPedido("P01");

        Pedido pedido = panaderia.buscarPedido("P01").orElse(null);
        assertNotNull(pedido);
        assertEquals(EstadoPedido.CANCELADO, pedido.getEstadoPedido(), "El pedido debe pasar a estado CANCELADO.");
        assertEquals(1, panaderia.getListaPedidos().size(), "El pedido cancelado debe permanecer en el historial.");
        assertEquals(100, ana.getMinutosDisponibles(), "Ana debe conservar sus 100 minutos intactos.");
    }
    @Test
    //Asignar un pedido, cancelarlo e intentar cancelarlo de nuevo.
    public void cancelacionDePedidos3() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "101", "E01", 120);
        panaderia.getListaEmpleados().add(carlos);

        panaderia.registrarPedido("P01", "12345", "Cliente 1", TipoPan.TRADICIONAL, 25);
        Pedido p01 = panaderia.buscarPedido("P01").orElse(null);

        panaderia.asignarPedidoProduccion(p01);

        panaderia.cancelarPedido("P01");
        assertEquals(120, carlos.getMinutosDisponibles());

        assertThrows(IllegalStateException.class, () -> {
            panaderia.cancelarPedido("P01");
        }, "Debe lanzar IllegalStateException al intentar re-cancelar un pedido ya cancelado.");
        
        assertEquals(120, carlos.getMinutosDisponibles(), "Los minutos solo se restauran una vez.");
    }
}




