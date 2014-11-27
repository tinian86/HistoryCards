import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database extends SQLiteOpenHelper {

	public Entry entry;
	
	private static final String DATABASE_NAME = "fragen.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String FRAGEN_TABLE = "create table FRAGEN (" + 
			" ID integer primary key, " +
			"TITEL text, " + 
			"TEXT text, " + 
			"JAHR int )";
	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(FRAGEN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(Database.class.getName(),
				"Upgrading database from version " + oldVersion +
				" to " + newVersion + ", wich will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + FRAGEN_TABLE);	//alte Tabelle loeschen
		onCreate(db);	//neue Tabelle erstellen
	}

}
