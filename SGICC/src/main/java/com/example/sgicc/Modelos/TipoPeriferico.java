package com.example.sgicc.Modelos;

public class TipoPeriferico {
    private int idTipoPeriferico;
    private String tipoPeriferico;

    // Constructor
    public TipoPeriferico(int idTipoPeriferico, String tipoPeriferico) {
        this.idTipoPeriferico = idTipoPeriferico;
        this.tipoPeriferico = tipoPeriferico;
    }

    public TipoPeriferico() {

    }

    // Getters and Setters
    public int getIdTipoPeriferico() {
        return idTipoPeriferico;
    }

    public void setIdTipoPeriferico(int idTipoPeriferico) {
        this.idTipoPeriferico = idTipoPeriferico;
    }

    public String getTipoPeriferico() {
        return tipoPeriferico;
    }

    public void setTipoPeriferico(String tipoPeriferico) {
        this.tipoPeriferico = tipoPeriferico;
    }

    @Override
    public String toString() {
        return "TipoPeriferico{" +
                "idTipoPeriferico=" + idTipoPeriferico +
                ", tipoPeriferico='" + tipoPeriferico + '\'' +
                '}';
    }
}

