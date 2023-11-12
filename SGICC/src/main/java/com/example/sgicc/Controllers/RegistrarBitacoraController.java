package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Bitacora;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class RegistrarBitacoraController {

    public int rolActual;
    public int idCentroComputo;
    public TextField tbTituloBitacora;
    public TextArea taDescripcion;
    public Bitacora bitacoraActual;

    public void btnSiguiente(ActionEvent actionEvent) throws IOException {
        if(!tbTituloBitacora.getText().isEmpty() && !taDescripcion.getText().isEmpty()){
            Calendar calendar = Calendar.getInstance();
            java.util.Date utilDate = calendar.getTime();
            Date sqlDate = new Date(utilDate.getTime());
            bitacoraActual = new Bitacora(taDescripcion.getText(), tbTituloBitacora.getText(), sqlDate, idCentroComputo);

            Stage stage = (Stage) tbTituloBitacora.getScene().getWindow();
            Scene scene = stage.getScene();
            AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/SeleccionarComponentes.fxml"));
            Parent root = fxmlLoader.load();

            SeleccionarComponentesController seleccionarComponentesController = fxmlLoader.getController();
            seleccionarComponentesController.rolActual = rolActual;
            seleccionarComponentesController.idCentroComputo = idCentroComputo;
            seleccionarComponentesController.bitacoraActual = bitacoraActual;
            seleccionarComponentesController.llenarTablaEquiposComputo();
            seleccionarComponentesController.llenarTablaPerifericos();

            apVistaMenuCC.getChildren().setAll(root);
        }else{
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
        }
    }

    public void btnRegresar(ActionEvent actionEvent) throws IOException {
        if (!tbTituloBitacora.getText().isEmpty() || !taDescripcion.getText().isEmpty()){
            if (Alerta.crearAlertaConfirmacion("Salir", "¿Está seguro de regresar?", "Si sale ahora no se guardarán los datos.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }
    }

    void regresarVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Bitacora.fxml"));
        Parent root = fxmlLoader.load();
        BitacoraController bitacoraController = fxmlLoader.getController();
        bitacoraController.rolActual = rolActual;
        bitacoraController.idCentroComputo = idCentroComputo;
        bitacoraController.llenarTabla(idCentroComputo);
        bitacoraController.inicializarBusquedaBitacoras();

        Scene scene = tbTituloBitacora.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        apVistaMenuCC.getChildren().setAll(root);
    }

    void llenarCamposBitacoraActual() {
        tbTituloBitacora.setText(bitacoraActual.getTitulo());
        taDescripcion.setText(bitacoraActual.getReporteBitacora());
    }
}
