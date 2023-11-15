package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.PerifericoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Periferico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PerifericosController {

    public int idCentroComputo;

    public int rolActual;

    @FXML
    private TableColumn<Periferico, String> tcNombre;

    @FXML
    public Button btnEliminar;

    @FXML
    private TableView<Periferico> tvPerifericos;

    @FXML
    private TableColumn<Periferico, String> tcCodigo;

    @FXML
    private TableColumn<Periferico, String> tcEspecificaciones;

    @FXML
    public Button btnAgregar;

    @FXML
    private TableColumn<Periferico, String> tcTipo;

    @FXML
    public Button btnEditar;

    @FXML
    private TableColumn<Periferico, String> tcEstado;

    @FXML
    void btnEliminar(ActionEvent event) {
        if (!tvPerifericos.getSelectionModel().isEmpty()) {
            if (Alerta.crearAlertaConfirmacion("Eliminar Periférico", "¿Está seguro de eliminar el Periférico?", "Si se elimina, su asignación se perderá.")) {
                Periferico perifericoSeleccionado = tvPerifericos.getSelectionModel().getSelectedItem();
                PerifericoDAO.eliminarPeriferico(perifericoSeleccionado.getIdPeriferico());
                llenarTabla(idCentroComputo);
            }
        } else {
            Alerta.crearAlertaError("Error", "Error al eliminar el Periférico.", "Debe seleccionar el Periférico que desea eliminar.").showAndWait();
        }
    }

    @FXML
    void btnEditar(ActionEvent event) throws IOException {
        if (!tvPerifericos.getSelectionModel().isEmpty()) {
            Stage stage = (Stage) tvPerifericos.getScene().getWindow();
            Scene scene = stage.getScene();
            AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarPeriferico.fxml"));
            Parent root = fxmlLoader.load();

            RegistrarEditarPerifericoController ventanaRegistrarEditarPeriferico = fxmlLoader.getController();
            ventanaRegistrarEditarPeriferico.lbTítulo.setText("Editar Periferico");
            ventanaRegistrarEditarPeriferico.esEdicion = true;
            ventanaRegistrarEditarPeriferico.rolActual = rolActual;
            ventanaRegistrarEditarPeriferico.idCentroComputo = idCentroComputo;
            ventanaRegistrarEditarPeriferico.btnRegistrarEditar.setText("Editar Periferico");
            ventanaRegistrarEditarPeriferico.llenarComboBox();
            ventanaRegistrarEditarPeriferico.setEsEdicion(tvPerifericos.getSelectionModel().getSelectedItem());
            ventanaRegistrarEditarPeriferico.perifericoEditar = tvPerifericos.getSelectionModel().getSelectedItem();

            apVistaMenuCC.getChildren().setAll(root);
        } else {
            Alerta.crearAlertaError("Error", "Error al editar el Periférico.", "Debe seleccionar el Periférico que desea editar.").showAndWait();
        }
    }

    @FXML
    void btnAgregar(ActionEvent event) throws IOException {
        Stage stage = (Stage) tvPerifericos.getScene().getWindow();
        Scene scene =  stage.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarPeriferico.fxml"));
        Parent root = fxmlLoader.load();

        RegistrarEditarPerifericoController ventanaRegistrarEditarPerifericos = fxmlLoader.getController();
        ventanaRegistrarEditarPerifericos.lbTítulo.setText("Registrar Periferico");
        ventanaRegistrarEditarPerifericos.esEdicion = false;
        ventanaRegistrarEditarPerifericos.rolActual = rolActual;
        ventanaRegistrarEditarPerifericos.idCentroComputo = idCentroComputo;
        ventanaRegistrarEditarPerifericos.btnRegistrarEditar.setText("Registrar Periferico");
        ventanaRegistrarEditarPerifericos.llenarComboBox();

        apVistaMenuCC.getChildren().setAll(root);
    }

    public void llenarTabla(int idCentroComputo) {

        this.idCentroComputo = idCentroComputo;
        tvPerifericos.setItems(null);

        List<Periferico> perifericosTabla = PerifericoDAO.consultarPerifericos(idCentroComputo);
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPeriferico"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcEspecificaciones.setCellValueFactory(new PropertyValueFactory<>("especificaciones"));

        ObservableList<Periferico> perifericosObservable = FXCollections.observableArrayList(perifericosTabla);
        tvPerifericos.setItems(perifericosObservable);
    }

}

