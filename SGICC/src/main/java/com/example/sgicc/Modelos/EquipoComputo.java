package com.example.sgicc.Modelos;

public class EquipoComputo {
    private int idEquipoComputo;
    private String codigoEquipo;
    private String procesador;
    private String memoria;
    private String almacenamiento;
    private String tarjetaMadre;
    private String gabinete;
    private String sistemaOperativo;
    private String estado;
    private int idCentroDeComputo;

    public EquipoComputo(){

    }

    public EquipoComputo(int idEquipoComputo, String codigoEquipo, String procesador, String memoria,
                         String almacenamiento, String tarjetaMadre, String gabinete, String sistemaOperativo,
                         String estado, int idCentroDeComputo) {
        this.idEquipoComputo = idEquipoComputo;
        this.codigoEquipo = codigoEquipo;
        this.procesador = procesador;
        this.memoria = memoria;
        this.almacenamiento = almacenamiento;
        this.tarjetaMadre = tarjetaMadre;
        this.gabinete = gabinete;
        this.sistemaOperativo = sistemaOperativo;
        this.estado = estado;
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public int getIdEquipoComputo() {
        return idEquipoComputo;
    }

    public void setIdEquipoComputo(int idEquipoComputo) {
        this.idEquipoComputo = idEquipoComputo;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getTarjetaMadre() {
        return tarjetaMadre;
    }

    public void setTarjetaMadre(String tarjetaMadre) {
        this.tarjetaMadre = tarjetaMadre;
    }

    public String getGabinete() {
        return gabinete;
    }

    public void setGabinete(String gabinete) {
        this.gabinete = gabinete;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCentroDeComputo() {
        return idCentroDeComputo;
    }

    public void setIdCentroDeComputo(int idCentroDeComputo) {
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public String mostrarInfo() {
        String info = "Código: " + getCodigoEquipo() + ". OS: " + getSistemaOperativo() + ". Estado: " + getEstado();
        return info;
    }

    @Override
    public String toString() {
        return "EquipoComputo{" +
                "idEquipoComputo=" + idEquipoComputo +
                ", codigoEquipo='" + codigoEquipo + '\'' +
                ", procesador='" + procesador + '\'' +
                ", memoria='" + memoria + '\'' +
                ", almacenamiento='" + almacenamiento + '\'' +
                ", tarjetaMadre='" + tarjetaMadre + '\'' +
                ", gabinete='" + gabinete + '\'' +
                ", sistemaOperativo='" + sistemaOperativo + '\'' +
                ", estado='" + estado + '\'' +
                ", idCentroDeComputo=" + idCentroDeComputo +
                '}';
    }
}

