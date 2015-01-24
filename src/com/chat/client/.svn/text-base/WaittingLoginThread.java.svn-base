package com.chat.client;
import java.util.List;

import javax.swing.SwingUtilities;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LoginInfo;
import com.chat.bean.User;
import com.chat.frame.UserListFrame;
import com.chat.frame.WaittingLogin;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.SocketUtil;

public class WaittingLoginThread extends Thread {
	private LoginInfo loginInfo;
	private WaittingLogin wl;

	public WaittingLoginThread(String userNo, String passWord, WaittingLogin wl) {
		loginInfo = new LoginInfo();
		loginInfo.setPassword(passWord);
		loginInfo.setUserNo(userNo);
		this.wl = wl;
	}

	public void run() {
		try {
			/**
			 * Validate whether can connect the server by IP and Port
			 */
			boolean isConnected = SocketUtil.testConnection(wl.getIp(),
					wl.getPort());
			if (isConnected) {
				/**
				 * Validate userInfo by userNo and password
				 * Refresh Server
				 * IP : serverIP
				 * Port: serverIP -1
				 */
				DataUtil.sendRequestValidateUserInfo(wl.getIp(),wl.getPort()-1,loginInfo);
				Thread.sleep(1000);
				while(true){
					/**
					 * Wait the result from Server;
					 */
					if(PersistenceContext.getEntity().getLoginStatus() == Constants.CON_LOGIN_STATUS_SECCESS){
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									/**
									 * 设置配置端口
									 */
									wl.dispose();
									PersistenceContext.getEntity().setServerPort(wl.getPort());
									PersistenceContext.getEntity().setServerIP(wl.getIp());
									User curUser = PersistenceContext.getEntity().getCurUser();
									List<User> allUsers = PersistenceContext.getEntity().getUsers();
									List<DiscussGroup> allGroups = PersistenceContext.getEntity().getGroups(); 
									new UserListFrame(curUser,allUsers,allGroups);
								}
							});
							PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_UNLOGINED);
							break;
						} else  if(PersistenceContext.getEntity().getLoginStatus() == Constants.CON_LOGIN_STATUS_FAILTURE){
							PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_UNLOGINED);
							DataUtil.LoginErrorView(wl, Constants.CON_ERR_UNORPW);
							break;
						}
				}

			} else {
				PersistenceContext.getEntity().setLoginStatus(Constants.CON_LOGIN_STATUS_UNLOGINED);
				DataUtil.LoginErrorView(wl, Constants.CON_ERR_UNCONNECTED);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
