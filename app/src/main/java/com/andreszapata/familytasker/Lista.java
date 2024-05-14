package com.andreszapata.familytasker;

public class Lista {
    private String id;
    private String nombre;

    public Lista() {
        // Constructor vacío requerido para Firebase
    }

    public Lista(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
