package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.projetCarnetAdresses.R;
public class AjouterContactActivity extends AppCompatActivity  {
    private Button boutonValider;
    private Button boutonEffacer;
    private EditText editTextNom;
    private EditText editTextTelephone;
    private ContactOperations contactOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_contact);

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        // A COMPLETER

        // On récupère une référence sur le bouton Valider
        boutonValider = findViewById(R.id.buttonValiderAjouterContact);
        // On récupère une référence sur le bouton Effacer
        boutonEffacer = findViewById(R.id.buttonEffacerAjouterContact);

        // On récupère une référence sur les champs d'édition de l'activité : nom et numéro
        // de téléphone et on place un filtre de 10 caractères sur le champs de saisi du
        //  numéro de téléphone
        editTextNom = findViewById(R.id.editTextNomAjouterContact);
        editTextTelephone = findViewById(R.id.editTextTelephoneAjouterContact);
        editTextTelephone.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // On gère l'évènement "click" sur le bouton "Valider"
        // Lorsqu'on clique sur le bouton "Valider", un contact
        // est ajouté  au vecteur puis le vecteur est enregistré dans le fichier :
        //   - on crée un objet contact avec le contenu des composants editTextNom et editTextTelephone
        //   - on appelle la méthode ajouterContact définie dans la classe ContactOperation
        //   - on appelle la methode finish pour revenir à l'activité principale
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
