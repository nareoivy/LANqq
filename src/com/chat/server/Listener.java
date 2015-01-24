package com.chat.server;

import com.chat.util.StringUtil;

public class Listener {
	private ReceiveListener receiveLister;
	private RefreshListener refreshListener;
	private static Listener _listener = new Listener();
    public static Listener getInstance(){
    	 return _listener;
    }
    public void start(int port,String savePath){
    	
    	/**
    	 *  Receive Listener
    	 */
    	receiveLister = new ReceiveListener(port,savePath);
    	receiveLister.setDaemon(true);
    	receiveLister.start();
    	
    	/**
    	 * Refresh Listener
    	 */
    	int refreshPort = port -1;
    	refreshListener = new RefreshListener(refreshPort);
    	refreshListener.setDaemon(true);
    	refreshListener.start();
    
    }
    
    /**
     * Stop the listener/Server
     */
    public void stop(){
    	if(!StringUtil.isNull(receiveLister)){
    		receiveLister.stopRun();
    	}
    	if(!StringUtil.isNull(refreshListener)){
    		refreshListener.stopRun();
    	}
    }
}
