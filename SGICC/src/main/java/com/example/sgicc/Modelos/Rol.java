package com.example.sgicc.Modelos;

public class Rol {
    private int idRol;
    private String rol;

    public Rol() {
        // Constructor vacío
    }

    public Rol(int idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Rol [idRol=" + idRol + ", rol=" + rol + "]";
    }
}