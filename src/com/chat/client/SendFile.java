package com.chat.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendFile
{
	private ClientSocket cs = null;

	private String ip;// 设置成服务器IP
	private int port;
	private String filePath;
	private Pact pact;
	private DataInputStream fis;
	private DataOutputStream ps;
	

	public SendFile(String ip, int port, String filePath,Pact pact) throws Exception
	{
		this.ip = ip;
		this.port = port;
		this.filePath = filePath;
		this.pact = pact;
		
		if (createConnection())
		{
			
			
			if (sendFile())
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

	private boolean sendFile()
	{
		if (cs == null)
			return false;
		
		
		/**
		 * Hand prepare
		 */
		

		File fi = new File(filePath);
		String fileName = fi.getName();
		this.pact.setFileName(fileName);

		try
		{
			long fileLen = (new File(filePath)).length();
			fis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(filePath)));

		    ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
			pact.setFileLen(fileLen);
			/**
			 * Pact -> String
			 */
			String strPact = JsonUtil.getJsonString(pact);
			byte[] bytePact = new byte[Constants.CON_PACT_LENGTH];
			bytePact = strPact.getBytes();
			System.out.println(bytePact.length);
            /**
             * write pact			
             */
			ps.write(bytePact);
			ps.flush();
			
			/**
			 * write file by bytes
			 */
			int bufferSize = 1024;
			byte[] buf = new byte[bufferSize];

			while (true)
			{
				int read = 0;
				if (fis != null)
				{
					read = fis.read(buf);
				}

				if (read == -1)
				{
					break;
				}
				ps.write(buf, 0, read);
			}
			ps.flush();
			System.out.println("文件传输完成");
			//JOptionPane.showMessageDialog(null, "对方已成功接受您的文件!");
			return true;
		} catch (IOException e)
		{
			return false;
		}finally{
			try {
				if(ps != null){
					ps.close();
				}
				if(fis != null){
					fis.close();
				}			
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}