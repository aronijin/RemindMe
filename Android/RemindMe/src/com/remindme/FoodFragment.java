package com.remindme;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;

public class FoodFragment extends ListFragment {
	private dbhandler datasource;
	protected ImageView _image;
	protected TextView _field;
	protected String path;
	protected boolean _taken;
	protected ListView list;
	protected FoodStuff fs;
	protected ArrayList<Parent> arrayParents;
	private ExpandableListView mExpandableList;
	private int eYear, eMonth, eDay;

	protected boolean visable;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View main_view = inflater.inflate(R.layout.activity_main, container,
				false);

		datasource = new dbhandler(getActivity());
		datasource.open();
		datasource.close();
		mExpandableList = (ExpandableListView) main_view
				.findViewById(R.id.expandable_list);

		// (ExpandableListView)
		// getWindow().getDecorView().findViewById(R.id.expandable_list);

		mExpandableList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				fs = new FoodStuff();

				datasource.open();
				List<FoodStuff> dblist = datasource.getAllFoodStuff();

				long id = arrayParents.get((int) arg2).getId();

				for (int i = 0; i < dblist.size(); i++) {
					if (dblist.get(i).getID() == id) {
						fs = dblist.get(i);
					}
				}
				datasource.close();

				
				if (!mExpandableList.isGroupExpanded(arg2)) {
					// System.out.println("Pos: " + arg2);
					for (int i = 0; i < mExpandableList.getCount(); i++) {
						if (mExpandableList.isGroupExpanded(i)) {
							if (i != arg2) {
								mExpandableList.collapseGroup(i);
							}
						}
					}

					mExpandableList.expandGroup(arg2);
				} else {
					mExpandableList.collapseGroup(arg2);
					
				}
				return true;
			}
		});

		// list = (ListView) findViewById(R.id.listview1);
		setDatabaseListView();

		return main_view;
	}

	public void setDatabaseListView() {
		datasource.open();

		List<FoodStuff> dblist = datasource.getAllFoodStuffByEpireDate();
		String[] dbstrings = new String[dblist.size()];
		arrayParents = new ArrayList<Parent>();
		if (dblist.size() == 0) {
			dbstrings = new String[1];
			dbstrings[0] = "You have no items listed.";
			Parent parent = new Parent();
			parent.setTitle(dbstrings[0]);
			parent.setChild("");
			arrayParents.add(parent);
		}
		for (int i = 0; i < dblist.size(); i++) {
			dbstrings[i] = dblist.get(i).toString();
			Parent parent = new Parent();
			parent.setId(dblist.get(i).getID());
			parent.setTitle(dblist.get(i).getName() + " "
					+ dblist.get(i).ifExpired());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(dblist.get(i).getExpire());
			String expired_date = c.getDisplayName(Calendar.MONTH,
					Calendar.SHORT, Locale.US)
					+ " "
					+ c.get(Calendar.DAY_OF_MONTH)
					+ " "
					+ ", "
					+ c.get(Calendar.YEAR);
			Calendar c2 = Calendar.getInstance();
			c.setTimeInMillis(dblist.get(i).getAdded());
			String added_date = c2.getDisplayName(Calendar.MONTH,
					Calendar.SHORT, Locale.US)
					+ " "
					+ c2.get(Calendar.DAY_OF_MONTH)
					+ " "
					+ ", "
					+ c2.get(Calendar.YEAR);
			parent.setChild("Added: " + added_date);
			if (dblist.get(i).isExpired()) {
				parent.setAdded_date("EXPIRED: " + expired_date);
			} else {
				parent.setAdded_date("Expires: " + expired_date);
			}
			
			parent.setExpired(dblist.get(i).isExpired());
			
			arrayParents.add(parent);
		}

		// ArrayAdapter adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, dbstrings);

		mExpandableList.setAdapter(new MyCustomAdapter(getActivity(),
				arrayParents));
		// ListView listView = (ListView) findViewById(R.id.listview1);
		// listView.setAdapter(adapter);
		datasource.close();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				datasource.open();
				datasource.deleteFoodStuff(fs);
				datasource.close();
				if (fs.getPath() != null) {
					File file = new File(fs.getPath());
					boolean isDeleted = file.delete();
					if (isDeleted) {
						Toast.makeText(getActivity(),
								"Item and picture deleted", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(getActivity(), "Picture not deleted!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "Item deleted",
							Toast.LENGTH_SHORT).show();
				}
				setDatabaseListView();
				// finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// Do your No progress
				break;
			}
		}
	};

	public void deleteItem(View view) {

		AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
		ab.setMessage("Are you sure to delete?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	public String getPath() {
		return fs.getPath();
	}
	
	public void setVisable(boolean vis) {
		this.visable = vis;
	}
	
	public boolean getVisable() {
		return this.visable;
	}
	
	public void refreshItem() {
		
		Calendar cal = Calendar.getInstance();
    	eYear = cal.get(Calendar.YEAR);
    	eMonth = cal.get(Calendar.MONTH);
    	eDay = cal.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dlog1 = new DatePickerDialog(getActivity(),
                mDateSetListener,
                eYear, eMonth, eDay);
    	dlog1.setTitle("Set new experation date: ");
    	dlog1.show();
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					eYear = year;
                    eMonth = monthOfYear;
                    eDay = dayOfMonth;
                    resetExperation();
				}
            };
            
    public void resetExperation() {
        Calendar expire = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
    	expire.set(eYear, eMonth, eDay);
    	
    	fs.setAdded(cal.getTimeInMillis());
        fs.setExpire(expire.getTimeInMillis());
        
        // Re-add to database
        datasource.open();
        datasource.deleteFoodStuff(fs);
        datasource.addFoodStuff(fs);
        datasource.close();
        // Refresh
        setDatabaseListView();
    }
            
}