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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentmeso);

        dbHelper = new DatabaseHelper(getApplicationContext());
        database = dbHelper.getWritableDatabase();

        dbHelper.open();

        // Récupérer le volume à partir de la base de données
        Cursor cursor = dbHelper.getVolume();

        // Vérifier si le curseur contient des données
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Récupérer les données de chaque colonne
                int volume = cursor.getInt(cursor.getColumnIndex("somme_produit"));
                int numeroCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));

                // Afficher ou utiliser les données comme vous le souhaitez
                Log.d("Donnees", "Volume: " + volume + ", NumeroCycle: " + numeroCycle);

                // Vous pouvez également mettre à jour votre interface utilisateur ici
            } while (cursor.moveToNext());
        }

        // Fermer la base de données après utilisation
        dbHelper.close();


        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayout monLayout = findViewById(R.id.layoutCurrentMeso);

        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(testLectureJSON(file));
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
                    //nomSeance.setText(oneObject.getString("nom"));
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

    public void writeJsonToInternalStorage(Context context, String name, String jsonContent) {
        try {
            // Get the internal storage directory
            File internalStorageDir = context.getFilesDir();

            // Create a File object representing the destination file in internal storage
            File file = new File(internalStorageDir, name);

            // Write the JSON content to the file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(jsonContent);
            bufferedWriter.close();

            Log.i("json_write", "JSON file written to internal storage: " + file.getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String testLectureJSON_ASSETS(Context context) {
        // Specify the file name in the assets folder
        String fileName = "jsonfileCurrent.json";
        AssetManager assetManager = context.getAssets();

        // Initialize variables
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            // Open the InputStream for the file
            InputStream inputStream = assetManager.open(fileName);

            // Read the InputStream into a String
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // The response will have JSON formatted string
        return stringBuilder.toString();
    }

    public String testLectureJSON(String fileName) {
        // /data/data/fr.centralesupelec.ianotto.GoMuscu/files
        File file = new File(getFilesDir(), fileName);
        String line = null;
        StringBuilder stringBuilder = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();
            line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            writeJsonToInternalStorage(getApplicationContext(),fileName,testLectureJSON_ASSETS(getApplicationContext()));
            testLectureJSON(fileName);
        }
        // This responce will have Json Format String
        return stringBuilder.toString();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}