package com.chat.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadAndWriteMessage {
	// public static String filePath =
	// ReadAndWriteMessage.class.getResource("")+"messageRecord/";
	
	public static String basePath = ReadAndWriteMessage.class.getClassLoader()
			.getResource("") + "messageRecord/";
	public int index = basePath.indexOf("file:/");
	public String path = basePath.substring(index+6, basePath.length() - 1)+"/";

	/**
	 * 将消息记录写入文件，文件名为 name(no) 格式 先将原来存在的文件删除，然后创建文件，重新将新的信息装入文件
	 * 
	 * @param fileName
	 * @param message
	 */

	public void createBasePath() {
		File fPath = new File(path);
		if (!fPath.exists()) {
			fPath.mkdirs();
		}
	}

	public void writeMsgToFile(String fileName, String message) {
		try {			
			createBasePath();
			File f = new File(path+ fileName + ".txt");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			String s = message;
			FileWriter fw = new FileWriter(f);
			fw.write(s, 0, s.length());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 传入文件名，读取该文件内容 返回一个字符串
	 * 
	 * @param fileName
	 * @return
	 */
	public String readMsgFromFile(String fileName) {

		String msgRecord = "";

		File f = new File(path + fileName + ".txt");
		try {
			if (f.exists()) {
				BufferedReader br;
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				String data = null;
				while ((data = br.readLine()) != null) {
					msgRecord = msgRecord + data;
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return msgRecord;
	}

}
