package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.Modelos.EquipoComputo;
import com.example.sgicc.Modelos.Periferico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class SeleccionarNuevoEstadoController {
    public ComboBox cbNuevoEstado;
    public TableColumn tcCodigoSelec;
    public TableColumn tcEstadoECSelec;
    public TableColumn tcNombrePSelec;
    public TableColumn tcEstadoPSelec;
    public EquipoComputo equipoComputo;
    public Periferico periferico;
    public TableView tableViewECSeleccionados;
    public TableView tableViewPerifericosSeleccionados;

    public void btnAceptar(ActionEvent actionEvent) throws IOException {
        if(!cbNuevoEstado.getSelectionModel().isEmpty()){
            if(!(equipoComputo == null)){
                equipoComputo.setEstado((String) cbNuevoEstado.getSelectionModel().getSelectedItem());
                tableViewECSeleccionados.getItems().remove(equipoComputo);
                tableViewECSeleccionados.refresh();
                tableViewECSeleccionados.getItems().add(equipoComputo);
                tcCodigoSelec.setCellValueFactory(new PropertyValueFactory<>("codigoEquipo"));
                tcEstadoECSelec.setCellValueFactory(new PropertyValueFactory<>("estado"));

                Stage ventanaNuevoEstado = (Stage) cbNuevoEstado.getScene().getWindow();
                ventanaNuevoEstado.close();
            }else{
                periferico.setEstado((String) cbNuevoEstado.getSelectionModel().getSelectedItem());
                tableViewPerifericosSeleccionados.getItems().remove(periferico);
                tableViewPerifericosSeleccionados.refresh();
                tableViewPerifericosSeleccionados.getItems().add(periferico);
                tcNombrePSelec.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                tcEstadoPSelec.setCellValueFactory(new PropertyValueFactory<>("estado"));

                Stage ventanaNuevoEstado = (Stage) cbNuevoEstado.getScene().getWindow();
                ventanaNuevoEstado.close();
            }
        }else{
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe seleccionar un nuevo estado para continuar.").showAndWait();
        }
    }

    public EquipoComputo obtenerECRepetido(EquipoComputo equipoComputoComparacion) {
        ObservableList<EquipoComputo> listaEquipos = tableViewECSeleccionados.getItems();
        EquipoComputo equipoIgual = null;

        for (EquipoComputo equipo : listaEquipos) {
            if (equipo.getCodigoEquipo().equals(equipoComputoComparacion.getCodigoEquipo())) {
                equipoIgual = equipo;
            }
        }

        return equipoIgual;
    }

    public Periferico obtenerPRepetido(Periferico perifericoComparacion) {
        ObservableList<Periferico> listaPerifericos = tableViewPerifericosSeleccionados.getItems();
        Periferico perifericoIgual = null;

        for (Periferico periferico : listaPerifericos) {
            if (periferico.getCodigo().equals(perifericoComparacion.getCodigo())) {
                perifericoIgual = periferico;
            }
        }

        return perifericoIgual;
    }

    public void setEquipoSeleccionado(EquipoComputo equipo) {
        this.equipoComputo = equipo;
    }

    public void llenarComboBoxEstados() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Disponible",
                "No disponible",
                "En mantenimiento"
        );
        cbNuevoEstado.setItems(items);
    }

    public void setPerifericoSeleccionado(Periferico periferico) {
        this.periferico = periferico;
    }
}
