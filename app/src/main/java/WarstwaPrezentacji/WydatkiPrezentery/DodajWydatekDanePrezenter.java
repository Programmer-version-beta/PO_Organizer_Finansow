package WarstwaPrezentacji.WydatkiPrezentery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fabian.po_organizer_wydatkow.R;

import LogikaBiznesowa.PodbudzetLogika;
import WarstwaDostepuDoDanych.BazaDanych;
import WarstwaDostepuDoDanych.UchwytBazy;

public class DodajWydatekDanePrezenter extends AppCompatActivity {
    EditText etNazwa;
    Spinner spKategoria;
    EditText etCena;
    EditText etData;
    Button btDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wydatek_dane_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
    }

    void inicjujElementy() {
        etNazwa = findViewById(R.id.dwdp_nazwa);
        spKategoria = findViewById(R.id.dwdp_kategoria);
        spKategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BazaDanych.Kategoria.values()));
        etCena = findViewById(R.id.dwdp_cena);
        etData = findViewById(R.id.dwdp_data);
        btDodaj = findViewById(R.id.dwdp_przycisk_dodaj);
        btDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydatek(v);
            }
        });
    }

    private void dodajWydatek(View v) {
        int id_p = getIntent().getExtras().getInt("ID_P");
        ((UchwytBazy)getApplication()).bazaDanych.dodajWydatek(id_p, String.valueOf(etNazwa.getText()),
                Double.valueOf(String.valueOf(etCena.getText())), spKategoria.getSelectedItem().toString(), String.valueOf(etData.getText()));
        PodbudzetLogika podbudzetLogika = new PodbudzetLogika(((UchwytBazy) getApplication()).bazaDanych);
        podbudzetLogika.obliczSaldoPodbudzetu(id_p);
        Intent intent = new Intent(DodajWydatekDanePrezenter.this,
                ZarzadzajWydatkamiPrezenter.class).putExtra("Message", "Wydatek został dodany");
        intent.putExtra("ID_P", getIntent().getExtras().getInt("ID_P"));
        startActivity(intent);
    }
}
