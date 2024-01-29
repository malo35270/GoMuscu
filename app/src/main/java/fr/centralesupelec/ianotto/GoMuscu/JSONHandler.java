package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

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

public class JSONHandler {

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

    public String LectureJSON(Context context, String fileName) {
        // /data/data/fr.centralesupelec.ianotto.GoMuscu/files
        File file = new File(context.getFilesDir(), fileName);
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
            writeJsonToInternalStorage(context,fileName,testLectureJSON_ASSETS(context));
            LectureJSON(context,fileName);
        }
        // This responce will have Json Format String
        return stringBuilder.toString();
    }

    public String getJsonSeance(JSONObject object, int i, Context context) {
        JSONArray jArray = null;
        String name;
        try {
            jArray = object.getJSONArray("seance" + i);
            JSONObject nomSeanceJSON = jArray.getJSONObject(0);
            name = nomSeanceJSON.getString("nom");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public String getJsonExo(JSONArray jArray,int i, int j, Context context){
        JSONObject oneObject = null;
        String name;
        try {
            oneObject = jArray.getJSONObject(j);
            name=oneObject.getString("exo"+i+"."+j);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
}
