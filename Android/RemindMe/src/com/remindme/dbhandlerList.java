package com.remindme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dbhandlerList {

	// Database fields
	private SQLiteDatabase database;
	private LocalDBList dbHelper;
	private String[] allColumns = { LocalDBList.COLUMN_NAME, LocalDBList.COLUMN_DESCR, LocalDBList.COLUMN_EXPIRE, LocalDBList.COLUMN_ADDED, LocalDBList.LIST_ID,
			LocalDBList.COLUMN_TYPE };

	public dbhandlerList(Context context) {
		dbHelper = new LocalDBList(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void addList(ListFood fs) {
		ContentValues values = new ContentValues();
		values.put(LocalDBList.COLUMN_NAME, fs.getName());
		values.put(LocalDBList.COLUMN_DESCR, fs.getDescr());
		values.put(LocalDBList.COLUMN_EXPIRE, fs.getExpire());
		values.put(LocalDBList.COLUMN_TYPE, fs.getType());
		values.put(LocalDBList.COLUMN_ADDED, fs.getAddedDate());
		database.insert(LocalDBList.TABLE_NAME, null, values);
	}

	public void deleteListFood(ListFood dp) {
		long id = dp.getID();
		System.out.println("Item deleted with id: " + id);
		database.delete(LocalDBList.TABLE_NAME, LocalDBList.LIST_ID + " = "
				+ id, null);
	}

	public void deleteEverything() {
		System.out.println("Deleted everything.");
		database.delete(LocalDBList.TABLE_NAME, null, null);
	}

	public List<ListFood> getAll() {
		List<ListFood> dps = new ArrayList<ListFood>();

		Cursor cursor = database.query(LocalDBList.TABLE_NAME, allColumns,
				null, null, null, null, "_id");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ListFood fs = cursorToDataPoint(cursor);
			dps.add(fs);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return dps;
	}

	private ListFood cursorToDataPoint(Cursor cursor) {
		ListFood fs = new ListFood();
		fs.setName(cursor.getString(0));
		fs.setDescr(cursor.getString(1));
	    fs.setType(cursor.getInt(3));
	    fs.setExpire(cursor.getLong(2));
	    fs.setAddedDate(cursor.getLong(4));
		return fs;
	}
}
