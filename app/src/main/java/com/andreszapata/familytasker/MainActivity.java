package com.andreszapata.familytasker;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private Button buttonSignUp;
    private Button buttonSignIn;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad de registro
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de correo y contraseña
                final String correo = editTextCorreo.getText().toString();
                final String contraseña = editTextContraseña.getText().toString();

                // Verificar que los campos no estén vacíos
                if (correo.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Buscar el usuario en la base de datos
                databaseReference.orderByChild("correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Usuario encontrado, verificar la contraseña
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Usuario usuario = snapshot.getValue(Usuario.class);
                                if (usuario != null && usuario.getContraseña().equals(contraseña)) {
                                    // Contraseña correcta, iniciar sesión y dirigir al usuario a la siguiente pantalla
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    // Aquí puedes redirigir al usuario a la siguiente actividad

                                    Intent intent = new Intent(MainActivity.this, ListaActivity.class);
                                    intent.putExtra("nombreUsuario", usuario.getNombre());
                                    intent.putExtra("idUsuario", usuario.getId());
                                    startActivity(intent);
                                    return;
                                }
                            }
                            // Contraseña incorrecta
                            Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        } else {
                            // Usuario no encontrado en la base de datos
                            Toast.makeText(MainActivity.this, "Usuario no encontrado, por favor regístrate", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Manejar errores de base de datos
                        Toast.makeText(MainActivity.this, "Error de base de datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });
    }
}
