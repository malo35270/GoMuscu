package fr.centralesupelec.ianotto.GoMuscu;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MaBaseDeDonnees";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la structure de la base de données (tables, colonnes, etc.)
        String createTableQuery = "CREATE TABLE MaTable (_id INTEGER PRIMARY KEY, nom TEXT, age INTEGER);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettre à jour la structure de la base de données en cas de nouvelle version
        // Vous pouvez ajouter des instructions ALTER TABLE ici
    }
}
