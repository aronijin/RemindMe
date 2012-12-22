package com.remindme;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AddFoodStuff extends Activity {
	private static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID2 = 1, MILLIS_IN_WEEK = 604800000;
	private dbhandler datasource;
	private int aYear, aMonth, aDay, eYear, eMonth, eDay;
	private Calendar c = Calendar.getInstance(), c2 = Calendar.getInstance();
	private String path;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfoodstuff);
        datasource = new dbhandler(this);
        
        //path = null;
        
        aYear = c.get(Calendar.YEAR);
        aMonth = c.get(Calendar.MONTH);
        aDay = c.get(Calendar.DAY_OF_MONTH);
        
        c2.setTimeInMillis(c.getTimeInMillis() + MILLIS_IN_WEEK);
        eYear = c2.get(Calendar.YEAR);
        eMonth = c2.get(Calendar.MONTH);
        eDay = c2.get(Calendar.DAY_OF_MONTH);
        
        updateTextViews();
    }
    
    public void saveFood(View view){
    	TextView name = (TextView) findViewById(R.id.nameLabel);
    	TextView descr = (TextView) findViewById(R.id.descrText);
    	Calendar add = Calendar.getInstance(), expire = Calendar.getInstance();
    	add.set(aYear, aMonth, aDay);
    	expire.set(eYear, eMonth, eDay);
    	
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(expire.getTimeInMillis());
	    calendar.set(Calendar.HOUR_OF_DAY, 12);
	    calendar.set(Calendar.MINUTE, 00);
	    calendar.set(Calendar.SECOND, 00);
	    
	    setNotification(calendar);
	    
	    
    	datasource.open();
    	FoodStuff fs = new FoodStuff(name.getText().toString(), descr.getText().toString(), path, add.getTimeInMillis(), expire.getTimeInMillis());
    	datasource.addFoodStuff(fs);
    	datasource.close();
    	this.finish();
    }
    
    public void takePicture(View view){
    	startCameraActivity();
    }
    
    protected void startCameraActivity() {
    	String dpath = Environment.getExternalStorageDirectory()+File.separator+"MeatCake";
    	File directory = new File(dpath);
    	directory.mkdirs();
    	path = dpath + "/" + System.currentTimeMillis() + ".png";
        Uri outputFileUri = Uri.fromFile(new File(path) );
        	
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
        
        startActivityForResult( intent, 1 );
        
    }
    
    /*
     * Used to set a status bar notification for expired food
     */
    protected void setNotification(Calendar calendar) {
    	
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	Intent intent = new Intent(this, NotificationIntent.class);
  	  	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    
   /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      
            if (resultCode == RESULT_OK) {
            	System.out.println(path);
                // Image captured and saved to fileUri specified in the Intent
            	
            	File imgFile = new  File(path);
            	if(imgFile.exists()){

            	    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            	    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            	    //myImage.setImageBitmap(myBitmap);

            	}
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            	path = null;
            } else {
                // Image capture failed, advise user
            }
    }*/
    
    public void updateTextViews(){
    	TextView addtext = (TextView) findViewById(R.id.addedDate);
    	TextView expiretext = (TextView) findViewById(R.id.expireDate);
    	Calendar add = Calendar.getInstance(), expire = Calendar.getInstance();
    	
    	add.set(aYear, aMonth, aDay);
    	addtext.setText(add.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + aDay + " " + ", " + aYear);
    	
    	expire.set(eYear, eMonth, eDay);
    	expiretext.setText(expire.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + eDay + " " + ", " + eYear);
    }
    
    @SuppressWarnings("deprecation")
	public void changeAdd(View view){
    	showDialog(DATE_DIALOG_ID);
    	updateTextViews();
    }
    
    @SuppressWarnings("deprecation")
	public void changeExpire(View view){
    	showDialog(DATE_DIALOG_ID2);
    	updateTextViews();
    }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					aYear = year;
                    aMonth = monthOfYear;
                    aDay = dayOfMonth;
                    updateTextViews();
				}
            };
            
    private DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					eYear = year;
                    eMonth = monthOfYear;
                    eDay = dayOfMonth;
                    updateTextViews();
				}
            };
    
    @Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	    	DatePickerDialog dlog1 = new DatePickerDialog(this,
                    mDateSetListener,
                    aYear, aMonth, aDay);
	    	dlog1.setTitle("Date the food was added:");
	    	
			return dlog1;
	    case DATE_DIALOG_ID2:
	    	DatePickerDialog dlog2 = new DatePickerDialog(this,
                    mDateSetListener2,
                    eYear, eMonth, eDay);
	    	dlog2.setTitle("Date the food will expire:");
	    	
			return dlog2;
	    }
	    return null;
	}
}
