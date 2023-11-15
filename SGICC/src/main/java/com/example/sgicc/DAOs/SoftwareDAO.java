package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.Software;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDAO {

    public static List<Software> consultarSoftware () {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Software> softwares = new ArrayList<>();

        try {
            String consultaSQL = "SELECT * FROM software";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idSoftware = resultado.getInt("idSoftware");
                String nombreSoftware = resultado.getString("nombreSoftware");
                boolean conLicencia = resultado.getInt("conLicencia") != 0;
                String tipoSoftware = resultado.getString("tipoSoftware");
                String version = resultado.getString("version");
                String tipoLicencia = resultado.getString("tipoLicencia");

                Software software = new Software(idSoftware, nombreSoftware, conLicencia, tipoSoftware, version, tipoLicencia);
                softwares.add(software);
            }

            resultado.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return softwares;
    }

    public static List<Software> consultarSoftwareInstalado(int idEquipoComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Software> softwaresInstalado = new ArrayList<>();

        try {
            String consultaSQL = "SELECT * FROM software s INNER JOIN softwareinstalado si on s.idSoftware = si.idSoftware WHERE si.idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idEquipoComputo);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idSoftware = resultado.getInt("idSoftware");
                String nombreSoftware = resultado.getString("nombreSoftware");
                boolean conLicencia = resultado.getInt("conLicencia") != 0;
                String tipoSoftware = resultado.getString("tipoSoftware");
                String version = resultado.getString("version");
                String tipoLicencia = resultado.getString("tipoLicencia");

                Software software = new Software(idSoftware, nombreSoftware, conLicencia, tipoSoftware, version, tipoLicencia);
                softwaresInstalado.add(software);
            }

            resultado.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return softwaresInstalado;
    }

    public static void registrarSoftware(String nombreSoftware, boolean conLicencia, String tipoSoftware, String version, String tipoLicencia) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO software (nombreSoftware, conLicencia, tipoSoftware, version, tipoLicencia) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, nombreSoftware);
            statement.setBoolean(2, conLicencia);
            statement.setString(3, tipoSoftware);
            statement.setString(4, version);
            statement.setString(5, tipoLicencia);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro exitoso", "Se guardó correctamente el nuevo software.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se guardó el sofware, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarSoftware(int idSoftware, String nombreSoftware, boolean conLicencia, String tipoSoftware, String version, String tipoLicencia) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "UPDATE software SET nombreSoftware = ?, conLicencia = ?, tipoSoftware = ?, version = ?, tipoLicencia = ? WHERE idSoftware = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, nombreSoftware);
            statement.setBoolean(2, conLicencia);
            statement.setString(3, tipoSoftware);
            statement.setString(4, version);
            statement.setString(5, tipoLicencia);
            statement.setInt(6, idSoftware);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Actualización exitosa", "Se actualizó correctamente el software.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se actualizó el software, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarSoftware(int idSoftware) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM software WHERE idSoftware = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idSoftware);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Eliminación exitosa", "Se eliminó correctamente el software.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se eliminó el software, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean consultaAsignacionEspecifica(int idSoftware, int idEquipoComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "SELECT COUNT(*) FROM softwareinstalado WHERE idSoftware = ? AND idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idSoftware);
            statement.setInt(2, idEquipoComputo);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int cantidadRegistros = resultado.getInt(1);
                return cantidadRegistros > 0;
            }

            statement.close();
            resultado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int eliminarAsignacion(int idSoftware, int idEquipoComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM softwareinstalado WHERE idSoftware = ? AND idEquipoComputo = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idSoftware);
            statement.setInt(2, idEquipoComputo);

            int filasAfectadas = statement.executeUpdate();

            statement.close();

            if (filasAfectadas > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int crearAsignacion(int idSoftware, int idEquipoComputo) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO softwareinstalado (idSoftware, idEquipoComputo) VALUES (?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idSoftware);
            statement.setInt(2, idEquipoComputo);

            int filasAfectadas = statement.executeUpdate();

            statement.close();

            if (filasAfectadas > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
