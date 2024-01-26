package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        boutonSeanceA.setText("toto1");

        boutonSeanceB = findViewById(R.id.boutonSeanceB);
        boutonSeanceB.setText("toto2");

        boutonSeanceC = findViewById(R.id.boutonSeanceC);
        boutonSeanceC.setText("toto3");
        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        if (v == boutonSeanceA) {
            i.setClass(getApplicationContext(), Seance.class);
            i.putExtra("seance", "toto1");
            startActivity(i);
        }
        if (v == boutonSeanceB) {
            i.setClass(getApplicationContext(), Seance.class);
            i.putExtra("seance", "toto2");
            startActivity(i);
        }
        if (v == boutonSeanceC) {
            i.setClass(getApplicationContext(), Seance.class);
            i.putExtra("seance", "toto3");
            startActivity(i);
        }
    }
}
