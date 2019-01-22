package WarstwaDostepuDoDanych;

import android.app.Application;

public class UchwytBazy extends Application {
    public BazaDanych bazaDanych;
    public int aktualnyBudzet = 1;

    @Override
    public void onCreate(){
        super.onCreate();
        bazaDanych = new BazaDanych(getApplicationContext());
        bazaDanych.open();
        if(bazaDanych.dajWszystkichUzytkownikow(null).getCount() == 0) {
            bazaDanych.dodajUzytkownia("Jan", "Kowalski", "Janek", "12345");
            bazaDanych.dodajBudzet(1);
            bazaDanych.dodajUzytkownia("Adam", "Nowak", "AdamN", "12345");
            bazaDanych.dodajBudzet(2);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        bazaDanych.close();
    }

}
