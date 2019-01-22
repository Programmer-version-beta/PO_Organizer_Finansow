package WarstwaPrezentacji.Adaptery;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import WarstwaPrezentacji.WydatkiPrezentery.EdytujWydatekDanePrezenter;
import com.example.fabian.po_organizer_wydatkow.R;
import WarstwaPrezentacji.WydatkiPrezentery.UsunWydatekZatwierdzeniePrezenter;

import WarstwaDostepuDoDanych.UchwytBazy;

public class WydatkiAdapter extends RecyclerView.Adapter<WydatkiAdapter.MyViewHolder> {
    private Cursor cursor;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNazwa;
        TextView tvKategoria;
        TextView tvCena;
        TextView tvData;
        Button btEdycja;
        Button btUsun;

        MyViewHolder(View item) {
            super(item);
            tvNazwa = item.findViewById(R.id.wi_nazwa);
            tvCena = item.findViewById(R.id.wi_cena);
            tvData = item.findViewById(R.id.wi_data);
            tvKategoria = item.findViewById(R.id.wi_kategoria);
            btUsun = item.findViewById(R.id.wi_usun);
            btEdycja = item.findViewById(R.id.wi_edycja);
        }
    }

    public WydatkiAdapter(UchwytBazy uchwyt, int id_p) {
        cursor = uchwyt.bazaDanych.dajWszystkieWydatki("ID_P =" + id_p);
    }

    @Override
    public WydatkiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wydatki_item, parent, false);
        return new WydatkiAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WydatkiAdapter.MyViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.tvNazwa.setText(holder.tvNazwa.getText() + cursor.getString(2));
        holder.tvData.setText(holder.tvData.getText() + cursor.getString(3));
        holder.tvCena.setText(holder.tvCena.getText() + cursor.getString(4));
        holder.tvKategoria.setText(holder.tvKategoria.getText() + cursor.getString(5));
        holder.btUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usunWydatek(v, holder.getAdapterPosition());
            }
        });
        holder.btEdycja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edytujWydatek(v, holder.getAdapterPosition());
            }
        });

    }

    private void usunWydatek(View v, int position) {
        cursor.moveToPosition(position);
        Intent intent = new Intent(v.getContext(),
                UsunWydatekZatwierdzeniePrezenter.class);
        intent.putExtra("ID_W", cursor.getInt(0));
        intent.putExtra("ID_P" ,cursor.getInt(1));
        v.getContext().startActivity(intent);
    }

    private void edytujWydatek(View v, int position) {
        cursor.moveToPosition(position);
        Intent intent = new Intent(v.getContext(),
                EdytujWydatekDanePrezenter.class);
        intent.putExtra("ID_W", cursor.getInt(0));
        intent.putExtra("ID_P" ,cursor.getInt(1));
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}