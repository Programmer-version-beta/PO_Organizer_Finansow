package WarstwaPrezentacji.Adaptery;

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

import LogikaBiznesowa.BudzetLogika;
import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.BudzetPrezenter;

public class WspodzielenieAdapter extends RecyclerView.Adapter<WspodzielenieAdapter.MyViewHolder> {
    private Cursor cursor;
    private UchwytBazy uchwytBazy;
    private int id_u;
    private int id_p;
    private String czyZBudzetu;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNazwa;
        Button btWybierz;

        MyViewHolder(View item) {
            super(item);
            tvNazwa = item.findViewById(R.id.pri_nazwa);
            btWybierz = item.findViewById(R.id.pri_wybierz);
        }
    }

    public WspodzielenieAdapter(UchwytBazy uchwyt, int id_u, int id_p, String czyZBudzetu) {
        cursor = uchwyt.bazaDanych.dajWszystkichUzytkownikow("ID_U !=" + id_u);
        uchwytBazy = uchwyt;
        this.id_u = id_u;
        this.id_p = id_p;
        this.czyZBudzetu = czyZBudzetu;
    }

    @Override
    public WspodzielenieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.przenoszenie_item, parent, false);
        return new WspodzielenieAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WspodzielenieAdapter.MyViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.tvNazwa.setText(cursor.getString(1) + " " + cursor.getString(2));
        holder.btWybierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wybierzUzytkownikow(v, holder.getAdapterPosition());
            }
        });

    }

    private void wybierzUzytkownikow(View v, int position) {
        cursor.moveToPosition(position);
        uchwytBazy.bazaDanych.dodajZwiazek(id_p, cursor.getInt(0));
        BudzetLogika budzetLogika = new BudzetLogika(uchwytBazy.bazaDanych);
        budzetLogika.obliczSaldoBudzetu(cursor.getInt(0));
        if(czyZBudzetu == null)
            v.getContext().startActivity(new Intent(v.getContext(),
                    BudzetPrezenter.class).putExtra("Message", "Podbudżet udostępniony"));
        else {
            Intent intent = new Intent(v.getContext(),
                    PodbudzetPrezenter.class).putExtra("Message", "Podbudżet udostępniony");
            intent.putExtra("ID_P", id_p);
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}