package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.UsuarioDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {

    String numPersonalBusqueda;
    String contraseniaBusqueda;

    Usuario usuarioSesion;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private Button btnIniciaSesion;

    @FXML
    private TextField tfNumPersonal;

    @FXML
    void btnIniciaSesion(ActionEvent event) throws IOException {
        if (validarCampos()) {
            usuarioSesion = UsuarioDAO.IniciarSesion(numPersonalBusqueda, contraseniaBusqueda);
            if (usuarioSesion != null) {

                btnIniciaSesion.setStyle("-fx-background-color: white; -fx-text-fill: black;");

                FXMLLoader fxmlLoader;
                Stage stage = new Stage();
                Scene scene;
                Label lbUser;
                MenuGeneralController menuGeneral;

                switch (usuarioSesion.getIdRol()){
                    case 1:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuGeneral.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        menuGeneral = fxmlLoader.getController();
                        menuGeneral.setIdRol(usuarioSesion.getIdRol());
                        menuGeneral.asignarCCRecuperados();
                        lbUser = (Label) scene.lookup("#lbUser");
                        lbUser.setText(usuarioSesion.getNombre()+" "+usuarioSesion.getApellido());
                        stage.setTitle("Encargado");
                        stage.setScene(scene);
                        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                        stage.show();
                        break;
                    case 2:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuGeneral.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        menuGeneral = fxmlLoader.getController();
                        menuGeneral.setIdRol(usuarioSesion.getIdRol());
                        menuGeneral.btnUsuarios.setVisible(false);
                        menuGeneral.btnCentrosComputo.setVisible(false);
                        menuGeneral.asignarCCRecuperados();
                        lbUser = (Label) scene.lookup("#lbUser");
                        lbUser.setText(usuarioSesion.getNombre()+" "+usuarioSesion.getApellido());
                        stage.setTitle("Administrativo");
                        stage.setScene(scene);
                        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                        stage.show();
                    case 3:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Menugeneral.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        menuGeneral = fxmlLoader.getController();
                        menuGeneral.setIdRol(usuarioSesion.getIdRol());
                        menuGeneral.btnUsuarios.setVisible(false);
                        menuGeneral.btnCentrosComputo.setVisible(false);
                        menuGeneral.asignarCCRecuperados();
                        lbUser = (Label) scene.lookup("#lbUser");
                        lbUser.setText(usuarioSesion.getNombre()+" "+usuarioSesion.getApellido());
                        stage.setTitle("Técnico Académico");
                        stage.setScene(scene);
                        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                        stage.show();
                        break;
                }

                Stage stageActual = (Stage) tfNumPersonal.getScene().getWindow();
                stageActual.close();

            } else {
                Alerta.crearAlertaError("Error al iniciar sesión", "Credenciales no coinciden con ningun usuario", "Vuelve a intentarlo.").showAndWait();
            }
        }
    }

    private boolean validarCampos(){
        if (!tfNumPersonal.getText().isEmpty() && !pfContrasenia.getText().isEmpty()){
            numPersonalBusqueda = tfNumPersonal.getText();
            contraseniaBusqueda = pfContrasenia.getText();
            return true;
        }else{
            Alerta.crearAlertaError("Error", "Error el el Inicio de Sesión", "Error a ingresar el Numero de Personal y/o Contraseña.").showAndWait();
            return false;
        }
    }
}