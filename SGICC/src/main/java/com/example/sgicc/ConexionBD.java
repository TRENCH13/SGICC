package com.example.sgicc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static ConexionBD instance;
    private Connection conexion;
    
    private final String url = "jdbc:mysql://localhost:3306/sgicc";
    private final String user = "root";
    private final String password = "Melchor22";

    private ConexionBD() {
        try {
            // Establecer la conexión a la base de datos
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConexionBD getInstance() {
        if (instance == null) {
            synchronized (ConexionBD.class) {
                if (instance == null) {
                    instance = new ConexionBD();
                }
            }
        }
        return instance;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void closeConnection() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
