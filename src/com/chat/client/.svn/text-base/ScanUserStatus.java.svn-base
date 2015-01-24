package com.chat.client;

import java.util.List;

import com.chat.bean.DiscussGroup;
import com.chat.bean.User;
import com.chat.frame.UserListFrame;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;

public class ScanUserStatus extends Thread{
	private UserListFrame userListFrame;
	private User curUser;
	public ScanUserStatus(UserListFrame userListFrame,User curUser){
		this.userListFrame = userListFrame;
		this.curUser = curUser;
	}
	
	@SuppressWarnings("unused")
	public void run(){
		  while(true){
			  System.out.println("开始刷新");
			  DataUtil.sendRequestRefreshInfo(PersistenceContext.getEntity().getServerIP(), Constants.CON_SERVER_ACCEPT_PORT-1, curUser);
			
			  //刷新User
			  int selectIndex = userListFrame.getPerson().getSelectedIndex();
			  userListFrame.getPerson().removeAll();			  
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			  List<User> users = PersistenceContext.getEntity().getUsers();
			  for(int i = 0; i < users.size(); i++){
				  if(users.get(i).getNo().equals(curUser.getNo())){
					  users.remove(i);
				  }
			  }
			  
			  DataUtil.setListByUsers(users, userListFrame.getPerson(),selectIndex);
			  //刷新Group
			  int selectGroupIndex = userListFrame.getGroup().getSelectedIndex();
			  userListFrame.getGroup().removeAll();
			  List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
//			  DataUtil.setListByGroups(discussGroups, userListFrame.getGroup(), selectGroupIndex);
				
			  try {
			      Thread.sleep(9000);
			   }catch (InterruptedException e) {
				  e.printStackTrace();
			  }
		  }
	}
}
