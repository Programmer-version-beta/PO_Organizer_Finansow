package LogikaBiznesowa;

import android.database.Cursor;

import WarstwaDostepuDoDanych.BazaDanych;

public class PodbudzetLogika {
    private BazaDanych uchwytBazy;

    public PodbudzetLogika(BazaDanych uchwytBazy){
        this.uchwytBazy = uchwytBazy;
    }

    public void obliczSaldoPodbudzetu(int id_p) {
        uchwytBazy.uaktualnijPodbudzet(id_p);
        Cursor cursor = uchwytBazy.dajIdBudzetowDlaPodbudzetu(id_p);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            do {
                uchwytBazy.uaktualnijBudzet(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
    }
}
