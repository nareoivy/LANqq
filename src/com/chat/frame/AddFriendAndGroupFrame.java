package com.chat.frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.chat.bean.User;
/**
 * 增加好友功能
 * 一次只能增加一个好友，不能重复添加相同的好友
 * @author Karen
 *
 */
public class AddFriendAndGroupFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private JTabbedPane tabPane;
	private JButton  search;
	private JLabel userNoLabel,groupNoLabel;
	private JTextField userNoField;
	
	public AddFriendAndGroupFrame(User curUser){
		
	}
	
	public void init(){
		
	}

}
