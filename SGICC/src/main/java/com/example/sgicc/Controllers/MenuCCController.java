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
    public Button btnSoftwareInstalado;

    @FXML
    private Label lbUser;

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

        BitacoraController bitacoraController = fxmlLoader.getController();
        bitacoraController.rolActual = rolActual;
        bitacoraController.llenarTabla(idCentroComputo);
        bitacoraController.inicializarBusquedaBitacoras();

        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnBitacora.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -145;");
    }

    @FXML
    void btnSoftwareInstalado(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/SoftwareInstalado.fxml"));
        Parent root = fxmlLoader.load();

        SoftwareInstaladoController ventanasoftwareInstalado = fxmlLoader.getController();
        ventanasoftwareInstalado.idCentroComputo = idCentroComputo;
        Label mensajeVacio1 = new Label("No existen registros");
        ventanasoftwareInstalado.tvSoftwareDisponible.setPlaceholder(mensajeVacio1);
        Label mensajeVacio2 = new Label("No existen registros");
        ventanasoftwareInstalado.tvSoftwareInstalado.setPlaceholder(mensajeVacio2);
        ventanasoftwareInstalado.llenarComboBox();
        ventanasoftwareInstalado.llenarTablaInicio();

        apVistaMenuCC.getChildren().setAll(root);
        refrescarBotones();
        btnSoftwareInstalado.setStyle("-fx-background-color: #173562; -fx-border-width: 0; -fx-padding: 0 0 0 -78;");
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
        btnSoftwareInstalado.setStyle("-fx-background-color: #295BA5; -fx-border-width: 0; -fx-padding: 0 0 0 -78;");
    }

}

