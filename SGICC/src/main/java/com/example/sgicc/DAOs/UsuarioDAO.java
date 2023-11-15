package com.example.sgicc.DAOs;

import com.example.sgicc.Alerta;
import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.Rol;
import com.example.sgicc.Modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static Usuario IniciarSesion(String NumPersonal, String Contrasenia){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD =conexionSQL.getConexion();
        Usuario usuarioSesion = null;

        try{

            String consultaSQL = "SELECT * FROM usuario WHERE BINARY numPersonal = ? AND BINARY contrasenia = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, NumPersonal);
            statement.setString(2, Contrasenia);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                int idUsuario = resultado.getInt("idUsuario");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String correoInstitucional = resultado.getString("correoInstitucional");
                int idRol = resultado.getInt("idRol");

                usuarioSesion = new Usuario(idUsuario, nombre, apellido, correoInstitucional, Contrasenia, NumPersonal, idRol);
            }

            resultado.close();
            statement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

        return usuarioSesion;

    }

    public static List<Usuario> ObtenerUsuarios(){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Usuario> usuarios = new ArrayList<>();

        try{
            String consultaSQL = "SELECT * FROM usuario";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()){
                int idUsuario = resultado.getInt("idUsuario");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String correoInstitucional = resultado.getString("correoInstitucional");
                String contrasenia = resultado.getString("contrasenia");
                String numPersonal = resultado.getString("numPersonal");
                int idRol = resultado.getInt("idRol");

                Usuario usuario = new Usuario(idUsuario, nombre, apellido, correoInstitucional, contrasenia, numPersonal, idRol);
                usuarios.add(usuario);
            }

            resultado.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return usuarios;

    }

    public static void eliminarUsuario(int idUsuario) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "DELETE FROM usuario WHERE idUsuario = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setInt(1, idUsuario);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Eliminación exitosa", "Se eliminó correctamente el usuario.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se eliminó el usuario, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Rol> consultarRoles() {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<Rol> roles = new ArrayList<>();

        try {
            String consultaSQL = "SELECT * FROM rol";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idRol = resultado.getInt("idRol");
                String rol = resultado.getString("rol");

                Rol rolObj = new Rol(idRol, rol);
                roles.add(rolObj);
            }

            resultado.close();
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public static void registrarUsuario(String nombre, String apellido, String correoInstitucional, String numeroPersonal, String password, int rol) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "INSERT INTO usuario (nombre, apellido, correoInstitucional, contrasenia, numPersonal, idRol) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correoInstitucional);
            statement.setString(4, password);
            statement.setString(5, numeroPersonal);
            statement.setInt(6, rol);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Registro exitoso", "Se guardó correctamente el nuevo usuario.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se guardó el usuario, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarUsuario(int idUsuarioEdicion, String nombre, String apellido, String correoInstitucional, String numeroPersonal, String password, int rol) {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();

        try {
            String consultaSQL = "UPDATE usuario SET nombre = ?, apellido = ?, correoInstitucional = ?, contrasenia = ?, numPersonal = ?, idRol = ? WHERE idUsuario = ?";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correoInstitucional);
            statement.setString(4, password);
            statement.setString(5, numeroPersonal);
            statement.setInt(6, rol);
            statement.setInt(7, idUsuarioEdicion);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                Alerta.crearAlertaInformacion("Actualización exitosa", "Se actualizó correctamente el usuario.", "").showAndWait();
            } else {
                Alerta.crearAlertaError("Error", "Error en la base de datos", "No se actualizó el usuario, por favor inténtelo más tarde.").showAndWait();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
