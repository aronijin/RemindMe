package com.remindme;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	// Databases
	private dbhandler datasource;
	private dbhandlerList datasourceList;
	
	private ActionBar actionBar;
	
	protected FoodFragment ff; // Reference to the food side fragment
	protected ListFragment lf; // Reference to the list side fragment

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		// Set action bar
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ff = new FoodFragment();
		String label1 = "Food";
		Tab tab = actionBar.newTab();
		tab.setText(label1);
		TabListener tl = new TabListener(ff);
		tab.setTabListener(tl);
		actionBar.addTab(tab);

		lf = new ListFragment();
		String label2 = "List";
		Tab tab2 = actionBar.newTab();
		tab2.setText(label2);
		TabListener tl2 = new TabListener(lf);
		tab2.setTabListener(tl2);
		actionBar.addTab(tab2);

		datasource = new dbhandler(this);
		datasource.open();
		datasource.close();

		datasourceList = new dbhandlerList(this);
		datasourceList.open();
		datasourceList.close();
	
		// Set the current fragment that is visable
		ff.setVisable(true);
		lf.setVisable(false);

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Determine which fragment is currently selected
		if (lf.getVisable()) {
			lf.setDatabaseListView();
		}
		if (ff.getVisable()) {
			ff.setDatabaseListView();
		}
	
	}

	/*
	 * Used to add new food item to database
	 */
	public void addFoddStuff(View view) {
		Intent myIntent = new Intent(getBaseContext(), AddFoodStuff.class);
		startActivity(myIntent);
	}

	/*
	 * Used to add new list item to database
	 */
	public void addListStuff(View view) {
		Intent myIntent = new Intent(getBaseContext(), AddListStuff.class);
		startActivity(myIntent);
	}

	public void deleteItem(View view) {
		ff.deleteItem(view);
	}

	/*
	 * NEED FIXING!!!
	 */
	public void imageView(View view) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + ff.getPath()), "image/*");
		startActivity(intent);
	}

}

class TabListener implements ActionBar.TabListener {
	private Fragment mFragment;

	public TabListener(Fragment fragment) {
		mFragment = fragment;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// ft.attach(mFragment);
		((ListFragment) mFragment).setVisable(true);
		ft.replace(R.id.fragment_container, mFragment, "" + tab.getText());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// ft.detach(mFragment);
		((ListFragment) mFragment).setVisable(false);
		ft.remove(mFragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}
}