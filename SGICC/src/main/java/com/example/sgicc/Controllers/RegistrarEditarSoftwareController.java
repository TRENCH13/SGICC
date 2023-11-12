package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.SoftwareDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Software;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrarEditarSoftwareController {

    public int rolActual;

    public boolean esEdicion;

    public Software softwareEdicion;

    @FXML
    private TextField tbNombre;

    @FXML
    private TextField tbTipoSoftware;

    @FXML
    private ToggleGroup licenciaRadioButton;

    @FXML
    private RadioButton rbLicenciaNo;

    @FXML
    private RadioButton rbLicenciaSi;

    @FXML
    public Label lbTítulo;

    @FXML
    public Label lbUser;

    @FXML
    private TextField tbVersion;

    @FXML
    private TextField tbTipoLicencia;

    @FXML
    public Button btnRegistrarEditar;

    @FXML
    void btnRegistrarEditarSoftware(ActionEvent event) {
        if (!esEdicion) {

            if (validarCampos()) {
                if (Alerta.crearAlertaConfirmacion("Registrar Software", "¿Seguro quiere registrar el Software?", "Al aceptar, el Software se Guardará.")) {
                    RadioButton radioButtonSeleccionado = (RadioButton) licenciaRadioButton.getSelectedToggle();
                    SoftwareDAO.registrarSoftware(tbNombre.getText(), radioButtonSeleccionado.getText().equals("Si"), tbTipoSoftware.getText(), tbVersion.getText(), tbTipoLicencia.getText());
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {

            if (validarCampos()) {
                if (Alerta.crearAlertaConfirmacion("Editar Software", "¿Seguro quiere editar el Software?", "Al aceptar, el Software se Guardará.")) {
                    RadioButton radioButtonSeleccionado = (RadioButton) licenciaRadioButton.getSelectedToggle();
                    SoftwareDAO.editarSoftware(softwareEdicion.getIdSoftware(), tbNombre.getText(), radioButtonSeleccionado.getText().equals("Si"), tbTipoSoftware.getText(), tbVersion.getText(), tbTipoLicencia.getText());
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @FXML
    void btnRegresar(ActionEvent event) throws IOException {
        regresarVentana();
    }

    public void regresarVentana() throws IOException {
        Stage stage = (Stage) lbUser.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Software.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        SoftwareController ventanasoftware = fxmlLoader.getController();
        ventanasoftware.llenarTabla();
        ventanasoftware.lbUser.setText(lbUser.getText());
        ventanasoftware.rolActual = rolActual;

        if (rolActual != 1) {
            ventanasoftware.btnRegistrar.setVisible(false);
            ventanasoftware.btnEditar.setVisible(false);
            ventanasoftware.btnEliminar.setVisible(false);
        }

        stage.setTitle("Software");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    public void setEdicion() {
        tbNombre.setText(softwareEdicion.getNombreSoftware());
        tbVersion.setText(softwareEdicion.getVersion());
        tbTipoSoftware.setText(softwareEdicion.getTipoSoftware());
        if (softwareEdicion.isConLicencia()) {
            rbLicenciaSi.fire();
        } else {
            rbLicenciaNo.fire();
        }
        tbTipoLicencia.setText(softwareEdicion.getTipoLicencia());
    }

    public boolean validarCampos() {
        if (tbNombre.getText().isEmpty() || tbVersion.getText().isEmpty() || tbTipoSoftware.getText().isEmpty() || tbTipoLicencia.getText().isEmpty() || licenciaRadioButton.getSelectedToggle() == null) {
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
            return false;
        }
        return true;
    }

}

