package fr.centralesupelec.ianotto.GoMuscu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class NouvelleSeance extends AppCompatActivity implements View.OnClickListener {
    private Button boutonSeanceA;
    private Button boutonSeanceB;
    private Button boutonSeanceC;
    private ContactOperations contactOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);

        // On récupère une référence sur le bouton Effacer
        boutonSeanceA = findViewById(R.id.boutonSeanceA);
        boutonSeanceA.setText("Pecs / Biceps");

        boutonSeanceB = findViewById(R.id.boutonSeanceB);
        boutonSeanceB.setText("Dos / Triceps");

        boutonSeanceC = findViewById(R.id.boutonSeanceC);
        boutonSeanceC.setText("Jambes / Épaules");
        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choisir la Séance");
        // On gère l'évènement "click" sur les boutons
        boutonSeanceA.setOnClickListener(this);
        boutonSeanceB.setOnClickListener(this);
        boutonSeanceC.setOnClickListener(this);

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
    public void onClick(View v) {
        Intent i = new Intent();
        Button transform = (Button)v;

        //start activity and passes the name of the activity as an id
        i.setClass(getApplicationContext(), Seance.class);
        i.putExtra("seance", transform.getText().toString());
        startActivity(i);
    }
}
