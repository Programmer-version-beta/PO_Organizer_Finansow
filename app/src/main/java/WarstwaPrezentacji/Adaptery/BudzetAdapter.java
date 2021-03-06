package WarstwaPrezentacji.Adaptery;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.PodbudzetPrezentery.UsunPodbudzetZatwierdzeniePrezenter;

public class BudzetAdapter extends RecyclerView.Adapter<BudzetAdapter.MyViewHolder> {
    private Cursor cursor;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNazwa;
        Button btUsun;

        MyViewHolder(View item) {
            super(item);
            tvNazwa = item.findViewById(R.id.pi_nazwa);
            btUsun = item.findViewById(R.id.pi_usun);
        }
    }

    public BudzetAdapter(UchwytBazy uchwyt) {
        cursor = uchwyt.bazaDanych.dajWszystkiePodbudzety(null);
    }

    @Override
    public BudzetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podbudzet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.tvNazwa.setText(cursor.getString(1));
        holder.btUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usunPodbudzet(v, holder.getAdapterPosition());
            }
        });

    }

    private void usunPodbudzet(View v, int position) {
        cursor.moveToPosition(position);
        v.getContext().startActivity(new Intent(v.getContext(),
                UsunPodbudzetZatwierdzeniePrezenter.class).putExtra("Number", cursor.getInt(0)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}