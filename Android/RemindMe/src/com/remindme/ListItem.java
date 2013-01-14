package com.remindme;

public class ListItem {
	private String title;
	private long id;
	private long parentID;

	ListItem(String name, long id) {
		this.title = name;
		this.parentID = id;
	}
	
	ListItem() {
	
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String mTitle) {
		this.title = mTitle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
}
