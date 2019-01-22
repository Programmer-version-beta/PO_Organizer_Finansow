package WarstwaPrezentacji.PodbudzetPrezentery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;

public class DodajPodbudzetPrezenter extends AppCompatActivity {
    EditText etNazwa;
    Button btUtworz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_podbudzet_dane_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
    }

    void inicjujElementy() {
        etNazwa = findViewById(R.id.dpdp_nowa_nazwa);
        btUtworz = findViewById(R.id.dpdp_przycisk_dalej);
        btUtworz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajPodbudzet(v);
            }
        });
    }

    private void dodajPodbudzet(View v) {
        ((UchwytBazy)getApplication()).bazaDanych.dodajPodbudzet(String.valueOf(etNazwa.getText()));
        Cursor cursor = ((UchwytBazy)getApplication()).bazaDanych.dajWszystkiePodbudzety(null, 0);
        cursor.moveToLast();
        ((UchwytBazy)getApplication()).bazaDanych.dodajZwiazek(cursor.getInt(0),1);
        startActivity(new Intent(DodajPodbudzetPrezenter.this,
                WspoldzielPodbudzetZatwierdzeniePrezenter.class).putExtra("ID_P", cursor.getInt(0)));
    }
}
