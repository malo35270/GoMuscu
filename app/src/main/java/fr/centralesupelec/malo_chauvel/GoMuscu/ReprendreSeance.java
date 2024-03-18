package fr.centralesupelec.malo_chauvel.GoMuscu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class ReprendreSeance extends BaseActivity {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);

        dbHelper = new DatabaseHelper(getApplicationContext());
        database = dbHelper.getWritableDatabase();
        dbHelper.open();
        int derniere_Seance = dbHelper.getLastSeance();
        // Fermer la base de données après utilisation
        dbHelper.close();

        Intent i = new Intent();

        //start activity and passes the name of the activity as an id
        i.setClass(getApplicationContext(), Seance.class);
        i.putExtra("derniere_seance", derniere_Seance);

        startActivity(i);
    }
}
