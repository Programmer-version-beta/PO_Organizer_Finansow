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

import LogikaBiznesowa.PodbudzetLogika;
import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.BudzetPrezenter;

public class PrzenoszenieAdapter extends RecyclerView.Adapter<PrzenoszenieAdapter.MyViewHolder> {
    private Cursor cursor;
    private UchwytBazy uchwytBazy;
    private int id;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNazwa;
        Button btWybierz;

        MyViewHolder(View item) {
            super(item);
            tvNazwa = item.findViewById(R.id.pri_nazwa);
            btWybierz = item.findViewById(R.id.pri_wybierz);
        }
    }

    public PrzenoszenieAdapter(UchwytBazy uchwyt, int id) {
        cursor = uchwyt.bazaDanych.dajWszystkiePodbudzety("ID_P !=" + id);
        uchwytBazy = uchwyt;
        this.id = id;
    }

    @Override
    public PrzenoszenieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.przenoszenie_item, parent, false);
        return new PrzenoszenieAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PrzenoszenieAdapter.MyViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        holder.tvNazwa.setText(cursor.getString(1));
        holder.btWybierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wybierzPodbudzet(v, holder.getAdapterPosition());
            }
        });

    }

    private void wybierzPodbudzet(View v, int position){
        cursor.moveToPosition(position);
        uchwytBazy.bazaDanych.usunPodbudzet(id);
        uchwytBazy.bazaDanych.usunZwiazek("ID_P =" + id);
        uchwytBazy.bazaDanych.przeniesWydatki(cursor.getInt(0), id);
        PodbudzetLogika podbudzetLogika = new PodbudzetLogika(uchwytBazy.bazaDanych);
        podbudzetLogika.obliczSaldoPodbudzetu(cursor.getInt(0));
        v.getContext().startActivity(new Intent(v.getContext(),
                BudzetPrezenter.class).putExtra("Message", "Podbudżet usunięty"));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
