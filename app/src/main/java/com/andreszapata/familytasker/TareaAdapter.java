package com.andreszapata.familytasker;

import android.content.Context;
import android.graphics.Paint;
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

        // Cambiar el estilo del texto si la tarea está completada
        if (tarea.isCompletada()) {
            textViewTarea.setPaintFlags(textViewTarea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textViewTarea.setPaintFlags(textViewTarea.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Manejar el clic en el botón "Completar"
        buttonCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea.setCompletada(!tarea.isCompletada());
                // Guardar los cambios en la base de datos u otro almacenamiento
                // Notificar al adaptador que los datos han cambiado
                notifyDataSetChanged();
            }
        });

        // Manejar el clic en el botón "Eliminar"
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la tarea seleccionada
                // Obtener la posición de la tarea
                int position = getPosition(tarea);
                // Eliminar la tarea de la lista
                remove(tarea);
                // Eliminar la tarea de la base de datos
                // Notificar al adaptador que los datos han cambiado
                notifyDataSetChanged();
            }
        });


        return listItemView;
    }
}
