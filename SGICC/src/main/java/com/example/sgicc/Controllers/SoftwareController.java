package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.SoftwareDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Software;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SoftwareController {

    public int rolActual;

    @FXML
    private TableView<Software> tvSoftware;

    @FXML
    private TableColumn<Software, String> tcNombre;

    @FXML
    private TableColumn<Software, String> tcLicencia;

    @FXML
    private TableColumn<Software, String> tcTipoLicencia;

    @FXML
    private TableColumn<Software, String> tcTipoSoftware;

    @FXML
    private TableColumn<Software, String> tcVersion;

    @FXML
    public Label lbUser;

    @FXML
    public Button btnRegistrar;

    @FXML
    public Button btnEditar;

    @FXML
    public Button btnEliminar;

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {
        regresarVentana();
    }

    @FXML
    void btnEliminar(ActionEvent event) {
        if (!tvSoftware.getSelectionModel().isEmpty()) {
            if (Alerta.crearAlertaConfirmacion("Eliminar software", "¿Está seguro de eliminar el Software?", ".")) {
                SoftwareDAO.eliminarSoftware(tvSoftware.getSelectionModel().getSelectedItem().getIdSoftware());
                llenarTabla();
            }
        } else {
            Alerta.crearAlertaError("Error", "Error al eliminar el software.", "Debe seleccionar el software que desea eliminar.").showAndWait();
        }
    }

    @FXML
    void btnEditar(ActionEvent event) throws IOException {

        if (!tvSoftware.getSelectionModel().isEmpty()) {
            Stage stage = (Stage) lbUser.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarSoftware.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            RegistrarEditarSoftwareController ventanaRegistrarEditarSoftware = fxmlLoader.getController();
            ventanaRegistrarEditarSoftware.btnRegistrarEditar.setText("Editar Software");
            ventanaRegistrarEditarSoftware.lbUser.setText(lbUser.getText());
            ventanaRegistrarEditarSoftware.lbTítulo.setText("Edición de Software");
            ventanaRegistrarEditarSoftware.rolActual = rolActual;
            ventanaRegistrarEditarSoftware.softwareEdicion = tvSoftware.getSelectionModel().getSelectedItem();
            ventanaRegistrarEditarSoftware.setEdicion();
            ventanaRegistrarEditarSoftware.esEdicion = true;


            stage.setTitle("Registrar Centro de Cómputo");
            stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
            stage.show();
        } else {
            Alerta.crearAlertaError("Error", "Error al editar el software.", "Debe seleccionar el software que desea editar.").showAndWait();
        }

    }

    @FXML
    void btnRegistrar(ActionEvent event) throws IOException {
        Stage stage = (Stage) lbUser.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarSoftware.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        RegistrarEditarSoftwareController ventanaRegistrarEditarSoftware = fxmlLoader.getController();
        ventanaRegistrarEditarSoftware.btnRegistrarEditar.setText("Registrar Software");
        ventanaRegistrarEditarSoftware.lbUser.setText(lbUser.getText());
        ventanaRegistrarEditarSoftware.lbTítulo.setText("Registro de Software");
        ventanaRegistrarEditarSoftware.rolActual = rolActual;
        ventanaRegistrarEditarSoftware.esEdicion = false;
        stage.setTitle("Registrar Centro de Cómputo");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    public void llenarTabla () {
        tvSoftware.setItems(null);

        List<Software> softwareTabla = SoftwareDAO.consultarSoftware();

        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSoftware"));
        tcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        tcTipoSoftware.setCellValueFactory(new PropertyValueFactory<>("tipoSoftware"));
        tcLicencia.setCellValueFactory(cellData -> {
            boolean valueLicencia = cellData.getValue().isConLicencia();
            return new SimpleStringProperty(valueLicencia ? "Si" : "No");
        });
        tcTipoLicencia.setCellValueFactory(new PropertyValueFactory<>("tipoLicencia"));

        ObservableList<Software> softwareObservable = FXCollections.observableArrayList(softwareTabla);

        tvSoftware.setItems(softwareObservable);
    }

    void regresarVentana() throws IOException {
        Stage stage = (Stage) lbUser.getScene().getWindow();
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

}
