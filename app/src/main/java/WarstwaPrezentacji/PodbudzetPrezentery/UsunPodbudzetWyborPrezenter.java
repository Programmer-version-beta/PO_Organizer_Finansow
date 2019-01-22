package WarstwaPrezentacji.PodbudzetPrezentery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import WarstwaPrezentacji.Adaptery.PrzenoszenieAdapter;
import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;

public class UsunPodbudzetWyborPrezenter extends AppCompatActivity {
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usun_podbudzet_wybor_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicjujElementy();
    }

    void inicjujElementy(){
        lista = findViewById(R.id.upwp_lista);
        lista.hasFixedSize();
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(new PrzenoszenieAdapter(((UchwytBazy)getApplication()), getIntent().getExtras().getInt("Number")));
    }
}
