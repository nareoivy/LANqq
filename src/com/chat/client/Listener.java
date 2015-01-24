package com.chat.client;


import com.chat.frame.UserListFrame;

public class Listener {
	private static Listener _listener = new Listener();
	
	private ClientReceiveListener clientReceiveListener;
	private ClientRefreshListener clientRefreshListener;
	/**
	 * return Single Instance
	 * @return
	 */
	public static Listener getInstance(){
    	 return _listener;
    }
	
	/**
	 * Start Receive Listener
	 * @param port
	 * @param savePath
	 * @param userListFrame
	 */
    public void startReceiveListener(int port,String savePath,UserListFrame userListFrame){
    	
    	/**
    	 *  Receive Listener
    	 *  open the port of 7001 to listener
    	 */
    	clientReceiveListener = new ClientReceiveListener(port,savePath,userListFrame);
    	clientReceiveListener.setDaemon(true);
    	clientReceiveListener.start();
    }
    
	/**
	 * Start Refresh Listener
	 * @param port
	 * @param savePath
	 * @param userListFrame
	 * open the port of 7000 to listener 
	 */
    public void startRefreshListener(){
    	
    	/**
    	 *  Receive Listener
    	 */
    	clientRefreshListener = new ClientRefreshListener();
    	clientRefreshListener.setDaemon(true);
    	clientRefreshListener.start();
    }

    /**
     * Stop the listener/Server
     */
    public void stop(){
    }
}
