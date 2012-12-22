package com.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "food";
    public static final String FOOD_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_DESCR = "description";
    public static final String COLUMN_ADDED = "addeddate"; // Primary key. Millisecond timestamp for each lat/lon coord pair.
    public static final String COLUMN_EXPIRE = "expiredate";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " VARCHAR(256), " +
                COLUMN_DESCR + " VARCHAR(256)," +
                COLUMN_PATH + " VARCHAR(256)," +
                COLUMN_ADDED + " INTEGER," +
                COLUMN_EXPIRE + " INTEGER," +
                FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE" +
                ");";
	private static final String DATABASE_NAME = "localdata";

    LocalDB(Context context) {
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