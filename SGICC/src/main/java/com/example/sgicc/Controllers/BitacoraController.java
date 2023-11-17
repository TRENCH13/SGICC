package com.example.sgicc.Controllers;

import com.example.sgicc.DAOs.BitacoraDAO;
import com.example.sgicc.IniciadorAplicacion;
import com.example.sgicc.Modelos.Bitacora;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class BitacoraController {
    public TextField tbBusqueda;
    public TableView tvBitacoras;
    public TableColumn tcTitulo;
    public TableColumn tcReporteBitacora;
    public TableColumn tcFechaRegistro;
    public int rolActual;
    public Button btnAgregar;
    public int idCentroComputo;
    private ObservableList<Bitacora> bitacorasObservable;

    public void btnAgregar(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) tvBitacoras.getScene().getWindow();
        Scene scene = stage.getScene();
        AnchorPane apVistaMenuCC = (AnchorPane) scene.lookup("#apVistaMenuCC");

        FXMLLoader fxmlLoader = new FXMLLoader(IniciadorAplicacion.class.getResource("Vistas/RegistrarBitacora.fxml"));
        Parent root = fxmlLoader.load();

        RegistrarBitacoraController bitacoraController = fxmlLoader.getController();
        bitacoraController.rolActual = rolActual;
        bitacoraController.idCentroComputo = idCentroComputo;

        apVistaMenuCC.getChildren().setAll(root);
    }

    public void llenarTabla(int idCentroComputo) {
        this.idCentroComputo = idCentroComputo;
        tvBitacoras.setItems(null);
        List<Bitacora> bitacorasTabla = BitacoraDAO.consultarBitacoras(idCentroComputo);
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcReporteBitacora.setCellValueFactory(new PropertyValueFactory<>("reporteBitacora"));

        tcReporteBitacora.setCellFactory(column -> {
            TableCell<Bitacora, String> cell = new TableCell<Bitacora, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        if (isEditing()) {
                            setText(null);
                        } else {
                            String truncatedText = item.length() > 50 ? item.substring(0, 50) + "..." : item;
                            setText(truncatedText);
                        }
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getClickCount() == 2) {
                    showTextPopup(cell.getItem());
                }
            });
            return cell;
        });

        tcFechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
        bitacorasObservable = FXCollections.observableArrayList(bitacorasTabla);
        tvBitacoras.setItems(bitacorasObservable);
    }

    private void showTextPopup(String text) {
        Stage popupStage = new Stage(StageStyle.UTILITY);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Descripción completa");

        TextArea textArea = new TextArea(text);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        popupStage.setScene(new Scene(scrollPane, 400, 300));
        popupStage.show();
    }

    public void inicializarBusquedaBitacoras(){
        if(bitacorasObservable.size() > 0){
            FilteredList<Bitacora> filtroBitacora = new FilteredList<>(bitacorasObservable, p -> true);

            tbBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroBitacora.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }

                        String filtroMinusculas = newValue.toLowerCase();

                        if(String.valueOf(busqueda.getTitulo()).toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }

                        return false;
                    });
                }
            });

            SortedList<Bitacora> bitacorasFiltradas = new SortedList<>(filtroBitacora);
            bitacorasFiltradas.comparatorProperty().bind(tvBitacoras.comparatorProperty());
            tvBitacoras.setItems(bitacorasFiltradas);
        }
    }
}
