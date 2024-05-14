package com.andreszapata.familytasker;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class ListaActivity extends AppCompatActivity {

    private ListView listViewLists;
    private TareaAdapter tareaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listas_activity);

        // Obtener el nombre de usuario del Intent
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String idUsuario = getIntent().getStringExtra("idUsuario");

        // Configurar el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Establecer el nombre de usuario como título del Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(nombreUsuario);
        }

        // Obtener la lista de tareas (simulada)
        List<Tarea> listaTareas = new ArrayList<>();
        // Aquí agregas las tareas a la lista (puedes obtenerlas de tu base de datos o crearlas manualmente)
        listaTareas.add(new Tarea("Tarea 1", "Descripción de la Tarea 1"));
        listaTareas.add(new Tarea("Tarea 2", "Descripción de la Tarea 2"));
        listaTareas.add(new Tarea("Tarea 3", "Descripción de la Tarea 3"));

        // Inicializar el adaptador
        tareaAdapter = new TareaAdapter(this, listaTareas);

        // Configurar la ListView
        listViewLists = findViewById(R.id.listViewLists);
        listViewLists.setAdapter(tareaAdapter);
    }


}
