package com.remindme;

import java.util.Calendar;

public class ListFood {
	private long id;
	private String name;
	private String description;
	private int type;
	private long expire_date, added_date;

	public ListFood(String name, String descr, long expire, long added, int t) {
		this.name = name;
		this.description = descr;
		this.expire_date = expire;
		this.added_date = added;
		this.type = t;
	}

	public ListFood() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescr(String description) {
		this.description = description;
	}

	public String getDescr() {
		return description;
	}

	public long getExpire() {
		return expire_date;
	}

	public void setExpire(long expire) {
		this.expire_date = expire;
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getAddedDate() {
		return added_date;
	}

	public void setAddedDate(long added_date) {
		this.added_date = added_date;
	}
	
	public boolean isExpired() {

		boolean expired = false;

		if (expire_date == 0) {
			return false;
		}

		if (expire_date <= Calendar.getInstance().getTimeInMillis()) {
			expired = true;
		}
		return expired;
	}

	public String toString() {
		return this.name;
	}
}
