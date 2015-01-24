/**
 * 
 */
package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

/**
 * @author pccw
 *
 */
public class SendStopVoiceMessage {
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private Pact pact;
	
	public SendStopVoiceMessage(String ip, int port,Pact pact) throws Exception
	{
		this.ip = ip;
		this.port = port;
		this.pact = pact;
		if (createConnection())
		{
			if (sendStopMessage())
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
	
	private boolean sendStopMessage()
	{
		if (cs == null)
			return false;

		try
		{
			DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
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
			System.out.println("消息传输完成");
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
