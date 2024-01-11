package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.projetCarnetAdresses.R;
public class EditerContactActivity extends AppCompatActivity {
    private EditText editTextNom;
    private EditText editTextTelephone;
    private Button buttonEffacer;
    private Button buttonValider;
    private ContactOperations contactOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_contact);

        // On récupère l'objet de type Contact qui a été sérialisé
        // depuis l'activité ModifierContactActivity
        Intent i = getIntent();
        Contact c = (Contact) i.getSerializableExtra("contact");

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        contactOperations = MainActivity.contactOperations;

        // On récupère une référence sur les champs d'édition de l'activité : nom et numéro
        // de téléphone et on place un filtre de 10 caractères sur le champs de saisi du
        // numéro de téléphone
        editTextNom = findViewById(R.id.editTextNomEditerContact);
        editTextTelephone = findViewById(R.id.editTextTelephoneEditerContact);
        editTextTelephone.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        // On affiche le nom et le numéro de téléphone dans les champs d'édition
        // A COMPLETER

        // On récupère une référence sur le bouton Valider
        buttonValider = findViewById(R.id.buttonValiderEditerContact);
        // On récupère une référence sur le bouton Effacer
        buttonEffacer = findViewById(R.id.buttonEffacerEditerContact);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // On gère l'évènement "click" sur le bouton "Valider"
        // Lorsqu'on clique sur le bouton "Valider", l'enregistrement
        // est modifié  dans le vecteur de contact puis le vecteur
        // est enregistré dans le fichier :
        //   - on modifie les attributs de l'objet c (de type contact) avec le
        //     contenu des composants editTextNom et editTextTelephone
        //   - on appelle la méthode modifierContact définie dans la classe ContactOperation
        //   - on appelle la methode finish pour revenir à l'activité principale
        //   - on affiche à nouveau l'activité ModifierContactActivity
        // A COMPLETER


        // On gère l'évènement "click" sur le bouton "Effacer"
        // Lorsqu'on clique sur le bouton "Effacer", le contenu des champs
        // Nom et Telephone est effacé :
        //    - on écrit la chaîne de caractères vide dans les champs editTextNom et editTextTelephone
        // A COMPLETER

    }


    // Méthode qui permet permet de revenir à l'activité précédente
    // lorsqu'on clique sur la flèche qui se trouve dans la barre de titre
    // de l'activité
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}