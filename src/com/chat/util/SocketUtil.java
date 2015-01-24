package com.chat.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.chat.client.ClientSocket;

/**
 * Socket -Io util
 * @author pccw
 *
 */
public class SocketUtil {
	public static DataInputStream getDataInputStream(Socket socket) throws Exception {
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			if (inputStream != null)
				inputStream.close();
			throw e;
		} finally {
		}
	}
	
	public static DataOutputStream getDataOutputStream(Socket socket) throws Exception {
		DataOutputStream outputStream = null;
		try {
			outputStream = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
			if (outputStream != null)
				outputStream.close();
			throw e;
		} finally {
		}
	}
	
    /**
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
	public static DataOutputStream getDateOutputStream(String filePath) throws Exception {
		DataOutputStream dos = null;		
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);
		} catch (Exception e) {
			e.printStackTrace();
			if (dos != null)
				dos.close();
			throw e;
		} finally {		
		}
		return dos;
	}
	
	/**
	 * Validate whether the connection is actived by ip and port
	 * @param ip
	 * @param port
	 * @return
	 */
	public static boolean testConnection(String ip,int port){
		ClientSocket tcs = new ClientSocket(ip, port);
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
