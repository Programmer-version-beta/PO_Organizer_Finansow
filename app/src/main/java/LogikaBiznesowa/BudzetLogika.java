package LogikaBiznesowa;

import WarstwaDostepuDoDanych.BazaDanych;

public class BudzetLogika{
    private BazaDanych uchwytBazy;

    public BudzetLogika(BazaDanych uchwytBazy){
        this.uchwytBazy = uchwytBazy;
    }

    public void obliczSaldoBudzetu(int id_b) {
        uchwytBazy.uaktualnijBudzet(id_b);
    }
}
