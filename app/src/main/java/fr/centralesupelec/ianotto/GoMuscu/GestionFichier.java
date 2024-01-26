package fr.centralesupelec.ianotto.projetCarnetAdresses;

import android.content.Context;

import java.io.File;
import java.util.Vector;

public class GestionFichier {
    private static String filename = "CarnetAdressesFile.dat";

    public static boolean existeFichierContact(Context context) {
        File myInternalFile;

        myInternalFile = new File(context.getFilesDir(), filename);
        if (myInternalFile.exists() == true)
            return true;
        else
            return false;
    }

    /**
     * On enregistre les contacts contenus dans le vecteur carnetAdresses dans le fichier
     * "CarnetAdressesFile.dat"
     * @param context
     * @param carnetAdresses
     */
    public static void enregistrerCarnetAdresses(Context context, Vector<Contact> carnetAdresses) {
        File myInternalFile;
        // On crée un objet de type File qui contient le chemin vers le
        // le nom du fichier : /data/user/0/com.example.projetfichierbinairememoireint2/files/CarnetAdressesFile.dat
        myInternalFile = new File(context.getFilesDir(), filename);

        // A COMPLETER
        // On crée un flux d'écriture sur le fichier

        // On enregistre le compteur de contact dans le fichier

        // On enregistre le vecteur de contact dans le fichier

        // On ferme le flux de sortie sur le fichier

    }

    /**
     * On lit le contenu du fichier "CarnetAdressesFile.dat" et on renvoie le vecteur de contacts
     * @param context
     * @return
     */
    public static Vector<Contact> lireCarnetAdresses(Context context) {
        File myInternalFile;
        Vector<Contact> lContact = null;

        // On crée un objet de type File qui contient le chemin vers le
        // le nom du fichier : /data/user/0/com.example.projetfichierbinairememoireint2/files/SampleFile.dat
        myInternalFile = new File(context.getFilesDir(), filename);

        // A COMPLETER
        // On crée un flux de lecture sur le fichier

        // On lit le compteur de contact

        // On lit le contenu du fichier

        // On ferme le flux de sortie sur le fichier


        return lContact;
    }
}
