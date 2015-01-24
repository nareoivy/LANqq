package com.chat.client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.User;
import com.chat.common.Hander;
import com.chat.frame.UserListFrame;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.SocketUtil;
import com.even.ReceiveRefreshMessageEvent;

/**
 * Receive File thread
 * 
 * @author pccw
 * 
 */
public class ClientRefreshThread extends Thread {
	private Socket socket;
	private DataInputStream inputStream;
	public ClientRefreshThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			Thread.sleep(300);
             inputStream = SocketUtil.getDataInputStream(socket);

			/**
			 * Accept Hander
			 */
			byte[] byteHander = new byte[Constants.CON_HANDER_LENGTH];
			inputStream.read(byteHander);
			if(byteHander.length == 0){
				return;
			}
			String strHander = new String(byteHander);
			if(strHander.trim().length() == 0){
				return;
			}
			Hander hander = (Hander) JsonUtil.getObject4JsonString(strHander,Hander.class);

			/**
			 * Hander Info
			 */
			String type = hander.getType();
			String method = hander.getMethod();
			String message = hander.getMessage();
			if(type.equals(Constants.CON_HANDER_TYPE_RESPONSE)){
				 if(method.equals(Constants.CON_HANDER_METHOD_VALIDATE)){
					  if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
						  /**
						   * Validation is ok
						   */
						  int loginUserLen = hander.getLength();
						  int[] usersLen = hander.getUsersLen();
						  int[] groupsLen = hander.getGroupLen();
						  int[] leaveMesLen = hander.getLeaveMesLen();
						  
						  List<User> users = new ArrayList<User>();
						  List<DiscussGroup> groups = new ArrayList<DiscussGroup>();
						  List<LeaveMessage> leaveMes = new ArrayList<LeaveMessage>();
						  
						  /**
						   * read Login user
						   */
						  byte[] bLoginUser = new byte[loginUserLen];
						  inputStream.read(bLoginUser);
						  if(bLoginUser.length == 0){
							  return;
						  }
						  String strLoginUser = new String(bLoginUser);
						  User loginUser = (User) JsonUtil.getObject4JsonString(strLoginUser, User.class);
						  
						  /**
						   * Read users
						   */
						  byte[] bUser = null;
						  String sUser = null;
						  if(usersLen.length > 0){
							  for(int userLen : usersLen){
								  bUser = new byte[userLen];
								  inputStream.read(bUser);
								  if(bUser.length > 0){
									  sUser = new String(bUser);
									  users.add((User) JsonUtil.getObject4JsonString(sUser, User.class));
								  }
							  }
						  }
						  
						  /**
						   * Read Groups
						   */
						  byte[] bGroup = null;
						  String sGroup = null;
						  if(groupsLen.length > 0){
							  for(int groupLen : groupsLen){
								  bGroup = new byte[groupLen];
								  inputStream.read(bGroup);
								  if(bGroup.length > 0){
									  sGroup = new String(bGroup);
									  groups.add((DiscussGroup) JsonUtil.getObject4JsonString(sGroup, DiscussGroup.class));
								  }
							  }
						  }						  
				
						  /**
						   * read leave messages
						   */
						  byte[] bLeaveMes = null;
						  String sLeaveMes = null;
						  if(leaveMesLen.length > 0){
							  for(int LeaveLen : leaveMesLen){
								  bLeaveMes = new byte[LeaveLen];
								  inputStream.read(bLeaveMes);
								  if(bLeaveMes.length > 0){
									  sLeaveMes = new String(bLeaveMes);
									  leaveMes.add((LeaveMessage) JsonUtil.getObject4JsonString(sLeaveMes, LeaveMessage.class));
								  }
							  }
						  }		
						  
						  PersistenceContext.getEntity().setCurUser(loginUser);
						  PersistenceContext.getEntity().setUsers(users);
						  PersistenceContext.getEntity().setGroups(groups);
						  PersistenceContext.getEntity().setLeaveMessages(leaveMes);
						  PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_SECCESS);
						
						  
					  }else if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR)){
						  /**
						   * Validation is faiture
						   */
						  PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_FAILTURE);
						  
					  }
					 
				 }else if(method.equals(Constants.CON_HANDER_METHOD_REFRESH)){
					 /**
					   * REFRESH
					   */

					  int[] usersLen = hander.getUsersLen();
					  int[] groupsLen = hander.getGroupLen();
					  
					  List<User> users = new ArrayList<User>();
					  List<DiscussGroup> groups = new ArrayList<DiscussGroup>();					  
					  
					  /**
					   * Read users
					   */
					  byte[] bUser = null;
					  String sUser = null;
					  if(usersLen.length > 0){
						  for(int userLen : usersLen){
							  bUser = new byte[userLen];
							  inputStream.read(bUser);
							  if(bUser.length > 0){
								  sUser = new String(bUser);
								  users.add((User) JsonUtil.getObject4JsonString(sUser, User.class));
							  }
						  }
					  }
					  
					  /**
					   * Read Groups
					   */
					  byte[] bGroup = null;
					  String sGroup = null;
					  if(groupsLen.length > 0){
						  for(int groupLen : groupsLen){
							  bGroup = new byte[groupLen];
							  inputStream.read(bGroup);
							  if(bGroup.length > 0){
								  sGroup = new String(bGroup);
								  groups.add((DiscussGroup) JsonUtil.getObject4JsonString(sGroup, DiscussGroup.class));
							  }
						  }
					  }						  
					  PersistenceContext.getEntity().setUsers(users);
					  PersistenceContext.getEntity().setGroups(groups);
					  PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_SECCESS);
					  //点火刷新
					  ReceiveRefreshMessageEvent e = new ReceiveRefreshMessageEvent(new String());
					  e.setUsers(users);
					  e.setDiscussGroups(groups);
					  UserListFrame userListFrame = PersistenceContext.getEntity().getUserListFrame();
					  userListFrame.fireReceiveRefreshEvent(e);
					  
				 }else if(method.equals(Constants.CON_HANDER_METHOD_REGISTER)){
					     /**
					      * 验证是否注册成功
					      */
					     if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
					    	 PersistenceContext.getEntity().setRegisterStatus((Constants.CON_REGISTER_STATUS_SECCESS));
					     }	 else{
					    	 PersistenceContext.getEntity().setRegisterStatus((Constants.CON_REGISTER_STATUS_FAILTURE));
					     }
				 }else if(method.equals(Constants.CON_HANDER_METHOD_CREATE_GROUP)){
					 if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
				    	 PersistenceContext.getEntity().setCreateGroupStatus((Constants.CON_CREATE_GROUP_STATUS_SECCESS));
				     }else{
				    	 PersistenceContext.getEntity().setCreateGroupStatus((Constants.CON_CREATE_GROUP_STATUS_FAILTURE));
				     }
				 }else if(method.equals(Constants.CON_HANDER_METHOD_UPDATE_GROUP)){
					 if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
				    	 PersistenceContext.getEntity().setUpdateGroupStatus((Constants.CON_UPDATE_GROUP_STATUS_SECCESS));
				     }else{
				    	 PersistenceContext.getEntity().setUpdateGroupStatus((Constants.CON_UPDATE_GROUP_STATUS_FAILTURE));
				     }
				 }else if(method.equals(Constants.CON_HANDER_METHOD_DELETE_GROUP)){
					 if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
				    	 PersistenceContext.getEntity().setDeleteGroupStatus((Constants.CON_DELETE_GROUP_STATUS_SECCESS));
				     }else{
				    	 PersistenceContext.getEntity().setDeleteGroupStatus((Constants.CON_DELETE_GROUP_STATUS_FAILTURE));
				     }
				 }else if(method.equals(Constants.COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE)){
				     /**
				      * 验证是否留言成功
				      */
				     if(message.equals(Constants.CON_HANDER_VALIDATE_RESPONSE_OK)){
				    	 PersistenceContext.getEntity().setSaveLeaveMessageStatus((Constants.CON_SAVE_LEAVE_MESSAGTE_STATUS_SECCESS));
				     }	 else{
				    	 PersistenceContext.getEntity().setSaveLeaveMessageStatus((Constants.CON_SAVE_LEAVE_MESSAGTE_STATUS_FAILTURE));
				     }
			 }
			}

			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
	}
}
