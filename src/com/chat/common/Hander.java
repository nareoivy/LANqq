package com.chat.common;

/**
 * 登录验证和刷新列表协议
 * @author pccw
 *
 */
public class Hander implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sourceIP;
	private int sourcePort;
	private String targeIP;
	private int targePort;
	private String type;
	private String method;
	private int length;
	private int[] usersLen;
	private int[] groupLen;
	private int[] leaveMesLen;
	private String message;

	public int[] getUsersLen() {
		return usersLen;
	}

	public void setUsersLen(int[] usersLen) {
		this.usersLen = usersLen;
	}

	public int[] getGroupLen() {
		return groupLen;
	}

	public void setGroupLen(int[] groupLen) {
		this.groupLen = groupLen;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getLeaveMesLen() {
		return leaveMesLen;
	}

	public void setLeaveMesLen(int[] leaveMesLen) {
		this.leaveMesLen = leaveMesLen;
	}

}
