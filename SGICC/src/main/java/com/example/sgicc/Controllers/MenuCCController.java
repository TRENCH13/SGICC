package com.example.sgicc.Controllers;


import com.example.sgicc.IniciadorAplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuCCController {

    private int rolActual = 0;

    public int idCentroComputo = 0;

    @FXML
    private AnchorPane apVistaMenuCC;

    @FXML
    private Button btnBitacora;

    @FXML
    private Button btnPerifericos;

    @FXML
    public Button btnAsignarSoftware;

    @FXML
    private Label lbUser;

    @FXML
    private Button btnConsultarSoftware;

    @FXML
    private Button btnEquiposComputo;

    public void setIdRol(int idRol) {
        this.rolActual = idRol;
    }

    @FXML
    void btnEquiposComputo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/EquiposComputo.fxml"));
        Parent root = fxmlLoader.load();
        EquiposComputoController ventanaEquiposComputo = fxmlLoader.getController();
        ventanaEquiposComputo.rolActual = rolActual;
        ventanaEquiposComputo.llenarTabla(idCentroComputo);

        if (rolActual != 1){
            ventanaEquiposComputo.btnAgregar.setVisible(false);
            ventanaEquiposComputo.btnEditar.setVisible(false);
            ventanaEquiposComputo.btnEliminar.setVisible(false);
        }

        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnEquiposComputo.setStyle("-fx-background-color: #173562; -fx-border-width: 0;-fx-padding: 0 0 0 -60;");
    }

    @FXML
    void btnPerifericos(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Perifericos.fxml"));
        Parent root = fxmlLoader.load();
        PerifericosController ventanaPerifericos = fxmlLoader.getController();
        ventanaPerifericos.rolActual = rolActual;
        ventanaPerifericos.llenarTabla(idCentroComputo);

        if (rolActual != 1){
            ventanaPerifericos.btnAgregar.setVisible(false);
            ventanaPerifericos.btnEditar.setVisible(false);
            ventanaPerifericos.btnEliminar.setVisible(false);
        }

        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnPerifericos.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -130;");
    }

    @FXML
    void btnBitacora(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Bitacora.fxml"));
        Parent root = fxmlLoader.load();
        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnBitacora.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -145;");
    }

    @FXML
    void btnAsignarSoftware(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/AsignarSoftware.fxml"));
        Parent root = fxmlLoader.load();
        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnAsignarSoftware.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -25;");
    }

    @FXML
    void btnConsultarSoftware(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/ConsultarSoftware.fxml"));
        Parent root = fxmlLoader.load();
        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnConsultarSoftware.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -5;");
    }

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuGeneral.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        MenuGeneralController menuGeneral = fxmlLoader.getController();
        menuGeneral.setIdRol(rolActual);
        menuGeneral.asignarCCRecuperados();

        if (rolActual == 2 || rolActual == 3){
            menuGeneral.btnUsuarios.setVisible(false);
            menuGeneral.btnCentrosComputo.setVisible(false);
        }

        stage.setScene(scene);
        Label lbUserNext = (Label) scene.lookup("#lbUser");
        lbUserNext.setText(lbUser.getText());
        stage.setTitle("Encargado");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    void refrescarBotones(){
        btnEquiposComputo.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0;-fx-padding: 0 0 0 -60;");
        btnPerifericos.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0; -fx-padding: 0 0 0 -130;");
        btnBitacora.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0; -fx-padding: 0 0 0 -145;");
        btnAsignarSoftware.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0; -fx-padding: 0 0 0 -25;");
        btnConsultarSoftware.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0; -fx-padding: 0 0 0 -5;");
    }

}

