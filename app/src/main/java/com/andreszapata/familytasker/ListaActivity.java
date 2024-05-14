package com.andreszapata.familytasker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class ListaActivity extends AppCompatActivity {

    private EditText editTextListName;
    private Button buttonCreateList;
    private ListView listViewLists;
    private ListaAdapter listaAdapter;
    private ArrayList<Lista> listas;

    private DatabaseReference databaseReference;

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


        databaseReference = FirebaseDatabase.getInstance().getReference("Listas");

        editTextListName = findViewById(R.id.editTextListName);
        buttonCreateList = findViewById(R.id.buttonCreateList);
        listViewLists = findViewById(R.id.listViewLists);

        // Inicializar la lista de listas y el adaptador
        listas = new ArrayList<>();
        listaAdapter = new ListaAdapter(this, listas);

        // Establecer el adaptador en el ListView
        listViewLists.setAdapter(listaAdapter);

        // Cargar las listas del usuario desde la base de datos
        cargarListasUsuario(idUsuario);

        buttonCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = editTextListName.getText().toString();

                if (!listName.isEmpty()) {
                    String listId = databaseReference.push().getKey();
                    // Guardar la lista en la base de datos
                    databaseReference.child(listId).setValue(new Lista(listId, listName));
                    Toast.makeText(ListaActivity.this, "Lista creada correctamente", Toast.LENGTH_SHORT).show();
                    // Limpiar el campo de nombre de la lista
                    editTextListName.setText("");
                } else {
                    Toast.makeText(ListaActivity.this, "Por favor, ingresa un nombre para la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listViewLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí puedes manejar el clic en un elemento de la lista si es necesario
            }
        });
    }

    private void cargarListasUsuario(String idUsuario) {
        // Referencia a la ubicación en la base de datos donde se almacenan las listas del usuario
        DatabaseReference listaUsuarioRef = databaseReference.child(idUsuario);

        // Agregar un listener para escuchar los cambios en los datos de las listas del usuario
        listaUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpiar la lista actual de listas
                listas.clear();

                // Iterar sobre las listas del usuario y agregarlas a la lista de listas
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Lista lista = snapshot.getValue(Lista.class);
                    if (lista != null) {
                        listas.add(lista);
                    }
                }

                // Notificar al adaptador que los datos han cambiado
                listaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de cancelación
                Toast.makeText(ListaActivity.this, "Error al cargar las listas del usuario: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
