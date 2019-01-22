package WarstwaPrezentacji;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import WarstwaPrezentacji.PodbudzetPrezentery.PodbudzetPrezenter;
import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    private Cursor cursor;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNazwa;
        Button btOtworz;

        MyViewHolder(View item) {
            super(item);
            tvNazwa = item.findViewById(R.id.pi_nazwa);
            btOtworz = item.findViewById(R.id.pi_usun);
            btOtworz.setText(R.string.otworz);
        }
    }

    MenuAdapter(UchwytBazy uchwyt) {
        cursor = uchwyt.bazaDanych.dajWszystkiePodbudzety(null);
    }

    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podbudzet_item, parent, false);
        return new MenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuAdapter.MyViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.tvNazwa.setText(cursor.getString(1));
        holder.btOtworz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(holder.getAdapterPosition());
                v.getContext().startActivity(new Intent(v.getContext(),
                        PodbudzetPrezenter.class).putExtra("ID_P", cursor.getInt(0)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
