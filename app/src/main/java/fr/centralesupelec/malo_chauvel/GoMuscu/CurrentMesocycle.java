package fr.centralesupelec.malo_chauvel.GoMuscu;
;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CurrentMesocycle extends BaseActivity  {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    JSONHandler jsonHandler = new JSONHandler();
    int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.GRAY, Color.CYAN};
    private LineGraphSeries<DataPoint>[] seriesArray;

    private int numSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentmeso);

        dbHelper = new DatabaseHelper(getApplicationContext());
        database = dbHelper.getWritableDatabase();

        dbHelper.open();




        LinearLayout monLayout = findViewById(R.id.layoutCurrentMeso);

        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(jsonHandler.LectureJSON(this,file));
            Log.i("json", String.valueOf(obj.getInt("nb_de_seance")));
            numSeries = obj.getInt("nb_de_seance");
            seriesArray = new LineGraphSeries[numSeries];
            for (int i = 1; i < numSeries+1; i++){
                seriesArray[i-1] = new LineGraphSeries<>();
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
                seriesArray[i-1].setColor(colors[(i-1) % colors.length]);
                seriesArray[i-1].setTitle(nomSeanceJSON.getString("nom"));
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


        Cursor cursor = dbHelper.getVolume();

        GraphView graph = (GraphView) findViewById(R.id.graph);

        // Vérifier si le curseur contient des données
        if (cursor != null && cursor.moveToFirst()) {
            do {

                double volume = cursor.getInt(cursor.getColumnIndex("somme_produit"));
                int numeroCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));
                int numeroseance = cursor.getInt(cursor.getColumnIndex("NumSeance"));
                int NbReps = cursor.getInt(cursor.getColumnIndex("NbReps"));
                int NbSerie = cursor.getInt(cursor.getColumnIndex("NbSerie"));
                int NbPoids = cursor.getInt(cursor.getColumnIndex("NbPoids"));
                String nom = cursor.getString(cursor.getColumnIndex("nom"));
                if (numeroseance >= 1 && numeroseance <= seriesArray.length) {
                    seriesArray[numeroseance - 1].appendData(new DataPoint(numeroCycle, volume), true, /* maxDataPoints */ 100);
                }
            } while (cursor.moveToNext());
        }

        // Fermer la base de données après utilisation
        dbHelper.close();

        graph.getLegendRenderer().setVisible(true);graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP); // or any other desired alignment

        for (int i = 0; i < numSeries; i++) {
            graph.addSeries(seriesArray[i]);
        }
        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}