package WarstwaPrezentacji.PodbudzetPrezentery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.fabian.po_organizer_wydatkow.R;
import WarstwaPrezentacji.Adaptery.WspodzielenieAdapter;

import WarstwaDostepuDoDanych.UchwytBazy;

public class WspoldzielPodbudzetWyborPrezenter extends AppCompatActivity {
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wspoldziel_podbudzet_wybor_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
    }

    void inicjujElementy() {
        lista = findViewById(R.id.wpwp_lista);
        lista.hasFixedSize();
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(new WspodzielenieAdapter(((UchwytBazy)getApplication()), 1,
                getIntent().getExtras().getInt("ID_P"), getIntent().getExtras().getString("Message")));
    }

}
