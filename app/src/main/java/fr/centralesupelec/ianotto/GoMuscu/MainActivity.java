package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.GoMuscu.R;

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

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonNouvelleSeance) {
            i.setClass(getApplicationContext(), NouvelleSeance.class);
            startActivity(i);
        }
        if (v == boutonReprendreSeance) {
            i.setClass(getApplicationContext(), AfficherContactsActivity.class);
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