package WarstwaPrezentacji.PodbudzetPrezentery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.fabian.po_organizer_wydatkow.R;

import LogikaBiznesowa.BudzetLogika;
import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.BudzetPrezenter;

public class UsunPodbudzetPrzenoszeniePrezenter extends AppCompatActivity {
    Button btTak;
    Button btNie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usun_podbudzet_przenoszenie_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicjujElementy();
    }

    void inicjujElementy() {
        btTak = findViewById(R.id.uppp_przycisk_tak);
        btNie = findViewById(R.id.uppp_przycisk_nie);
        btTak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zatwierdzPrzenoszenie(v);
            }
        });
        btNie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odrzucPrzenoszenie(v);
            }
        });
    }

    private void zatwierdzPrzenoszenie(View v) {
        Cursor cursor = ((UchwytBazy)getApplication()).bazaDanych.dajWszystkiePodbudzety("ID_P !=" + getIntent().getExtras().getInt("Number"));
        if (cursor.getCount() > 0)
            startActivity(new Intent(UsunPodbudzetPrzenoszeniePrezenter.this,
                    UsunPodbudzetWyborPrezenter.class).putExtra("Number", getIntent().getExtras().getInt("Number")));
        else
            startActivity(new Intent(UsunPodbudzetPrzenoszeniePrezenter.this,
                    BudzetPrezenter.class).putExtra("Message", "Brak innych podbudzetow"));
        finish();
    }

    private void odrzucPrzenoszenie(View v) {
        int id_p = getIntent().getExtras().getInt("Number");
        ((UchwytBazy)getApplication()).bazaDanych.usunPodbudzet(id_p);
        ((UchwytBazy)getApplication()).bazaDanych.usunZwiazek("ID_P =" + id_p);
        ((UchwytBazy)getApplication()).bazaDanych.usunWydatek("ID_P =" + id_p);
        BudzetLogika budzetLogika = new BudzetLogika((((UchwytBazy) getApplication()).bazaDanych));
        budzetLogika.obliczSaldoBudzetu(1);
        startActivity(new Intent(UsunPodbudzetPrzenoszeniePrezenter.this,
                BudzetPrezenter.class).putExtra("Message", "Podbudżet usunięty"));
        finish();
    }

}
