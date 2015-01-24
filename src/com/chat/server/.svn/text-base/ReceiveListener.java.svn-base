package com.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class ReceiveListener extends Thread
{
	private int port;
	private String savePath;
	private ServerSocket serverSocket;



	public ReceiveListener(int aListenPort,String savePath)
	{
		this.port = aListenPort;
		this.savePath = savePath;
	}

	public void run()
	{
		ServerSocketFactory serverSocketFactory = ServerSocketFactory
				.getDefault();
		try
		{

			serverSocket = serverSocketFactory.createServerSocket(port);
			System.out.println("Receive Server is ok!");

			while (true) {				
				Socket socket = serverSocket.accept();
				new ReceiveThread(socket,savePath).start();				
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
			System.out.println("Receive Server is stop!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
