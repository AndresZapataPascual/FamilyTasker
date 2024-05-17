package com.andreszapata.familytasker;

public class Tarea {
    private String id;
    private String nombre;
    private boolean completada; // Nuevo campo para indicar si la tarea está completada
    private String idLista;

    // Constructor vacío requerido para Firebase
    public Tarea() {
    }

    public Tarea(String id, String nombre, boolean completada, String idLista) {
        this.id = id;
        this.nombre = nombre;
        this.completada = completada;
        this.idLista = idLista;
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

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }
}
