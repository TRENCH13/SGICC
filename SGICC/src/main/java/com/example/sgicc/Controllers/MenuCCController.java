package com.example.sgicc.Controllers;


import com.example.sgicc.IniciadorAplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuCCController {

    @FXML
    private Label lbUser;

    @FXML
    void btnEquiposComputo(ActionEvent event) {

    }

    @FXML
    void btnPerifericos(ActionEvent event) {

    }

    @FXML
    void btnBitacora(ActionEvent event) {

    }

    @FXML
    void btnAsignarSoftware(ActionEvent event) {

    }

    @FXML
    void btnConsultarSoftware(ActionEvent event) {

    }

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuGeneral.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Label lbUserNext = (Label) scene.lookup("#lbUser");
        lbUserNext.setText(lbUser.getText());
        stage.setTitle("Encargado");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

}

