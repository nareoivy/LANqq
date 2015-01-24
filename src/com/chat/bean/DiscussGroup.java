package com.chat.bean;

public class DiscussGroup {
	private String groupMaster;
	private String discussGroupName;
	private String userNos;
	private String status;
	private byte[] groupImage;
	
	
	public String getGroupMaster() {
		return groupMaster;
	}
	public void setGroupMaster(String groupMaster) {
		this.groupMaster = groupMaster;
	}
	public String getDiscussGroupName() {
		return discussGroupName;
	}
	public void setDiscussGroupName(String discussGroupName) {
		this.discussGroupName = discussGroupName;
	}
	public String getUserNos() {
		return userNos;
	}
	public void setUserNos(String userNos) {
		this.userNos = userNos;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getGroupImage() {
		return groupImage;
	}
	public void setGroupImage(byte[] groupImage) {
		this.groupImage = groupImage;
	}
}
