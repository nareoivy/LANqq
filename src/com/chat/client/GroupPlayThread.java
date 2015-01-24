package com.chat.client;

import com.chat.frame.UserListFrame;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;

public class GroupPlayThread extends Thread{
	private UserListFrame ulf;
	private int status = 1;
	private String groupName;
	public GroupPlayThread(UserListFrame ulf,String groupName){
    	this.ulf = ulf;
    	this.groupName = groupName;
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
					
					DataUtil.setListByGroups(PersistenceContext.getEntity().getGroups(), ulf.getGroup(),groupName);
				}else if(counter%2 == 1){
					DataUtil.setListByGroups(PersistenceContext.getEntity().getGroups(), ulf.getGroup());
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
