package com.even;

import java.util.List;

import com.chat.bean.DiscussGroup;
import com.chat.bean.User;

public class ReceiveRefreshMessageEvent extends java.util.EventObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<User> users;
	private List<DiscussGroup> discussGroups;

	public ReceiveRefreshMessageEvent(Object source) {
		super(source);
	}
	
	public ReceiveRefreshMessageEvent(Object source,List<User> users,List<DiscussGroup> discussGroups){
		super(source);
		this.users = users;
		this.discussGroups = discussGroups;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<DiscussGroup> getDiscussGroups() {
		return discussGroups;
	}

	public void setDiscussGroups(List<DiscussGroup> discussGroups) {
		this.discussGroups = discussGroups;
	}
}
