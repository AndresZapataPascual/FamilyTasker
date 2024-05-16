package com.andreszapata.familytasker;

public class Tarea {
    private String id;
    private String nombre;
    private String idLista; // Agregar el ID de la lista a la que pertenece la tarea

    // Constructor vacío requerido para Firebase
    public Tarea() {
    }

    public Tarea(String id, String nombre, String idLista) {
        this.id = id;
        this.nombre = nombre;
        this.idLista = idLista; // Asignar el ID de la lista
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

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }
}
