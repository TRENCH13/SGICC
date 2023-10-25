package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.EquipoComputoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.EquipoComputo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegistrarEditarEquipoComputoController {

    public int idCentroComputo;

    public int rolActual;

    public boolean esEdicion;

    public EquipoComputo equipoEditar;

    @FXML
    private TextField tbGabineteEQ;

    @FXML
    private TextField tbProcesadorEQ;

    @FXML
    private TextField tbAlmacenamientoEQ;

    @FXML
    private TextField tbSistemaOperativoEQ;

    @FXML
    public Label lbTítulo;

    @FXML
    private TextField tbTarjetaMadreEQ;

    @FXML
    private TextField tbCodigoEQ;

    @FXML
    public Button btnRegistrarEditar;

    @FXML
    private TextField tbMemoriaEQ;

    @FXML
    void btnRegistrarEditar(ActionEvent event) {
        if (!esEdicion){
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Registrar Equipo de Cómputo", "¿Seguro quiere registrar el Equipo de Cómputo?", "Al aceptar, el Equipo de Computo se Guardará.")){
                    EquipoComputoDAO.registrarEquipoComputo(tbCodigoEQ.getText(), tbProcesadorEQ.getText(), tbMemoriaEQ.getText(), tbAlmacenamientoEQ.getText(), tbTarjetaMadreEQ.getText(), tbGabineteEQ.getText(), tbSistemaOperativoEQ.getText(), "Disponible", idCentroComputo);
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else{
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Editar Centro de Cómputo", "¿Seguro quiere editar el Centro de Cómputo?", "Al aceptar, el Centro de Computo se Actualizará.")){
                    EquipoComputoDAO.editarEquipoComputo(equipoEditar.getIdEquipoComputo(), tbCodigoEQ.getText(), tbProcesadorEQ.getText(), tbMemoriaEQ.getText(), tbAlmacenamientoEQ.getText(), tbTarjetaMadreEQ.getText(), tbGabineteEQ.getText(), tbSistemaOperativoEQ.getText(), idCentroComputo);
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {

        if (!tbGabineteEQ.getText().isEmpty() || !tbProcesadorEQ.getText().isEmpty() || !tbAlmacenamientoEQ.getText().isEmpty() || !tbTarjetaMadreEQ.getText().isEmpty() || !tbCodigoEQ.getText().isEmpty() || !tbMemoriaEQ.getText().isEmpty()){
            if (Alerta.crearAlertaConfirmacion("Salir", "¿Esta seguro de salir?", "Si sale ahora no se guardará el Centro de Cómputo.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }

    }

    private boolean validarCampos() {
        if (tbCodigoEQ.getText().isEmpty() || tbProcesadorEQ.getText().isEmpty() || tbMemoriaEQ.getText().isEmpty() || tbAlmacenamientoEQ.getText().isEmpty() || tbTarjetaMadreEQ.getText().isEmpty() || tbSistemaOperativoEQ.getText().isEmpty() || tbGabineteEQ.getText().isEmpty()) {
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
            return false;
        }
        return true;
    }


    void regresarVentana() throws IOException {
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

        Scene scene = lbTítulo.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        apVistaMenuCC.getChildren().setAll(root);
    }

    void setEdicion(EquipoComputo equipoEditar){
        tbCodigoEQ.setText(equipoEditar.getCodigoEquipo());
        tbProcesadorEQ.setText(equipoEditar.getProcesador());
        tbMemoriaEQ.setText(equipoEditar.getMemoria());
        tbAlmacenamientoEQ.setText(equipoEditar.getAlmacenamiento());
        tbTarjetaMadreEQ.setText(equipoEditar.getTarjetaMadre());
        tbGabineteEQ.setText(equipoEditar.getGabinete());
        tbSistemaOperativoEQ.setText(equipoEditar.getSistemaOperativo());
    }

}

