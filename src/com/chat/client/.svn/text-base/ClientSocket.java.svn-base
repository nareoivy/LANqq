package com.chat.client;
import java.net.*;
import java.io.*;

public class ClientSocket {
	private String ip;

	private int port;

	private Socket socket = null;

	DataOutputStream out = null;

	DataInputStream getMessageStream = null;

	public ClientSocket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/** */
	/**
	 * 创建socket连接
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 * @throws Exception
	 *             exception
	 */
	public void CreateConnection() throws UnknownHostException, IOException{
			socket = new Socket(ip, port);
	}

	public void shutDownConnection() {
		try {
			if (out != null)
				out.close();
			if (getMessageStream != null)
				getMessageStream.close();
			if (socket != null)
				socket.close();
			
		} catch (Exception e) {

		}
	}
	
	/**
	 * getInputStream
	 * @return
	 */
	public InputStream getInputStream(){
		try {
			return socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * getOutputStream
	 * @return
	 */
	public OutputStream getOutputStream(){
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close(){
		try {
			if(socket != null){
				 socket.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	 
}
