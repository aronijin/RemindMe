package com.remindme;

import java.util.List;

public class ListParent {
	private String title;
	private List<ListItem> children;
	private long id;

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

	public List<ListItem> getChildren() {
		return children;
	}

	public void setChildren(List<ListItem> children) {
		this.children = children;
	}
	
	public int getChildCount() {
		return children.size();
	}
}