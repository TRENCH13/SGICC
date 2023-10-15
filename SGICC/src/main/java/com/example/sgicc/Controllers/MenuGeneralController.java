package com.example.sgicc.Controllers;

import com.example.sgicc.IniciadorAplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuGeneralController {

    @FXML
    void btnIrCC1(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuCC.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("CC1");
        stage.show();
    }

    @FXML
    void btnIrCC2(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuCC.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("CC2");
        stage.show();
    }

    @FXML
    void btnIrCC3(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuCC.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("CC3");
        stage.show();
    }

    @FXML
    void btnUsuarios(ActionEvent event) {

    }

}