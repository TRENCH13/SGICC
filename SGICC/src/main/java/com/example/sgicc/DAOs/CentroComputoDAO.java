package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.CentroComputo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;
import java.util.List;

public class CentroComputoDAO {

    public static List<CentroComputo> consultarCentrosComputo(){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<CentroComputo> centros = new ArrayList<>();

        try {
            String consultaSQL = "SELECT * FROM centrocomputo";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idCentroDeComputo = resultado.getInt("idCentroDeComputo");
                String codigoCC = resultado.getString("codigoCC");
                String edificio = resultado.getString("edificio");

                CentroComputo centro = new CentroComputo(idCentroDeComputo, codigoCC, edificio);
                centros.add(centro);
            }

            resultado.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return centros;
    }

    public static void registrarCentroComputo(String codigoCC, String edificio){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO centrocomputo (codigoCC, edificio) VALUES (?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, codigoCC);
            statement.setString(2, edificio);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro Exitoso", "Se guardo correctamente el nuevo Centro de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se guardó el Centro de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCentroComputo(int idCentro){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM centrocomputo WHERE idCentroDeComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idCentro);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Eliminación Exitosa", "Se elimino correctamente el Centro de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se eliminó el Centro de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarCentroComputo(int idCentro, String codigoCC, String edificio){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "UPDATE centrocomputo SET codigoCC = ?, edificio = ? WHERE idCentroDeComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, codigoCC);
            statement.setString(2, edificio);
            statement.setInt(3, idCentro);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Actualización Exitosa", "Se actualizó correctamente el Centro de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se actualizó el Centro de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void desasignarParaEliminarCentroComputo(int idCentroDeComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            // Desasignar software instalado asociado a los equipos de cómputo en el centro
            String desasignarSoftwareSQL = "DELETE FROM softwareinstalado WHERE idEquipoComputo IN (SELECT idEquipoComputo FROM equipocomputo WHERE idCentroDeComputo = ?)";
            PreparedStatement desasignarSoftwareStatement = conexionBD.prepareStatement(desasignarSoftwareSQL);
            desasignarSoftwareStatement.setInt(1, idCentroDeComputo);
            desasignarSoftwareStatement.executeUpdate();
            desasignarSoftwareStatement.close();

            // Eliminar equipos de cómputo asociados al centro
            String eliminarEquiposSQL = "DELETE FROM equipocomputo WHERE idCentroDeComputo = ?";
            PreparedStatement eliminarEquiposStatement = conexionBD.prepareStatement(eliminarEquiposSQL);
            eliminarEquiposStatement.setInt(1, idCentroDeComputo);
            eliminarEquiposStatement.executeUpdate();
            eliminarEquiposStatement.close();

            // Eliminar bitácoras asociadas al centro
            String eliminarBitacorasSQL = "DELETE FROM bitacora WHERE idCentroDeComputo = ?";
            PreparedStatement eliminarBitacorasStatement = conexionBD.prepareStatement(eliminarBitacorasSQL);
            eliminarBitacorasStatement.setInt(1, idCentroDeComputo);
            int filasAfectadasBitacoras = eliminarBitacorasStatement.executeUpdate();
            eliminarBitacorasStatement.close();

            // Eliminar periféricos asociados al centro
            String eliminarPerifericosSQL = "DELETE FROM periferico WHERE idCentroDeComputo = ?";
            PreparedStatement eliminarPerifericosStatement = conexionBD.prepareStatement(eliminarPerifericosSQL);
            eliminarPerifericosStatement.setInt(1, idCentroDeComputo);
            int filasAfectadasPerifericos = eliminarPerifericosStatement.executeUpdate();
            eliminarPerifericosStatement.close();

            if (filasAfectadasPerifericos > 0) {
                System.out.println("Se desasignaron y eliminaron " + filasAfectadasPerifericos + " periféricos asociados al centro con ID: " + idCentroDeComputo);
            } else {
                System.out.println("No se encontraron periféricos para desasignar y eliminar en el centro con ID: " + idCentroDeComputo);
            }

            if (filasAfectadasBitacoras > 0) {
                System.out.println("Se desasignaron y eliminaron " + filasAfectadasBitacoras + " bitácoras asociadas al centro con ID: " + idCentroDeComputo);
            } else {
                System.out.println("No se encontraron bitácoras para desasignar y eliminar en el centro con ID: " + idCentroDeComputo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
