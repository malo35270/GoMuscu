package fr.centralesupelec.ianotto.GoMuscu;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public Cursor getVolume() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT NumSeance, NumCycle, SUM(NbSerie * NbReps * NbPoids) AS somme_produit FROM MaTable GROUP BY NumSeance, NumCycle ORDER BY NumSeance, NumCycle", null);
    }

    public void ajoutData(String name, int series,int reps, double poids, int numeroseance, int numerocycle){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put("nom", name); // Utiliser "nom" ici, correspondant à la colonne "nom" dans la table
        values.put("NbSerie", series);
        values.put("NbReps", reps);
        values.put("NbPoids", poids);
        values.put("NumSeance", numeroseance);
        values.put("NumCycle", numerocycle);
        long result = db.insert("MaTable", null, values);
        Log.i("Data", String.valueOf(result));

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

    public Cursor test() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT *, SUM(product) AS somme_produit, COUNT(*) as nombre_occurrences\n" +
                "FROM (\n" +
                "    SELECT *, NbSerie * NbReps * NbPoids AS product\n" +
                "    FROM MaTable\n" +
                ") AS subquery\n" +
                "GROUP BY NumSeance, NumCycle\n"+
                "HAVING COUNT(*) > 1;", null);
    }
}
