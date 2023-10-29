package com.example.sgicc.Modelos;

public class Periferico {
    private int idPeriferico;
    private String estado;
    private String especificaciones;
    private String codigo;
    private String nombre;
    private int idTipoPeriferico;
    private String tipoPeriferico;
    private int idCentroDeComputo;

    public Periferico(int idPeriferico, String estado, String especificaciones, String codigo, String nombre, int idTipoPeriferico, String tipoPeriferico, int idCentroDeComputo) {
        this.idPeriferico = idPeriferico;
        this.estado = estado;
        this.especificaciones = especificaciones;
        this.codigo = codigo;
        this.nombre = nombre;
        this.idTipoPeriferico = idTipoPeriferico;
        this.tipoPeriferico = tipoPeriferico;
        this.idCentroDeComputo = idCentroDeComputo;
    }

    public Periferico(){

    }

    // Getters and Setters

    public int getIdPeriferico() {
        return idPeriferico;
    }

    public void setIdPeriferico(int idPeriferico) {
        this.idPeriferico = idPeriferico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdTipoPeriferico() {
        return idTipoPeriferico;
    }

    public void setIdTipoPeriferico(int idTipoPeriferico) {
        this.idTipoPeriferico = idTipoPeriferico;
    }

    public String getTipoPeriferico(){
        return tipoPeriferico;
    }

    public void setTipoPeriferico(String tipoPeriferico){
        this.tipoPeriferico = tipoPeriferico;
    }

    public int getIdCentroDeComputo() {
        return idCentroDeComputo;
    }

    public void setIdCentroDeComputo(int idCentroDeComputo) {
        this.idCentroDeComputo = idCentroDeComputo;
    }

    @Override
    public String toString() {
        return "Perifericos{" +
                "idPeriferico=" + idPeriferico +
                ", estado='" + estado + '\'' +
                ", especificaciones='" + especificaciones + '\'' +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idTipoPeriferico=" + idTipoPeriferico +
                ", idCentroDeComputo=" + idCentroDeComputo +
                '}';
    }
}

