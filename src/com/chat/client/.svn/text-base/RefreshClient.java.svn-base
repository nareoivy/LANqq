package com.chat.client;

import java.util.List;

import com.chat.bean.DiscussGroup;
import com.chat.bean.User;
import com.chat.frame.UserListFrame;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;

public class RefreshClient {
	public static void refresh(User curUser,UserListFrame userListFrame){
		//刷新User
		 int selectIndex = userListFrame.getPerson().getSelectedIndex();
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
		  DataUtil.setListByGroups(discussGroups, userListFrame, selectGroupIndex);
	}
}
