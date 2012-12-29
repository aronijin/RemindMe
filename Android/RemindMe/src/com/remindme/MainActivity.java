package com.remindme;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.hardware.Camera;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	protected static final String PHOTO_TAKEN = "photo_taken";
	private dbhandler datasource;
	protected ImageView _image;
	protected TextView _field;
	protected String path;
	protected boolean _taken;
	protected ListView list;
	protected FoodStuff fs;
	protected ArrayList<Parent> arrayParents;
	protected FoodFragment ff;
	protected ListFragment lf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		ActionBar actionBar = getActionBar();

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

	}

	@Override
	protected void onResume() {
		super.onResume();
		ff.setDatabaseListView();
		// setDatabaseListView();
	}

	/*
	 * Used to add new food item to database
	 */
	public void addFoddStuff(View view) {
		Intent myIntent = new Intent(getBaseContext(), AddFoodStuff.class);
		startActivity(myIntent);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }

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
		ft.replace(R.id.fragment_container, mFragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// ft.detach(mFragment);
		ft.remove(mFragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}
}