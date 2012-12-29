package com.remindme;

public class Parent {
	private String title;
	private String added_date;
	private String children;
	private long id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String mTitle) {
		this.title = mTitle;
	}

	public String getChild() {
		return this.children;
	}

	public void setChild(String children) {
		this.children = children;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdded_date() {
		return added_date;
	}

	public void setAdded_date(String added_date) {
		this.added_date = added_date;
	}
}