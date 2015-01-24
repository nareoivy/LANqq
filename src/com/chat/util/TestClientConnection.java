package com.chat.util;

import java.io.IOException;
import java.net.UnknownHostException;

import com.chat.client.ClientSocket;

public class TestClientConnection {
	private ClientSocket tcs = null;
	
	public boolean testConnection(String ip,int port){
		tcs = new ClientSocket(ip, port);
		try {
			tcs.CreateConnection();
			return true;
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException");
			return false;
		} catch (IOException e) {
			System.out.println("IOException");
			return false;
		}finally{
			tcs.close();
		}
	}
}
