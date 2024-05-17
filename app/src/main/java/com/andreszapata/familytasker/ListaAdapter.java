package com.andreszapata.familytasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListaAdapter extends ArrayAdapter<Lista> {

    private DatabaseReference databaseReference;

    public ListaAdapter(Context context, List<Lista> listas) {
        super(context, 0, listas);
        databaseReference = FirebaseDatabase.getInstance().getReference("Listas");
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Lista lista = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
        }

        TextView textViewListName = convertView.findViewById(R.id.textViewListName);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

        if (lista != null) {
            textViewListName.setText(lista.getNombre());

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Eliminar la lista de Firebase
                    String userId = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("idUsuario", null);
                    if (userId != null) {
                        databaseReference.child(userId).child(lista.getId()).removeValue();
                    }
                }
            });
        }

        return convertView;
    }
}
