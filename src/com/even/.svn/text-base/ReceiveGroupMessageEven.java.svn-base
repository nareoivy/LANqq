package com.even;

import com.chat.bean.User;

public class ReceiveGroupMessageEven extends java.util.EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private User sourceUser;
	private String groupName;
	
	public ReceiveGroupMessageEven(Object source) {
		super(source);
	}
	
	public ReceiveGroupMessageEven(Object source,String message,User sourceUser,String groupName) {
		super(source);
		this.message = message;
		this.sourceUser = sourceUser;
		this.groupName = groupName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
