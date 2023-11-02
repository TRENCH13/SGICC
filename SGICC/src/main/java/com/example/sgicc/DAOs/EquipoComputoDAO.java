package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.EquipoComputo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoComputoDAO {

    public static List<EquipoComputo> consultarEquiposComputo(int idCentroBusqueda){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<EquipoComputo> equipos = new ArrayList<>();

        try{
            String consultaSQL = "SELECT * FROM equipocomputo WHERE idCentroDeComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idCentroBusqueda);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()){
                int idEquipoComputo = resultado.getInt("idEquipoComputo");
                String codigoEquipo = resultado.getString("codigoEquipo");
                String procesador = resultado.getString("procesador");
                String memoria = resultado.getString("memoria");
                String almacenamiento = resultado.getString("almacenamiento");
                String tarjetaMadre = resultado.getString("tarjetaMadre");
                String gabinete = resultado.getString("gabinete");
                String sistemaOperativo = resultado.getString("sistemaOperativo");
                String estado = resultado.getString("estado");
                int idCentroDeComputo = resultado.getInt("idCentroDeComputo");

                EquipoComputo equipo = new EquipoComputo(idEquipoComputo, codigoEquipo, procesador, memoria, almacenamiento, tarjetaMadre, gabinete, sistemaOperativo, estado, idCentroDeComputo);
                equipos.add(equipo);
            }

            resultado.close();
            statement.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return equipos;
    }

    public static void registrarEquipoComputo(String codigoEquipo, String procesador, String memoria, String almacenamiento, String tarjetaMadre, String gabinete, String sistemaOperativo, String estado, int idCentroDeComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO equipocomputo (codigoEquipo, procesador, memoria, almacenamiento, tarjetaMadre, gabinete, sistemaOperativo, estado, idCentroDeComputo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, codigoEquipo);
            statement.setString(2, procesador);
            statement.setString(3, memoria);
            statement.setString(4, almacenamiento);
            statement.setString(5, tarjetaMadre);
            statement.setString(6, gabinete);
            statement.setString(7, sistemaOperativo);
            statement.setString(8, estado);
            statement.setInt(9, idCentroDeComputo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro Exitoso", "Se guardo correctamente el nuevo Equipo de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se guardó el Equipo de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void eliminarEquipoComputo(int idEquipoDeComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM equipocomputo WHERE idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idEquipoDeComputo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Eliminación Exitosa", "Se elimino correctamente el Equipo de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se eliminó el Equipo de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarEquipoComputo(int idEquipoComputo, String codigoEquipo, String procesador, String memoria, String almacenamiento, String tarjetaMadre, String gabinete, String sistemaOperativo, int idCentroDeComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "UPDATE equipocomputo SET codigoEquipo = ?, procesador = ?, memoria = ?, almacenamiento = ?, tarjetaMadre = ?, gabinete = ?, sistemaOperativo = ?, idCentroDeComputo = ? WHERE idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, codigoEquipo);
            statement.setString(2, procesador);
            statement.setString(3, memoria);
            statement.setString(4, almacenamiento);
            statement.setString(5, tarjetaMadre);
            statement.setString(6, gabinete);
            statement.setString(7, sistemaOperativo);
            statement.setInt(8, idCentroDeComputo);
            statement.setInt(9, idEquipoComputo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Actualización Exitosa", "Se han actualizado los datos del Equipo de Cómputo.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la Base de Datos", "No se han actualizado los datos del Equipo de Cómputo.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean cambiarEstadoEquipoComputo(int idEquipoComputo, String estado) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        boolean modificacionExistosa = false;

        try {
            String consultaSQL = "UPDATE equipocomputo SET estado = ? WHERE idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, estado);
            statement.setInt(2, idEquipoComputo);

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
