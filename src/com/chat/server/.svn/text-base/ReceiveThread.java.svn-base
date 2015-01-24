package com.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;


import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;
import com.chat.util.SocketUtil;

/**
 * Receive File thread
 * 
 * @author pccw
 * 
 */
public class ReceiveThread extends Thread {
	private Socket socket;
	private String savePath;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private DataOutputStream fileOut;
	private Socket targeSocket;

	public ReceiveThread(Socket socket, String saveFile) {
		this.socket = socket;
		this.savePath = saveFile;
	}

	public void run() {
		try {

			inputStream = SocketUtil.getDataInputStream(socket);
			/**
			 * Accept Pact
			 */
			byte[] bytePact = new byte[Constants.CON_PACT_LENGTH];
			inputStream.read(bytePact);
			String strPact = new String(bytePact);
			System.out.println(strPact);
			Pact pact = (Pact) JsonUtil.getObject4JsonString(strPact,
					Pact.class);

			/**
			 * Transfers to client
			 */
			String targeIP = pact.getTargeIP();
			int targePort = pact.getTargePort();
			targeSocket = new Socket(targeIP, targePort);
			outputStream = SocketUtil.getDataOutputStream(targeSocket);

			/**
			 * Transfer pact
			 */
			outputStream.write(bytePact);
			outputStream.flush();

			/**
			 * get transfers type
			 */
			String pactType = pact.getTransfersType();
			System.out.println("Transfers Type=" + pact.getTransfersType());
			String fileName = pact.getFileName();

			if (pactType.equals(Constants.CON_TRANSFERS_TYPE_MESSAGE)) {
				/**
				 * Accept Message
				 */
				int messageLen = pact.getMessageLen();
				byte[] buf = new byte[messageLen];
				inputStream.read(buf);
				String message = new String(buf,"UTF-8");
				System.out.println("接收完成，消息为" + message.trim());
				/**
				 * Transfer Message
				 */
				outputStream.write(message.getBytes("UTF-8"));
				outputStream.flush();

			} else if (pactType.equals(Constants.CON_TRANSFERS_TYPE_FILE)) {
				/**
				 * Accept File
				 */
				int bufferSize = 1024;
				byte[] buf = new byte[bufferSize];

				/**
				 * Read fileName
				 */
				String path = savePath + fileName;
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				fileOut = SocketUtil.getDateOutputStream(path);
				System.out.println("开始接收文件!" + "\n");
				while (true) {
					int read = 0;
					if (inputStream != null) {
						read = inputStream.read(buf);
						
						if (read == -1) {
							break;
						}
						
						/**
						 * Transfer file
						 */
						outputStream.write(buf,0,read);
						
						/**
						 * Copy file in server
						 */
						fileOut.write(buf,0,read);
					}

				}
				outputStream.flush();
				fileOut.flush();
				System.out.println("接收完成文件");
			}else if(pactType.equals(Constants.CON_TRANSFERS_TYPE_VOICE)){
				//说明传过来的是语音信息				   
				   int bufferSize=16000;
				   try { 
					   byte[] buf = new byte[bufferSize];
			           while (true) {
							int read = 0;
							if (inputStream != null) {
								read = inputStream.read(buf);
								System.out.println("Read = "+read);
								if (read == -1) {
									break;
								}
								System.out.println("音频文件"+buf.toString());
								outputStream.write(buf,0,read);
								outputStream.flush();
							}
						}
				   } catch (Exception e1) {
				    e1.printStackTrace();
				   }
			}else if(pactType.equals(Constants.CON_TRANSFERS_TYPE_STOP_VOICE)){
				//说明用户想向主机发送取消语音消息请求
				
			}
		} catch (Exception e) {
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if(fileOut != null){
					fileOut.close();
				}
				if(targeSocket != null){
					targeSocket.close();
				}
				if(socket != null){
					socket.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
