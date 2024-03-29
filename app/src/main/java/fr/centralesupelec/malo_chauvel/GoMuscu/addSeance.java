package fr.centralesupelec.malo_chauvel.GoMuscu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;

public class addSeance extends BaseActivity {


    private Button ajouterSeance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_json_seance);

        EditText simpleEditText = (EditText) findViewById(R.id.edit_text);



        ajouterSeance = findViewById(R.id.boutonAddJson);
        ajouterSeance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if (v == ajouterSeance) {
                    String editTextValue = simpleEditText.getText().toString();
                    Log.i("edittext",editTextValue);
                    editTextValue = Normalizer.normalize(editTextValue, Normalizer.Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "")
                            .toLowerCase();
                    testEcritureJSON(editTextValue);
                    i.setClass(getApplicationContext(), NewMesocycle.class);
                    startActivity(i);
                }
            }
        });
        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void testEcritureJSON (String text) {
        // Convert JsonObject to String Format
        String fileName = "jsonfileNew.json";
        JSONObject jsonMeso;
        try {
            jsonMeso = new JSONObject(testLectureJSON());
            jsonMeso.put("nb_de_seance",jsonMeso.getInt("nb_de_seance")+1);
            JSONArray jsonArray = new JSONArray();
            jsonMeso.put("seance"+jsonMeso.getInt("nb_de_seance"),jsonArray);
            JSONObject seance = new JSONObject();
            seance.put("nom",text);
            jsonArray.put(seance);
            Log.i("json_test-fin-add", String.valueOf(jsonMeso));
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

    public String testLectureJSON() {
        String fileName = "jsonfileNew.json";

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
