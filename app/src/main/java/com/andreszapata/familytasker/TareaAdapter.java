package com.andreszapata.familytasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TareaAdapter extends ArrayAdapter<Tarea> {

    public TareaAdapter(Context context, List<Tarea> tareas) {
        super(context, 0, tareas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el elemento de la lista en la posición actual
        Tarea tarea = getItem(position);

        // Verificar si la vista está siendo reutilizada, de lo contrario, inflarla
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_tarea, parent, false);
        }

        // Obtener las referencias a las vistas
        TextView textViewNombreTarea = convertView.findViewById(R.id.textViewNombreTarea);
        TextView textViewDescripcionTarea = convertView.findViewById(R.id.textViewDescripcionTarea);

        // Establecer los valores de las vistas
        textViewNombreTarea.setText(tarea.getNombre());
        textViewDescripcionTarea.setText(tarea.getDescripcion());

        // Devolver la vista inflada y modificada para mostrar los datos de la tarea
        return convertView;
    }
}