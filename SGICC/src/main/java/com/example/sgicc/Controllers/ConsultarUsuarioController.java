package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.UsuarioDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ConsultarUsuarioController {
    public Button btnAgregar;
    public Label lbUser;
    public Label lbTítulo;
    public int rolActual;
    public TableView tvUsuarios;
    public TableColumn tcNombre;
    public TableColumn tcApellido;
    public TableColumn tcCorreoInstitucional;
    public TableColumn tcNumeroPersonal;
    public TableColumn tcRol;

    public void btnRegresar(ActionEvent actionEvent) {
        if (Alerta.crearAlertaConfirmacion("Regresar", "¿Está seguro de regresar?", "Seleccione 'Aceptar' para regresar al menú general.")){
            try {
                regresarVentana();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnEliminar(ActionEvent actionEvent) {
        if (!tvUsuarios.getSelectionModel().isEmpty()){

            if (Alerta.crearAlertaConfirmacion("Eliminar Usuario", "¿Está seguro de eliminar este usuario?", "Si desea continuar de clic en 'Aceptar'.")){
                Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
                UsuarioDAO.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
                llenarTabla();
            }

        }else {
            Alerta.crearAlertaError("Error", "Error al eliminar al usuario.", "Debes seleccionar al usuario que deseas eliminar.").showAndWait();
        }
    }

    public void btnEditar(ActionEvent actionEvent) throws IOException {

        if (!tvUsuarios.getSelectionModel().isEmpty()){

            Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarUsuario.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            RegistrarEditarUsuarioController usuarioController = fxmlLoader.getController();
            usuarioController.btnRegistrarEditar.setText("Editar Usuario");
            usuarioController.lbUser.setText(lbUser.getText());
            usuarioController.lbTítulo.setText("Edición de Usuario");
            usuarioController.setIdRol(rolActual);
            usuarioController.setEsEdicion(true);
            usuarioController.btnRegistrarEditar.setText("Editar Usuario");
            usuarioController.usuarioEdicion = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            usuarioController.llenarComboBox();
            usuarioController.setEdicion();
            stage.setTitle("Editar Usuario");
            stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
            stage.show();

        }else {
            Alerta.crearAlertaError("Error", "Error al editar al usuario.", "Debes seleccionar al usuario que deseas editar.").showAndWait();
        }

    }

    public void btnAgregar(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) btnAgregar.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarUsuario.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        RegistrarEditarUsuarioController usuarioController = fxmlLoader.getController();
        usuarioController.btnRegistrarEditar.setText("Registrar usuario");
        usuarioController.lbUser.setText(lbUser.getText());
        usuarioController.lbTítulo.setText("Registro de usuario");
        usuarioController.setIdRol(rolActual);
        usuarioController.setEsEdicion(false);
        usuarioController.btnRegistrarEditar.setText("Registrar Usuario");
        usuarioController.llenarComboBox();
        stage.setTitle("Registro de usuario");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    public void setIdRol(int rolActual) {
        this.rolActual = rolActual;
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

    public void llenarTabla() {
        tvUsuarios.setItems(null);

        List<Usuario> usuariosTabla = UsuarioDAO.ObtenerUsuarios();

        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tcCorreoInstitucional.setCellValueFactory(new PropertyValueFactory<>("correoInstitucional"));
        tcNumeroPersonal.setCellValueFactory(new PropertyValueFactory<>("numPersonal"));
        tcRol.setCellValueFactory(new RolStringConverter());

        ObservableList<Usuario> usuariosObservable = FXCollections.observableArrayList(usuariosTabla);

        tvUsuarios.setItems(usuariosObservable);
    }

    public class RolStringConverter implements Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>> {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuario, String> param) {
            int idRol = param.getValue().getIdRol();
            String rolString = "";

            switch (idRol) {
                case 1:
                    rolString = "Encargado";
                    break;
                case 2:
                    rolString = "Personal Administrativo";
                    break;
                case 3:
                    rolString = "Técnico Académico";
                    break;
            }

            return new SimpleStringProperty(rolString);
        }
    }
}
