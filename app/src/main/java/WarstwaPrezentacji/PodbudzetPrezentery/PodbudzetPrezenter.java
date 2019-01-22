package WarstwaPrezentacji.PodbudzetPrezentery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabian.po_organizer_wydatkow.R;
import WarstwaPrezentacji.WydatkiPrezentery.ZarzadzajWydatkamiPrezenter;

import WarstwaDostepuDoDanych.UchwytBazy;

public class PodbudzetPrezenter extends AppCompatActivity {
    TextView tvNazwa;
    TextView tvSaldo;
    TextView tvWspoldzielenie;
    Button btWydatki;
    Button btInwestycje;
    Button btPrzychodyO;
    Button btPrzychodyP;
    Button btWspoldzielenie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podbudzet_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicjujElementy();
        if(getIntent().getExtras().getString("Message") != null)
            wyswietlPotwierdzenie(getIntent().getExtras().getString("Message"));
    }

    void inicjujElementy() {
        UchwytBazy uchwyt = ((UchwytBazy)getApplication());
        final int id_p = getIntent().getExtras().getInt("ID_P");
        Cursor cursor = uchwyt.bazaDanych.dajWszystkiePodbudzety("ID_P =" + id_p);
        cursor.moveToFirst();
        tvNazwa = findViewById(R.id.pp_nazwa);
        tvSaldo = findViewById(R.id.pp_saldo);
        tvWspoldzielenie = findViewById(R.id.pp_wspoldzielony);
        tvNazwa.setText(tvNazwa.getText() + cursor.getString(1));
        tvSaldo.setText(tvSaldo.getText() + cursor.getString(2));
        int liczbaUzytkownikow = ((UchwytBazy)getApplication()).bazaDanych.dajWszystkieZwiazki("ID_P =" + id_p).getCount() - 1;
        tvWspoldzielenie.setText(tvWspoldzielenie.getText() + " " + liczbaUzytkownikow + " osobą/ami");
        btWydatki = findViewById(R.id.pp_wydatki);
        btInwestycje = findViewById(R.id.pp_inwestycje);
        btPrzychodyP = findViewById(R.id.pp_przychody_p);
        btPrzychodyO = findViewById(R.id.pp_przychody_o);
        btWspoldzielenie = findViewById(R.id.pp_wspodzielenie);
        btWydatki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zarzadzajWydatkami(v, id_p);
            }
        });
        btWspoldzielenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wspoldzielPodbudzet(v, id_p);
            }
        });
    }

    void wyswietlPotwierdzenie(String tekst) {
        Toast.makeText(getApplicationContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void zarzadzajWydatkami(View v, int id_p) {
        startActivity(new Intent(PodbudzetPrezenter.this,
                ZarzadzajWydatkamiPrezenter.class).putExtra("ID_P", id_p));
    }

    private void wspoldzielPodbudzet(View v, int id_p) {
        Intent intent = new Intent(PodbudzetPrezenter.this,
                WspoldzielPodbudzetZatwierdzeniePrezenter.class).putExtra("Message", "PP");
        intent.putExtra("ID_P", id_p);
        startActivity(intent);
    }
}
