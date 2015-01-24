package com.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import com.chat.db.DBUtil;
/**
 * 
 * @author PCCW
 * Listener the socket come from the  client
 * port 9000
 *
 */
public class RefreshListener extends Thread
{
	private int port;
	private ServerSocket serverSocket;

    private DBUtil dbUtil;

	public RefreshListener(int port)
	{
		this.port = port;
		dbUtil = new DBUtil();
	}

	public void run()
	{
		ServerSocketFactory serverSocketFactory = ServerSocketFactory
				.getDefault();
		try
		{

			serverSocket = serverSocketFactory.createServerSocket(port);
			System.out.println("Refresh server is ok!");

			while (true) {				
				Socket socket = serverSocket.accept();
				new RefreshThread(socket,dbUtil).start();				
			}
		} catch (IOException e)
		{
			//e.printStackTrace();
		}
	}
	
	public void stopRun(){
		try {
			if(!serverSocket.isClosed()){
				serverSocket.close();	
			}	
			System.out.println("Refresh Server is stop!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
