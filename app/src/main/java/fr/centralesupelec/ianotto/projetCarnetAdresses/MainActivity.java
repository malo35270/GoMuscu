package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.projetCarnetAdresses.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button boutonAjouterContact;
    private Button boutonAfficherContact;
    private Button boutonSupprimerContact;
    private Button boutonModifierContact;
    public static ContactOperations contactOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On crée un objet de type ContactOperations
        contactOperations = new ContactOperations(getApplicationContext());

        // On récupère une référence sur les 4 boutons de l'activité
        // On récupère une référence sur le bouton "MainAct_Btn_Ajouter_Contact"
        boutonAjouterContact = findViewById(R.id.boutonAjouterMainActivity);
        boutonAjouterContact.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Afficher_Contact"
        boutonAfficherContact = findViewById(R.id.MainAct_Btn_Afficher_Contact);
        boutonAfficherContact.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Supprimer_Contact"
        boutonSupprimerContact = findViewById(R.id.boutonSupprimerMainActivity);
        boutonSupprimerContact.setOnClickListener(this);

        // On récupère une référence sur le bouton "MainAct_Btn_Modifier_Contact"
        boutonModifierContact = findViewById(R.id.boutonMidifierMainActivity);
        boutonModifierContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        if (v == boutonAjouterContact) {
            i.setClass(getApplicationContext(), AjouterContactActivity.class);
            startActivity(i);
        }
        if (v == boutonAfficherContact) {
            i.setClass(getApplicationContext(), AfficherContactsActivity.class);
            startActivity(i);
        }
        if (v == boutonSupprimerContact) {
            i.setClass(getApplicationContext(), SupprimerContactActivity.class);
            startActivity(i);
        }
        if (v == boutonModifierContact) {
            i.setClass(getApplicationContext(), ModifierContactActivity.class);
            startActivity(i);
        }
    }
}