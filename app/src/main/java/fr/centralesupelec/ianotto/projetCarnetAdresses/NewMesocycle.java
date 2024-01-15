package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;


public class NewMesocycle extends AppCompatActivity implements View.OnClickListener{

    private Button boutonNewSeance;
    private Button boutonNewExo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmeso);

        boutonNewSeance = findViewById(R.id.buttonAddSeance);
        boutonNewSeance.setOnClickListener(this);

        boutonNewExo = findViewById(R.id.buttonAddExo);
        boutonNewExo.setOnClickListener(this);

        LinearLayout monLayout = findViewById(R.id.layoutNewMeso);

        //int nbSeance = new ;

        for (int i = 0; i < 3; i++){
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
            nomSeance.setText("Seance"+i);
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
            for (int j=0;j<4;j++){
                TextView nomExo = new TextView(getApplicationContext());
                nomExo.setText("Exo"+j);
                nomExo.setLayoutParams(textParam);
                ExoLayout.addView(nomExo);
            }


        }

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

    }


}