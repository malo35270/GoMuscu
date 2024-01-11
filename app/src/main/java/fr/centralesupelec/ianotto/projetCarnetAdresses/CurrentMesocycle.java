package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentMesocycle extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentmeso);

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        // A COMPLETER

        // On récupère une référence sur le ListView de l'activité
        boutonCurrentMeso = findViewById(R.id.boutonCurrentMeso);
        // On récupère une référence sur le bouton Valider
        boutonNewMeso = findViewById(R.id.boutonNewMeso);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // On affiche le contenu dans le fichier  dans le ListView
        getListContact();

        // On gère l'évènement "sélection d'un item" dans le ListView :
        //  - la méthode setOnItemClickListener permet de gérer l'évènement
        //    "click sur un item"
        //  - Dans la méthode onItemClick, on récupére l'objet contact correspondant à l'item sur
        //    lequel l'utilisateur a cliqué. On utilise pour cela la méthode "getItemAtPosition"
        // A COMPLETER

        // On gère l'évènement "click" sur le bouton supprimer :
        //  - on appelle la méthode supprimerContact définie dans la
        //    classe ContactOperation
        //  - on réaffiche la nouvelle liste de contacts
        // A COMPLETER

    }

    /*
     * Permet d'afficher dans le ListView les contacts contenus dans la base de données.
     * On utilise un listView du type "simple_list_item_checked"
     */
    public void getListContact() {
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