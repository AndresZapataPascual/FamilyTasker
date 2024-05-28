package com.andreszapata.familytasker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity {

    private EditText editTextTarea;
    private Button buttonCrearTarea;
    private Button btnAtras;
    private ListView listViewTareas;

    private DatabaseReference databaseReference;
    private String idLista;
    private TareaAdapter tareaAdapter;
    private ArrayList<Tarea> tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas_activity);

        // Obtener el ID de la lista del Intent
        idLista = getIntent().getStringExtra("idLista");

        // Configurar el Toolbar con el nombre de la lista como título
        String nombreLista = getIntent().getStringExtra("nombreLista");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(nombreLista);
        }

        // Inicializar la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("Tareas").child(idLista);

        editTextTarea = findViewById(R.id.editTextListName);
        buttonCrearTarea = findViewById(R.id.buttonCreateTarea);
        listViewTareas = findViewById(R.id.listViewtasks);
        btnAtras = findViewById(R.id.btnAtras);

        // Inicializar la lista de tareas y el adaptador
        tareas = new ArrayList<>();
        tareaAdapter = new TareaAdapter(this, tareas);
        listViewTareas.setAdapter(tareaAdapter);

        // Cargar las tareas asociadas a la lista
        cargarTareas();

        buttonCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el texto de la tarea ingresada por el usuario
                String nombreTarea = editTextTarea.getText().toString().trim();

                if (!nombreTarea.isEmpty()) {
                    // Generar un ID único para la tarea
                    String tareaId = databaseReference.push().getKey();

                    // Guardar la tarea en la base de datos asociada al ID de la lista
                    Tarea nuevaTarea = new Tarea(tareaId, nombreTarea, false, idLista);// Aquí se pasa el ID de la lista
                    databaseReference.child(tareaId).setValue(nuevaTarea);

                    // Limpiar el campo de texto después de guardar la tarea
                    editTextTarea.setText("");

                    // Notificar al usuario que la tarea se creó correctamente
                    Toast.makeText(TareasActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Notificar al usuario si el campo de texto está vacío
                    Toast.makeText(TareasActivity.this, "Por favor, ingresa el nombre de la tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TareasActivity.this, ListaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cargarTareas() {
        // Escuchar los cambios en la base de datos para cargar las tareas asociadas a la lista
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tareas.clear(); // Limpiar la lista antes de cargar las nuevas tareas
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tarea tarea = snapshot.getValue(Tarea.class);
                    if (tarea != null) {
                        // Agregar la tarea a la lista
                        tareas.add(tarea);
                    }
                }
                // Notificar al adaptador que los datos han cambiado
                tareaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de cancelación
                Toast.makeText(TareasActivity.this, "Error al cargar las tareas: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearNuevaTarea(String nombreTarea) {
        if (!nombreTarea.isEmpty()) {
            // Generar un ID único para la tarea
            String tareaId = databaseReference.push().getKey();

            // Guardar la tarea en la base de datos asociada al ID de la lista
            Tarea nuevaTarea = new Tarea(tareaId, nombreTarea, false, idLista); // Establecer el estado de completado inicial
            databaseReference.child(tareaId).setValue(nuevaTarea);

            // Limpiar el campo de texto después de guardar la tarea
            editTextTarea.setText("");

            // Notificar al usuario que la tarea se creó correctamente
            Toast.makeText(TareasActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Notificar al usuario si el campo de texto está vacío
            Toast.makeText(TareasActivity.this, "Por favor, ingresa el nombre de la tarea", Toast.LENGTH_SHORT).show();
        }
    }


    // Método para encontrar una tarea por su ID en la lista de tareas existente
    private Tarea encontrarTareaPorId(String tareaId) {
        for (Tarea tarea : tareas) {
            if (tarea.getId().equals(tareaId)) {
                return tarea;
            }
        }
        return null;
    }

}

