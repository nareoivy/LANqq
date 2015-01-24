package com.even;

import com.chat.bean.User;



public class ReceiveMessageEven extends java.util.EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public User getTargeUser() {
		return targeUser;
	}

	public void setTargeUser(User targeUser) {
		this.targeUser = targeUser;
	}


	
	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}

	private User sourceUser;
	private User targeUser;

	private String message;
	public ReceiveMessageEven(Object source) {
		super(source);
	}
	
	public ReceiveMessageEven(Object source,String message,User sourceUser,User targeUser) {
		super(source);
		this.message = message;
		this.sourceUser = sourceUser;
		this.targeUser = targeUser;
	}
	
}
