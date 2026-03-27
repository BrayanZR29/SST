package com.sst.registro.model.enums;

public enum EstadoEvento {
    ABIERTO("Abierto"),
    EN_PROCESO("En Proceso"),
    CERRADO("Cerrado");

    private final String descripcion;

    EstadoEvento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
