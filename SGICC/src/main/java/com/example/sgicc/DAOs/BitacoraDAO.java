package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.Bitacora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {
    public static List<Bitacora> consultarBitacoras(int idCentroBusqueda){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Bitacora> bitacoras = new ArrayList<>();

        try{
            String consultaSQL = "SELECT * FROM bitacora WHERE idCentroDeComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idCentroBusqueda);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()){
                int idBitacora = resultado.getInt("idBitacora");
                String reporteBitacora = resultado.getString("reporteBitacora");
                String titulo = resultado.getString("titulo");
                Date fechaRegistro = resultado.getDate("fechaRegistro");
                int idCentroDeComputo = resultado.getInt("idCentroDeComputo");

                Bitacora bitacora = new Bitacora(idBitacora, reporteBitacora, titulo, fechaRegistro, idCentroDeComputo);
                bitacoras.add(bitacora);
            }

            resultado.close();
            statement.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return bitacoras;
    }

    public static void registrarBitacora(String reporteBitacora, String titulo, Date fechaRegistro, int idCentroDeComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO bitacora (reporteBitacora, titulo, fechaRegistro, idCentroDeComputo) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, reporteBitacora);
            statement.setString(2, titulo);
            statement.setDate(3, fechaRegistro);
            statement.setInt(4, idCentroDeComputo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro Exitoso", "Se guardo correctamente la nueva bitácora.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se guardó la bitácora.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
