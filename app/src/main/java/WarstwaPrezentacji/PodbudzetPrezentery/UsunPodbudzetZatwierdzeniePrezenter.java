package WarstwaPrezentacji.PodbudzetPrezentery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaPrezentacji.BudzetPrezenter;

public class UsunPodbudzetZatwierdzeniePrezenter extends AppCompatActivity {
    Button btTak;
    Button btNie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usun_podbudzet_zatwierdzenie_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicjujElementy();
    }

    void inicjujElementy() {
        btTak = findViewById(R.id.upzp_przycisk_tak);
        btNie = findViewById(R.id.upzp_przycisk_nie);
        btTak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usunPodbudzet(v);
            }
        });
        btNie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                odrzucUsunieciePodbudzetu(v);
            }
        });
    }

    private void usunPodbudzet(View v) {
        startActivity(new Intent(UsunPodbudzetZatwierdzeniePrezenter.this,
                UsunPodbudzetPrzenoszeniePrezenter.class).putExtra("Number", getIntent().getExtras().getInt("Number")));
        finish();
    }

    private void odrzucUsunieciePodbudzetu(View v) {
        startActivity(new Intent(UsunPodbudzetZatwierdzeniePrezenter.this,
                BudzetPrezenter.class));
        finish();
    }
}
