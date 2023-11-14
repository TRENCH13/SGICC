package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.UsuarioDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Rol;
import com.example.sgicc.Modelos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class RegistrarEditarUsuarioController {
    public TextField tbNombre;
    public TextField tbApellido;
    public TextField tbCorreoInstitucional;
    public TextField tbNumeroPersonal;
    public PasswordField tbPassword;
    public ComboBox<Rol> cbRol;

    public Label lbUser;

    public Label lbTítulo;

    public Button btnRegistrarEditar;

    public int rolActual;
    public boolean esEdicion;
    public Usuario usuarioEdicion;

    public void btnRegresar(ActionEvent actionEvent) throws IOException {

        if (!tbNombre.getText().isEmpty() || !tbApellido.getText().isEmpty() || !tbCorreoInstitucional.getText().isEmpty() || !tbNumeroPersonal.getText().isEmpty() || !tbPassword.getText().isEmpty() || !cbRol.getSelectionModel().isEmpty()){
            if (Alerta.crearAlertaConfirmacion("Salir", "¿Esta seguro de salir?", "Si sale ahora no se guardará el Usuario.")){
                regresarVentana();
            }
        }else{
            regresarVentana();
        }

    }

    public void btnRegistrarEditar(ActionEvent actionEvent) {
        if (!esEdicion){
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Registrar Usuario", "¿Seguro quiere registrar este Usuario?", "Al aceptar, el Usuario se Guardará.")){
                    Rol rolSeleccionado = (Rol) cbRol.getSelectionModel().getSelectedItem();
                    UsuarioDAO.registrarUsuario(tbNombre.getText(), tbApellido.getText(), tbCorreoInstitucional.getText(), tbNumeroPersonal.getText(), tbPassword.getText(), rolSeleccionado.getIdRol());
                    try {
                        regresarVentana();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else{
            if (validarCampos()){
                if (Alerta.crearAlertaConfirmacion("Editar Usuario", "¿Seguro quiere editar este Usuario?", "Al aceptar, el Usuario se Actualizará.")){
                    Rol rolSeleccionado = (Rol) cbRol.getSelectionModel().getSelectedItem();
                    UsuarioDAO.editarUsuario(usuarioEdicion.getIdUsuario(), tbNombre.getText(), tbApellido.getText(), tbCorreoInstitucional.getText(), tbNumeroPersonal.getText(), tbPassword.getText(), rolSeleccionado.getIdRol());
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
        if (tbNombre.getText().isEmpty() || tbApellido.getText().isEmpty() || tbCorreoInstitucional.getText().isEmpty() || tbNumeroPersonal.getText().isEmpty() || tbPassword.getText().isEmpty() || cbRol.getSelectionModel().isEmpty()) {
            Alerta.crearAlertaError("Error", "Campos vacíos", "Debe llenar todos los campos para continuar.").showAndWait();
            return false;
        }
        return true;
    }

    public void setIdRol(int rolActual) {
        this.rolActual = rolActual;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    void regresarVentana() throws IOException {
        Stage stage = (Stage) tbNombre.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Usuarios.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        UsuariosController usuariosController = fxmlLoader.getController();
        usuariosController.setIdRol(rolActual);
        usuariosController.lbTítulo.setText("Usuarios");
        usuariosController.llenarTabla();

        stage.setScene(scene);
        Label lbUserNext = (Label) scene.lookup("#lbUser");
        if (esEdicion) {
            usuariosController.lbUser.setText(tbNombre.getText()+" "+tbApellido.getText());
        }
        stage.setTitle("Usuarios");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    public void setEdicion(){
        tbNombre.setText(usuarioEdicion.getNombre());
        tbApellido.setText(usuarioEdicion.getApellido());
        tbCorreoInstitucional.setText(usuarioEdicion.getCorreoInstitucional());
        tbNumeroPersonal.setText(usuarioEdicion.getNumPersonal());
        tbPassword.setText(usuarioEdicion.getContrasenia());

        for (Rol item : cbRol.getItems()) {
            if (item.getIdRol() == usuarioEdicion.getIdRol()) {
                cbRol.setValue(item);
                System.out.println(item);
                break;
            }
        }
    }

    public void llenarComboBox(){
        List<Rol> items = UsuarioDAO.consultarRoles();
        cbRol.getItems().clear();
        cbRol.getItems().addAll(items);

        cbRol.setConverter(new StringConverter<Rol>() {
            @Override
            public String toString(Rol rol) { return rol.getRol(); }

            @Override
            public Rol fromString(String s) {
                for (Rol item : cbRol.getItems()) {
                    if (item.getRol().equals(s)){
                        return item;
                    }
                }
                return null;
            }
        });
    }
}
