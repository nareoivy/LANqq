package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.chat.bean.RegisterInfo;
import com.chat.common.Hander;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendRegisterHander {
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private RegisterInfo registerInfo;
	private Hander hander;
	
	public SendRegisterHander(String ip,int port,RegisterInfo registerInfo,Hander hander){
		this.ip = ip;
		this.port = port;
		this.registerInfo = registerInfo;
		this.hander = hander;
	}
	
	private boolean createConnection() throws Exception 
	{
		cs = new ClientSocket(ip, port);
		cs.CreateConnection();
		System.out.print("连接服务器成功!准备注册用户！" + "\n");
		return true;
	}
	
	private boolean sendRegisterHander()
	{
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			
			/**
			 * User Info
			 */
			String registerInfo = JsonUtil.getJsonString(this.registerInfo);
			byte[] buf = registerInfo.getBytes("UTF-8");
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
			System.out.println("Write Hander of register request");
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void sendRegisterRequest(){
		try {
			if (createConnection())
			{
				if (sendRegisterHander())
				{
					cs.shutDownConnection();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
