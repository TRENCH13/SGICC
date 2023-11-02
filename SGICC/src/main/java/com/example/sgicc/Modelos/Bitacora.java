package com.example.sgicc.Modelos;

import java.sql.Date;

public class Bitacora {
    private int idBitacora;
    private String reporteBitacora;
    private String titulo;
    private Date fechaRegistro;
    private int idCentroDeComputo;


    public Bitacora() {
    }

    public Bitacora(int idBitacora, String reporteBitacora, String titulo, Date fechaRegistro, int idCentroDeComputo) {
        this.idBitacora = idBitacora;
        this.reporteBitacora = reporteBitacora;
        this.titulo = titulo;
        this.fechaRegistro = fechaRegistro;
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public Bitacora(String reporteBitacora, String titulo, Date fechaRegistro, int idCentroDeComputo) {
        this.reporteBitacora = reporteBitacora;
        this.titulo = titulo;
        this.fechaRegistro = fechaRegistro;
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getReporteBitacora() {
        return reporteBitacora;
    }

    public void setReporteBitacora(String reporteBitacora) {
        this.reporteBitacora = reporteBitacora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCentroDeComputo() {
        return idCentroDeComputo;
    }

    public void setIdCentroDeComputo(int idCentroDeComputo) {
        this.idCentroDeComputo = idCentroDeComputo;
    }
}
