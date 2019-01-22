package WarstwaPrezentacji;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import WarstwaPrezentacji.Adaptery.BudzetAdapter;
import WarstwaPrezentacji.PodbudzetPrezentery.DodajPodbudzetPrezenter;
import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;

public class BudzetPrezenter extends AppCompatActivity {
    FloatingActionButton fabDodaj;
    TextView tvSaldo;
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budzet_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
        if(getIntent().getExtras() != null)
            wyswietlPotwierdzenie(getIntent().getExtras().getString("Message"));

    }

    void inicjujElementy() {
        UchwytBazy uchwyt = ((UchwytBazy)getApplication());
        Cursor cursor = uchwyt.bazaDanych.dajWszystkieBudzety("ID_B = 1");
        cursor.moveToFirst();
        tvSaldo = findViewById(R.id.bp_saldo);
        tvSaldo.setText(tvSaldo.getText() + cursor.getString(2));
        fabDodaj = findViewById(R.id.fab);
        fabDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajPodbudzet(v);
            }
        });
        lista = findViewById(R.id.bp_lista_podbudzetow);
        lista.hasFixedSize();
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(new BudzetAdapter(((UchwytBazy)getApplication())));
    }

    void wyswietlPotwierdzenie(String tekst) {
        Toast.makeText(getApplicationContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void dodajPodbudzet(View v) {
        startActivity(new Intent(BudzetPrezenter.this, DodajPodbudzetPrezenter.class));
    }
}
