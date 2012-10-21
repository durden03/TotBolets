package com.tfg.politmiro.totbolets;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BoletDbAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ID = "id";
	public static final String KEY_NOM = "nom";
	public static final String KEY_NOMCIENTIFIC = "nomcientific";
	public static final String KEY_ALTRES = "altres";
	public static final String KEY_CLASSE = "classe";
	public static final String KEY_HABITAT = "habitat";
	public static final String KEY_GASTRONOMIA = "gastronomia";
	public static final String KEY_CONFUSIO = "confusio";
	public static final String KEY_IMGBOLET = "imgbolet";
	private static final String DATABASE_TABLE = "bolet";
	private Context context;
	private SQLiteDatabase database;
	private DataBase dbHelper;

	public BoletDbAdapter(Context context) {
		this.context = context;
	}

	public BoletDbAdapter open() throws SQLException {
		dbHelper = new DataBase(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long insert(Bolet bolet) {
		ContentValues values = objectToValues(bolet);
		if (bolet._id != 0)
			values.put(KEY_ROWID, bolet._id);
		return database.insert(DATABASE_TABLE, null, values);
	}

	public long replace(Bolet bolet) {
		ContentValues values = objectToValues(bolet);
		return database.replace(DATABASE_TABLE, null, values);
	}
	
	public int update(Bolet bolet) {
		ContentValues values = objectToValues(bolet);
		return database.update(DATABASE_TABLE, values, KEY_ROWID + "="+ bolet._id , null);
	}

	private ContentValues objectToValues(Bolet bolet) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID,bolet.id);
		values.put(KEY_NOM,bolet.nom);
		values.put(KEY_NOMCIENTIFIC,bolet.nomcientific);
		values.put(KEY_ALTRES,bolet.altres);
		values.put(KEY_CLASSE,bolet.classe);
		values.put(KEY_HABITAT,bolet.habitat);
		values.put(KEY_GASTRONOMIA,bolet.gastronomia);
		values.put(KEY_CONFUSIO,bolet.confusio);
		values.put(KEY_IMGBOLET,bolet.imgbolet);
		
		if (bolet._id != 0)
			values.put(KEY_ROWID, bolet._id);
		return values;
	}	
	
	
	public Cursor getAll() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_ID,KEY_NOM, KEY_NOMCIENTIFIC, KEY_ALTRES, KEY_CLASSE,
				KEY_HABITAT, KEY_GASTRONOMIA, KEY_CONFUSIO,
				KEY_IMGBOLET}, null, null, null,
				null, null);
	}
	
	public Cursor getNomBolet(String nom) {
		String where = "nom = ?";
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_ID, KEY_NOM, KEY_NOMCIENTIFIC, KEY_IMGBOLET},
				where, new String [] {nom}, null, null, null);
	}
	
	public Cursor getNomBoletCientific(String nomcientific) {
		String where = "nomcientific = ?";
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_ID, KEY_NOM, KEY_NOMCIENTIFIC, KEY_IMGBOLET},
				where, new String [] {nomcientific}, null, null, null);
	}
			
	
	public Bolet getBolet(Cursor cursor) {
		Bolet bol = new Bolet();
		bol.id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
		bol.nom=cursor.getString(cursor.getColumnIndex(KEY_NOM));
		bol.nomcientific=cursor.getString(cursor.getColumnIndex(KEY_NOMCIENTIFIC));
		bol.altres=cursor.getString(cursor.getColumnIndex(KEY_ALTRES));
		bol.classe=cursor.getString(cursor.getColumnIndex(KEY_CLASSE));
		bol.habitat=cursor.getString(cursor.getColumnIndex(KEY_HABITAT));
		bol.gastronomia=cursor.getString(cursor.getColumnIndex(KEY_GASTRONOMIA));
		bol.confusio=cursor.getString(cursor.getColumnIndex(KEY_CONFUSIO));
		bol.imgbolet=cursor.getString(cursor.getColumnIndex(KEY_IMGBOLET));
		
		bol._id=cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
		return bol;
	}
	
	public void truncate() {
		database.execSQL("delete from "+DATABASE_TABLE);
	}
	
	
}