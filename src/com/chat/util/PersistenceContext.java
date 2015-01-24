package com.chat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.User;
import com.chat.frame.UserListFrame;

public class PersistenceContext {
	
	public int loginStatus;
	public int registerStatus;
	public int createGroupStatus;
	public int updateGroupStatus;
	public int deleteGroupStatus;
	public int saveLeaveMessageStatus;

	public  User curUser;
	public  String serverIP;
	public  int serverPort;
	public  List<User> users;
	public  String clientIP;
	public  List<DiscussGroup> groups;
	public List<LeaveMessage> leaveMessages;
	public Map<String,User> hpUsers = new HashMap<String,User>();
	private UserListFrame userListFrame;
	
	
	public Map<String, User> getHpUsers() {
		return hpUsers;
	}

	public void setHpUsers(Map<String, User> hpUsers) {
		this.hpUsers = hpUsers;
	}
	private static final PersistenceContext _this = new PersistenceContext();
	
	public static PersistenceContext getEntity(){
		return _this;
	}
	
	public  List<DiscussGroup> getGroups() {
		return groups;
	}
	public  void setGroups(List<DiscussGroup> groups) {
		this.groups = groups;
	}
	public  int getLoginStatus() {
		return loginStatus;
	}
	public  void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public  String getClientIP() {
		return clientIP;
	}
	public  void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public  int getClientPort() {
		return clientPort;
	}
	public  void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
	public  int clientPort;

	public  List<User> getUsers() {
		return users;
	}
	public  void setUsers(List<User> users) {
		
		this.users = users;
		/**
		 * clear and put the new elements
		 */
		hpUsers.clear();
		if(!StringUtil.isNull(users)){
			String strKey = "";
			for(User user:users){
				strKey = user.getIp() + "@" + user.getPort();
				this.hpUsers.put(strKey, user);
			}
		}
	}
	
	public  List<User> getCopyUsers() {
		List<User> copyUsers = new ArrayList<User>();
		if(!StringUtil.isNull(users)){
			for(User user:users){
				copyUsers.add(user);
			}			
		}
		return copyUsers;
	}
	
	public  User getCurUser() {
		return curUser;
	}
	public  void setCurUser(User curUser) {
		this.curUser = curUser;
	}
	public  String getServerIP() {
		return serverIP;
	}
	public  void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public  int getServerPort() {
		return serverPort;
	}
	public  void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(int registerStatus) {
		this.registerStatus = registerStatus;
	}

	public int getCreateGroupStatus() {
		return createGroupStatus;
	}

	public void setCreateGroupStatus(int createGroupStatus) {
		this.createGroupStatus = createGroupStatus;
	}

	public int getUpdateGroupStatus() {
		return updateGroupStatus;
	}

	public void setUpdateGroupStatus(int updateGroupStatus) {
		this.updateGroupStatus = updateGroupStatus;
	}

	public int getDeleteGroupStatus() {
		return deleteGroupStatus;
	}

	public void setDeleteGroupStatus(int deleteGroupStatus) {
		this.deleteGroupStatus = deleteGroupStatus;
	}
	
	public User getUserByIPAndPort(String ip,int port){
		if(!hpUsers.isEmpty()){
			String strKey = ip + "@" + port;
			return hpUsers.get(strKey);
		}
		return null;
	}

	public UserListFrame getUserListFrame() {
		return userListFrame;
	}

	public void setUserListFrame(UserListFrame userListFrame) {
		this.userListFrame = userListFrame;
	}

	public List<LeaveMessage> getLeaveMessages() {
		return leaveMessages;
	}

	public void setLeaveMessages(List<LeaveMessage> leaveMessages) {
		this.leaveMessages = leaveMessages;
	}

	public int getSaveLeaveMessageStatus() {
		return saveLeaveMessageStatus;
	}

	public void setSaveLeaveMessageStatus(int saveLeaveMessageStatus) {
		this.saveLeaveMessageStatus = saveLeaveMessageStatus;
	}
	
	
}
