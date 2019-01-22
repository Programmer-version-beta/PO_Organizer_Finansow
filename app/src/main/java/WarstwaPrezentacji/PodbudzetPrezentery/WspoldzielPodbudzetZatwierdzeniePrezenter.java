package WarstwaPrezentacji.PodbudzetPrezentery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.BudzetPrezenter;

public class WspoldzielPodbudzetZatwierdzeniePrezenter extends AppCompatActivity {
    Button btTak;
    Button btNie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wspoldziel_podbudzet_zatwierdzenie_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicjujElementy();
    }

    void inicjujElementy() {
        btTak = findViewById(R.id.wpzp_przycisk_tak);
        btNie = findViewById(R.id.wpzp_przycisk_nie);
        btTak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zatwierdzWspoldzielenie(v);
            }
        });
        btNie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odrzucWspoldzielenie(v);
            }
        });
    }

    private void zatwierdzWspoldzielenie (View v) {
        int liczbaUzytkownikow = ((UchwytBazy)getApplication()).bazaDanych.dajWszystkichUzytkownikow(null).getCount();
        if(liczbaUzytkownikow == 1)
            if(getIntent().getExtras().getString("Message") != null)
                startActivity(new Intent(WspoldzielPodbudzetZatwierdzeniePrezenter.this,
                        PodbudzetPrezenter.class).putExtra("Message", "Brak innych użytkowników"));
            else
                startActivity(new Intent(WspoldzielPodbudzetZatwierdzeniePrezenter.this,
                        BudzetPrezenter.class).putExtra("Message", "Brak innych użytkowników"));
        else {
            Intent intent = new Intent(WspoldzielPodbudzetZatwierdzeniePrezenter.this,
                    WspoldzielPodbudzetWyborPrezenter.class);
            intent.putExtra("ID_P", getIntent().getExtras().getInt("ID_P"));
            if(getIntent().getExtras().getString("Message") != null)
                intent.putExtra("Message", "PP");
            startActivity(intent);
        }
    }

    private void odrzucWspoldzielenie (View v) {
        if(getIntent().getExtras().getString("Message") != null)
            startActivity(new Intent(WspoldzielPodbudzetZatwierdzeniePrezenter.this,
                    PodbudzetPrezenter.class));
        else
            startActivity(new Intent(WspoldzielPodbudzetZatwierdzeniePrezenter.this,
                    BudzetPrezenter.class).putExtra("Message", "Dodano podbudzet"));
    }
}
