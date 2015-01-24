package com.chat.util;

public class Constants {
     
	public final static int CON_SERVER_ACCEPT_PORT = 9001;
	public final static int CON_CLIENT_ACCEPT_PORT = 7001;
	public final static int CON_CLIENT_REFRESH_PORT = 7000;
	public final static String CON_SERVER_ADDRESS = "192.168.3.51";
	public final static String CON_SERVER_FILE_SAVEPATH = "C:\\receive\\server\\";
	public final static String CON_CLIENT_FILE_SAVEPATH = "C:\\receive\\client\\";
	
	public final static String CON_TRANSFERS_TYPE_FILE = "FILE";
	public final static String CON_TRANSFERS_TYPE_MESSAGE = "MESSAGE";
	public final static String CON_TRANSFERS_TYPE_VOICE = "VOICE";
	public final static String CON_TRANSFERS_TYPE_STOP_VOICE = "STOP_VOICE";
	
	
	public final static int CON_PACT_LENGTH = 500;
	public final static int CON_MESSAGE_LENGTH = 1024;
	
	/**
	 * Hander
	 * Login
	 */
	public final static int CON_HANDER_LENGTH = 500;
	
	/**
	 * Hander Method
	 */
	public final static String CON_HANDER_METHOD_VALIDATE = "validate";
	public final static String CON_HANDER_METHOD_REFRESH = "refresh";
	public final static String CON_HANDER_METHOD_SETTING = "setting";
	public final static String CON_HANDER_METHOD_REGISTER = "register";
	public final static String CON_HANDER_METHOD_CREATE_GROUP = "createGroup";
	public final static String CON_HANDER_METHOD_UPDATE_GROUP = "updateGroup";
	public final static String CON_HANDER_METHOD_DELETE_GROUP = "deleteGroup";
	public final static String CON_HANDER_METHOD_EXIT_GROUP = "exitGroup";
	public final static String COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE = "saveLeaveMessage";
	
	public final static String CON_HANDER_VALIDATE_RESPONSE_OK = "ok";
	public final static String CON_HANDER_VALIDATE_RESPONSE_ERR = "err";
	
	/**
	 * Operation type
	 */
	public final static String CON_HANDER_OPERATION_OFFLINE ="offline";
	
	/**
	 * Hander Type
	 */
	public final static String CON_HANDER_TYPE_REQUEST = "request";
	public final static String CON_HANDER_TYPE_RESPONSE = "response";
	public final static String CON_HANDER_MES_REQUEST ="requestValidate";
	
	/**
	 * Login Status
	 */
	public final static int CON_LOGIN_STATUS_UNLOGINED =0;
	public final static int CON_LOGIN_STATUS_SECCESS = 1;
	public final static int CON_LOGIN_STATUS_FAILTURE =-1;
	/**
	 * Register Status
	 */
	public final static int CON_REGISTER_STATUS_SECCESS = 1;
	public final static int CON_REGISTER_STATUS_FAILTURE =-1;
	/**
	 * Create group Status
	 */
	public final static int CON_CREATE_GROUP_STATUS_UNLOGINED =0;
	public final static int CON_CREATE_GROUP_STATUS_SECCESS = 1;
	public final static int CON_CREATE_GROUP_STATUS_FAILTURE =-1;
	/**
	 * Update group Status
	 */
	public final static int CON_UPDATE_GROUP_STATUS_UNLOGINED =0;
	public final static int CON_UPDATE_GROUP_STATUS_SECCESS = 1;
	public final static int CON_UPDATE_GROUP_STATUS_FAILTURE =-1;
	/**
	 * Delete group Status
	 */
	public final static int CON_DELETE_GROUP_STATUS_UNLOGINED =0;
	public final static int CON_DELETE_GROUP_STATUS_SECCESS = 1;
	public final static int CON_DELETE_GROUP_STATUS_FAILTURE =-1;
	/**
	 * save leave message status
	 */
	public final static int CON_SAVE_LEAVE_MESSAGTE_STATUS_SECCESS = 1;
	public final static int CON_SAVE_LEAVE_MESSAGTE_STATUS_FAILTURE =-1;
	
	public final static String CON_CUR_USERNAME="80273816";
	public final static String CON_CUR_PASSWORD="1";
	
	public final static int CON_SCREEM_WIDTH = 610;
	public final static int CON_SCREEM_HEIGHT = 500;
	
	public final static int CON_CHAT_TYPE_MESSAGE = 0;
	public final static int CON_CHAT_TYPE_GROUP = 1;
	public final static int CON_CHAT_TYPE_HAND = 3;
	public final static int CON_CHAT_TYPE_LOGIN = 4;
	public final static int CON_CHAT_TYPE_VOICE = 5;
	
	public final static String CON_CHAT_HAND_PREPARE = "isReceiveFile";
	public final static String CON_CHAT_HAND_Y = "y";
	public final static String CON_CHAT_HAND_N = "n";
	
	public final static String CON_CHAT_HAND_CHAT_VOICE = "isChatVoice";
	public final static String CON_CHAT_HAND_CHAT_VOICE_Y = "yes";
	public final static String CON_CHAT_HAND_CHAT_VOICE_N = "no";
	public final static String CON_CHAT_HAND_CHAT_VOICE_CANCEL = "autoCancel";
	
	public final static int CON_CHAT_CHAT_VOICE_DEFAULT = 0;
	public final static int CON_CHAT_CHAT_VOICE_Y = 1;
	public final static int CON_CHAT_CHAT_VOICE_N = -1;
	
	public final static int CON_CHAT_PREPARE_DEFAULT = 0;
	public final static int CON_CHAT_PREPARE_Y = 1;
	public final static int CON_CHAT_PREPARE_N = -1;
		
	/**
	 * Tips
	 */
	public final static String CON_ERR_UNORPW = "用户名或密码错误";
	public final static String CON_ERR_UNCONNECTED = "连接服务器失败！";
	public final static String CON_TIPS_REGISTER_SUCCESS = "恭喜你,注册成功，赶快登陆试试吧~";
	public final static String CON_TIPS_REGISTER_PAS_ERR = "两次密码输入不一致 ,请再检查一次!";
	public final static String CON_TIPS_REGISTER_PAS_TOOLONG = "亲~密码长度不能大于10个字符!";
	public final static String CON_TIPS_REGISTER_IP_ERR="您的IP地址格式错误！";
	public final static String CON_TIPS_REGISTER_NULL ="亲~以上每项都需要填写哦~";	
	public final static String CON_TIPS_REGISTER_FAILTURE = "可惜了,注册失败，再来一次吧~";
	public final static String CON_TIPS_SAVE_LEAVE_MESSAGE_SUCCESS = "留言成功!";
	public final static String CON_TIPS_SAVE_LEAVE_MESSAGE_FAILTURE = "未知原因留言失败!";

}
