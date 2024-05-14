package com.andreszapata.familytasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ListaAdapter extends ArrayAdapter<Lista> {

    private ArrayList<Lista> listas;
    private Context context;

    public ListaAdapter(Context context, ArrayList<Lista> listas) {
        super(context, 0, listas);
        this.context = context;
        this.listas = listas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Verificar si la vista actual está siendo reutilizada, de lo contrario, inflar la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        }

        // Obtener la lista actual
        Lista lista = listas.get(position);

        // Obtener referencias a las vistas en el layout del item
        TextView textViewListName = convertView.findViewById(R.id.textViewListName);

        // Establecer los datos de la lista en las vistas
        textViewListName.setText(lista.getNombre());

        return convertView;
    }
}
