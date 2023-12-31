package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.Periferico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerifericoDAO {

    public static List<Periferico> consultarPerifericos(int idCentroBusqueda) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Periferico> perifericos = new ArrayList<>();

        try {
            String consultaSQL = "SELECT P.idPeriferico, P.estado, P.especificaciones, P.codigo, P.nombre, TP.idTipoPeriferico, P.idCentroDeComputo, TP.tipoPeriferico FROM periferico P INNER JOIN tipoperiferico TP ON P.idTipoPeriferico = TP.idTipoPeriferico WHERE idCentroDeComputo = ? ORDER BY codigo";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idCentroBusqueda);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idPeriferico = resultado.getInt("idPeriferico");
                String estado = resultado.getString("estado");
                String especificaciones = resultado.getString("especificaciones");
                String codigo = resultado.getString("codigo");
                String nombre = resultado.getString("nombre");
                int idTipoPeriferico = resultado.getInt("idTipoPeriferico");
                String tipoPeriferico = resultado.getString("tipoPeriferico");
                int idCentroDeComputo = resultado.getInt("idCentroDeComputo");

                Periferico periferico = new Periferico(idPeriferico, estado, especificaciones, codigo, nombre, idTipoPeriferico, tipoPeriferico, idCentroDeComputo);
                perifericos.add(periferico);
            }

            resultado.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perifericos;
    }

    public static void registrarPeriferico(String especificaciones, String codigo, String nombre, int idTipoPeriferico, int idCentroComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String query = "INSERT INTO periferico (estado, especificaciones, codigo, nombre, idTipoPeriferico, idCentroDeComputo) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conexionBD.prepareStatement(query);
            statement.setString(1, "Disponible");
            statement.setString(2, especificaciones);
            statement.setString(3, codigo);
            statement.setString(4, nombre);
            statement.setInt(5, idTipoPeriferico);
            statement.setInt(6, idCentroComputo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro exitoso", "Se guardó correctamente el nuevo periférico.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se guardó el periférico, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void eliminarPeriferico(int idPeriferico) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM periferico WHERE idPeriferico = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idPeriferico);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Eliminación exitosa", "Se eliminó correctamente el periférico.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se eliminó el periférico, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarPeriferico(int idPeriferico, String especificaciones, String codigo, String nombre, int idTipoPeriferico, int idCentroComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "UPDATE periferico SET especificaciones = ?, codigo = ?, nombre = ?, idTipoPeriferico = ?, idCentroDeComputo = ? WHERE idPeriferico = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, especificaciones);
            statement.setString(2, codigo);
            statement.setString(3, nombre);
            statement.setInt(4, idTipoPeriferico);
            statement.setInt(5, idCentroComputo);
            statement.setInt(6, idPeriferico);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                // La actualización fue exitosa
                Alerta.crearAlertaInformacion("Actualización exitosa", "Se actualizó correctamente el periférico.", "").showAndWait();
            } else {
                // No se pudo actualizar
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se actualizó el periférico, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean cambiarEstadoPeriferico(int idPeriferico, String estado) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        boolean modificacionExistosa = false;

        try {
            String consultaSQL = "UPDATE periferico SET estado = ? WHERE idPeriferico = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, estado);
            statement.setInt(2, idPeriferico);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                modificacionExistosa = true;
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modificacionExistosa;
    }

}
