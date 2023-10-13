package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.UsuarioDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {

    String numPersonalBusqueda;
    String contraseniaBusqueda;

    Usuario usuarioSesion;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private ImageView ivImagenInicioSesion;

    @FXML
    private TextField tfNumPersonal;

    @FXML
    void btnIniciaSesion(ActionEvent event) throws IOException {
        if (validarCampos()) {
            usuarioSesion = UsuarioDAO.IniciarSesion(numPersonalBusqueda, contraseniaBusqueda);
            if (usuarioSesion != null) {

                FXMLLoader fxmlLoader;
                Stage stage = new Stage();
                Scene scene;

                switch (usuarioSesion.getIdRol()){
                    case 1:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuEncargado.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        stage.setTitle("Encargado");
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case 2:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuGeneral.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        stage.setTitle("Administrativo");
                        stage.setScene(scene);
                        stage.show();
                        break;
                    case 3:
                        fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/Menugeneral.fxml"));
                        scene = new Scene(fxmlLoader.load());
                        stage.setTitle("Técnico Académico");
                        stage.setScene(scene);
                        stage.show();
                        break;
                }

                Stage stageActual = (Stage) tfNumPersonal.getScene().getWindow();
                stageActual.close();

            } else {
                Alerta.crearAlertaError("Error", "Error al Iniciar Sesión", "Credenciales no coinciden con ningún usuario. Vuelve a intentarlo.").showAndWait();
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