module co.edu.uniquindio.poo.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.parcial3.app to javafx.fxml;

    exports co.edu.uniquindio.poo.parcial3.app;
    exports co.edu.uniquindio.poo.parcial3.model;
}