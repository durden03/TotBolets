package com.tfg.politmiro.totbolets;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Base";
	private static final int DATABASE_VERSION = 1;
	// Database creation sql statement
	
	private static final String DATABASE_CREATE_BOLET = "create table bolet" +
			"(_id integer primary key autoincrement, "+
			"  id integer not null," +
			"  nom text not null," +
			"  nomcientific text not null," +
			"  altres text null," +
			"  classe text not null," +
			"  habitat text  null," +
			"  gastronomia text  null," +
			"  confusio text  null," +
			"  imgbolet text null);";
	
		
	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_BOLET);
		Log.v("SQL",DATABASE_CREATE_BOLET);
		
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Not implemented
//		if (oldVersion==1 && newVersion==2)
//		{
//			final String DATABASE_UPDATE = DATABASE_CREATE_CLIENTES;
//			db.execSQL(DATABASE_UPDATE);
//		}
//		if (oldVersion < newVersion && newVersion==3)
//		{
//			final String DATABASE_UPDATE = "alter table articulo ADD COLUMN codsubcat text null;";
//			db.execSQL(DATABASE_UPDATE);
//		}
	}
	
	public static void deletethis(Context context)
	{
		context.deleteDatabase(DATABASE_NAME);
	}

}

	