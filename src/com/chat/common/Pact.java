package com.chat.common;


public class Pact implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Transfers Type
	 * Message
	 * File
	 */
	private String transfersType;
	
	/**
	 * source ip in socket
	 */
	private String sourceIP;
	
	/**
	 * source port in socket
	 */
	private int sourcePort;
	
	/**
	 * source ip in socket
	 */
	private String targeIP;
	
	/**
	 * targe port in socket
	 */
	private int targePort;
	
	/**
	 * message length
	 */
	private int messageLen;
	
	/**
	 * File length
	 */
	private long fileLen;
	
	/**
	 * File name
	 */
	private String fileName;
	
	/**
	 *  whether message or massage Group
	 */
	private int chatType;
	
	/**
	 * group name
	 */
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getChatType() {
		return chatType;
	}
	public void setChatType(int chatType) {
		this.chatType = chatType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getMessageLen() {
		return messageLen;
	}
	public void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}
	public String getTransfersType() {
		return transfersType;
	}
	public void setTransfersType(String transfersType) {
		this.transfersType = transfersType;
	}
	public String getSourceIP() {
		return sourceIP;
	}
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}
	public int getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getTargeIP() {
		return targeIP;
	}
	public void setTargeIP(String targeIP) {
		this.targeIP = targeIP;
	}
	public int getTargePort() {
		return targePort;
	}
	public void setTargePort(int targePort) {
		this.targePort = targePort;
	}
	public long getFileLen() {
		return fileLen;
	}
	public void setFileLen(long fileLen) {
		this.fileLen = fileLen;
	}
}
