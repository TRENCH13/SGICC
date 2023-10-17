package com.example.sgicc.Modelos;

public class CentroComputo {
    private int idCentroDeComputo;
    private String codigoCC;
    private String edificio;

    public CentroComputo() {

    }

    public CentroComputo(int idCentroDeComputo, String codigoCC, String edificio) {
        this.idCentroDeComputo = idCentroDeComputo;
        this.codigoCC = codigoCC;
        this.edificio = edificio;
    }

    public int getIdCentroDeComputo() {
        return idCentroDeComputo;
    }

    public void setIdCentroDeComputo(int idCentroDeComputo) {
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public String getCodigoCC() {
        return codigoCC;
    }

    public void setCodigoCC(String codigoCC) {
        this.codigoCC = codigoCC;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Override
    public String toString() {
        return "CentroComputo [idCentroDeComputo=" + idCentroDeComputo + ", codigoCC=" + codigoCC + ", edificio=" + edificio + "]";
    }
}
