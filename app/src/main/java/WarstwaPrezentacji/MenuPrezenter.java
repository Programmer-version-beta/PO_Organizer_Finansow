package WarstwaPrezentacji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.fabian.po_organizer_wydatkow.R;

import WarstwaDostepuDoDanych.UchwytBazy;

public class MenuPrezenter extends AppCompatActivity {
    Button bt;
    RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_prezenter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    void initViews() {
        bt = findViewById(R.id.mp_budzet);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrezenter.this,
                        BudzetPrezenter.class));
            }
        });
        lista = findViewById(R.id.mp_lista);
        lista.hasFixedSize();
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(new MenuAdapter(((UchwytBazy)getApplication())));
    }

}
