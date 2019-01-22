package com.example.fabian.po_organizer_wydatkow;

import android.content.Context;
import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import LogikaBiznesowa.BudzetLogika;
import LogikaBiznesowa.PodbudzetLogika;
import WarstwaDostepuDoDanych.BazaDanych;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExampleUnitTest {
    private Context context = RuntimeEnvironment.application;
    private BazaDanych mDb;

    @Before
    public void createDb() {
        mDb = new BazaDanych(context);
        mDb.open();
        mDb.dodajBudzet(1);
    }

    @After
    public void closeDb()  {
        mDb.close();
    }

    //Testy jednostkowe działania bazy danych
    @Test
    public void prawidloweDodawaniePodbudzetu() {
        assertEquals(1L, mDb.dodajPodbudzet("Podbudzet"));
    }

    @Test
    public void prawidloweUsuwanieWydatku() {
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "");
        assertEquals( Boolean.TRUE, mDb.usunWydatek("id_w = 1"));
    }

    @Test
    public void bledneUsuwanieWydatku() {
        assertEquals( Boolean.FALSE, mDb.usunWydatek("id_w = 0")); //nieistniejąca wartość klucza głównego
    }

    @Test
    public void prawidlowaEdycjaWydatku() {
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        assertEquals(Boolean.TRUE, mDb.edytujWydatek(1, 1, "Bułka", 2, "Jedzenie", "01.01.2019"));
    }

    @Test
    public void blednaEdycjaWydatku() {
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        assertEquals(Boolean.FALSE, mDb.edytujWydatek(2, 1, "Bułka", 2, "Jedzenie", "01.01.2019")); //nieistniejąca wartość klucza głównego
    }

    @Test
    public void obliczanieSaldaPustegoPodbudzetu() {
        mDb.dodajPodbudzet("Test");
        PodbudzetLogika ob = new PodbudzetLogika(mDb);
        ob.obliczSaldoPodbudzetu(1);
        Cursor cursor = mDb.dajWszystkiePodbudzety(null);
        cursor.moveToFirst();
        assertEquals(0.0, cursor.getDouble(2), 0.001);
    }

    @Test
    public void obliczanieSaldaPodbudzetuZJednymWydatkiem() {
        mDb.dodajPodbudzet("Test");
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        PodbudzetLogika ob = new PodbudzetLogika(mDb);
        ob.obliczSaldoPodbudzetu(1);
        Cursor cursor = mDb.dajWszystkiePodbudzety(null);
        cursor.moveToFirst();
        assertEquals(1, cursor.getDouble(2), 0.001);
    }

    @Test
    public void oblczanieSladaPodbudzetuZDwomaWydatkami() {
        mDb.dodajPodbudzet("Test");
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        PodbudzetLogika ob = new PodbudzetLogika(mDb);
        ob.obliczSaldoPodbudzetu(1);
        Cursor cursor = mDb.dajWszystkiePodbudzety(null);
        cursor.moveToFirst();
        assertEquals(2, cursor.getDouble(2), 0.001);
    }

    @Test
    public void obliczanieSaldaBudzetuBezPodbudzetow() {
        BudzetLogika ob = new BudzetLogika(mDb);
        ob.obliczSaldoBudzetu(1);
        Cursor cursor = mDb.dajWszystkieBudzety(null);
        cursor.moveToFirst();
        assertEquals(0, cursor.getDouble(2), 0.001);
    }

    @Test
    public void obliczanieSaldaBudzetuZJednymPodbudzetem() {
        mDb.dodajPodbudzet("Test");
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        PodbudzetLogika ob = new PodbudzetLogika(mDb);
        ob.obliczSaldoPodbudzetu(1);
        mDb.dodajZwiazek(1,1);
        BudzetLogika ob2 = new BudzetLogika(mDb);
        ob2.obliczSaldoBudzetu(1);
        Cursor cursor = mDb.dajWszystkieBudzety(null);
        cursor.moveToFirst();
        assertEquals(1, cursor.getDouble(2), 0.001);
    }

    @Test
    public void obliczanieSaldaBudzetuZDwomaPodbudzetami() {
        mDb.dodajPodbudzet("Test");
        mDb.dodajPodbudzet("Test2");
        mDb.dodajWydatek(1, "Bułka", 1, "Jedzenie", "01.01.2019");
        PodbudzetLogika ob = new PodbudzetLogika(mDb);
        ob.obliczSaldoPodbudzetu(1);
        mDb.dodajZwiazek(1,1);
        mDb.dodajWydatek(2, "Bułka", 1, "Jedzenie", "01.01.2019");
        ob.obliczSaldoPodbudzetu(2);
        mDb.dodajZwiazek(2,1);
        BudzetLogika ob2 = new BudzetLogika(mDb);
        ob2.obliczSaldoBudzetu(1);
        Cursor cursor = mDb.dajWszystkieBudzety(null);
        cursor.moveToFirst();
        assertEquals(2, cursor.getDouble(2), 0.001);
    }
}