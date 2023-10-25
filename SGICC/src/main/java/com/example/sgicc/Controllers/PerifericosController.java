package com.example.sgicc.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PerifericosController {

    @FXML
    private TableColumn<?, ?> tcSistemaOperativo;

    @FXML
    private TableColumn<?, ?> tcAlmacenamiento;

    @FXML
    private TableView<?> tvEquiposComputo;

    @FXML
    private TableColumn<?, ?> tcTarjetaMadre;

    @FXML
    private TableColumn<?, ?> tcMemoria;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcProcesador;

    @FXML
    private TableColumn<?, ?> tcGabinete;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    void btnEliminar(ActionEvent event) {

    }

    @FXML
    void btnEditar(ActionEvent event) {

    }

    @FXML
    void btnAgregar(ActionEvent event) {

    }

}

