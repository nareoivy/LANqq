package com.chat.client;

import com.chat.frame.UserListFrame;

public class ImagePlayThread extends Thread{
      
	private UserListFrame ulf;
	private int status = 1;
	public ImagePlayThread(UserListFrame ulf){
    	this.ulf = ulf;	
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
					ulf.getTrayIcon().setImage(ulf.getImgEnable());
				}else if(counter%2 == 1){
					ulf.getTrayIcon().setImage(ulf.getImgDisable());
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
