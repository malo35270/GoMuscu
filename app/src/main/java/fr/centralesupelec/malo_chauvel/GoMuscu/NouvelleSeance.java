package fr.centralesupelec.malo_chauvel.GoMuscu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class NouvelleSeance extends BaseActivity {
    private LinearLayout linear;
    //for JSON reading :
    private JSONHandler handler = new JSONHandler();
    int nb;

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);

        linear = findViewById(R.id.linear);

        try {
            String file = "jsonfileCurrent.json";
            JSONObject obj = new JSONObject(handler.LectureJSON(this, file));
            Log.i("json", String.valueOf(obj.getInt("nb_de_seance")));
            nb = obj.getInt("nb_de_seance");
            for (int i = 1; i < nb + 1; i++) {
                //params
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(60,20,60,20);

                Button myButton = new Button(this);
                myButton.setLayoutParams(params);
                myButton.setText(handler.getJsonSeance(obj, i));
                myButton.setBackground(myButton.getContext().getResources().getDrawable(R.drawable.button));
                myButton.setTextColor(myButton.getContext().getResources().getColor(R.color.black));
                myButton.setId(i);

                final int id_ = myButton.getId();
                linear.addView(myButton);

                myButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent();
                        Button transform = (Button) v;

                        //start activity and passes the name of the activity as an id
                        i.setClass(getApplicationContext(), Seance.class);
                        i.putExtra("seance", v.getId());
                        i.putExtra("numberForTheExercice", -1);

                        startActivity(i);

                    }
                });
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choisir la Séance");
        // On gère l'évènement "click" sur les boutons
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
