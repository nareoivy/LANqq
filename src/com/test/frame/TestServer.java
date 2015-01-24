package com.test.frame;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(9005);
			Socket client = null;
			PrintStream out = null;
			System.out.println("服务器运行，等待客户端连接。。");
			
			while(true){
				client = server.accept();
				String str = "hello...";
				out = new PrintStream(client.getOutputStream());
				out.print(str);
				out.close();
				client.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(server != null)
				     server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
