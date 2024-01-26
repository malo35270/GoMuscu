package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class addExo extends AppCompatActivity {


    private Button ajouterExo;

    public Button getAjouterSeance() {
        return ajouterExo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_json_exo);

        // On récupère une référence sur le bouton Effacer
        EditText simpleEditText = (EditText) findViewById(R.id.edit_text);
        String editTextValue = simpleEditText.getText().toString();
        testEcritureJSON(editTextValue);

        ajouterExo = findViewById(R.id.boutonAddJson);
        ajouterExo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if (v == ajouterExo) {
                    String editTextValue = simpleEditText.getText().toString();
                    Log.i("edittext",editTextValue);
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
            JSONArray seance = jsonMeso.getJSONArray("seance"+jsonMeso.getInt("nb_de_seance"));
            JSONObject exo = new JSONObject();
            exo.put("exo"+jsonMeso.getInt("nb_de_seance")+"."+seance.length(),text);
            seance.put(exo);
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
        // /data/data/fr.centralesupelec.ianotto.GoMuscu/files
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

    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}