package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NouvelleSeance extends AppCompatActivity  {
    private Button boutonSeanceA;
    private Button boutonSeanceB;
    private Button boutonSeanceC;
    private ContactOperations contactOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        // A COMPLETER

        // On récupère une référence sur le bouton Effacer
        boutonSeanceA = findViewById(R.id.boutonSeanceA);
        boutonSeanceA.setText("toto1");

        boutonSeanceB = findViewById(R.id.boutonSeanceB);
        boutonSeanceB.setText("toto2");

        boutonSeanceC = findViewById(R.id.boutonSeanceC);
        boutonSeanceC.setText("toto3");
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
