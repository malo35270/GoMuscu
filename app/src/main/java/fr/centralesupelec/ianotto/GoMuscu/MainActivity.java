package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boutonNouvelleSeance;
    private Button boutonReprendreSeance;
    private Button boutonGestionnaireMesocycle;
    private Button boutonParametreCompte;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonNouvelleSeance = findViewById(R.id.boutonDemarrerUneNouvelleSeance);
        boutonNouvelleSeance.setOnClickListener(this);

        boutonReprendreSeance = findViewById(R.id.MainAct_Btn_ReprendreSeance);
        boutonReprendreSeance.setOnClickListener(this);

        boutonGestionnaireMesocycle = findViewById(R.id.boutonGestionnaireMesocycle);
        boutonGestionnaireMesocycle.setOnClickListener(this);

        boutonParametreCompte = findViewById(R.id.boutonParametreCompte);
        boutonParametreCompte.setOnClickListener(this);


        dbHelper = new DatabaseHelper(this);

        if (!dbHelper.baseDeDonneesExiste(this)) {
            // Si la base de données n'existe pas, getWritableDatabase() créera la base de données et appelle automatiquement onCreate().
            database = dbHelper.getWritableDatabase();
            Log.i("donnees", "Base de données créée avec succès");
        } else {
            // Si la base de données existe déjà, getWritableDatabase() l'ouvrira.
            database = dbHelper.getWritableDatabase();
            Log.i("donnees", "Base de données existante ouverte avec succès");
        }

        dbHelper.ajoutData("Chess Press",	4	,8,49,1,1);
        dbHelper.ajoutData("Ecarte haltère",	4,7,	41.6,1,1);
        dbHelper.ajoutData("Traction Supination",	4,7,	85,1,1);
        dbHelper.ajoutData("Curl Marteaux",	4,8,	12,1,1);

//        dbHelper.ajoutData("Chess Press",	4	,8,49,1,2);
//        dbHelper.ajoutData("Ecarte haltère",	4,6,12,1,2);
//        dbHelper.ajoutData("Traction Supination",	4,8,	85,1,2);
//        dbHelper.ajoutData("Curl Marteaux",	4,12,10,1,2);
//
//        dbHelper.ajoutData("Traction Pronation",	4	,7,85,2,1);
//        dbHelper.ajoutData("Rowing planche",	4,8,	16,2,1);
//        dbHelper.ajoutData("dips machine",	4,10,	64,2,1);
//        dbHelper.ajoutData("extension haltères",	4,8,15,2,1);
//
//        dbHelper.ajoutData("Traction Pronation",	4	,8,85,2,2);
//        dbHelper.ajoutData("Rowing planche",	4,8,	16,2,2);
//        dbHelper.ajoutData("dips machine",	4,10,	64,2,2);
//        dbHelper.ajoutData("extension haltères",	3,8,15,2,2);
//
//        dbHelper.ajoutData("Fentes bulgares",	4	,10,14,3,1);
//        dbHelper.ajoutData("Leg curl ischio",	4,10,	52,3,1);
//        dbHelper.ajoutData("calf press",	4,10,	93,3,1);
//        dbHelper.ajoutData("over head press",	4,6,35,3,1);
//
//        dbHelper.ajoutData("Fentes bulgares",	4	,10,18,3,2);
//        dbHelper.ajoutData("Leg curl ischio",	4,10,	55.4,3,2);
//        dbHelper.ajoutData("calf press",	4,10,	93,3,2);
//        dbHelper.ajoutData("over head press",	4,6,35,3,2);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonNouvelleSeance) {
            i.setClass(getApplicationContext(), NouvelleSeance.class);
            startActivity(i);
        }
        if (v == boutonReprendreSeance) {
            i.setClass(getApplicationContext(), ReprendreSeance.class);
            startActivity(i);
        }
        if (v == boutonGestionnaireMesocycle) {
            i.setClass(getApplicationContext(), GestionMesocycle.class);
            startActivity(i);
        }
        if (v == boutonParametreCompte) {
            i.setClass(getApplicationContext(), parametreCompte.class);
            startActivity(i);
        }
    }


}