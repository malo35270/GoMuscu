package fr.centralesupelec.ianotto.GoMuscu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class ReprendreSeance extends BaseActivity {
    private JSONHandler handler = new JSONHandler();
    int nb;
    JSONArray arr;
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
