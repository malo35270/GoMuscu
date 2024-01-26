package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Context;

import java.util.Vector;

public class ContactOperations {
    private Vector<Contact> lContacts;
    private Context context;

    /*
     * Le constructeur
     */
    public ContactOperations(Context context) {
        this.context = context;
        if (GestionFichier.existeFichierContact(context) == false)
            lContacts = new Vector<Contact>();
        else {
            lContacts = GestionFichier.lireCarnetAdresses(context);
        }
    }


    /*
     * Ajout d'un contact dans le vecteur de contacts. La méthode renvoie
     * l’id du contact. Le nouveau vecteur est enregistré dans le fichier.
     */
    public long ajouterContact(Contact c) {
       // A COMPLETER
        return 0;
    }

    /*
     * On récupère le contenu de la variable lContacts.
     */
    public Vector<Contact> listerTousLesContacts() {
        // A COMPLETER
        return null;
    }

    /*
     * Suppression d'un contact dans le vecteur de contacts.
     * Le nouveau vecteur est enregistré dans le fichier.
     */
    public void supprimerContact(Contact c) {
        // A COMPLETER
    }

    /*
     * Modificiation d'un contact dans le vecteur de contacts.
     * Le nouveau vecteur est enregistré dans le fichier.
     */
    public void modifierContact(Contact c) {
        // A COMPLETER
    }
}
