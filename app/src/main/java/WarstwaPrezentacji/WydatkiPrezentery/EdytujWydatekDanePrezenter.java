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

public class EdytujWydatekDanePrezenter extends AppCompatActivity {
    EditText etNazwa;
    Spinner spKategoria;
    EditText etCena;
    EditText etData;
    Button btZatwierdz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_wydatek_dane_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
    }

    void inicjujElementy(){
        etNazwa = findViewById(R.id.ewdp_nazwa);
        etCena = findViewById(R.id.ewdp_cena);
        spKategoria = findViewById(R.id.ewdp_kategoria);
        spKategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BazaDanych.Kategoria.values()));
        etData = findViewById(R.id.ewdp_data);
        btZatwierdz = findViewById(R.id.ewdp_przycisk_zatwierdz);
        btZatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edytujWydatek(v);
            }
        });
    }

    private void edytujWydatek(View v) {
        int id_p = getIntent().getExtras().getInt("ID_P");
        ((UchwytBazy)getApplication()).bazaDanych.edytujWydatek(getIntent().getExtras().getInt("ID_W"), id_p,
                String.valueOf(etNazwa.getText()), Double.valueOf(String.valueOf(etCena.getText())), spKategoria.getSelectedItem().toString(), String.valueOf(etData.getText()));
        PodbudzetLogika podbudzetLogika = new PodbudzetLogika(((UchwytBazy) getApplication()).bazaDanych);
        podbudzetLogika.obliczSaldoPodbudzetu(id_p);
        Intent intent = new Intent(EdytujWydatekDanePrezenter.this,
                ZarzadzajWydatkamiPrezenter.class).putExtra("Message", "Edycja zakończona pomyślnie");
        intent.putExtra("ID_P", id_p);
        startActivity(intent);
    }

}
