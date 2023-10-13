package com.example.sgicc.DAOs;

import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public static Usuario IniciarSesion(String NumPersonal, String Contrasenia){
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD =conexionSQL.getConexion();
        Usuario usuarioSesion = null;

        try{

            String consultaSQL = "SELECT * FROM usuario WHERE numPersonal = ? AND contrasenia = ?";
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

}
