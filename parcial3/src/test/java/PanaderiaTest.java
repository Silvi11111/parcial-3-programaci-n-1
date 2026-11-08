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
        panaderia = new Panaderia("Trigal", "en el local", null, null);
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
}




