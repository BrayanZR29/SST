package com.sst.registro.model.enums;

public enum TipoEvento {
    ACCIDENTE("Accidente"),
    INCIDENTE("Incidente"),
    ENFERMEDAD_PROFESIONAL("Enfermedad Profesional");

    private final String descripcion;

    TipoEvento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
