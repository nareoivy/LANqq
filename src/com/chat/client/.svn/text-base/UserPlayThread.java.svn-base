package com.chat.client;

import com.chat.frame.UserListFrame;
import com.chat.util.DataUtil;

public class UserPlayThread extends Thread{
	private UserListFrame ulf;
	private int status = 1;
	private String userNo;
	public UserPlayThread(UserListFrame ulf,String userNo){
    	this.ulf = ulf;
    	this.userNo = userNo;
    }
	public void run()  {


		 int counter = 0;
		  while(true){
			  
			  /**
			   * 运行状态表示，如果status=0，则线程终止
			   */
			 if(status == 0){
				 return;
			 } 
			 
			 if(counter > 60000){
				 counter =1;
			 }
			 
			try {
				Thread.sleep(500);
				counter++;				
				if(counter%2 == 0){
					
					DataUtil.setListByUsers(ulf.getUsers(), ulf.getPerson(),userNo);
				}else if(counter%2 == 1){
					DataUtil.setListByUsers(ulf.getUsers(), ulf.getPerson());
				}
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		  }
	}
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
