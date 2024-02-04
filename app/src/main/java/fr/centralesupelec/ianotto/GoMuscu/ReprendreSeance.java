package fr.centralesupelec.ianotto.GoMuscu;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class ReprendreSeance extends AppCompatActivity {
    private JSONHandler handler = new JSONHandler();
    int nb;
    JSONArray arr;

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);

        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(handler.LectureJSON(this, file));
            int seance = obj.getInt("nb_de_seance");

            Intent i = new Intent();

            //start activity and passes the name of the activity as an id
            i.setClass(getApplicationContext(), Seance.class);
            i.putExtra("seance", seance);
            startActivity(i);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
