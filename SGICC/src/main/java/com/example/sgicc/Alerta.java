package com.example.sgicc;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.util.Optional;

public class Alerta {
    public static Alert crearAlertaInformacion(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        configurarAlerta(alert, titulo, encabezado, mensaje);
        return alert;
    }

    public static Alert crearAlertaAdvertencia(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        configurarAlerta(alert, titulo, encabezado, mensaje);
        return alert;
    }

    public static Alert crearAlertaError(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        configurarAlerta(alert, titulo, encabezado, mensaje);
        return alert;
    }

    public static boolean crearAlertaConfirmacion(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        configurarAlerta(alert, titulo, encabezado, mensaje);

        // Obtén la respuesta del usuario
        Optional<ButtonType> resultado = alert.showAndWait();

        // Verifica qué botón se presionó
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private static void configurarAlerta(Alert alert, String titulo, String encabezado, String mensaje) {
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
    }
}