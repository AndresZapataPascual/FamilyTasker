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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


        buttonCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea.setCompletada(!tarea.isCompletada());

                // Obtener la referencia a la base de datos para esta tarea
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tareas")
                        .child(tarea.getIdLista())
                        .child(tarea.getId());

                // Actualizar el estado de la tarea en la base de datos
                databaseReference.child("completada").setValue(tarea.isCompletada()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Notificar al adaptador que los datos han cambiado
                            notifyDataSetChanged();
                            Toast.makeText(getContext(), "Estado de tarea actualizado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al actualizar el estado de la tarea: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la tarea seleccionada
                Tarea tarea = getItem(position); // Obtener la tarea actual
                if (tarea != null) {
                    // Obtener la referencia a la base de datos
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tareas").child(tarea.getIdLista()).child(tarea.getId());

                    // Eliminar la tarea de la base de datos
                    databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Si la eliminación de la base de datos es exitosa, elimina la tarea de la lista local
                                remove(tarea);
                                // Notificar al adaptador que los datos han cambiado
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Tarea eliminada correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                // mensaje de error
                                Toast.makeText(getContext(), "Error al eliminar la tarea: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return listItemView;
    }
}
