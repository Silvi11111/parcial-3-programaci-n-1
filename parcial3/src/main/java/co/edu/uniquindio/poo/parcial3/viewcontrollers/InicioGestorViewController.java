package co.edu.uniquindio.poo.parcial3.viewcontrollers;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.parcial3.app.Aplicacion;
import co.edu.uniquindio.poo.parcial3.controller.InicioGestorController;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioGestorViewController {

    private InicioGestorController inicioGestorController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIngresarSistema;

    @FXML
    void iniciarGestorEvent(ActionEvent event) {
        inicioGestorAction();

    }
    private void inicioGestorAction(){

        if(inicioGestorController.entrarAlGestor()){
           aplicacion.openViewGestor();
        }
    }

    private Aplicacion aplicacion;

    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        inicioGestorController=new InicioGestorController(aplicacion.panaderia);

    }

    @FXML
    void initialize() {
        assert btnIngresarSistema != null : "fx:id=\"btnIngresarSistema\" was not injected: check your FXML file 'iniciogestorpedidos.fxml'.";

    }

}
