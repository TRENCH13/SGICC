package com.example.sgicc.DAOs;

import com.example.sgicc.ConexionBD;
import com.example.sgicc.Modelos.TipoPeriferico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPerifericoDAO {


    public static List<TipoPeriferico> consultarTiposPeriferico() {
        ConexionBD conexionSQL = ConexionBD.getInstance();
        Connection conexionBD = conexionSQL.getConexion();
        List<TipoPeriferico> tiposPeriferico = new ArrayList<>();

        try {
            String consultaSQL = "SELECT * FROM tipoperiferico";
            PreparedStatement statement = conexionBD.prepareStatement(consultaSQL);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int idTipoPeriferico = resultado.getInt("idTipoPeriferico");
                String tipoPeriferico = resultado.getString("tipoPeriferico");

                TipoPeriferico tipoPerifericoObjeto = new TipoPeriferico(idTipoPeriferico, tipoPeriferico);
                tiposPeriferico.add(tipoPerifericoObjeto);
            }

            resultado.close();
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tiposPeriferico;
    }
}
