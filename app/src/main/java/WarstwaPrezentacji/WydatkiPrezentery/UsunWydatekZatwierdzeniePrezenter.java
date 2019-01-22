package WarstwaPrezentacji.WydatkiPrezentery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.fabian.po_organizer_wydatkow.R;

import LogikaBiznesowa.PodbudzetLogika;
import WarstwaDostepuDoDanych.UchwytBazy;

public class UsunWydatekZatwierdzeniePrezenter extends AppCompatActivity {
    Button btTak;
    Button btNie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usun_wydatek_zatwierdzenie_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
    }

    void inicjujElementy() {
        btTak = findViewById(R.id.uwzp_przycisk_tak);
        btNie = findViewById(R.id.uwzp_przycisk_nie);
        btNie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nieUsuwajWydatku(v);
            }
        });
        btTak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usunWydatek(v);
            }
        });
    }

    private void usunWydatek(View v) {
        ((UchwytBazy)getApplication()).bazaDanych.usunWydatek("ID_W=" + getIntent().getExtras().getInt("ID_W"));
        PodbudzetLogika podbudzetLogika = new PodbudzetLogika(((UchwytBazy) getApplication()).bazaDanych);
        podbudzetLogika.obliczSaldoPodbudzetu(getIntent().getExtras().getInt("ID_P"));
        Intent intent = new Intent(UsunWydatekZatwierdzeniePrezenter.this,
                ZarzadzajWydatkamiPrezenter.class).putExtra("Message", "Wydatek został usunięty.");
        intent.putExtra("ID_P", getIntent().getExtras().getInt("ID_P"));
        startActivity(intent);
    }

    private void nieUsuwajWydatku(View v) {
        startActivity(new Intent(UsunWydatekZatwierdzeniePrezenter.this,
                ZarzadzajWydatkamiPrezenter.class));
    }
}
