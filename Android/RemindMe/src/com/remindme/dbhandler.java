package com.remindme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dbhandler {

  // Database fields
  private SQLiteDatabase database;
  private LocalDB dbHelper;
  private String[] allColumns = { LocalDB.COLUMN_NAME,
      LocalDB.COLUMN_DESCR, LocalDB.COLUMN_ADDED, LocalDB.COLUMN_EXPIRE, LocalDB.FOOD_ID, LocalDB.COLUMN_PATH };

  public dbhandler(Context context) {
    dbHelper = new LocalDB(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }
  
  public void addFoodStuff(FoodStuff fs) {
	    ContentValues values = new ContentValues();
	    values.put(LocalDB.COLUMN_NAME, fs.getName());
	    values.put(LocalDB.COLUMN_DESCR, fs.getDescr());
	    values.put(LocalDB.COLUMN_ADDED, fs.getAdded());
	    values.put(LocalDB.COLUMN_EXPIRE, fs.getExpire());
	    values.put(LocalDB.COLUMN_PATH, fs.getPath());
	    database.insert(LocalDB.TABLE_NAME, null, values);
	  }

  public void deleteFoodStuff(FoodStuff dp) {
    long id = dp.getID();
    System.out.println("Item deleted with id: " + id);
    database.delete(LocalDB.TABLE_NAME, LocalDB.FOOD_ID
        + " = " + id, null);
  }
  
  public void deleteEverything() {
	    System.out.println("Deleted everything.");
	    database.delete(LocalDB.TABLE_NAME, null, null);
  }

  public List<FoodStuff> getAllFoodStuff() {
    List<FoodStuff> dps = new ArrayList<FoodStuff>();

    Cursor cursor = database.query(LocalDB.TABLE_NAME,
        allColumns, null, null, null, null, "_id");

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      FoodStuff fs = cursorToDataPoint(cursor);
      dps.add(fs);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return dps;
  }
  
  public List<FoodStuff> getAllFoodStuffByEpireDate() {
	    List<FoodStuff> dps = new ArrayList<FoodStuff>();

	    Cursor cursor = database.query(LocalDB.TABLE_NAME,
	        allColumns, null, null, null, null, LocalDB.COLUMN_EXPIRE);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      FoodStuff fs = cursorToDataPoint(cursor);
	      dps.add(fs);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return dps;
	  }
  
  public List<FoodStuff> getAllDataPoints() {
	    List<FoodStuff> dps = new ArrayList<FoodStuff>();

	    Cursor cursor = database.query(LocalDB.TABLE_NAME,
	        allColumns, null, null, null, null, "_id");

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      FoodStuff dp = cursorToDataPoint(cursor);
	      dps.add(dp);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return dps;
	  }
  
  private FoodStuff cursorToDataPoint(Cursor cursor) {
    FoodStuff fs = new FoodStuff();
    fs.setID(cursor.getLong(4));
    fs.setName(cursor.getString(0));
    fs.setDescr(cursor.getString(1));
    fs.setAdded(cursor.getLong(2));
    fs.setExpire(cursor.getLong(3));
    fs.setPath(cursor.getString(5));
    return fs;
  }
} 
