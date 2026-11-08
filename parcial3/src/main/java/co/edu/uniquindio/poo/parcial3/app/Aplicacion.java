package co.edu.uniquindio.poo.parcial3.app;
import co.edu.uniquindio.poo.parcial3.model.*;
import co.edu.uniquindio.poo.parcial3.viewcontrollers.InicioGestorViewController;
import co.edu.uniquindio.poo.parcial3.viewcontrollers.PanaderiaViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Aplicacion extends Application {

        private Stage primaryStage;
        public static Panaderia panaderia = new Panaderia("El Trigal","En su local");

        @Override
        public void start(Stage primaryStage) throws IOException {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Gestion de pedidos");
            inicializarData();
            openViewPrincipal();
        }
    public void openViewPrincipal() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/co/edu/uniquindio/poo/parcial3/iniciogestorpedidos.fxml"));
            javafx.scene.layout.AnchorPane rootLayout = (javafx.scene.layout.AnchorPane) loader.load();
            InicioGestorViewController InicioGestorViewController = loader.getController();
            InicioGestorViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openViewGestor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/co/edu/uniquindio/poo/parcial3/panaderiaview.fxml"));
            javafx.scene.layout.AnchorPane rootLayout = (javafx.scene.layout.AnchorPane) loader.load();
            PanaderiaViewController PanaderiaViewController = loader.getController();
            PanaderiaViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
    public void inicializarData(){
            Empleado empleado3=new PanaderoIndustrial("John Alejandro","9703","E03",120);
            Empleado empleado4=new PanaderoIndustrial("Elsa pato","6453","E04",60);
            Empleado empleado5=new PanaderoIndustrial("Julianito","3480","E05",45);
            Empleado empleado6=new PanaderoArtesanal("Pedrito","6421","E06",60);
            Empleado empleado7=new PanaderoArtesanal("Brayan","7543","E07",100);
            Empleado empleado8=new PanaderoArtesanal("José","0586","E08",40);
            panaderia.registrarEmpleado(empleado3);
            panaderia.registrarEmpleado(empleado4);
            panaderia.registrarEmpleado(empleado5);
            panaderia.registrarEmpleado(empleado6);
            panaderia.registrarEmpleado(empleado7);
            panaderia.registrarEmpleado(empleado8);

            panaderia.registrarPedido("P03","123456","Pablito",TipoPan.TRADICIONAL,25);
            panaderia.registrarPedido("P04","789456","Martica",TipoPan.INTEGRAL,10);
            panaderia.registrarPedido("P05","675184","Juan",TipoPan.TRADICIONAL,30);
    }
}

