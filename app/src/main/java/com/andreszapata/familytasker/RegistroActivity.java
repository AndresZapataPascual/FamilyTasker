package com.andreszapata.familytasker;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
    private Button buttonRegistrar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        Button buttonRegistrar = findViewById(R.id.buttonRegistrar);
        Button ButonIniciar = findViewById(R.id.ButonIniciar);

        ButonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de registro
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        // Obtener los valores de los campos
        String nombre = editTextNombre.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();
        String contraseña = editTextContraseña.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Los campos están llenos, crear el registro en la base de datos
            // Generar un ID único para el nuevo registro
            String userId = databaseReference.push().getKey();

            // Crear un objeto Usuario con los datos proporcionados por el usuario
            Usuario usuario = new Usuario(userId, nombre, correo, contraseña);

            // Agregar el usuario a la base de datos
            databaseReference.child(userId).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Registro exitoso
                        Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Opcionalmente, puedes redirigir al usuario a otra actividad después del registro
                        // Por ejemplo, puedes abrir la actividad principal
                        // Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                        // startActivity(intent);
                    } else {
                        // Error al agregar el usuario a la base de datos
                        Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public class Usuario {

        private String id;
        private String nombre;
        private String correo;
        private String contraseña;

        public Usuario() {
            // Constructor vacío requerido por Firebase
        }

        public Usuario(String id, String nombre, String correo, String contraseña) {
            this.id = id;
            this.nombre = nombre;
            this.correo = correo;
            this.contraseña = contraseña;
        }

        // Métodos getters y setters

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

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContraseña() {
            return contraseña;
        }

        public void setContraseña(String contraseña) {
            this.contraseña = contraseña;
        }
    }

}
