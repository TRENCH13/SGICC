package com.example.sgicc.Modelos;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correoInstitucional;
    private String contrasenia;
    private String numPersonal;
    private int idRol;

    public Usuario() {
        // Constructor vacío
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correoInstitucional, String contrasenia, String numPersonal, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoInstitucional = correoInstitucional;
        this.contrasenia = contrasenia;
        this.numPersonal = numPersonal;
        this.idRol = idRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido
                + ", correoInstitucional=" + correoInstitucional + ", contrasenia=" + contrasenia
                + ", numPersonal=" + numPersonal + ", idRol=" + idRol + "]";
    }
}
