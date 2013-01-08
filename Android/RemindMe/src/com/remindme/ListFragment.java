package com.remindme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ListFragment extends Fragment {

	private dbhandlerList datasource;
	protected ListView list;
	protected ListFood fs;
	protected ArrayList<ListParent> arrayParents;
	private ExpandableListView mExpandableList;
	
	protected boolean visable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View main_view = inflater.inflate(R.layout.list_view, container, false);
		datasource = new dbhandlerList(getActivity());
		datasource.open();
		datasource.close();
		mExpandableList = (ExpandableListView) main_view
				.findViewById(R.id.expandableList);

		mExpandableList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				fs = new ListFood();

				datasource.open();
				List<ListFood> dblist = datasource.getAll();

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

		setDatabaseListView();
		return main_view;
	}

	public void setDatabaseListView() {
		datasource.open();

		List<ListFood> dblist = datasource.getAll();
		String[] dbstrings = new String[dblist.size()];
		arrayParents = new ArrayList<ListParent>();
		ArrayList<String> children = new ArrayList<String>();
		
		
		// Decide what to display based on database
		if (dblist.size() == 0) {
			dbstrings = new String[1];
			dbstrings[0] = "You have no items listed.";
			ListParent parent = new ListParent();
			parent.setTitle(dbstrings[0]);
			parent.setChild(children);
			arrayParents.add(parent);
		} else {
			for (int i = 0; i < dblist.size(); i++) {
				dbstrings[i] = dblist.get(i).toString();
				ListParent parent = new ListParent();
				parent.setId(dblist.get(i).getID());
				parent.setTitle(dblist.get(i).getName());

				// Expire date
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(dblist.get(i).getExpire());
				String expired_date = c.getDisplayName(Calendar.MONTH,
						Calendar.SHORT, Locale.US)
						+ " "
						+ c.get(Calendar.DAY_OF_MONTH)
						+ " "
						+ ", "
						+ c.get(Calendar.YEAR);

				// Added date
				Calendar c2 = Calendar.getInstance();
				c.setTimeInMillis(dblist.get(i).getAddedDate());
				String added_date = c2.getDisplayName(Calendar.MONTH,
						Calendar.SHORT, Locale.US)
						+ " "
						+ c2.get(Calendar.DAY_OF_MONTH)
						+ " "
						+ ", "
						+ c2.get(Calendar.YEAR);

				//parent.setChild("Added: " + added_date);
	
				arrayParents.add(parent);
			}
		}

		mExpandableList.setAdapter(new MyCustomAdapterList(getActivity(),
				arrayParents));

		datasource.close();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				datasource.open();
				datasource.deleteListFood(fs);
				datasource.close();
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
	
	public void setVisable(boolean vis) {
		this.visable = vis;
	}
	
	public boolean getVisable() {
		return this.visable;
	}
}