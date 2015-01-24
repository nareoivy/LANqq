package com.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.LoginInfo;
import com.chat.bean.RegisterInfo;
import com.chat.bean.User;
import com.chat.common.Hander;
import com.chat.db.DBUtil;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;
import com.chat.util.SocketUtil;
import com.chat.util.StringUtil;

/**
 * Refresh Listener
 * .Login validate
 * .Refresh user list
 * .Refresh gourp list
 * 
 * @author pccw
 * 
 */
public class RefreshThread extends Thread {
	private Socket socket;
	
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	
	private Socket resSock;
    private DBUtil dbUtil;
	public RefreshThread(Socket socket,DBUtil dbUtil) {
		this.socket = socket;
		this.dbUtil = dbUtil;
	}

	public void run() {
		try {

			inputStream = SocketUtil.getDataInputStream(socket);
			/**
			 * Accept Hander
			 */
			byte[] byteHander = new byte[Constants.CON_HANDER_LENGTH];
			inputStream.read(byteHander);
			String strHander = new String(byteHander);
			System.out.println(strHander);
			System.out.println("刷新");
			Hander hander = (Hander) JsonUtil.getObject4JsonString(strHander,
					Hander.class);

			/**
			 * Parse Hander
			 */
			String type = hander.getType();
			String method = hander.getMethod();
			int mesLen = hander.getLength();
			
			/**
			 * 构建回复链接
			 */
			
			
			if(type.equals(Constants.CON_HANDER_TYPE_REQUEST)){
			   if(method.equals(Constants.CON_HANDER_METHOD_VALIDATE)){
                       /**
                        *  Receive Login Info(UserNo/Password)
                        */
				   		resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
				   		outputStream = SocketUtil.getDataOutputStream(resSock);
				      byte[] bloginInfo = new byte[mesLen];
				      inputStream.read(bloginInfo);				      
						String strLoginInfo = new String(bloginInfo);
						System.out.println(strLoginInfo);
						LoginInfo loginInfo = (LoginInfo) JsonUtil.getObject4JsonString(strLoginInfo,
								LoginInfo.class);
						String userNo =  loginInfo.getUserNo();
						String password = loginInfo.getPassword();
						User loginUser = dbUtil.getLoginUser(userNo, password);
						if(!StringUtil.isNull(loginUser)){
							/**
							 * Login Info is ok, update the user status is 'Y'
							 */
							List<User> onlineUsers = dbUtil.getOnlineUsers();
							
							dbUtil.setUserStatus(loginUser, "Y");
							
							/**
							 * LoginUser
							 */
							String strLoginUser = JsonUtil.getJsonString(loginUser);
							byte[] bLoginUser = strLoginUser.getBytes("UTF-8");
							
							/**
							 *  All user
							 */
							List<User> users = dbUtil.getUsers();
						
//							List<User> users = dbUtil.getFriends(loginUser);
							if(users == null){
								users = new ArrayList<User>();
							}
							int iUserLen = users.size();
							int[] iUsersLen = new int[iUserLen];
							byte[][] bbUser = new byte[iUserLen][];
							User tUser = null;
							String strUser = null;
							byte[] tbUser = null;
						    for(int i = 0;i < iUserLen; i++){
						    	tUser = users.get(i);
						    	strUser = JsonUtil.getJsonString(tUser);
						    	tbUser = strUser.getBytes("UTF-8");
						    	bbUser[i] = tbUser;
						    	iUsersLen[i] = tbUser.length;
						    }
						    
							/**
							 * All DiscussGroup
							 */
							List<DiscussGroup> groups = dbUtil.getGroupByCurUser(loginUser);
							if(null == groups){
								groups = new ArrayList<DiscussGroup>();
							}
							int iGroupLen = groups.size();
							int[] iGroupsLen = new int[iGroupLen];
							
							byte[][] bbGroup = new byte[iGroupLen][];
							DiscussGroup tGroup = null;
							String strGroup = null;
							byte[] tbGroup = null;
						    for(int i = 0;i < iGroupLen; i++){
						    	tGroup = groups.get(i);
						    	strGroup = JsonUtil.getJsonString(tGroup);
						    	tbGroup = strGroup.getBytes("UTF-8");
						    	bbGroup[i] = tbGroup;
						    	iGroupsLen[i] = tbGroup.length;
						    }
						    
							/**
							 * all leave message
							 */
						    List<LeaveMessage> leaveMessages = dbUtil.getLeaveMessages(userNo);
						    if(leaveMessages == null){
						    	leaveMessages = new ArrayList<LeaveMessage>();
						    }
						    int mLen = leaveMessages.size();
							int[] lmLen = new int[mLen];
							
							byte[][] bbLm = new byte[mLen][];
							LeaveMessage lm = null;
							String strmess = null;
							byte[] tbmesss = null;
						    for(int i = 0;i < mLen; i++){
						    	lm = leaveMessages.get(i);
						    	strmess = JsonUtil.getJsonString(lm);
						    	tbmesss = strmess.getBytes("UTF-8");
						    	bbLm[i] = tbmesss;
						    	lmLen[i] = tbmesss.length;
						    }
						    
						    /**
						     * init hander
						     */
							Hander resHander = new Hander();
							resHander.setMethod(Constants.CON_HANDER_METHOD_VALIDATE);
							resHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
							resHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
							resHander.setLength(bLoginUser.length);
							resHander.setUsersLen(iUsersLen);
							resHander.setGroupLen(iGroupsLen);
							resHander.setLeaveMesLen(lmLen);
							String strResHander = JsonUtil.getJsonString(resHander);							
							byte[] bResHander = strResHander.getBytes("UTF-8");
							byte[] copyResHander = new byte[Constants.CON_HANDER_LENGTH];
							for(int i = 0;i<bResHander.length;i++){
								copyResHander[i] = bResHander[i];
							}
							
							/**
							 * write Hander
							 */
							outputStream.write(copyResHander);
							outputStream.flush();
							
							/**
							 * write Login user
							 */
							outputStream.write(bLoginUser);
							outputStream.flush();
							
							/**
							 * write All users
							 */
							for(byte[]  bUser : bbUser){
								outputStream.write(bUser);
								outputStream.flush();
							}
		
							/**
							 * write All groups
							 */
							for(byte[]  bGroup : bbGroup){
								outputStream.write(bGroup);
								outputStream.flush();
							}
							
							/**
							 * write all leave messages
							 */
							for(byte[]  blm : bbLm){
								outputStream.write(blm);
								outputStream.flush();
							}
										
							/**
							 * 将leave_message 表的isRead字段设为Y
							 */
							for(int i=0;i<mLen;i++){
								dbUtil.setLeaveMessageIsRead(leaveMessages.get(i).getReceiver().getNo(), "Y");
							}
							
							sendMsgToUsers(onlineUsers);
							

						}else{
							/**
							 * Login Info is error
							 */
							Hander resHander = new Hander();
							resHander.setMethod(Constants.CON_HANDER_METHOD_VALIDATE);
							resHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
							resHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
							String strResHander = JsonUtil.getJsonString(resHander);
							
							byte[] bResHander = strResHander.getBytes("UTF-8");
							byte[] copyResHander = new byte[Constants.CON_HANDER_LENGTH];
							for(int i = 0;i<bResHander.length;i++){
								copyResHander[i] = bResHander[i];
							}
							/**
							 * write Hander
							 */
							outputStream.write(copyResHander);
							outputStream.flush();
						}
				   
			   }else if(method.equals(Constants.CON_HANDER_METHOD_REFRESH)){
				   resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
					outputStream = SocketUtil.getDataOutputStream(resSock);
				   byte[] userInfo = new byte[mesLen];
				   inputStream.read(userInfo);				      
				   String strUserInfo = new String(userInfo);
				   System.out.println(strUserInfo);
				   User curUser = (User) JsonUtil.getObject4JsonString(strUserInfo,User.class);
				   
				   
				   /**
					 *  All user
					 */
//					List<User> users = dbUtil.getFriends(curUser);
				   List<User> users = dbUtil.getUsers();
					
					int iUserLen = users.size();
					int[] iUsersLen = new int[iUserLen];
					byte[][] bbUser = new byte[iUserLen][];
					User tUser = null;
					String strUser = null;
					byte[] tbUser = null;
				    for(int i = 0;i < iUserLen; i++){
				    	tUser = users.get(i);
				    	strUser = JsonUtil.getJsonString(tUser);
				    	tbUser = strUser.getBytes("UTF-8");
				    	bbUser[i] = tbUser;
				    	iUsersLen[i] = tbUser.length;
				    }
				    
					/**
					 * All DiscussGroup
					 */
					List<DiscussGroup> groups = dbUtil.getGroupByCurUser(curUser);
					int iGroupLen = groups.size();
					int[] iGroupsLen = new int[iGroupLen];
					
					byte[][] bbGroup = new byte[iGroupLen][];
					DiscussGroup tGroup = null;
					String strGroup = null;
					byte[] tbGroup = null;
				    for(int i = 0;i < iGroupLen; i++){
				    	tGroup = groups.get(i);
				    	strGroup = JsonUtil.getJsonString(tGroup);
				    	tbGroup = strGroup.getBytes("UTF-8");
				    	bbGroup[i] = tbGroup;
				    	iGroupsLen[i] = tbGroup.length;
				    }
				    
					
				    /**
				     * init hander
				     */
					Hander resHander = new Hander();
					resHander.setMethod(Constants.CON_HANDER_METHOD_REFRESH);
					resHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					resHander.setUsersLen(iUsersLen);
					resHander.setGroupLen(iGroupsLen);
					String strResHander = JsonUtil.getJsonString(resHander);							
					byte[] bResHander = strResHander.getBytes("UTF-8");
					byte[] copyResHander = new byte[Constants.CON_HANDER_LENGTH];
					for(int i = 0;i<bResHander.length;i++){
						copyResHander[i] = bResHander[i];
					}
					
					/**
					 * write Hander
					 */
					outputStream.write(copyResHander);
					outputStream.flush();
					
					/**
					 * write All users
					 */
					for(byte[]  bUser : bbUser){
						outputStream.write(bUser);
						outputStream.flush();
					}

					/**
					 * write All groups
					 */
					for(byte[]  bGroup : bbGroup){
						outputStream.write(bGroup);
						outputStream.flush();
					}
				   
			   }else if(method.equals(Constants.CON_HANDER_METHOD_SETTING)){
				   /**
                    *  Receive Login Info(UserNo/Password)
                    */
			      byte[] bloginInfo = new byte[mesLen];
			      inputStream.read(bloginInfo);				      
					String strLoginInfo = new String(bloginInfo);
					System.out.println(strLoginInfo);
					LoginInfo loginInfo = (LoginInfo) JsonUtil.getObject4JsonString(strLoginInfo,
							LoginInfo.class);
					String userNo =  loginInfo.getUserNo();
					String password = loginInfo.getPassword();
					User loginUser = dbUtil.getLoginUser(userNo, password);
					if(!StringUtil.isNull(loginUser)){					
						dbUtil.setUserStatus(loginUser, "N");
						//把好友信息推送给在线的用户
						//取出在线好友信息
						List<User> users = dbUtil.getOnlineUsers();
						sendMsgToUsers(users);

					}	
			   }else if(method.equals(Constants.CON_HANDER_METHOD_REGISTER)){
				   resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
					outputStream = SocketUtil.getDataOutputStream(resSock);
				   /**
                    *  Receive Register Info to register a user
                    */
			        byte[] registerInfo = new byte[mesLen];
			        inputStream.read(registerInfo);				      
					String strRegisterInfo = new String(registerInfo);
					System.out.println(strRegisterInfo);
					RegisterInfo registeInfo = (RegisterInfo) JsonUtil.getObject4JsonString(strRegisterInfo,
							RegisterInfo.class);
					String userNo =  registeInfo.getUserno();
					String password = registeInfo.getPassword();
					String ip = registeInfo.getIpAdress();
					byte[] userImage = registeInfo.getUserImage();
					String userName = registeInfo.getName();
					boolean flag = dbUtil.saveUser(userName, userNo, password, userImage, ip);
					
					if(flag){  //注册成功
						
						Hander registerHander = new Hander();
						registerHander.setMethod(Constants.CON_HANDER_METHOD_REGISTER);
						registerHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
						registerHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
						registerHander.setLength(registerInfo.length);
						String strRegisHander = JsonUtil.getJsonString(registerHander);							
						byte[] bRegisHander = strRegisHander.getBytes("UTF-8");
						byte[] copyRegisHander = new byte[Constants.CON_HANDER_LENGTH];
						for(int i = 0;i<bRegisHander.length;i++){
							copyRegisHander[i] = bRegisHander[i];
						}					
						/**
						 * write register Hander to client
						 */
						outputStream.write(copyRegisHander);
						outputStream.flush();
						
					}else{//注册失败
						Hander registerHander = new Hander();
						registerHander.setMethod(Constants.CON_HANDER_METHOD_REGISTER);
						registerHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
						registerHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
						registerHander.setLength(registerInfo.length);
						String strRegisHander = JsonUtil.getJsonString(registerHander);							
						byte[] bRegisHander = strRegisHander.getBytes("UTF-8");
						byte[] copyRegisHander = new byte[Constants.CON_HANDER_LENGTH];
						for(int i = 0;i<bRegisHander.length;i++){
							copyRegisHander[i] = bRegisHander[i];
						}						
						outputStream.write(copyRegisHander);
						outputStream.flush();
					} 
			   }else if(method.equals(Constants.CON_HANDER_METHOD_CREATE_GROUP)){
				   resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
					outputStream = SocketUtil.getDataOutputStream(resSock);
				   byte[] groupInfo = new byte[mesLen];
				   inputStream.read(groupInfo);
				   String strGroupInfo = new String(groupInfo);
				   DiscussGroup discussGroup = (DiscussGroup) JsonUtil.getObject4JsonString(strGroupInfo,
						   DiscussGroup.class);
				   boolean flag = dbUtil.createDiscussGroup(discussGroup);
				   if(flag){
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_CREATE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
					   createGroupHander.setLength(groupInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
						
						//把消息推送给该群的好友
						User curUser = dbUtil.getUserByNo(discussGroup.getGroupMaster());
						List<User> users = dbUtil.getUsersByDiscussGroup(discussGroup, curUser);
						for(int i = 0; i < users.size();){
							if(users.get(i).getStatus().toUpperCase().equals("N")){
								users.remove(i);
							}else{
								i++;
							}
						}
						sendMsgToUsers(users);
						
				   }else{
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_CREATE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
					   createGroupHander.setLength(groupInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
				   }
			   }else if(method.equals(Constants.CON_HANDER_METHOD_UPDATE_GROUP)){
				   resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
					outputStream = SocketUtil.getDataOutputStream(resSock);
				   byte[] groupInfo = new byte[mesLen];
				   inputStream.read(groupInfo);
				   String strGroupInfo = new String(groupInfo);
				   
				   DiscussGroup discussGroup = (DiscussGroup) JsonUtil.getObject4JsonString(strGroupInfo,
						   DiscussGroup.class);
				   DiscussGroup oldDiscussGroup = dbUtil.getDiscussGroupByGroupName(discussGroup.getDiscussGroupName());
					  
				   User curUser = dbUtil.getUserByNo(discussGroup.getGroupMaster());
				   List<User> nowUsers = dbUtil.getUsersByDiscussGroup(discussGroup, curUser);
				   List<User> oldUsers = dbUtil.getUsersByDiscussGroup(oldDiscussGroup, curUser);
				   List<User> users = new ArrayList<User>();
				   
				   users.add(curUser);
				   if(nowUsers.size() >= oldUsers.size()){
					   for(int i = 0; i < nowUsers.size(); i++){
						   users.add(nowUsers.get(i));
					   }
					   
					   int j = 0;
					   for(int i = 0; i < nowUsers.size(); i++){
						   for(; j < oldUsers.size();j++){
							   if(nowUsers.get(i).getNo().equals(oldUsers.get(j).getNo())){
								   break;
							   }
							   if(j == oldUsers.size()-1){
								   users.add(oldUsers.get(j));
							   }
						   }
					   }
				   }else{
					   for(int i = 0; i < oldUsers.size(); i++){
						   users.add(oldUsers.get(i));
					   }
					   
					   int j = 0;
					   for(int i = 0; i < oldUsers.size(); i++){
						   for(; j < nowUsers.size();j++){
							   if(oldUsers.get(i).getNo().equals(nowUsers.get(j).getNo())){
								   break;
							   }
							   if(j == nowUsers.size()-1){
								   users.add(nowUsers.get(j));
							   }
						   }
					   }
				   }
				  
				   
				   
				   
				   for(int i = 0; i < users.size();){
						if(users.get(i).getStatus().toUpperCase().equals("N")){
							users.remove(i);
						}else{
							i++;
						}
					}
				   
				   
				   
				   boolean flag = dbUtil.updateDiscussGroup(discussGroup);
				   if(flag){
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_UPDATE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
					   createGroupHander.setLength(groupInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
						
						sendMsgToUsers(users);
				   }else{
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_UPDATE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
					   createGroupHander.setLength(groupInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
				   }
			   }else if(method.equals(Constants.CON_HANDER_METHOD_DELETE_GROUP)){
				   resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
					outputStream = SocketUtil.getDataOutputStream(resSock);
				   byte[] groupNameInfo = new byte[mesLen];
				   inputStream.read(groupNameInfo);
				   String strGroupName = new String(groupNameInfo);
				   DiscussGroup discussGroup = dbUtil.getDiscussGroupByGroupName(strGroupName);
				   
				   boolean flag = dbUtil.dropGroup(strGroupName);
				   if(flag){
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_DELETE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
					   createGroupHander.setLength(groupNameInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
						
						//推送给好友
						List<User> users = dbUtil.getUsersByDiscussGroup(discussGroup, dbUtil.getUserByNo(discussGroup.getGroupMaster()));
						for(int i = 0; i < users.size();){
							if(users.get(i).getStatus().toUpperCase().equals("N")){
								users.remove(i);
							}else{
								i++;
							}
						}
						sendMsgToUsers(users);
						
				   }else{
					   Hander createGroupHander = new Hander();
					   createGroupHander.setMethod(Constants.CON_HANDER_METHOD_DELETE_GROUP);
					   createGroupHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
					   createGroupHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
					   createGroupHander.setLength(groupNameInfo.length);
					   String strCreateGroupHander = JsonUtil.getJsonString(createGroupHander);
					   byte[] bCreateGroupHander = strCreateGroupHander.getBytes("UTF-8");
					   byte[] copyCreateGroupHander = new byte[Constants.CON_HANDER_LENGTH];
					   for(int i = 0;i<bCreateGroupHander.length;i++){
						   copyCreateGroupHander[i] = bCreateGroupHander[i];
						}					
						/**
						 * write Hander to client
						 */
						outputStream.write(copyCreateGroupHander);
						outputStream.flush();
				   }
			   }else if(method.equals(Constants.COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE)){
				    resSock = new Socket(socket.getInetAddress(),Constants.CON_CLIENT_REFRESH_PORT);
			   		outputStream = SocketUtil.getDataOutputStream(resSock);
			        byte[] leaveMess = new byte[mesLen];
			        inputStream.read(leaveMess);				      
					String strleaveMessage = new String(leaveMess);
					System.out.println(strleaveMessage);
					LeaveMessage leaveMessage = (LeaveMessage) JsonUtil.getObject4JsonString(strleaveMessage,LeaveMessage.class);
					boolean flag = dbUtil.saveLeaveMessage(leaveMessage);

					if(flag){  //留言成功
						
						Hander saveMsgHander = new Hander();
						saveMsgHander.setMethod(Constants.COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE);
						saveMsgHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
						saveMsgHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_OK);
						saveMsgHander.setLength(leaveMess.length);
						String strSaveMsgHander = JsonUtil.getJsonString(saveMsgHander);							
						byte[] bSaveMsgHander = strSaveMsgHander.getBytes("UTF-8");
						byte[] copySaveMsgHander = new byte[Constants.CON_HANDER_LENGTH];
						for(int i = 0;i<bSaveMsgHander.length;i++){
							copySaveMsgHander[i] = bSaveMsgHander[i];
						}					
						/**
						 * write save leave message Hander to client
						 */
						outputStream.write(copySaveMsgHander);
						outputStream.flush();
						
					}else{//留言失败
						Hander saveMsgHander = new Hander();
						saveMsgHander.setMethod(Constants.COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE);
						saveMsgHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
						saveMsgHander.setMessage(Constants.CON_HANDER_VALIDATE_RESPONSE_ERR);
						saveMsgHander.setLength(leaveMess.length);
						String strSaveMsgHander = JsonUtil.getJsonString(saveMsgHander);							
						byte[] bSaveMsgHander = strSaveMsgHander.getBytes("UTF-8");
						byte[] copySaveMsgHander = new byte[Constants.CON_HANDER_LENGTH];
						for(int i = 0;i<bSaveMsgHander.length;i++){
							copySaveMsgHander[i] = bSaveMsgHander[i];
						}						
						outputStream.write(copySaveMsgHander);
						outputStream.flush();
					} 					
			   }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if(resSock != null){
					resSock.close();
				}
				if(socket != null){
					socket.close();
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendMsgToUsers(List<User> users){
		Socket resSock = null;
		DataOutputStream outputStream = null;
		
		try{
			List<User> allUsers = dbUtil.getUsers();
			
			int iUserLen = allUsers.size();
			int[] iUsersLen = new int[iUserLen];
			byte[][] bbUser = new byte[iUserLen][];
			User tUser = null;
			String strUser = null;
			byte[] tbUser = null;
		    for(int i = 0;i < iUserLen; i++){
		    	tUser = allUsers.get(i);
		    	strUser = JsonUtil.getJsonString(tUser);
		    	tbUser = strUser.getBytes("UTF-8");
		    	bbUser[i] = tbUser;
		    	iUsersLen[i] = tbUser.length;
		    }
		    
		    for(int i = 0; i < users.size(); i++){
				
				/**
				 * 相应User的 DiscussGroup信息
				 */
				List<DiscussGroup> groups = dbUtil.getGroupByCurUser(users.get(i));
				int iGroupLen = groups.size();
				int[] iGroupsLen = new int[iGroupLen];
				
				byte[][] bbGroup = new byte[iGroupLen][];
				DiscussGroup tGroup = null;
				String strGroup = null;
				byte[] tbGroup = null;
			    for(int j = 0;j < iGroupLen; j++){
			    	tGroup = groups.get(j);
			    	strGroup = JsonUtil.getJsonString(tGroup);
			    	tbGroup = strGroup.getBytes("UTF-8");
			    	bbGroup[j] = tbGroup;
			    	iGroupsLen[j] = tbGroup.length;
			    }
			    resSock= new Socket(users.get(i).getIp(),users.get(i).getPort()-1);
			    outputStream = SocketUtil.getDataOutputStream(resSock);
				
				/**
			     * init hander
			     */
				Hander resHander = new Hander();
				resHander.setMethod(Constants.CON_HANDER_METHOD_REFRESH);
				resHander.setType(Constants.CON_HANDER_TYPE_RESPONSE);
				resHander.setUsersLen(iUsersLen);
				resHander.setGroupLen(iGroupsLen);
				String strResHander = JsonUtil.getJsonString(resHander);							
				byte[] bResHander = strResHander.getBytes("UTF-8");
				byte[] copyResHander = new byte[Constants.CON_HANDER_LENGTH];
				for(int j = 0;j<bResHander.length;j++){
					copyResHander[j] = bResHander[j];
				}
				
				/**
				 * write Hander
				 */
				outputStream.write(copyResHander);
				outputStream.flush();
				
				/**
				 * write All users
				 */
				for(byte[]  bUser : bbUser){
					outputStream.write(bUser);
					outputStream.flush();
				}

				/**
				 * write groups
				 */
				for(byte[]  bGroup : bbGroup){
					outputStream.write(bGroup);
					outputStream.flush();
				}
			}
		}catch(Exception e){
//			e.printStackTrace();
		}finally{			
				try {
					if(null != resSock){
					resSock.close();
					}
					if(null != outputStream){
						outputStream.close();
					}	
				} catch (IOException e) {
					e.printStackTrace();
				}								
		}
		
	}

}
