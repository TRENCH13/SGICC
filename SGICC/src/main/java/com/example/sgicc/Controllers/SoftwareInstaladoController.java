package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.EquipoComputoDAO;
import com.example.sgicc.DAOs.SoftwareDAO;
import com.example.sgicc.Modelos.EquipoComputo;
import com.example.sgicc.Modelos.Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoftwareInstaladoController {

    int idCentroComputo;

    int numRegistrosAsignacion;
    int numEliminacionAsignacion;

    ObservableList<Software> observableDisponible;
    ObservableList<Software> observableInstalado;

    List<Software> softwareParaAsignar;
    List<Software> softwareParaEliminar;

    @FXML
    public TableView<Software> tvSoftwareDisponible;

    @FXML
    private TableColumn<Software, String> tcNombreDisponible;

    @FXML
    private TableColumn<Software, String> tcVersionDisponible;

    @FXML
    public TableView<Software> tvSoftwareInstalado;

    @FXML
    private TableColumn<Software, String> tcNombreInstalado;

    @FXML
    private TableColumn<Software, String> tcVersionInstalado;

    @FXML
    private Button btnDisponibleAInstalado;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnInstaladoADisponible;

    @FXML
    private ComboBox<EquipoComputo> cbEquiposComputo;

    @FXML
    void btnDisponibleAInstalado(ActionEvent event) {

        if (cbEquiposComputo.getSelectionModel().getSelectedItem() != null) {
            if (!tvSoftwareDisponible.getSelectionModel().isEmpty()) {
                Software softwareSeleccionado = tvSoftwareDisponible.getSelectionModel().getSelectedItem();
                ObservableList<Software> observableDisponible = tvSoftwareDisponible.getItems();
                observableDisponible.remove(softwareSeleccionado);

                if (SoftwareDAO.consultaAsignacionEspecifica(softwareSeleccionado.getIdSoftware(), cbEquiposComputo.getSelectionModel().getSelectedItem().getIdEquipoComputo())) {
                    softwareParaEliminar.remove(softwareSeleccionado);
                } else {
                    softwareParaAsignar.add(softwareSeleccionado);
                }

                observableInstalado.add(softwareSeleccionado);
                tvSoftwareInstalado.setItems(observableInstalado);

                System.out.println("Software para Asignar: "+softwareParaAsignar);
                System.out.println("Software para Eliminar: "+softwareParaEliminar);
            } else {
                Alerta.crearAlertaError("Error", "Error en la asignación de Software", "Debe seleccionar el software que quiere asignar.").showAndWait();
            }
        } else {
            Alerta.crearAlertaError("Error", "Error en la asignación de Software", "Debe seleccionar el equipo de cómputo al que quiere asignar.").showAndWait();
        }
    }

    @FXML
    void btnInstaladoADisponible(ActionEvent event) {
        if (cbEquiposComputo.getSelectionModel().getSelectedItem() != null) {
            if (!tvSoftwareInstalado.getSelectionModel().isEmpty()) {
                Software softwareSeleccionado = tvSoftwareInstalado.getSelectionModel().getSelectedItem();
                ObservableList<Software> observableInstalado = tvSoftwareInstalado.getItems();
                observableInstalado.remove(softwareSeleccionado);

                if (SoftwareDAO.consultaAsignacionEspecifica(softwareSeleccionado.getIdSoftware(), cbEquiposComputo.getSelectionModel().getSelectedItem().getIdEquipoComputo())) {
                    softwareParaEliminar.add(softwareSeleccionado);
                    softwareParaAsignar.remove(softwareSeleccionado);
                } else {
                    softwareParaAsignar.remove(softwareSeleccionado);
                }

                observableDisponible.add(softwareSeleccionado);
                tvSoftwareDisponible.setItems(observableDisponible);
                System.out.println("Software para Asignar: "+softwareParaAsignar);
                System.out.println("Software para Eliminar: "+softwareParaEliminar);
            } else {
                Alerta.crearAlertaError("Error", "Error en la asignación de Software", "Debe seleccionar el software que quiere desasignar.").showAndWait();
            }
        } else {
            Alerta.crearAlertaError("Error", "Error en la asignación de Software", "Debe seleccionar el equipo de cómputo al que quiere desasignar.").showAndWait();
        }
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!softwareParaAsignar.isEmpty() || !softwareParaEliminar.isEmpty()) {
            if (Alerta.crearAlertaConfirmacion("Confirmación", "¿Está seguro de realizar la operación?", "Si continua se actualizarán los Softwares del Equipo de Cómputo")) {
                if (!softwareParaEliminar.isEmpty()) {
                    for (Software software: softwareParaEliminar) {
                        numEliminacionAsignacion += SoftwareDAO.eliminarAsignacion(software.getIdSoftware(), cbEquiposComputo.getSelectionModel().getSelectedItem().getIdEquipoComputo());
                    }
                    softwareParaEliminar.clear();
                }
                if (!softwareParaAsignar.isEmpty()) {
                    for (Software software: softwareParaAsignar) {
                        numRegistrosAsignacion += SoftwareDAO.crearAsignacion(software.getIdSoftware(), cbEquiposComputo.getSelectionModel().getSelectedItem().getIdEquipoComputo());
                    }
                    softwareParaAsignar.clear();
                }
                Alerta.crearAlertaInformacion("Operación Exitosa", "Operación Exitosa", "Se desasignaron " + numEliminacionAsignacion + " softwares correctamente.\nSe asignaron "+ numRegistrosAsignacion+" softwares correctamente.").showAndWait();
                numRegistrosAsignacion = 0;
                numEliminacionAsignacion = 0;
            }
        }
    }

    public void initialize() {

        observableDisponible = FXCollections.observableArrayList();
        observableDisponible.clear();
        tvSoftwareDisponible.setItems(observableDisponible);
        observableInstalado = FXCollections.observableArrayList();
        observableInstalado.clear();
        tvSoftwareInstalado.setItems(observableInstalado);

        softwareParaAsignar = new ArrayList<>();
        softwareParaEliminar = new ArrayList<>();

        cbEquiposComputo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                llenarTablas();
                softwareParaAsignar.clear();
                softwareParaEliminar.clear();
            }
        });
    }

    public void llenarTablas() {

        if (!cbEquiposComputo.getSelectionModel().isEmpty()) {

            observableDisponible.clear();
            observableInstalado.clear();

            EquipoComputo equipoSeleccionado = cbEquiposComputo.getSelectionModel().getSelectedItem();
            List<Software> softwaresInstalados = SoftwareDAO.consultarSoftwareInstalado(cbEquiposComputo.getSelectionModel().getSelectedItem().getIdEquipoComputo());
            observableInstalado.addAll(softwaresInstalados);

            List<Software> softwares = SoftwareDAO.consultarSoftware();

            List<Software> softwaresDisponibles = new ArrayList<>(softwares);

            Iterator<Software> iterator = softwaresDisponibles.iterator();
            while (iterator.hasNext()) {
                Software softwareDisponible = iterator.next();
                for (Software softwareInstalado : softwaresInstalados) {
                    if (softwareInstalado.getIdSoftware() == softwareDisponible.getIdSoftware()) {
                        iterator.remove();
                        break;
                    }
                }
            }

            observableDisponible.addAll(softwaresDisponibles);

            llenarTablaInstalado();
            llenarTablaDisponible();
        }
    }

    public void llenarTablaInstalado() {
        tvSoftwareInstalado.setItems(null);

        tcNombreInstalado.setCellValueFactory(new PropertyValueFactory<>("nombreSoftware"));
        tcVersionInstalado.setCellValueFactory(new PropertyValueFactory<>("version"));

        tvSoftwareInstalado.setItems(observableInstalado);
    }

    public void llenarTablaDisponible() {
        tvSoftwareDisponible.setItems(null);

        tcNombreDisponible.setCellValueFactory(new PropertyValueFactory<>("nombreSoftware"));
        tcVersionDisponible.setCellValueFactory(new PropertyValueFactory<>("version"));

        tvSoftwareDisponible.setItems(observableDisponible);

    }

    public void llenarComboBox() {
        cbEquiposComputo.getItems().clear();
        List<EquipoComputo> listaEquiposCC = EquipoComputoDAO.consultarEquiposComputo(idCentroComputo);
        cbEquiposComputo.getItems().addAll(listaEquiposCC);
        cbEquiposComputo.setConverter(new StringConverter<EquipoComputo>() {
            @Override
            public String toString(EquipoComputo equipoComputo) {
                return equipoComputo != null ? equipoComputo.mostrarInfo() : "";
            }

            @Override
            public EquipoComputo fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }

                for (EquipoComputo item : cbEquiposComputo.getItems()) {
                    if (item.mostrarInfo().equals(s)) {
                        return item;
                    }
                }
                return null;
            }
        });
    }

    public void llenarTablaInicio() {
        tvSoftwareDisponible.setItems(null);
        List<Software> softwares = SoftwareDAO.consultarSoftware();
        observableDisponible.removeAll();
        observableDisponible.addAll(softwares);

        tcNombreDisponible.setCellValueFactory(new PropertyValueFactory<>("nombreSoftware"));
        tcVersionDisponible.setCellValueFactory(new PropertyValueFactory<>("version"));

        ObservableList<Software> softwareDisponibleObservable = FXCollections.observableArrayList(softwares);
        tvSoftwareDisponible.setItems(softwareDisponibleObservable);
    }

}

