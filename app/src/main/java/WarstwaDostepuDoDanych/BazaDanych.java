package WarstwaDostepuDoDanych;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BazaDanych {
    private static String BD_NAZWA = "ORGANIZER_FINANSOW";
    private static int BD_WERSJA = 1;
    private static String UTWORZ_TABLICE_ZWIAZKI = "CREATE TABLE Zwiazki (ID_P integer(10) NOT NULL, ID_B integer(10) NOT NULL, PRIMARY KEY (ID_P, ID_B));";
    private static String UTWORZ_TABLICE_PRZYCHODY_P = "CREATE TABLE Przychody_prognozowane (ID_PP INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_P integer(10) NOT NULL, Zrodlo varchar(255) NOT NULL, Data time NOT NULL, Kwota real(10) NOT NULL, Kategoria varchar(255) NOT NULL, FOREIGN KEY(ID_P) REFERENCES Podbudzety(ID_P));";
    private static String UTWORZ_TABLICE_PRZYCHODY_O = "CREATE TABLE Przychody_osiagniete (ID_PO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_P integer(10) NOT NULL, Zrodlo varchar(255) NOT NULL, Data time NOT NULL, Kwota real(10) NOT NULL, Katgoria varchar(255) NOT NULL, FOREIGN KEY(ID_P) REFERENCES Podbudzety(ID_P));";
    private static String UTWORZ_TABLICE_INWESTYCJE = "CREATE TABLE Inwestycje (ID_I INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_P integer(10) NOT NULL, Cel varchar(255) NOT NULL, Data time NOT NULL, Kwota real(10) NOT NULL, Kategoria varchar(255) NOT NULL, FOREIGN KEY(ID_P) REFERENCES Podbudzety(ID_P));";
    private static String UTWORZ_TABLICE_UZYTKOWNICY = "CREATE TABLE Uzytkownicy (ID_U INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Imie varchar(255) NOT NULL, Nazwisko varchar(255), Haslo varchar(255) NOT NULL, Login varchar(255) NOT NULL);";
    private static String UTWORZ_TABLICE_BUDZETY = "CREATE TABLE Budzety (ID_B INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_U integer(10) NOT NULL, Saldo real(10) NOT NULL, FOREIGN KEY(ID_U) REFERENCES Uzytkownicy(ID_U));";
    private static String UTWORZ_TABLICE_WYDATKI = "CREATE TABLE Wydatki (ID_W INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_P integer(10) NOT NULL, Nazwa varchar(255) NOT NULL, Data text, Kwota real(10) NOT NULL, Kategoria varchar(255) NOT NULL, FOREIGN KEY(ID_P) REFERENCES Podbudzety(ID_P));";
    private static String UTWORZ_TABLICE_NOTATKI = "CREATE TABLE Notatki (ID_N INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_U integer(10) NOT NULL, Tytul varchar(255) NOT NULL, Tresc varchar(255) NOT NULL, FOREIGN KEY(ID_U) REFERENCES Uzytkownicy(ID_U));";
    private static String UTWORZ_TABLICE_CELE = "CREATE TABLE Cele (ID_C INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_U integer(10) NOT NULL, Cel varchar(255) NOT NULL, Data_osiagniecia time, FOREIGN KEY(ID_U) REFERENCES Uzytkownicy(ID_U));";
    private static String UTWORZ_TABLICE_PODBUDZETY = "CREATE TABLE Podbudzety (ID_P INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Nazwa varchar(255) NOT NULL, Saldo real(10) NOT NULL);";
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(UTWORZ_TABLICE_UZYTKOWNICY);
            db.execSQL(UTWORZ_TABLICE_BUDZETY);
            db.execSQL(UTWORZ_TABLICE_CELE);
            db.execSQL(UTWORZ_TABLICE_NOTATKI);
            db.execSQL(UTWORZ_TABLICE_PODBUDZETY);
            db.execSQL(UTWORZ_TABLICE_ZWIAZKI);
            db.execSQL(UTWORZ_TABLICE_WYDATKI);
            db.execSQL(UTWORZ_TABLICE_INWESTYCJE);
            db.execSQL(UTWORZ_TABLICE_PRZYCHODY_O);
            db.execSQL(UTWORZ_TABLICE_PRZYCHODY_P);

            Log.d("Baza", "Database creating...");
            Log.d("Baza", "Table " + BD_NAZWA + " ver." + BD_WERSJA + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public BazaDanych(Context context) {
            this.context = context;
        }

        public enum Kategoria{
            TRANSPORT,ZYWNOSC
        }


        public BazaDanych open(){
            dbHelper = new DatabaseHelper(context, BD_NAZWA, null, BD_WERSJA);
            try {
                db = dbHelper.getWritableDatabase();
            } catch (SQLException e) {
                db = dbHelper.getReadableDatabase();
            }
            return this;
        }

        public void close() {
            dbHelper.close();
        }

        public long dodajWydatek(int id_p, String nazwa, double cena, String kategoria, String data) {
            ContentValues newValues = new ContentValues();
            newValues.put("ID_P", id_p);
            newValues.put("Nazwa", nazwa);
            newValues.put("Data", data);
            newValues.put("Kwota", cena);
            newValues.put("Kategoria", kategoria);
            return db.insert("Wydatki", null, newValues);
        }

        public boolean edytujWydatek(int id, int id_p, String nazwa, double cena, String kategoria, String data) {
            String where = "ID_W" + "=" + id;
            ContentValues updateValues = new ContentValues();
            updateValues.put("ID_P", id_p);
            updateValues.put("Nazwa", nazwa);
            updateValues.put("Data", data);
            updateValues.put("Kwota", cena);
            updateValues.put("Kategoria", kategoria);
            return db.update("Wydatki", updateValues, where, null) > 0;
        }

        public boolean przeniesWydatki(int nowe_id_p, int stare_id_p){
            String where = "ID_P" + "=" + stare_id_p;
            ContentValues updateValues = new ContentValues();
            updateValues.put("ID_P", nowe_id_p);
            return db.update("Wydatki", updateValues, where, null) > 0;
        }

        public boolean usunWydatek(String zapytanie) {
            return db.delete("Wydatki", zapytanie, null) > 0;
        }

        public Cursor dajWszystkieWydatki(String zapytanie) {
            String[] columns = {"ID_W", "ID_P", "Nazwa", "Data", "Kwota", "Kategoria"};
            return db.query("Wydatki", columns, zapytanie, null, null, null, null);
        }

        public Cursor dajWszystkiePodbudzety(String zapytanie) {
            String[] columns = {"ID_P", "Nazwa", "Saldo"};
            return db.query("Podbudzety", columns, zapytanie, null, null, null, null);
        }

        public Cursor dajWszystkiePodbudzety(String zapytanie, int i) {
            String[] columns = {"ID_P", "Nazwa", "Saldo"};
            return db.query("Podbudzety", columns, zapytanie, null, null, null, "ID_P");
        }

        public long dodajPodbudzet(String nazwa) {
            ContentValues newValues = new ContentValues();
            newValues.put("Nazwa", nazwa);
            newValues.put("Saldo", 0);
            return db.insert("Podbudzety", null, newValues);
        }

        public boolean uaktualnijPodbudzet(int id_p) {
            String where = "ID_P" + "=" + id_p;
            ContentValues updateValues = new ContentValues();
            Cursor cursor = dajSumeWydatkow(id_p);
            cursor.moveToFirst();
            updateValues.put("Saldo", cursor.getDouble(0));
            return db.update("Podbudzety", updateValues, where, null) > 0;
        }

        public boolean usunPodbudzet(int id) {
            String where = "ID_P" + "=" + id;
            usunZwiazek(where);
            return db.delete("Podbudzety", where, null) > 0;
        }

        public long dodajBudzet(int id_u) {
            ContentValues newValues = new ContentValues();
            newValues.put("ID_U", id_u);
            newValues.put("Saldo", 0);
            return db.insert("Budzety", null, newValues);
        }

        public boolean uaktualnijBudzet(int id_b) {
            String where = "ID_B" + "=" + id_b;
            ContentValues updateValues = new ContentValues();
            Cursor cursor =  dajSumePodbuzetow(id_b);
            cursor.moveToFirst();
            updateValues.put("Saldo", cursor.getDouble(0));
            return db.update("Budzety", updateValues, where, null) > 0;
        }

        public long dodajZwiazek(int id_p, int id_b) {
            ContentValues newValues = new ContentValues();
            newValues.put("ID_P", id_p);
            newValues.put("ID_B", id_b);
            return db.insert("Zwiazki", null, newValues);
        }

        public long dodajUzytkownia(String imie, String nazwisko, String login, String haslo) {
            ContentValues newValues = new ContentValues();
            newValues.put("Imie", imie);
            newValues.put("Nazwisko", nazwisko);
            newValues.put("Login", login);
            newValues.put("Haslo", haslo);
            return db.insert("Uzytkownicy", null, newValues);
        }

        public boolean usunZwiazek(String zapytanie) {
            return db.delete("Zwiazki", zapytanie, null) > 0;
        }

        public Cursor dajWszystkichUzytkownikow(String zapytanie) {
            String[] columns = {"ID_U", "Imie", "Nazwisko"};
            return db.query("Uzytkownicy", columns, zapytanie, null, null, null, null);
        }

        public Cursor dajWszystkieZwiazki(String zapytanie) {
            String [] columns = {"ID_P", "ID_B"};
            return db.query("Zwiazki", columns, zapytanie, null, null, null, null);
        }

        public Cursor dajWszystkieBudzety(String zapytanie) {
            String[] columns = {"ID_B", "ID_U", "Saldo"};
            return db.query("Budzety", columns, zapytanie, null, null, null, null);
        }

        Cursor dajSumeWydatkow(int id_p) {
            String [] columns = {"sum(Kwota)"};
            String where = "ID_P =" + id_p;
            return db.query("Wydatki", columns, where, null, null, null, null);
        }

        Cursor dajSumePodbuzetow(int id_b) {
            return db.rawQuery("SELECT SUM(Saldo) FROM Podbudzety WHERE ID_P IN " +
                    "(SELECT ID_P FROM Zwiazki WHERE ID_B =" + id_b + ");", null);
        }

        public Cursor dajIdBudzetowDlaPodbudzetu(int id_p) {
            String [] columns = {"ID_B"};
            String where = "ID_P =" + id_p;
            return db.query("Zwiazki", columns, where, null, null, null, null);
        }
}
