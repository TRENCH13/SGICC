package com.example.sgicc.Modelos;

public class Software {
    private int idSoftware;
    private String nombreSoftware;
    private boolean conLicencia;
    private String tipoSoftware;
    private String version;
    private String tipoLicencia;

    public Software(int idSoftware, String nombreSoftware, boolean conLicencia, String tipoSoftware, String version, String tipoLicencia) {
        this.idSoftware = idSoftware;
        this.nombreSoftware = nombreSoftware;
        this.conLicencia = conLicencia;
        this.tipoSoftware = tipoSoftware;
        this.version = version;
        this.tipoLicencia = tipoLicencia;
    }

    public Software() {}

    // Getters and Setters

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getNombreSoftware() {
        return nombreSoftware;
    }

    public void setNombreSoftware(String nombreSoftware) {
        this.nombreSoftware = nombreSoftware;
    }

    public boolean isConLicencia() {
        return conLicencia;
    }

    public void setConLicencia(boolean conLicencia) {
        this.conLicencia = conLicencia;
    }

    public String getTipoSoftware() {
        return tipoSoftware;
    }

    public void setTipoSoftware(String tipoSoftware) {
        this.tipoSoftware = tipoSoftware;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    @Override
    public String toString() {
        return "Software{" +
                "idSoftware=" + idSoftware +
                ", nombreSoftware='" + nombreSoftware + '\'' +
                ", conLicencia=" + conLicencia +
                ", tipoSoftware='" + tipoSoftware + '\'' +
                ", version='" + version + '\'' +
                ", tipoLicencia='" + tipoLicencia + '\'' +
                '}';
    }
}

