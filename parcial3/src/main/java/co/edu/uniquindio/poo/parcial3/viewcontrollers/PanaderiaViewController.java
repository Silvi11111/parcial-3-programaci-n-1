package co.edu.uniquindio.poo.parcial3.viewcontrollers;

import co.edu.uniquindio.poo.parcial3.app.Aplicacion;
import co.edu.uniquindio.poo.parcial3.controller.PanaderiaController;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class PanaderiaViewController {

    private PanaderiaController panaderiaController;
    private Aplicacion aplicacion;

    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.panaderiaController = new PanaderiaController(aplicacion.panaderia);
    }

    @FXML
    void initialize() {

    }

    @FXML
    void registrarPedidoEvent(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Registrar Nuevo Pedido");
        dialog.setHeaderText("Ingrese la información del pedido:");

        ButtonType btnGuardar = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        TextField txtCodigo = new TextField();

        TextField txtDocumento = new TextField();

        TextField txtNombre = new TextField();


        ComboBox<TipoPan> cbTipoPan = new ComboBox<>();
        cbTipoPan.setItems(FXCollections.observableArrayList(TipoPan.values()));
        cbTipoPan.getSelectionModel().selectFirst();

        TextField txtCantidad = new TextField();


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(new Label("Doc. Cliente:"), 0, 1);
        grid.add(txtDocumento, 1, 1);
        grid.add(new Label("Nombre Cliente:"), 0, 2);
        grid.add(txtNombre, 1, 2);
        grid.add(new Label("Tipo de Pan:"), 0, 3);
        grid.add(cbTipoPan, 1, 3);
        grid.add(new Label("Cantidad:"), 0, 4);
        grid.add(txtCantidad, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == btnGuardar) {
            try {
                String codigo = txtCodigo.getText().trim();
                String doc = txtDocumento.getText().trim();
                String nombre = txtNombre.getText().trim();
                TipoPan tipo = cbTipoPan.getValue();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                panaderiaController.registroDePedidos(codigo, doc, nombre, tipo, cantidad);
                mostrarAlerta("Éxito", "Pedido " + codigo + " registrado correctamente.", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de entrada", "La cantidad debe ser un número entero válido.", Alert.AlertType.ERROR);
            } catch (Exception e) {
                mostrarAlerta("Error al Registrar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void asignarPedidoEvent(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("Asignar Pedido a Producción");

        ButtonType btnAsignar = new ButtonType("Asignar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAsignar, ButtonType.CANCEL);

        Label lblCodigo = new Label("Código del pedido:");
        TextField txtCodigo = new TextField();

        Label lblEmpleado = new Label("Seleccione el trabajador disponible:");
        ComboBox<Empleado> cbEmpleados = new ComboBox<>();

        if (panaderiaController != null) {
            cbEmpleados.setItems(FXCollections.observableArrayList(panaderiaController.obtenerListaEmpleados()));
        }

        cbEmpleados.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Empleado emp, boolean empty) {
                super.updateItem(emp, empty);
                if (empty || emp == null) {
                    setText(null);
                } else {
                    setText(emp.getNombre() + " (" + emp.getClass().getSimpleName() + ") - " + emp.getMinutosDisponibles() + " min dispon.");
                }
            }
        });

        cbEmpleados.setButtonCell(cbEmpleados.getCellFactory().call(null));
        if (!cbEmpleados.getItems().isEmpty()) {
            cbEmpleados.getSelectionModel().selectFirst();
        }

        VBox contenedor = new VBox(10);
        contenedor.getChildren().addAll(lblCodigo, txtCodigo, lblEmpleado, cbEmpleados);
        dialog.getDialogPane().setContent(contenedor);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == btnAsignar) {
            String codigo = txtCodigo.getText().trim();
            Empleado empleadoSeleccionado = cbEmpleados.getValue();

            if (codigo.isEmpty()) {
                mostrarAlerta("Atención", "Debe ingresar el código del pedido.", Alert.AlertType.WARNING);
                return;
            }

            if (empleadoSeleccionado == null) {
                mostrarAlerta("Atención", "Debe seleccionar un trabajador.", Alert.AlertType.WARNING);
                return;
            }

            try {
                Pedido pedido = panaderiaController.buscarPedido(codigo);
                if (pedido == null) {
                    throw new IllegalArgumentException("El pedido con código '" + codigo + "' no existe.");
                }

                panaderiaController.asignacionDePedidosAProduccion(pedido);

                mostrarAlerta("Éxito", "El pedido " + codigo + " fue asignado a " + empleadoSeleccionado.getNombre() + " correctamente.", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                mostrarAlerta("Error al Asignar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void cancelarPedidoEvent(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cancelar Pedido");
        dialog.setHeaderText("Cancelación de Pedido");
        dialog.setContentText("Ingrese el código del pedido a cancelar:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String codigo = result.get().trim();
            try {
                panaderiaController.cancelarUnPedido(codigo);
                mostrarAlerta("Éxito", "El pedido " + codigo + " ha sido cancelado.", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                mostrarAlerta("Error al Cancelar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}