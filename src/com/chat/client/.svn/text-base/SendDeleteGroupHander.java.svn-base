package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.chat.common.Hander;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendDeleteGroupHander {
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private String groupName;
	private Hander hander;
	
	public SendDeleteGroupHander(String ip,int port,String groupName,Hander hander){
		this.ip = ip;
		this.port = port;
		this.groupName = groupName;
		this.hander = hander;
	}
	
	private boolean createConnection() throws Exception 
	{
		cs = new ClientSocket(ip, port);
		cs.CreateConnection();
		System.out.print("连接服务器成功!" + "\n");
		return true;
	}
	
	private boolean sendDeleteGroupHander()
	{
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			
			/**
			 * groupName Info
			 */
			byte[] buf = groupName.getBytes("UTF-8");
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
	
	public void sendDeleteGroupRequest(){
		try {
			if (createConnection())
			{
				if (sendDeleteGroupHander())
				{
					cs.shutDownConnection();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
