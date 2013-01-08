package com.remindme;

import java.util.ArrayList;

public class ListParent {
	private String title;
	private ArrayList<String> children;
	private long id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String mTitle) {
		this.title = mTitle;
	}

	public ArrayList<String> getChild() {
		return this.children;
	}

	public void setChild(ArrayList<String> children) {
		this.children = children;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}