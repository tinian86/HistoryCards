import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataSource {
	
	private SQLiteDatabase sqlBase;
	private Database database;
	private String[] allColumns = {"ID", "TITEL", "TEXT", "JAHR"};
	
	public DataSource (Context context){
		database = new Database(context);
	}
	
	//Eintraege aus Datenbank lesen
	public void open() {
		sqlBase = database.getReadableDatabase();
	}
	
	public void close(){
		database.close();
	}
	
	public Entry createEntry(String id, String titel, String frage, int jahr){
		//speichert alle Werte aus einem Eintrag
		ContentValues values = new ContentValues();
		
		//id wird in ID eingetragen
		values.put("ID", id);
		values.put("TITEL", titel);
		values.put("TEXT", frage);
		values.put("JAHR", jahr);
		
		//alle Werte in Datenbank eintragen
		long insertId = sqlBase.insert("FRAGEN", null, values);
		
		Cursor cursor = sqlBase.query("FRAGEN", allColumns, "ID"+insertId, null, null, null, null);
		//curser an Anfang, falls neuer Eintrag erstellt wird
		cursor.moveToFirst();
		
		return cursorToEntry(cursor);
	}
	
	//alle Werte auslesen
	protected List<Entry> getAllEntries(){
		List<Entry> entriesList= new ArrayList<Entry>();
		entriesList = new ArrayList<Entry>();
		
		Cursor cursor = sqlBase.query("FRAGEN", allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		//wenn cursor keine Eintraege erkennt
		if(cursor.getCount() == 0)
			return entriesList;
		
		//solange cursor noch nicht hinter letztem Eintrag ist
		//liest solange aus bis kein neuer Eintrag mehr kommt
		while(cursor.isAfterLast() == false){
			
			Entry entry = cursorToEntry(cursor);
			entriesList.add(entry);
			cursor.moveToNext();
		}
		
		cursor.close();
		return entriesList;
	}
	
	private Entry cursorToEntry(Cursor cursor){
		//Werte in enrty zwischenspeichern
		Entry entry = new Entry();
		
		//id in erste Spalte (0) speichern
		entry.setId(cursor.getString(0));
		entry.setTitel(cursor.getString(1));
		entry.setText(cursor.getString(2));
		entry.setJahr(cursor.getInt(3));
		
		return entry;
	}
}
