package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.chat.bean.LoginInfo;
import com.chat.common.Hander;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendHander
{
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private LoginInfo loginInfo;
	private Hander hander;

	public SendHander(String ip, int port, LoginInfo loginInfo,Hander hander) throws Exception
	{
		this.ip = ip;
		this.port = port;
		this.loginInfo = loginInfo;
		this.hander = hander;
	}

	private boolean createConnection() throws Exception 
	{
		cs = new ClientSocket(ip, port);
		cs.CreateConnection();
		System.out.print("连接服务器成功!" + "\n");
		return true;
	}

	/**
	 * Send a request(hander/userNo/pw) to server to validate
	 */
	public void sendLoginRequest(){
		try {
			if (createConnection())
			{
				if (sendLoginValidateHander())
				{
					cs.shutDownConnection();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * send a request body
	 * @return
	 */
	private boolean sendLoginValidateHander()
	{
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			
			/**
			 * Login Info 
			 */
			String strLoginInfo = JsonUtil.getJsonString(this.loginInfo);
			byte[] buf = strLoginInfo.getBytes("UTF-8");
			hander.setLength(buf.length);
			
			/**
			 * hander -> String
			 */
			String strHander = JsonUtil.getJsonString(hander);
			byte[] copybyteHander = new byte[Constants.CON_HANDER_LENGTH];
			byte[] byteHander = strHander.getBytes();
			System.out.println(byteHander.length);
			
			for(int i = 0;i<byteHander.length;i++){
				copybyteHander[i] = byteHander[i];
			}
			
            /**
             * write hander			
             */
			ps.write(copybyteHander);
			ps.flush();
			
			/**
			 * write LoginInfo
			 */
			ps.write(buf);
			ps.flush();
			System.out.println("Write Hander of login validate request");
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * send a hander to server
	 */
	public void sendHander(){
		try {
			if (createConnection())
			{
				if (sendHanderBody())
				{
					cs.shutDownConnection();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * send a hander body to server 
	 */
	private boolean sendHanderBody(){
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			/**
			 * hander -> String
			 */
			String strHander = JsonUtil.getJsonString(hander);
			byte[] copybyteHander = new byte[Constants.CON_HANDER_LENGTH];
			byte[] byteHander = strHander.getBytes();
			System.out.println(byteHander.length);
			
			for(int i = 0;i<byteHander.length;i++){
				copybyteHander[i] = byteHander[i];
			}
			
            /**
             * write hander			
             */
			ps.write(copybyteHander);
			ps.flush();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
}