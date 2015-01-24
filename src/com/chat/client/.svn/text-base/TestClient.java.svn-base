package com.chat.client;

import com.chat.common.Pact;
import com.chat.util.Constants;


public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String filePath = "c:\\send\\校友信息表.doc";
		try {
//			String message = "您好aaa";
			//SendFile send = new SendFile(Constants.CON_SERVER_ADDRESS, Constants.CON_SERVER_PORT,filePath);
			Pact pact = new Pact();
			pact.setSourceIP(Constants.CON_SERVER_ADDRESS);
			pact.setSourcePort(Constants.CON_SERVER_ACCEPT_PORT);
			pact.setTargeIP(Constants.CON_SERVER_ADDRESS);
			pact.setTargePort(Constants.CON_SERVER_ACCEPT_PORT);
//			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
//			SendMessage send = new SendMessage(Constants.CON_SERVER_ADDRESS, Constants.CON_SERVER_PORT,message,pact);
			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_FILE);
//			SendFile send = new SendFile(Constants.CON_SERVER_ADDRESS, Constants.CON_SERVER_ACCEPT_PORT,filePath,pact);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
