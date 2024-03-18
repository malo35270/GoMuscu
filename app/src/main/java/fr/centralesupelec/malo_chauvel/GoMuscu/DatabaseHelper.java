package fr.centralesupelec.malo_chauvel.GoMuscu;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.text.Normalizer;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DonneesBrut";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la structure de la base de données (tables, colonnes, etc.)
        String createTableQuery = "CREATE TABLE MaTable (id INTEGER PRIMARY KEY, nom TEXT, NumSeance INTEGER, NumCycle INTEGER, NbSerie INTEGER, NbReps INTEGER, NbPoids FLOAT);";
        db.execSQL(createTableQuery);
        Log.i("Data", "database creat");
    }

    @SuppressLint("Range")
    public int getLastSeance() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id FROM MaTable ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int seanceId = -1; // Valeur par défaut en cas de problème
        if (cursor != null && cursor.moveToFirst()) {
            seanceId = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        return seanceId;
    }

    public Pair<Cursor, Integer> getSeancesById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM MaTable WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        int numSeance = -1;
        int numCycle = -1;
        if (cursor != null && cursor.moveToFirst()) {
            numSeance = cursor.getInt(cursor.getColumnIndex("NumSeance"));
            numCycle = cursor.getInt(cursor.getColumnIndex("NumCycle"));
            cursor.close();
        }
        if (numSeance != -1 && numCycle != -1) {
            query = "SELECT * FROM MaTable WHERE NumSeance = ? AND NumCycle = ?";
            selectionArgs = new String[]{String.valueOf(numSeance), String.valueOf(numCycle)};
            Cursor seancesCursor = db.rawQuery(query, selectionArgs);
            return new Pair<>(seancesCursor, numSeance);
        } else {
            // Retourner un curseur vide si les numéros de séance et de cycle ne sont pas valides
            return new Pair<>(null, -1);
        }
    }

    public void ajoutData(String name, int series, int reps, double poids, int numeroseance, int numerocycle) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
        values.put("nom", name); // Utiliser "nom" ici, correspondant à la colonne "nom" dans la table
        values.put("NbSerie", series);
        values.put("NbReps", reps);
        values.put("NbPoids", poids);
        values.put("NumSeance", numeroseance);
        values.put("NumCycle", numerocycle);
        long result = db.insertWithOnConflict("MaTable", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i("seance_malo_db","resultat ajouter :"+ String.valueOf(result));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettre à jour la structure de la base de données en cas de nouvelle version
        // Vous pouvez ajouter des instructions ALTER TABLE ici
    }

    public static boolean baseDeDonneesExiste(Context context) {
        return context.getDatabasePath(DATABASE_NAME).exists();
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public Cursor getVolume() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT *, SUM(product) AS somme_produit, COUNT(*) as nombre_occurrences\n" +
                "FROM (\n" +
                "    SELECT *, NbSerie * NbReps * NbPoids AS product\n" +
                "    FROM MaTable\n" +
                ") AS subquery\n" +
                "GROUP BY NumSeance, NumCycle\n" +
                "HAVING COUNT(*) > 1;", null);
    }
    public boolean isLastCycleNumberEqual(String name, int numCycle) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Normaliser le nom si nécessaire, comme dans votre fonction ajoutData
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
        // Définir la requête pour sélectionner le dernier enregistrement pour ce 'name'
        String query = "SELECT NumCycle FROM MaTable WHERE nom = ? ORDER BY NumCycle DESC LIMIT 1";
        // 'une_colonne_pour_ordre' pourrait être une colonne de timestamp ou un identifiant auto-incrémenté, selon votre schéma de base de données

        Cursor cursor = db.rawQuery(query, new String[]{name});

        boolean result = false; // Par défaut, le résultat est false
        if (cursor != null && cursor.moveToFirst()) {
            int lastCycleNum = cursor.getInt(0); // Récupérer la valeur de NumCycle
            if (lastCycleNum == numCycle) {
                result = true; // Si le dernier NumCycle correspond au numCycle fourni, définir le résultat sur true
            }
            cursor.close(); // Fermer le Cursor après utilisation
        }

        return result; // Retourner le résultat
    }

    public void modifierData(String name, int series, int reps, double poids, int numeroseance, int numerocycle) {
        SQLiteDatabase db = this.getWritableDatabase();
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();

        ContentValues values = new ContentValues();
        values.put("NbSerie", series);
        values.put("NbReps", reps);
        values.put("NbPoids", poids);

        String selection = "nom = ? AND NumSeance = ? AND NumCycle = ?";
        String[] selectionArgs = {name, String.valueOf(numeroseance), String.valueOf(numerocycle)};

        int count = db.update("MaTable", values, selection, selectionArgs);
        Log.i("seance_malo_db","resultat modifier :" + String.valueOf(count));
    }

    public Cursor getLastData(String nameExo) {
        Log.i("seance_malo_db",nameExo);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT NbSerie, NbReps, NbPoids, NumSeance, NumCycle " +
                "FROM MaTable " +
                "WHERE nom = ? " +
                "AND NumCycle = ( " +
                "    SELECT MAX(NumCycle) " +
                "    FROM MaTable " +
                "    WHERE nom = ? " +
                ");";
        return db.rawQuery(query, new String[]{nameExo, nameExo});
    }


}
