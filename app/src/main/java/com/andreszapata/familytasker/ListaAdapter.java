package com.andreszapata.familytasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListaAdapter extends ArrayAdapter<Lista> {

    public ListaAdapter(Context context, List<Lista> listas) {
        super(context, 0, listas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtiene el objeto Lista en la posición dada
        Lista lista = getItem(position);

        // Verifica si una vista existente está siendo reutilizada, de lo contrario infla la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Busca el TextView en la vista inflada y establece el nombre de la lista
        TextView textView = convertView.findViewById(android.R.id.text1);
        if (lista != null) {
            textView.setText(lista.getNombre());
        }

        return convertView;
    }
}

