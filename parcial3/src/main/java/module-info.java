module co.edu.uniquindio.poo.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    opens co.edu.uniquindio.poo.parcial3 to javafx.fxml;

    exports co.edu.uniquindio.poo.parcial3.app;
    exports co.edu.uniquindio.poo.parcial3.model;
    exports co.edu.uniquindio.poo.parcial3.viewcontrollers;

    opens co.edu.uniquindio.poo.parcial3.viewcontrollers to javafx.fxml;
}