module com.example.sgicc {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.sgicc to javafx.fxml;
    exports com.example.sgicc;
    exports com.example.sgicc.Controllers;
    opens com.example.sgicc.Controllers to javafx.fxml;
}