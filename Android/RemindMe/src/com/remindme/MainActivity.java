package com.remindme;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource = new dbhandler(this);        
        datasource.open();
        datasource.close();
        
        list = (ListView) findViewById(R.id.listview1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				datasource.open();
		        List<FoodStuff> dblist = datasource.getAllFoodStuffByEpireDate();
				FoodStuff fs = dblist.get(arg2);
				//Toast.makeText(getApplicationContext(), fs.getID() + "", Toast.LENGTH_SHORT).show();
				datasource.close();
				Intent viewI = new Intent(MainActivity.this, ViewFood.class);
				Bundle b = new Bundle();
				b.putLong("food", fs.getID());
				viewI.putExtras(b);
				MainActivity.this.startActivity(viewI);
				
			}  
         });  
        
        setDatabaseListView();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        setDatabaseListView();
    }

    public void setDatabaseListView(){
    	datasource.open();
        
        List<FoodStuff> dblist = datasource.getAllFoodStuffByEpireDate();
        String[] dbstrings = new String[dblist.size()];
        if(dblist.size() == 0){
        	dbstrings = new String[1];
        	dbstrings[0] = "You have no items listed.";
        }
        for(int i = 0; i < dblist.size(); i++){
        	dbstrings[i] = dblist.get(i).toString();
        } 
        
        
        
        ArrayAdapter adapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, dbstrings);
        
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        datasource.close();
    }
    
    
    /*
     * Used to add new food item to database
     */
    public void addFoddStuff(View view) {
    	Intent myIntent = new Intent(getBaseContext(), AddFoodStuff.class);
    	startActivity(myIntent);
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
