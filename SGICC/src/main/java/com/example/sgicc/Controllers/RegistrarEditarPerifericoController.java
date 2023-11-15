package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.PerifericoDAO;
import com.example.sgicc.DAOs.TipoPerifericoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Periferico;
import com.example.sgicc.Modelos.TipoPeriferico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrarEditarPerifericoController {

    public int idCentroComputo;

    public int rolActual;

    public boolean esEdicion;

    public Periferico perifericoEditar;

    @FXML
    public Label lbTítulo;

    @FXML
    private TextField tbCodigo;

    @FXML
    private TextField tbNombre;

    @FXML
    public Button btnRegistrarEditar;

    @FXML
    private TextArea taEspecificaciones;

    @FXML
    public ComboBox<TipoPeriferico> cbTipo;

    @FXML
    void btnRegistrarEditar(ActionEvent event) {
        if (!esEdicion) {
            if (validarCampos()) {
                if (Alerta.crearAlertaConfirmacion("Registrar Periférico", "¿Seguro quiere registrar el Periférico?", "Al aceptar, el Periférico se Guardará.")) {
                    PerifericoDAO.registrarPeriferico(taEspecificaciones.getText(), tbCodigo.getText(), tbNombre.getText(), cbTipo.getValue().getIdTipoPeriferico(), idCentroComputo);
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            if (validarCampos()) {
                if (Alerta.crearAlertaConfirmacion("Editar Periférico", "¿Seguro quiere editar el Periférico?", "Al aceptar, el Periférico se Actualizará.")) {
                    PerifericoDAO.editarPeriferico(perifericoEditar.getIdPeriferico(), taEspecificaciones.getText(), tbCodigo.getText(), tbNombre.getText(), cbTipo.getValue().getIdTipoPeriferico(), idCentroComputo);
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
        if (!tbCodigo.getText().isEmpty() || cbTipo.getSelectionModel().getSelectedItem() != null || !tbNombre.getText().isEmpty() || !taEspecificaciones.getText().isEmpty()) {
            if (Alerta.crearAlertaConfirmacion("Salir", "¿Está seguro de salir?", "Si sale ahora no se guardará el Periférico.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }
    }

    private boolean validarCampos() {
        if (tbCodigo.getText().isEmpty() || cbTipo.getSelectionModel().getSelectedItem() == null || tbNombre.getText().isEmpty() || taEspecificaciones.getText().isEmpty()) {
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
            return false;
        }
        return true;
    }

    void regresarVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Perifericos.fxml"));
        Parent root = fxmlLoader.load();
        PerifericosController ventanaEquiposComputo = fxmlLoader.getController();
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

    void setEsEdicion(Periferico perifericoEditar) {
        tbCodigo.setText(perifericoEditar.getCodigo());
        for (TipoPeriferico item : cbTipo.getItems()) {
            if (item.getIdTipoPeriferico() == perifericoEditar.getIdTipoPeriferico()) {
                cbTipo.setValue(item);
                System.out.println(item.toString());
                break;
            }
        }
        tbNombre.setText(perifericoEditar.getNombre());
        taEspecificaciones.setText(perifericoEditar.getEspecificaciones());
    }

    public void llenarComboBox() {
        List<TipoPeriferico> items = TipoPerifericoDAO.consultarTiposPeriferico();
        cbTipo.getItems().addAll(items);

        cbTipo.setConverter(new StringConverter<TipoPeriferico>() {
            @Override
            public String toString(TipoPeriferico tipoPeriferico) {
                return tipoPeriferico.getTipoPeriferico();
            }

            @Override
            public TipoPeriferico fromString(String s) {
                for (TipoPeriferico item : cbTipo.getItems()) {
                    if (item.getTipoPeriferico().equals(s)) {
                        return item;
                    }
                }
                return null;
            }
        });
    }

}
