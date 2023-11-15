package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.CentroComputoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.CentroComputo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrarEditarCentroComputoController {

    private int rolActual = 0;

    private boolean esEdicion;

    public CentroComputo centroEditar;

    @FXML
    public Label lbTítulo;

    @FXML
    public Label lbUser;

    @FXML
    public Button btnRegistrarEditar;

    @FXML
    private TextField tbCodigoCC;

    @FXML
    private TextField tbEdificio;

    public void setIdRol(int idRol) {
        this.rolActual = idRol;
    }

    public void setEsEdicion(boolean esEdicion) { this.esEdicion = esEdicion; }

    @FXML
    void btnRegistrarEditar(ActionEvent event) {
        if (!esEdicion){
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Registrar Centro de Cómputo", "¿Seguro quiere registrar el Centro de Cómputo?", "Al aceptar, el Centro de Cómputo se Guardará.")){
                    CentroComputoDAO.registrarCentroComputo(tbCodigoCC.getText(), tbEdificio.getText());
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else{
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Editar Centro de Cómputo", "¿Seguro quiere editar el Centro de Cómputo?", "Al aceptar, el Centro de Cómputo se Actualizará.")){
                    CentroComputoDAO.editarCentroComputo(centroEditar.getIdCentroDeComputo(), tbCodigoCC.getText(), tbEdificio.getText());
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean validarCampos() {
        if (tbCodigoCC.getText().isEmpty() || tbEdificio.getText().isEmpty()) {
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {

        if (!tbCodigoCC.getText().isEmpty() || !tbEdificio.getText().isEmpty()){
            if (Alerta.crearAlertaConfirmacion("Salir", "¿Está seguro de salir?", "Si sale ahora no se guardará el Centro de Cómputo.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }
    }

    void regresarVentana() throws IOException {
        Stage stage = (Stage) lbTítulo.getScene().getWindow();
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

    public void setEdicion(){
        tbCodigoCC.setText(centroEditar.getCodigoCC());
        tbEdificio.setText(centroEditar.getEdificio());
    }

}