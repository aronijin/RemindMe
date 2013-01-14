package com.remindme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dbhandlerListItems {

	// Database fields
	private SQLiteDatabase database;
	private LocalDBListItems dbHelper;
	private String[] allColumns = { LocalDBListItems.COLUMN_NAME, LocalDBListItems.COLUMN_PARENT, LocalDBListItems.ITEM_ID};

	public dbhandlerListItems(Context context) {
		dbHelper = new LocalDBListItems(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void addItem(ListItem li) {
		ContentValues values = new ContentValues();
		values.put(LocalDBListItems.COLUMN_NAME, li.getTitle());
		values.put(LocalDBListItems.COLUMN_PARENT, li.getParentID());
		database.insert(LocalDBListItems.TABLE_NAME, null, values);
	}

	public void deleteItem(long id) {
		System.out.println("Item deleted with id: " + id);
		database.delete(LocalDBListItems.TABLE_NAME, LocalDBListItems.ITEM_ID + " = "
				+ id, null);
	}
	
	public void deleteItems(long id) {
		System.out.println("Items deleted with parent id: " + id);
		database.delete(LocalDBListItems.TABLE_NAME, LocalDBListItems.COLUMN_PARENT + " = "
				+ id, null);
	}

	public void deleteEverything() {
		System.out.println("Deleted everything.");
		database.delete(LocalDBListItems.TABLE_NAME, null, null);
	}

	public List<ListItem> getItems(long id) {
		List<ListItem> dps = new ArrayList<ListItem>();

		Cursor cursor = database.query(LocalDBListItems.TABLE_NAME, allColumns,
				LocalDBListItems.COLUMN_PARENT + " = " + id, null, null, null, "_id");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			
			// Add logic to get all items in a list
			
			ListItem fs = cursorToDataPoint(cursor);
			dps.add(fs);
			cursor.moveToNext();
		}
		
		// Make sure to close the cursor
		cursor.close();
		return dps;
	}

	private ListItem cursorToDataPoint(Cursor cursor) {
		ListItem fs = new ListItem();
		fs.setTitle(cursor.getString(0));
		fs.setParentID(cursor.getLong(1));
	    fs.setId(cursor.getLong(2));
		return fs;
	}
}
