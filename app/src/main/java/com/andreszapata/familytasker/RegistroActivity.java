package com.andreszapata.familytasker;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private Button Registrar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro);

        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        Registrar = findViewById(R.id.buttonRegistrar);
        Button ButonIniciar = findViewById(R.id.ButonIniciar);

        ButonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de registro
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtener los valores de los campos
                String nombre = editTextNombre.getText().toString();
                String correo = editTextCorreo.getText().toString();
                String contraseña = editTextContraseña.getText().toString();
                String userId = databaseReference.push().getKey();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contraseña)) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Usuario usuario = new Usuario(userId, nombre, correo, contraseña);

                    databaseReference.child(userId).setValue(usuario);

                    Toast.makeText(RegistroActivity.this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();

                    editTextNombre.setText("");
                    editTextCorreo.setText("");
                    editTextContraseña.setText("");
                }
            }
        });

    }
}
