package com.test.frame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket client1 =new Socket("localhost",9005);			
			BufferedReader buf1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			String str1 = buf1.readLine();
			buf1.close();
			client1.close();
			Socket client2 = new Socket("localhost",9005);			
			BufferedReader buf2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			String str2 = buf2.readLine();			
			client2.close();
			buf2.close();
			System.out.println("str1:"+str1+"  str2:"+str2);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
