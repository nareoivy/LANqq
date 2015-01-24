package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendMessage
{
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private String message;
	private Pact pact;

	public SendMessage(String ip, int port, String message,Pact pact) throws Exception
	{
		this.ip = ip;
		this.port = port;
		this.message = message;
		this.pact = pact;
		if (createConnection())
		{
			if (sendMessage())
			{
				cs.shutDownConnection();
			}
		}
	}

	private boolean createConnection() throws Exception 
	{
		cs = new ClientSocket(ip, port);
		cs.CreateConnection();
		System.out.print("连接服务器成功!" + "\n");
		return true;
	}

	private boolean sendMessage()
	{
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			
			byte[] buf = message.getBytes("UTF-8");
			pact.setMessageLen(buf.length);
			/**
			 * Pact -> String
			 */
			String strPact = JsonUtil.getJsonString(pact);
			byte[] copybytePact = new byte[Constants.CON_PACT_LENGTH];
			byte[] bytePact = strPact.getBytes();
			System.out.println(bytePact.length);
			
			for(int i = 0;i<bytePact.length;i++){
				copybytePact[i] = bytePact[i];
			}
			
            /**
             * write pact			
             */
			ps.write(copybytePact);
			ps.flush();
			/**
			 * write message
			 */
			ps.write(buf);
			ps.flush();
			System.out.println("消息传输完成");
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}