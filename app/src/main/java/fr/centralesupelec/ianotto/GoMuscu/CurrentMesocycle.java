package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CurrentMesocycle extends AppCompatActivity  {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    JSONHandler jsonHandler = new JSONHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentmeso);

        dbHelper = new DatabaseHelper(getApplicationContext());
        database = dbHelper.getWritableDatabase();

        dbHelper.open();

        // Récupérer le volume à partir de la base de données
        Cursor cursor = dbHelper.getVolume();
        Log.i("Cursor_Volume", String.valueOf(cursor));

        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();



        // Vérifier si le curseur contient des données
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Récupérer les données de chaque colonne
//                int volume = cursor.getInt(cursor.getColumnIndex("somme_produit"));
//                int numeroCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));
//                int numeroseance = cursor.getInt(cursor.getColumnIndex("NumSeance"));


                double volume = cursor.getInt(cursor.getColumnIndex("somme_produit"));
                int numeroCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));
                int numeroseance = cursor.getInt(cursor.getColumnIndex("NumSeance"));
                int NbReps = cursor.getInt(cursor.getColumnIndex("NbReps"));
                int NbSerie = cursor.getInt(cursor.getColumnIndex("NbSerie"));
                int NbPoids = cursor.getInt(cursor.getColumnIndex("NbPoids"));
                String nom = cursor.getString(cursor.getColumnIndex("nom"));
                Log.i("Donnees","Nom: " + nom + ", NumeroCycle: " + numeroCycle + ", NumeroSeance: "+numeroseance + ", Series: "+NbSerie +", NbReps: "+NbReps + ", NbPoids: "+NbPoids +", produit: "+volume);
                // Afficher ou utiliser les données comme vous le souhaitez
                //Log.d("Donnees", "Volume: " + volume + ", NumeroCycle: " + numeroCycle + ", NumeroSeance: "+numeroseance);
                if ( numeroseance == 1){
                    series1.appendData(new DataPoint(numeroCycle, volume), true, /* maxDataPoints */ 100);
                }
                if (numeroseance == 2 ){
                    series2.appendData(new DataPoint(numeroCycle, volume), true, /* maxDataPoints */ 100);
                }
                if (numeroseance == 3 ){
                    series3.appendData(new DataPoint(numeroCycle, volume), true, /* maxDataPoints */ 100);
                }


                // Vous pouvez également mettre à jour votre interface utilisateur ici
            } while (cursor.moveToNext());
        }

        // Fermer la base de données après utilisation
        dbHelper.close();


        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.addSeries(series3);


        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayout monLayout = findViewById(R.id.layoutCurrentMeso);

        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(jsonHandler.LectureJSON(this,file));
            Log.i("json", String.valueOf(obj.getInt("nb_de_seance")));
            for (int i = 1; i < obj.getInt("nb_de_seance")+1; i++){
                LinearLayout SeanceLayout = new LinearLayout(getApplicationContext());
                SeanceLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                SeanceLayout.setLayoutParams(param);
                monLayout.addView(SeanceLayout);

                TextView nomSeance = new TextView(getApplicationContext());
                JSONArray jArray = obj.getJSONArray("seance"+i);
                JSONObject nomSeanceJSON = jArray.getJSONObject(0);
                nomSeance.setText(nomSeanceJSON.getString("nom"));
                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                nomSeance.setLayoutParams(textParam);
                SeanceLayout.addView(nomSeance);

                LinearLayout ExoLayout = new LinearLayout(getApplicationContext());
                ExoLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams paramExo = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                ExoLayout.setLayoutParams(paramExo);
                SeanceLayout.addView(ExoLayout);
                for (int j=1;j<jArray.length();j++){
                    TextView nomExo = new TextView(getApplicationContext());
                    JSONObject oneObject = jArray.getJSONObject(j);
                    Log.i("json1", String.valueOf(oneObject));
                    nomExo.setText(oneObject.getString("exo"+i+"."+j));
                    nomExo.setLayoutParams(textParam);
                    ExoLayout.addView(nomExo);
                }

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}