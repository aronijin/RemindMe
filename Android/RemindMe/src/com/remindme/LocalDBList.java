package com.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDBList extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    public static final String TABLE_NAME = "foodlist";
    public static final String LIST_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCR = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ADDED = "added";
    public static final String COLUMN_EXPIRE = "expire";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " VARCHAR(256), " +
                COLUMN_DESCR + " VARCHAR(256), " +
                COLUMN_TYPE + " INTEGER," + // Parent object is 1 and child is 0
                COLUMN_EXPIRE + " INTEGER," + 
                COLUMN_ADDED + " INTEGER," + 
                LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE" +
                ");";
	private static final String DATABASE_NAME = "localdataList";

    LocalDBList(Context context) {
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