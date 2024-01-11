package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class seance extends AppCompatActivity {
    private ContactOperations contactOperations;
    private ListView listViewContacts;
    private Vector<Contact> lContacts;

    private Contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_contact);

        // On récupère une référence sur l’objet contactOperations qui
        // a été créé dans MainActivity
        // A COMPLETER

        // On récupère une référence sur le ListView de l'activité
        listViewContacts = findViewById(R.id.listViewModifierContact);

        // Pour que la flèche s'affiche dans la barre de titre de l'activité
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // On affiche le contenu de la base de données dans le ListView
        getListContact();

        // On gère l'évènement "sélection d'un item" dans le ListView :
        //    - on récupère l'objet Contact sur lequel l'utilisateur a cliqué
        //    - On affiche l'activité EditerActivity avec les coordonnéés du
        //      contact
        //    - on ferme l'activité courante
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

    /*
     * Permet d'afficher dans le ListView les contacts contenus dans la base de données
     */
    public void getListContact() {
        // A COMPLETER

    }

}