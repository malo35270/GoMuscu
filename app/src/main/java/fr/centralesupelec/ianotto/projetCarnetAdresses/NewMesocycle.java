package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class NewMesocycle extends AppCompatActivity implements View.OnClickListener{

    private Button boutonNewSeance;
    private Button boutonNewExo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmeso);

        TextView Seance1 = (TextView) findViewById(R.id.Seance1);
        Seance1.setText("Pecs/Biceps");

        TextView Exo1_1 = (TextView) findViewById(R.id.Exo1_1);
        Exo1_1.setText("Chess Press");

        TextView Seance2 = (TextView) findViewById(R.id.Seance2);
        Seance2.setText("Seance2");

        boutonNewSeance = findViewById(R.id.buttonAddSeance);
        boutonNewSeance.setOnClickListener(this);

        boutonNewExo = findViewById(R.id.buttonAddExo);
        boutonNewExo.setOnClickListener(this);
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