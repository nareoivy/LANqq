package com.chat.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import com.chat.util.Constants;

public class ClientRefreshListener extends Thread
{
	public ClientRefreshListener()
	{
	}

	public void run()
	{
		ServerSocketFactory serverSocketFactory = ServerSocketFactory
				.getDefault();
		try
		{
			ServerSocket serverSocket = serverSocketFactory
					.createServerSocket(Constants.CON_CLIENT_REFRESH_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				new ClientRefreshThread(socket).start();
			}
		} catch (IOException e)
		{
//			e.printStackTrace();
		}
	}
}
