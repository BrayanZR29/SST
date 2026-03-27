package com.sst.registro.model.enums;

public enum EstadoAccion {
    PENDIENTE("Pendiente"),
    COMPLETADA("Completada");

    private final String descripcion;

    EstadoAccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
