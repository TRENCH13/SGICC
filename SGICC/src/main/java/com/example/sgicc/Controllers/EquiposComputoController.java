package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.EquipoComputoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.EquipoComputo;
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

public class EquiposComputoController {

    public int idCentroComputo;

    public int rolActual;

    @FXML
    private TableView<EquipoComputo> tvEquiposComputo;

    @FXML
    private TableColumn<EquipoComputo, String> tcSistemaOperativo;

    @FXML
    private TableColumn<EquipoComputo, String> tcAlmacenamiento;

    @FXML
    private TableColumn<EquipoComputo, String> tcTarjetaMadre;

    @FXML
    private TableColumn<EquipoComputo, String> tcMemoria;

    @FXML
    private TableColumn<EquipoComputo, String> tcCodigo;

    @FXML
    private TableColumn<EquipoComputo, String> tcProcesador;

    @FXML
    private TableColumn<EquipoComputo, String> tcGabinete;

    @FXML
    private TableColumn<EquipoComputo, String> tcEstado;

    @FXML
    public Button btnAgregar;

    @FXML
    public Button btnEditar;

    @FXML
    public Button btnEliminar;

    @FXML
    void btnEliminar(ActionEvent event) {

        if (!tvEquiposComputo.getSelectionModel().isEmpty()){

            if (Alerta.crearAlertaConfirmacion("Eliminar Equipo de Cómputo", "¿Está seguro de eliminar el equipo de cómputo?", "Si se elimina, su asignación se perderá.")){
                EquipoComputo equipoSeleccionado = tvEquiposComputo.getSelectionModel().getSelectedItem();
                EquipoComputoDAO.eliminarEquipoComputo(equipoSeleccionado.getIdEquipoComputo());
                llenarTabla(idCentroComputo);

            }

        }else {

            Alerta.crearAlertaError("Error", "Error al eliminar el equipo de cómputo.", "Debes seleccionar el equipo de cómputo que deseas eliminar.").showAndWait();

        }

    }

    @FXML
    void btnEditar(ActionEvent event) throws IOException {

        if (!tvEquiposComputo.getSelectionModel().isEmpty()){
            Stage stage = (Stage) tvEquiposComputo.getScene().getWindow();
            Scene scene = stage.getScene();
            AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarEquipoComputo.fxml"));
            Parent root = fxmlLoader.load();

            RegistrarEditarEquipoComputoController ventanaRegistrarEditarEC = fxmlLoader.getController();
            ventanaRegistrarEditarEC.lbTítulo.setText("Editar Equipo de Cómputo");
            ventanaRegistrarEditarEC.esEdicion = true;
            ventanaRegistrarEditarEC.rolActual = rolActual;
            ventanaRegistrarEditarEC.idCentroComputo = idCentroComputo;
            ventanaRegistrarEditarEC.btnRegistrarEditar.setText("Registrar Equipo de Cómputo");
            ventanaRegistrarEditarEC.setEdicion(tvEquiposComputo.getSelectionModel().getSelectedItem());
            ventanaRegistrarEditarEC.equipoEditar = tvEquiposComputo.getSelectionModel().getSelectedItem();

            apVistaMenuCC.getChildren().setAll(root);
        } else {
            Alerta.crearAlertaError("Error", "Error al editar el equipo de cómputo.", "Debes seleccionar el equipo de cómputo que deseas editar.").showAndWait();
        }

    }

    @FXML
    void btnAgregar(ActionEvent event) throws IOException {
        Stage stage = (Stage) tvEquiposComputo.getScene().getWindow();
        Scene scene = stage.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarEquipoComputo.fxml"));
        Parent root = fxmlLoader.load();

        RegistrarEditarEquipoComputoController ventanaRegistrarEditarEC = fxmlLoader.getController();
        ventanaRegistrarEditarEC.lbTítulo.setText("Registrar Equipo de Cómputo");
        ventanaRegistrarEditarEC.esEdicion = false;
        ventanaRegistrarEditarEC.rolActual = rolActual;
        ventanaRegistrarEditarEC.idCentroComputo = idCentroComputo;
        ventanaRegistrarEditarEC.btnRegistrarEditar.setText("Registrar Equipo de Cómputo");
        ventanaRegistrarEditarEC.equipoEditar = tvEquiposComputo.getSelectionModel().getSelectedItem();

        apVistaMenuCC.getChildren().setAll(root);
    }

    public void llenarTabla(int idCentroComputo) {

        this.idCentroComputo = idCentroComputo;
        tvEquiposComputo.setItems(null);

        List<EquipoComputo> equiposTabla = EquipoComputoDAO.consultarEquiposComputo(idCentroComputo);


        tcSistemaOperativo.setCellValueFactory(new PropertyValueFactory<>("sistemaOperativo"));
        tcAlmacenamiento.setCellValueFactory(new PropertyValueFactory<>("almacenamiento"));
        tcTarjetaMadre.setCellValueFactory(new PropertyValueFactory<>("tarjetaMadre"));
        tcMemoria.setCellValueFactory(new PropertyValueFactory<>("memoria"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoEquipo"));
        tcProcesador.setCellValueFactory(new PropertyValueFactory<>("procesador"));
        tcGabinete.setCellValueFactory(new PropertyValueFactory<>("gabinete"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<EquipoComputo> equiposObservable = FXCollections.observableArrayList(equiposTabla);

        tvEquiposComputo.setItems(equiposObservable);
    }
}
