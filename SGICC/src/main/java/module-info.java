module com.example.sgicc {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.sgicc to javafx.fxml;
    exports com.example.sgicc;
}