package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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

        // On affiche le contenu dans le fichier  dans le ListView


        // On gère l'évènement "sélection d'un item" dans le ListView :
        //  - la méthode setOnItemClickListener permet de gérer l'évènement
        //    "click sur un item"
        //  - Dans la méthode onItemClick, on récupére l'objet contact correspondant à l'item sur
        //    lequel l'utilisateur a cliqué. On utilise pour cela la méthode "getItemAtPosition"
        // A COMPLETER

        // On gère l'évènement "click" sur le bouton supprimer :
        //  - on appelle la méthode supprimerContact définie dans la
        //    classe ContactOperation
        //  - on réaffiche la nouvelle liste de contacts
        // A COMPLETER
        testLectureJSON();

        testEcritureJSON();
    }

    public void testEcritureJSON() {
        // Convert JsonObject to String Format
        String fileName = "jsonformatter1.json";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", "CHAUVEL");
            jsonObject.put("Enroll_No", "1234");
            jsonObject.put("Mobile", "0455443344");
            jsonObject.put("Address", "Metz");
            jsonObject.put("Branch", "Computer Science");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String userString = jsonObject.toString();
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
    public void testLectureJSON() {
        // /data/data/fr.centralesupelec.ianotto.projetCarnetAdresses/files
        String fileName = "jsonformatter.json";
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
        String responce = stringBuilder.toString();
    }

    /*
     * Permet d'afficher dans le ListView les contacts contenus dans la base de données.
     * On utilise un listView du type "simple_list_item_checked"
     */


    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}