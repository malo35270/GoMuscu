package fr.centralesupelec.ianotto.GoMuscu;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import fr.centralesupelec.ianotto.GoMuscu.R;

public class AfficherContactsActivity extends AppCompatActivity {
    private ContactOperations contactOperations;
    private ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_contacts);

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        // A COMPLETER


        // On récupère une référence sur le ListView de l'activité
        // A COMPLETER

        // On récupère la liste des contacts contenus dans le fichier en appelant
        // la méthode listerTousLesContacts de la classe ContactOperations
        // A COMPLETER


        // On crée un objet de type ArrayAdapater<Contact> qui est initialisé avec la
        // liste de contacts lContacts
        // A COMPLETER

        // On fait le lien entre le ListView et l'objet de type ArrayAdapter
        // A COMPLETER

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
