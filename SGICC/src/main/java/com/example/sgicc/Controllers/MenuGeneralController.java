package com.example.sgicc.Controllers;

import com.example.sgicc.Alerta;
import com.example.sgicc.DAOs.CentroComputoDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.CentroComputo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class MenuGeneralController {

    private int rolActual = 0;

    private int CCSelecionado = 0;

    @FXML
    public Label lbUser;

    @FXML
    private VBox vbCC;

    @FXML
    public Button btnUsuarios;

    @FXML
    public Button btnCentrosComputo;

    public void setIdRol(int idRol) {
        this.rolActual = idRol;
    }


    public void asignarCCRecuperados() {
        List<CentroComputo> centrosComputo = CentroComputoDAO.consultarCentrosComputo();
        vbCC.getChildren().clear();
        for (CentroComputo centro : centrosComputo) {
            vbCC.getChildren().add(crearPane(centro.getIdCentroDeComputo(), centro.getCodigoCC(), centro.getEdificio(), new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/CC.jpeg").toExternalForm())));

            Pane paneBlanco = new Pane();
            paneBlanco.setPrefWidth(10);
            paneBlanco.setPrefHeight(10);
            paneBlanco.setStyle("-fx-background-color: white;");
            vbCC.getChildren().add(paneBlanco);
        }
    }

    public Pane crearPane(int idCentro, String codigoCC, String edificio, Image imagenCC) {

        Pane customPane = new Pane();
        customPane.setPrefWidth(936);
        customPane.setPrefHeight(119);
        customPane.setStyle(
                "-fx-background-color: #FAFAF5; " +
                        "-fx-border-color: #E6E6E6; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 24px; " +
                        "-fx-background-radius: 24px;"
        );

        ImageView imageView = new ImageView(imagenCC);
        imageView.setFitWidth(218);
        imageView.setFitHeight(119);
        imageView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(218, 119);
        clip.setArcWidth(24);
        clip.setArcHeight(24);
        imageView.setClip(clip);

        Label codigoCCLabel = new Label(codigoCC);
        codigoCCLabel.setLayoutX(231);
        codigoCCLabel.setLayoutY(24);
        codigoCCLabel.setFont(Font.font("System Bold", 18));

        Label edificioLabel = new Label(edificio);
        edificioLabel.setLayoutX(231);
        edificioLabel.setLayoutY(69);
        edificioLabel.setTextFill(Color.web("#426b1f"));
        edificioLabel.setFont(Font.font("System Bold", 18));

        Button botonIr = new Button("IR   ->");
        botonIr.setLayoutX(839);
        botonIr.setLayoutY(43);
        botonIr.setMnemonicParsing(false);
        botonIr.setPrefHeight(30);
        botonIr.setPrefWidth(69);
        botonIr.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: black; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8;"
        );
        botonIr.setFont(Font.font("System Bold", 15));
        botonIr.setCursor(javafx.scene.Cursor.HAND);

        if (rolActual == 1){

            Button botonEliminar = new Button("Eliminar  ->");
            botonEliminar.setLayoutX(720);
            botonEliminar.setLayoutY(43);
            botonEliminar.setMnemonicParsing(false);
            botonEliminar.setPrefHeight(30);
            botonEliminar.setPrefWidth(100);
            botonEliminar.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-border-color: black; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-radius: 8;"
            );
            botonEliminar.setFont(Font.font("System Bold", 15));
            botonEliminar.setCursor(javafx.scene.Cursor.HAND);

            Button botonEditar = new Button("Editar  ->");
            botonEditar.setLayoutX(610);
            botonEditar.setLayoutY(43);
            botonEditar.setMnemonicParsing(false);
            botonEditar.setPrefHeight(30);
            botonEditar.setPrefWidth(90);
            botonEditar.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-border-color: black; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-radius: 8;"
            );
            botonEditar.setFont(Font.font("System Bold", 15));
            botonEditar.setCursor(javafx.scene.Cursor.HAND);

            customPane.getChildren().addAll(imageView, codigoCCLabel, edificioLabel, botonEliminar, botonEditar, botonIr);

            botonIr.setOnAction(e -> {
                try {
                    Stage stage = (Stage) lbUser.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuCC.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    MenuCCController menuCC = fxmlLoader.getController();
                    menuCC.setIdRol(rolActual);
                    CCSelecionado = idCentro;
                    menuCC.idCentroComputo = CCSelecionado;


                    if (rolActual == 2 || rolActual == 3) {
                        menuCC.btnAsignarSoftware.setVisible(false);
                    }

                    Label lbUserNext = (Label) scene.lookup("#lbUser");
                    lbUserNext.setText(lbUser.getText());
                    stage.setTitle("CC1");
                    stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            botonEliminar.setOnAction(e ->{
                if (Alerta.crearAlertaConfirmacion("Eliminar Centro de Cómputo", "¿Está seguro de eliminar el centro de cómputo?", "Si se elimina, todos sus elementos perderan su asignación.")){
                    CentroComputoDAO.eliminarCentroComputo(idCentro);
                    asignarCCRecuperados();
                }
            });

            botonEditar.setOnAction(e ->{
                Stage stage = (Stage) lbUser.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarCentroComputo.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                RegistrarEditarCentroComputoController edicionCCController = fxmlLoader.getController();
                edicionCCController.btnRegistrarEditar.setText("Editar Centro de Computo");
                edicionCCController.lbUser.setText(lbUser.getText());
                edicionCCController.lbTítulo.setText("Edición de Centro de Cómputo");
                edicionCCController.setIdRol(rolActual);
                edicionCCController.setEsEdicion(true);
                edicionCCController.centroEditar = new CentroComputo(idCentro, codigoCC, edificio);
                edicionCCController.setEdicion();
                stage.setTitle("Editar Centro de Cómputo");
                stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                stage.show();
            });

        }else{

            customPane.getChildren().addAll(imageView, codigoCCLabel, edificioLabel, botonIr);

            botonIr.setOnAction(e -> {
                try {
                    Stage stage = (Stage) lbUser.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/MenuCC.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    MenuCCController menuCC = fxmlLoader.getController();
                    menuCC.setIdRol(rolActual);
                    menuCC.idCentroComputo = idCentro;
                    stage.setScene(scene);
                    if (rolActual == 2 || rolActual == 3) {
                        menuCC.btnAsignarSoftware.setVisible(false);
                    }

                    Label lbUserNext = (Label) scene.lookup("#lbUser");
                    lbUserNext.setText(lbUser.getText());
                    stage.setTitle("CC1");
                    stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        }

        return customPane;
    }

    @FXML
    void btnUsuarios(ActionEvent event) {

    }

    @FXML
    void btnSoftware(ActionEvent event) {

    }

    @FXML
    void btnCentrosComputo(ActionEvent event) throws IOException {
        Stage stage = (Stage) lbUser.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarEditarCentroComputo.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        RegistrarEditarCentroComputoController registroCCController = fxmlLoader.getController();
        registroCCController.btnRegistrarEditar.setText("Registrar Centro de Computo");
        registroCCController.lbUser.setText(lbUser.getText());
        registroCCController.lbTítulo.setText("Registro de Centro de Cómputo");
        registroCCController.setIdRol(rolActual);
        registroCCController.setEsEdicion(false);
        stage.setTitle("Registrar Centro de Cómputo");
        stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
        stage.show();
    }

    @FXML
    void btnCerrarSesion(ActionEvent event) throws IOException {
        Boolean confirmacion = Alerta.crearAlertaConfirmacion("Cerrar Sesión", "¿Está seguro que quiere cerrar sesión?", "");
        if (confirmacion){
            FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/InicioSesion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(scene);
            stage.getIcons().add(new Image(IniciadorAplicacion.class.getResource("/com/example/sgicc/Recursos/icono_UV.png").toExternalForm()));
            stage.show();
            Stage stageActual = (Stage) btnUsuarios.getScene().getWindow();
            stageActual.close();
        }
    }

}