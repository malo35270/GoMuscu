package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.projetCarnetAdresses.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button boutonNouvelleSeance;
    private Button boutonReprendreSeance;
    private Button boutonGestionnaireMesocycle;
    private Button boutonParametreCompte;
    public static ContactOperations contactOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On crée un objet de type ContactOperations
        contactOperations = new ContactOperations(getApplicationContext());

        // On récupère une référence sur les 4 boutons de l'activité
        // On récupère une référence sur le bouton "MainAct_Btn_Ajouter_Contact"
        boutonNouvelleSeance = findViewById(R.id.boutonDemarrerUneNouvelleSeance);
        boutonNouvelleSeance.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Afficher_Contact"
        boutonReprendreSeance = findViewById(R.id.MainAct_Btn_ReprendreSeance);
        boutonReprendreSeance.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Supprimer_Contact"
        boutonGestionnaireMesocycle = findViewById(R.id.boutonGestionnaireMesocycle);
        boutonGestionnaireMesocycle.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Modifier_Contact"
        boutonParametreCompte = findViewById(R.id.boutonParametreCompte);
        boutonParametreCompte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonNouvelleSeance) {
            i.setClass(getApplicationContext(), NouvelleSeance.class);
            startActivity(i);
        }
        if (v == boutonReprendreSeance) {
            i.setClass(getApplicationContext(), AfficherContactsActivity.class);
            startActivity(i);
        }
        if (v == boutonGestionnaireMesocycle) {
            i.setClass(getApplicationContext(), SupprimerContactActivity.class);
            startActivity(i);
        }
        if (v == boutonParametreCompte) {
            i.setClass(getApplicationContext(), ModifierContactActivity.class);
            startActivity(i);
        }
    }
}