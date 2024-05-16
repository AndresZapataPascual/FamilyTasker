package com.andreszapata.familytasker;

public class Tarea {
    private String id;
    private String nombre;

    // Constructor vacío requerido para Firebase
    public Tarea() {
    }

    public Tarea(String id, String nombre) {
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
