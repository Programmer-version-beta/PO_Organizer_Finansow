package WarstwaPrezentacji.WydatkiPrezentery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.Adaptery.WydatkiAdapter;
import WarstwaPrezentacji.PodbudzetPrezentery.PodbudzetPrezenter;

public class ZarzadzajWydatkamiPrezenter extends AppCompatActivity {
    RecyclerView listaWydatkow;
    FloatingActionButton fabDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarzadzaj_wydatkami_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElemenety();
        if(getIntent().getExtras().getString("Message") != null)
            wyswietlPotwierdzenie(getIntent().getExtras().getString("Message"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        startActivity(new Intent(getApplicationContext(), PodbudzetPrezenter.class).putExtra("ID_P", getIntent().getExtras().getInt("ID_P")));
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    void inicjujElemenety() {
        listaWydatkow = findViewById(R.id.zwp_lista);
        listaWydatkow.setHasFixedSize(true);
        listaWydatkow.setLayoutManager(new LinearLayoutManager(this));
        listaWydatkow.setAdapter(new WydatkiAdapter(((UchwytBazy)getApplication()), getIntent().getExtras().getInt("ID_P")));
        fabDodaj = findViewById(R.id.zwp_dodaj);
        fabDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydatek(v);
            }
        });
    }

    void wyswietlPotwierdzenie(String tekst) {
        Toast.makeText(getApplicationContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void dodajWydatek(View v) {
        startActivity(new Intent(ZarzadzajWydatkamiPrezenter.this,
                DodajWydatekDanePrezenter.class).putExtra("ID_P", getIntent().getExtras().getInt("ID_P")));
    }
}
