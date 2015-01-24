package com.chat.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import com.chat.frame.UserListFrame;

public class ClientReceiveBody extends Thread
{
	private int port;
	private String savePath;
	private UserListFrame userListFrame;

	public ClientReceiveBody(int port,String savePath,UserListFrame userListFrame)
	{
		this.port = port;
		this.savePath = savePath;
		this.userListFrame = userListFrame;
	}

	public void run()
	{
		ServerSocketFactory serverSocketFactory = ServerSocketFactory
				.getDefault();
		try
		{
			ServerSocket serverSocket = serverSocketFactory
					.createServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				new ClientReceiveThread(socket,savePath,userListFrame).start();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
