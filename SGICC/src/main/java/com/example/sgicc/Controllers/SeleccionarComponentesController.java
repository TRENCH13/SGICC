package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.BitacoraDAO;
import com.example.sgicc.DAOs.EquipoComputoDAO;
import com.example.sgicc.DAOs.PerifericoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Bitacora;
import com.example.sgicc.Modelos.EquipoComputo;
import com.example.sgicc.Modelos.Periferico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SeleccionarComponentesController {
    public Label lbTítulo;
    public int rolActual;
    public int idCentroComputo;
    public TableView tvEquiposComputo;
    public TableColumn tcCodigo;
    public TableColumn tcEstadoEC;
    public TableView tvPerifericos;
    public TableColumn tcNombre;
    public TableColumn tcEstadoPeriferico;
    public TableView tvEquiposComputoSeleccionados;
    public TableColumn tcCodigoSelec;
    public TableColumn tcEstadoECSelec;
    public TableView tvPerifericosSeleccionados;
    public TableColumn tcNombreSelec;
    public TableColumn tcEstadoPerifericoSelec;
    public Bitacora bitacoraActual;

    public void btnRegistrar(ActionEvent actionEvent) throws IOException {
        if (Alerta.crearAlertaConfirmacion("Registrar Bitácora", "¿Está seguro de registrar la bitácora?", "Al aceptar, la bitácora se guardará y no podrá ser modificada.")){
            BitacoraDAO.registrarBitacora(bitacoraActual.getReporteBitacora(), bitacoraActual.getTitulo(), bitacoraActual.getFechaRegistro(), bitacoraActual.getIdCentroDeComputo());
            boolean modificacionECExitosa = false;
            boolean modificacionPExitosa = false;

            if(!tvEquiposComputoSeleccionados.getItems().isEmpty()){
                ObservableList<EquipoComputo> equiposCambiarEstado = tvEquiposComputoSeleccionados.getItems();
                for (EquipoComputo equipo : equiposCambiarEstado) {
                    if(EquipoComputoDAO.cambiarEstadoEquipoComputo(equipo.getIdEquipoComputo(), equipo.getEstado())){
                        modificacionECExitosa = true;
                    }
                }
                if(modificacionECExitosa == true){
                    Alerta.crearAlertaInformacion("Actualización Exitosa", "Se han actualizado los estados de los Equipos de Cómputo.", "").showAndWait();
                }else{
                    Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se han actualizado los estados de los Equipos de Cómputo.").showAndWait();
                }
            }
            if(!tvPerifericosSeleccionados.getItems().isEmpty()){
                ObservableList<Periferico> perifericosCambiarEstado = tvPerifericosSeleccionados.getItems();
                for (Periferico periferico : perifericosCambiarEstado) {
                    if(PerifericoDAO.cambiarEstadoPeriferico(periferico.getIdPeriferico(), periferico.getEstado())){
                        modificacionPExitosa = true;
                    }
                }
                if(modificacionPExitosa == true){
                    Alerta.crearAlertaInformacion("Actualización Exitosa", "Se han actualizado los estados de los Periféricos.", "").showAndWait();
                }else{
                    Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se han actualizado los estados de los Periféricos.").showAndWait();
                }
            }

            regresarConsultarBitacora();
        }
    }

    public void btnRegresar(ActionEvent actionEvent) throws IOException{
        if (!tvEquiposComputoSeleccionados.getItems().isEmpty() || !tvPerifericosSeleccionados.getItems().isEmpty()){
            if (Alerta.crearAlertaConfirmacion("Regresar", "¿Está seguro de regresar?", "Si regresa ahora no se guardarán los datos.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }
    }

    void regresarVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarBitacora.fxml"));
        Parent root = fxmlLoader.load();
        RegistrarBitacoraController bitacoraController = fxmlLoader.getController();
        bitacoraController.rolActual = rolActual;
        bitacoraController.lbTítulo.setText("Registrar Bitácora");
        bitacoraController.bitacoraActual = bitacoraActual;
        bitacoraController.idCentroComputo = idCentroComputo;
        bitacoraController.llenarCamposBitacoraActual();

        Scene scene = lbTítulo.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        apVistaMenuCC.getChildren().setAll(root);
    }

    void regresarConsultarBitacora() throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Bitacora.fxml"));
        Parent root = fxmlLoader.load();
        BitacoraController bitacoraController = fxmlLoader.getController();
        bitacoraController.rolActual = rolActual;
        bitacoraController.idCentroComputo = idCentroComputo;
        bitacoraController.llenarTabla(idCentroComputo);
        bitacoraController.inicializarBusquedaBitacoras();

        Scene scene = lbTítulo.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        apVistaMenuCC.getChildren().setAll(root);
    }

    public void llenarTablaEquiposComputo() {
        tvEquiposComputo.setItems(null);
        List<EquipoComputo> equiposTabla = EquipoComputoDAO.consultarEquiposComputo(idCentroComputo);

        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoEquipo"));
        tcEstadoEC.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<EquipoComputo> equiposObservable = FXCollections.observableArrayList(equiposTabla);
        tvEquiposComputo.setItems(equiposObservable);
    }

    public void llenarTablaPerifericos() {
        tvPerifericos.setItems(null);

        List<Periferico> perifericosTabla = PerifericoDAO.consultarPerifericos(idCentroComputo);
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcEstadoPeriferico.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<Periferico> perifericosObservable = FXCollections.observableArrayList(perifericosTabla);
        tvPerifericos.setItems(perifericosObservable);
    }

    public void btnSeleccionarEC(ActionEvent actionEvent) throws IOException{
        if (!tvEquiposComputo.getSelectionModel().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/SeleccionarNuevoEstado.fxml"));
            Parent root = fxmlLoader.load();

            SeleccionarNuevoEstadoController seleccionarNuevoEstadoController = fxmlLoader.getController();
            seleccionarNuevoEstadoController.equipoComputo = null;
            seleccionarNuevoEstadoController.setEquipoSeleccionado((EquipoComputo) tvEquiposComputo.getSelectionModel().getSelectedItem());
            seleccionarNuevoEstadoController.llenarComboBoxEstados();
            seleccionarNuevoEstadoController.tableViewECSeleccionados = tvEquiposComputoSeleccionados;
            seleccionarNuevoEstadoController.tcCodigoSelec = tcCodigoSelec;
            seleccionarNuevoEstadoController.tcEstadoECSelec = tcEstadoECSelec;

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Seleccionar nuevo estado");
            nuevaVentana.initModality(Modality.WINDOW_MODAL);
            nuevaVentana.initOwner(tvEquiposComputo.getScene().getWindow());

            Scene nuevaScene = new Scene(root);
            nuevaVentana.setScene(nuevaScene);
            nuevaVentana.show();
        } else {
            Alerta.crearAlertaError("Error", "Error al agregar el equipo de cómputo.", "Debe seleccionar el equipo de cómputo que desea agregar.").showAndWait();
        }
    }

    public void btnQuitarEC(ActionEvent actionEvent) {
        if(!tvEquiposComputoSeleccionados.getSelectionModel().isEmpty()){
            tvEquiposComputoSeleccionados.getItems().remove(tvEquiposComputoSeleccionados.getSelectionModel().getSelectedItem());
            tvEquiposComputoSeleccionados.refresh();
        }else{
            Alerta.crearAlertaError("Error", "Error al quitar el equipo de cómputo.", "Debe seleccionar el equipo de cómputo que desea quitar.").showAndWait();
        }
    }

    public void btnQuitarP(ActionEvent actionEvent) {
        if(!tvPerifericosSeleccionados.getSelectionModel().isEmpty()){
            tvPerifericosSeleccionados.getItems().remove(tvPerifericosSeleccionados.getSelectionModel().getSelectedItem());
            tvPerifericosSeleccionados.refresh();
        }else{
            Alerta.crearAlertaError("Error", "Error al quitar el periférico.", "Debe seleccionar el periférico que desea quitar.").showAndWait();
        }
    }

    public void btnSeleccionarP(ActionEvent actionEvent) throws IOException {
        if (!tvPerifericos.getSelectionModel().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/SeleccionarNuevoEstado.fxml"));
            Parent root = fxmlLoader.load();

            SeleccionarNuevoEstadoController seleccionarNuevoEstadoController = fxmlLoader.getController();
            seleccionarNuevoEstadoController.periferico = null;
            seleccionarNuevoEstadoController.setPerifericoSeleccionado((Periferico) tvPerifericos.getSelectionModel().getSelectedItem());
            seleccionarNuevoEstadoController.llenarComboBoxEstados();
            seleccionarNuevoEstadoController.tableViewPerifericosSeleccionados = tvPerifericosSeleccionados;
            seleccionarNuevoEstadoController.tcNombrePSelec = tcNombreSelec;
            seleccionarNuevoEstadoController.tcEstadoPSelec = tcEstadoPerifericoSelec;

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Seleccionar nuevo estado");
            nuevaVentana.initModality(Modality.WINDOW_MODAL);
            nuevaVentana.initOwner(tvPerifericos.getScene().getWindow());

            Scene nuevaScene = new Scene(root);
            nuevaVentana.setScene(nuevaScene);
            nuevaVentana.show();
        } else {
            Alerta.crearAlertaError("Error", "Error al agregar el periférico.", "Debe seleccionar el periférico que desea agregar.").showAndWait();
        }
    }
}
