package com.chat.frame;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import com.chat.bean.PicInfo;

public class SuperFrame extends JFrame{

	
	private static final long serialVersionUID = 1L;
	boolean picWindowIsOpen = false;
	protected JTextPane writeMessageArea ;
	protected JLabel showPic;
	protected JTextPane showMessagePane;
	protected List<PicInfo> myPicInfo = new LinkedList<PicInfo>(); 
	 /** 
     * 插入图片 
     *  
     * @param icon 
     */  
    public void insertSendPic(ImageIcon imgIc) {
    	StyledDocument docChat = writeMessageArea.getStyledDocument();
    	System.out.println("##############"+writeMessageArea.getCaretPosition());
    	writeMessageArea.setCaretPosition(docChat.getLength());
    	writeMessageArea.insertIcon(imgIc); // 插入图片 
        System.out.print(imgIc.toString());  
       
    } 
	
	public boolean isPicWindowIsOpen() {
		return picWindowIsOpen;
	}

	public void setPicWindowIsOpen(boolean picWindowIsOpen) {
		this.picWindowIsOpen = picWindowIsOpen;
	}

	public JTextPane getWriteMessageArea() {
		return writeMessageArea;
	}

	public void setWriteMessageArea(JTextPane writeMessageArea) {
		this.writeMessageArea = writeMessageArea;
	}

	public JLabel getShowPic() {
		return showPic;
	}

	public void setShowPic(JLabel showPic) {
		this.showPic = showPic;
	}

	public JTextPane getShowMessagePane() {
		return showMessagePane;
	}

	public void setShowMessagePane(JTextPane showMessagePane) {
		this.showMessagePane = showMessagePane;
	}

	public List<PicInfo> getMyPicInfo() {
		return myPicInfo;
	}

	public void setMyPicInfo(List<PicInfo> myPicInfo) {
		this.myPicInfo = myPicInfo;
	}
	
}
