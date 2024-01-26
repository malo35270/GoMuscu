package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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


public class NewMesocycle extends AppCompatActivity implements View.OnClickListener{
    private Button boutonNewSeance;
    private Button boutonNewExo;
    private Button bouttonEffectif;

    public int Nombre_de_seance_new_meso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmeso);

        boutonNewSeance = findViewById(R.id.buttonAddSeance);
        boutonNewSeance.setOnClickListener(this);

        boutonNewExo = findViewById(R.id.buttonAddExo);
        boutonNewExo.setOnClickListener(this);

        bouttonEffectif = findViewById(R.id.bouttonEffectif);
        bouttonEffectif.setOnClickListener(this);

        String fileName = "jsonfileNew.json";
        testEcritureJSON(fileName);


        LinearLayout monLayout = findViewById(R.id.layoutNewMeso);

        try {
            JSONObject obj = new JSONObject(testLectureJSON(fileName));
            Log.i("json_test", String.valueOf(obj.getInt("nb_de_seance")));
                        //TextView nomSeance = new TextView(getApplicationContext());
            for (int i = 0; i < obj.getInt("nb_de_seance"); i++){
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
                JSONArray jArray = obj.getJSONArray("seance"+(i+1));
                JSONObject nomSeanceJSON = jArray.getJSONObject(0);
                nomSeance.setText(nomSeanceJSON.getString("nom"));
                //nomSeance.setText("seance");
                Log.i("nom",nomSeanceJSON.getString("nom"));
                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f);

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
                for (int j=1;j<jArray.length();j++) {
                    TextView nomExo = new TextView(getApplicationContext());
                    JSONObject oneObject = jArray.getJSONObject(j);
                    //nomSeance.setText(oneObject.getString("nom"));
                    Log.i("json1", String.valueOf(oneObject));
                    nomExo.setText(oneObject.getString("exo" + (i + 1) + "." + j));
                    //nomExo.setText("exo");
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


    public void testEcritureJSON (String fileName) {

        try {
            JSONObject obj = new JSONObject(testLectureJSON(fileName));
            Log.i("json_test-long", String.valueOf(obj.length()));
            if (obj.length() == 0 ) {
                JSONObject jsonMeso = new JSONObject();
                jsonMeso.put("nb_de_seance", 0);
                Log.i("json_test-fin-write", String.valueOf(jsonMeso));

                String userString = jsonMeso.toString();
                File file = new File(getApplicationContext().getFilesDir(), fileName);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(userString);
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (JSONException ex) {
            String jsonContent = "{}"; // Your JSON content here
            writeJsonToInternalStorage(getApplicationContext(),fileName, jsonContent);
            testEcritureJSON(fileName);
        }
    }




    public String testLectureJSON(String fileName) {
        // /data/data/fr.centralesupelec.ianotto.projetCarnetAdresses/files
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



    public void changeFile () {
        String currentFile = "jsonfileCurrent.json";
        String fileName = "jsonfileNew.json";
        String json = testLectureJSON(fileName);
        writeJsonToInternalStorage(getApplicationContext(),currentFile,json);


        String jsonContent = "{}"; // Your JSON content here
        writeJsonToInternalStorage(getApplicationContext(),fileName, jsonContent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonNewSeance) {
            i.setClass(getApplicationContext(), addSeance.class);
            startActivity(i);
        }
        if (v == boutonNewExo) {
            i.setClass(getApplicationContext(), addExo.class);
            startActivity(i);
        }
        if (v == bouttonEffectif) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Êtes-vous sûr de vouloir effectuer cette action ?");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                            changeFile();
                            i.setClass(getApplicationContext(), GestionMesocycle.class);
                            startActivity(i);
                            dialog.dismiss();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}