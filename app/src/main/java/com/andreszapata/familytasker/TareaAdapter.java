package com.andreszapata.familytasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TareaAdapter extends ArrayAdapter<Tarea> {

    private DatabaseReference databaseReference;


    public TareaAdapter(Context context, ArrayList<Tarea> tareas) {
        super(context, 0, tareas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tarea, parent, false);
        }

        // Obtener la tarea actual
        final Tarea tarea = getItem(position);

        // Obtener referencias a los elementos de la fila
        TextView textViewTarea = listItemView.findViewById(R.id.textViewTarea);
        Button buttonCompletar = listItemView.findViewById(R.id.buttonCompletar);
        Button buttonEliminar = listItemView.findViewById(R.id.buttonEliminar);

        // Configurar el texto de la tarea
        textViewTarea.setText(tarea.getNombre());

        // Manejar el clic en el botón "Completar"
        buttonCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica para marcar la tarea como completada
                // Por ejemplo, podrías cambiar el estado de la tarea en la base de datos
            }
        });

        // Manejar el clic en el botón "Eliminar"
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la tarea seleccionada
                Tarea tarea = getItem(position); // Asegúrate de que getItem(position) devuelva la tarea correcta

                // Verificar si la tarea no es nula y tiene un ID
                if (tarea != null && tarea.getId() != null) {
                    // Eliminar la tarea de la base de datos
                    databaseReference.child(tarea.getId()).removeValue(); // Asegúrate de que tarea.getId() no sea nulo
                    Toast.makeText(getContext(), "Tarea eliminada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: No se pudo eliminar la tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return listItemView;
    }
}
