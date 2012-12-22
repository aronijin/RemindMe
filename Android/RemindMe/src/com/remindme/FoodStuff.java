package com.remindme;

import android.annotation.TargetApi;
import android.os.Build;
import java.util.Calendar;
import java.util.Locale;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class FoodStuff {
	private long id;
	private String name;
	private String description;
	private String path;
	private long added_date, expire_date;
	
	public FoodStuff(String name, String descr, String dpath, long added, long expire){
		this.name = name;
		this.path = dpath;
		this.description = descr;
		this.added_date = added;
		this.expire_date = expire;
	}

	public FoodStuff() {
		
	}
	
	public void setPath(String path){
		this.path = path;
	}
	public String getPath(){
		return this.path;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public void setDescr(String description){
		this.description = description;
	}
	public String getDescr(){
		return description;
	}
	
	public void setAdded(long date){
		this.added_date = date;
	}
	public long getAdded(){
		return added_date;
	}
	
	public void setExpire(long date){
		this.expire_date = date;
	}
	public long getExpire(){
		return expire_date;
	}
	
	public void setID(long id){
		this.id = id;
	}
	public long getID(){
		return id;
	}
	
	public String toString(){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(expire_date);
		String expired_date = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + c.get(Calendar.DAY_OF_MONTH) + " " + ", " + c.get(Calendar.YEAR);
		String expired = new String();
		
		if(expire_date <= Calendar.getInstance().getTimeInMillis()){
			expired = "[EXPIRED]";
		}
		
		return name + ": " + description + " Expires:  " + expired_date + " " + expired;
	}
	
	
}
