package com.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDBListItems extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 15;
    public static final String TABLE_NAME = "listitems";
    public static final String ITEM_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PARENT = "parent";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " VARCHAR(256), " +
                COLUMN_PARENT + " INTEGER," +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE" +
                ");";
	private static final String DATABASE_NAME = "localdatabase3";

    LocalDBListItems(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LocalDB.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);
	}
}