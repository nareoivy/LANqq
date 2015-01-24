package com.chat.ui;


import javax.swing.JList;

import com.chat.util.GetGroupImageUtil;

public class GroupListUI extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GroupListUI(){
		super();
	}
	
	public void setCellRenderer(GetGroupImageUtil getGroupImageUtil,String groupName){
		super.setCellRenderer(getGroupImageUtil);
		
	}
}
