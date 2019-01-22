package com.example.fabian.po_organizer_wydatkow;

import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import WarstwaDostepuDoDanych.UchwytBazy;
import WarstwaPrezentacji.MenuPrezenter;
import WarstwaPrezentacji.PodbudzetPrezentery.PodbudzetPrezenter;
import WarstwaPrezentacji.WydatkiPrezentery.ZarzadzajWydatkamiPrezenter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private UchwytBazy uchwyt;
    public static class MyViewAction {

        static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    @Rule
    public ActivityTestRule<MenuPrezenter> basicrule =
            new ActivityTestRule<>(MenuPrezenter.class, true, true);
    @Rule
    public ActivityTestRule<ZarzadzajWydatkamiPrezenter> rule =
            new ActivityTestRule<>(ZarzadzajWydatkamiPrezenter.class, true, false);

    @Rule
    public ActivityTestRule<PodbudzetPrezenter> rule2 =
            new ActivityTestRule<>(PodbudzetPrezenter.class, true, false);

    @Before
    public void createDB() {
        uchwyt = (UchwytBazy) basicrule.getActivity().getApplication();
        if(uchwyt.bazaDanych.dajWszystkiePodbudzety("ID_P = 1").getCount() == 0) {
            uchwyt.bazaDanych.dodajPodbudzet("Wakacje");
            uchwyt.bazaDanych.dodajZwiazek(1, 1);
        }
        uchwyt.bazaDanych.usunZwiazek("ID_P = 1 AND ID_B = 2");
    }

    //Dodanie wydatku
    @Test
    public void PT005(){
        Intent intent = new Intent();
        intent.putExtra("ID_P", 1);
        rule.launchActivity(intent);
        onView(withId(R.id.zwp_dodaj)).perform(click());
        onView(withId(R.id.dwdp_nazwa)).perform(replaceText("Bułka"), closeSoftKeyboard());
        onView(withId(R.id.dwdp_cena)).perform(replaceText("0.5"), closeSoftKeyboard());
        onView(withId(R.id.dwdp_data)).perform(replaceText("01-01-2019"), closeSoftKeyboard());
        onView(withId(R.id.dwdp_przycisk_dodaj)).perform(click());
        onView(withText(R.string.dwpp_potwierdzenie_napis)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        assertEquals(1, uchwyt.bazaDanych.dajWszystkieWydatki("Kwota = 0.5").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkieBudzety("Saldo = 0.5 AND ID_B = 1").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkiePodbudzety("Saldo = 0.5").getCount());
    }

    //Współdzielenie podbudżetu
    @Test
    public void PT006() {
        Intent intent = new Intent();
        intent.putExtra("ID_P", 1);
        rule2.launchActivity(intent);
        onView(withId(R.id.pp_wspodzielenie)).perform(click());
        onView(withId(R.id.wpzp_przycisk_tak)).perform(click());
        onView(withId(R.id.wpwp_lista)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.pri_wybierz)));
        onView(withText("Podbudżet udostępniony")).inRoot(withDecorView(not(is(rule2.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        assertEquals(2, uchwyt.bazaDanych.dajWszystkieBudzety("Saldo = 0.5").getCount());
    }

    //Edycja wydatku
    @Test
    public void PT007() {
        Intent intent = new Intent();
        intent.putExtra("ID_P", 1);
        rule.launchActivity(intent);
        onView(withId(R.id.zwp_lista)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.wi_edycja)));
        onView(withId(R.id.ewdp_nazwa)).perform(replaceText("Bułka"), closeSoftKeyboard());
        onView(withId(R.id.ewdp_cena)).perform(replaceText("0.6"), closeSoftKeyboard());
        onView(withId(R.id.ewdp_data)).perform(replaceText("01-01-2019"), closeSoftKeyboard());
        onView(withId(R.id.ewdp_przycisk_zatwierdz)).perform(click());
        onView(withText(R.string.ewpp_potwierdzenie_napis)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        assertEquals(1, uchwyt.bazaDanych.dajWszystkieWydatki("Kwota = 0.6").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkieBudzety("Saldo = 0.6").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkiePodbudzety("Saldo = 0.6").getCount());
    }

    //Usunięcie wydatku
    @Test
    public void PT008() {
        Intent intent = new Intent();
        intent.putExtra("ID_P", 1);
        rule.launchActivity(intent);
        onView(withId(R.id.zwp_lista)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.wi_usun)));
        onView(withId(R.id.uwzp_przycisk_tak)).perform(click());
        onView(withText(R.string.uwpp_potwierdzenie_napis)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        assertEquals(0, uchwyt.bazaDanych.dajWszystkieWydatki("Kwota = 0.6").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkieBudzety("Saldo = 0 AND ID_B = 1").getCount());
        assertEquals(1, uchwyt.bazaDanych.dajWszystkiePodbudzety("Saldo = 0").getCount());
    }

    @After
    public void dropDB() {
    }
}


