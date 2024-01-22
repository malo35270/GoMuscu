package fr.centralesupelec.ianotto.projetCarnetAdresses;

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



public class CurrentMesocycle extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentmeso);

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
            JSONObject obj = new JSONObject(testLectureJSON(MainActivity.num_meso));
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

    public void testEcritureJSON ( int numb_mes_act ) {
        // Convert JsonObject to String Format
        String fileName = "jsonfileCurrent.json";
        JSONObject jsonMeso = new JSONObject();
        try {
            jsonMeso.put("id", numb_mes_act);
            jsonMeso.put("nb_de_seance",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String userString = jsonMeso.toString();
        // Define the File Path and its Name
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String testLectureJSON(int num_meso) {
        // /data/data/fr.centralesupelec.ianotto.projetCarnetAdresses/files
        String fileName = "jsonfileCurrent.json";
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
            throw new RuntimeException(e);
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