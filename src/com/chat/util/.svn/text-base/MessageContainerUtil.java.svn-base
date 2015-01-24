package com.chat.util;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.chat.bean.LeaveMessage;
import com.chat.bean.User;


public class MessageContainerUtil {
	
	@SuppressWarnings("rawtypes")
	private static Map<String,List> containers = new HashMap<String,List>();
	
	private static Map<String,List<StyleUtil>> recordMsgs = new HashMap<String,List<StyleUtil>>();
	private static Map<String,List<StyleUtil>> recordGroupMsgs = new HashMap<String,List<StyleUtil>>();
	private static Map<String,List<LeaveMessage>> recordLeaveMsgs = new HashMap<String,List<LeaveMessage>>();
	
	private static StyleUtil font = null;
	
	
	/**
	 * 清除已读留言
	 */
	public static void cleanRecordMsgs(User sourceUser){
		String strKey = "@"+sourceUser.toString();												
		List<LeaveMessage> msgs = recordLeaveMsgs.get(strKey);
		if(null == msgs){
			msgs = new ArrayList<LeaveMessage>();	
		}
		recordLeaveMsgs.remove(strKey);
	}
	
	/**
	 * 判断内存里是否存在未读的消息
	 * @return fasle:不存在 true:存在
	 * by karen
	 */
	public static boolean isExistMsg(User sourceUser,User targeUser){
		String sourceIp = sourceUser.getIp();
		String targeIp = targeUser.getIp();
		String strKey1 = "@"+sourceIp+"@"+targeIp+"@";
		String strKey2 = "@"+targeIp+"@"+sourceIp+"@";
		String strKey = null;
		
		if(recordLeaveMsgs.containsKey(strKey1)){
			strKey = strKey1;
		}
		
		if(recordLeaveMsgs.containsKey(strKey2)){
			strKey = strKey2;
		}
		
		if(strKey == null){
			strKey = strKey1;
		}
		List<LeaveMessage> msgs = recordLeaveMsgs.get(strKey);
		if(null == msgs){  //不存在消息
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 *  add Message
	 *  把消息记录到内存
	 *  modify by luo
	 */
	public static void loadMessage(User sourceUser,User targeUser,StyleUtil message,String who){
		
		String sourceIp = sourceUser.getIp();
		String targeIp = targeUser.getIp();
		String strKey1 = "@"+sourceIp+"@"+targeIp+"@";
		String strKey2 = "@"+targeIp+"@"+sourceIp+"@";
		String strKey = null;
		
		if(recordMsgs.containsKey(strKey1)){
			strKey = strKey1;
		}
		
		if(recordMsgs.containsKey(strKey2)){
			strKey = strKey2;
		}
		
		if(strKey == null){
			strKey = strKey1;
		}
		if(who.equals("FRIEND")){
			font = new StyleUtil("","Arial",14,Color.BLUE);
		}else{
			font = new StyleUtil("","Arial",14,Color.GREEN);
		}
		
		String curTime = DataUtil.getCurTime();
		
		String msg =  sourceUser.getUsername()+"(" +sourceUser.getNo() +")"+"  " + curTime;
		font.setMsg(msg);
		
		List<StyleUtil> msgs = recordMsgs.get(strKey);
		if(null == msgs){
			msgs = new ArrayList<StyleUtil>();	
		}
		msgs.add(font);
		System.out.println(font.getMsg());
		System.out.println(font.getMsg().length());
		
		msgs.add(message);
		recordMsgs.put(strKey, msgs);
	}
	
	/**
	 * 把留言记录到内存
	 */
public static void loadLeaveMessage(LeaveMessage message){
				
	   String userJson = JsonUtil.getJsonString(message.getSender());
		String strKey = "@"+userJson;												
		List<LeaveMessage> msgs = recordLeaveMsgs.get(strKey);
		if(null == msgs){
			msgs = new ArrayList<LeaveMessage>();	
		}
		
		msgs.add(message);
		recordLeaveMsgs.put(strKey, msgs);
		
	}
	/**
	 * 把群消息记录到内存
	 */
	public static void loadGroupMessage(String groupName,User sourceUser,StyleUtil message,String who){
		String curTime = DataUtil.getCurTime();
		
		
		if(who.equals("FRIEND")){
			font = new StyleUtil("","Arial",14,Color.BLUE);
		}else{
			font = new StyleUtil("","Arial",14,Color.GREEN);
		}
		String msg =  sourceUser.getUsername()+"(" +sourceUser.getNo() +")"+"  " + curTime;
		font.setMsg(msg);
		
		List<StyleUtil> msgs = recordGroupMsgs.get(groupName);
		if(msgs == null){
			msgs = new ArrayList<StyleUtil>();
		}
		msgs.add(font);
		msgs.add(message);
		recordGroupMsgs.put(groupName, msgs);
	}
	
	
	/**
	 * 把内存中的消息写入面板
	 */
	public static void writeRecordToPane(User sourceUser,User targeUser,JTextPane showMessageEditPane){
		 String strKey1 = "@"+sourceUser.getIp()+"@"+targeUser.getIp()+"@";
		 String strKey2 = "@"+targeUser.getIp()+"@"+sourceUser.getIp()+"@";
		 String strKey = null;
		 if(recordMsgs.containsKey(strKey1)){
			 strKey = strKey1;
		 }
		 if(recordMsgs.containsKey(strKey2)){
			 strKey = strKey2;
		 }
		 if(null != recordMsgs){
			 List<StyleUtil> msgs = recordMsgs.get(strKey);
			 
			 if(null != msgs && msgs.size() > 0){
				 for(int i = 0; i < msgs.size(); i++){ 
					 insert(showMessageEditPane,msgs.get(i));
				 }
			 }
		 }
	}
	
	/**
	 * 把内存中的留言写入留言面板
	 */
	public static void writeLeaveMsgToPane(User sourceUser,JTextPane leaveMsgPane){
		 String userJson = JsonUtil.getJsonString(sourceUser);
	    String strKey = "@"+userJson;	
		 MessageUtil mu = new MessageUtil();
		 if(null != recordLeaveMsgs){
			 List<LeaveMessage> msgs = recordLeaveMsgs.get(strKey);			 
			 if(null != msgs && msgs.size() > 0){
				 for(int i = 0; i < msgs.size(); i++){ 
					 String mess = msgs.get(i).getSendTime()+"  "+msgs.get(i).getMessage();
					 StyleUtil st = mu.getRecivedFont(mess);
					 insert(leaveMsgPane,st);
				 }
			 }
		 }
	}
	
	/**
	 * 把内存中的消息写入群面板
	 */
	public static void writeRecordToGroupPane(User sourceUser,String groupName,JTextPane showMessageEditPane){
		 if(null != recordGroupMsgs){
			 List<StyleUtil> msgs = recordGroupMsgs.get(groupName);
			 
			 if(null != msgs && msgs.size() > 0){
				 for(int i = 0; i < msgs.size(); i++){ 
					 insert(showMessageEditPane,msgs.get(i));
				 }
			 }
		 }
	}
	
	public static void insert(JTextPane showMessageEditPane,StyleUtil attrib) {
		
		StyledDocument docChat = showMessageEditPane.getStyledDocument();
		String msg = attrib.getMsg();
		int index = msg.lastIndexOf("*");
		
		if(index>0 && index < msg.length()-1) /*存在表情信息*/
		{
			URL url;
			String str_msg = attrib.getMsg().substring(0, index);
			String str_pic = attrib.getMsg().substring(index+1, attrib.getMsg().length());
			String[] pic = msg.substring(index+1,msg.length()-1).split("\\+");
			int[] pos = new int[str_pic.split("\\+").length];
			for(int i = 0; i < str_pic.split("\\+").length; i++){
				pos[i] = Integer.valueOf(str_pic.split("\\+")[i].split("&")[0]);
			}
			
			try {
				docChat.insertString(docChat.getLength(),str_msg.substring(0, pos[0]),
						attrib.getAttrSet());
				System.out.println(pos.length);
				for(int i = 0; i < pos.length; i++){
					System.out.println(i);
					showMessageEditPane.setCaretPosition(docChat.getLength());
					url = DataUtil.getIconPath(MessageContainerUtil.class, pic[i].split("&")[1]+".gif");
					showMessageEditPane.insertIcon(new  ImageIcon(url)); /*插入图片*/
					
					System.out.println("插入图片");
					if(i == pos.length-1){
						docChat.insertString(docChat.getLength(),str_msg.substring(pos[i]+1,str_msg.length())+"\n",
								attrib.getAttrSet());
						System.out.println("插入Msg"+str_msg.substring(pos[i]+1,str_msg.length()));
					}else{
						docChat.insertString(docChat.getLength(),str_msg.substring(pos[i]+1, pos[i+1]),
								attrib.getAttrSet());
						System.out.println("插入Msg"+str_msg.substring(pos[i]+1, pos[i+1]));
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}		
		}else{//没有图片
			if(index > 0){//插入用户信息和 不含图片的message
				try { 
					docChat.insertString(docChat.getLength(), attrib.getMsg().substring(0, attrib.getMsg().length()-1) + "\n",
							attrib.getAttrSet());
					showMessageEditPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}else{//只插入用户信息，例如：陈雪佳(80352891) 2012-06-12 13:25:36
				try { 
					docChat.insertString(docChat.getLength(), attrib.getMsg() + "\n",
							attrib.getAttrSet());
					showMessageEditPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 *  List to String
	 */
	@SuppressWarnings("unchecked")
	public static String tString(String sourceIp,String targeIp){
		 String reMessage = "";
		 StringBuffer bf = new StringBuffer();
		 
		 String strKey1 = "@"+sourceIp+"@"+targeIp+"@";
		 String strKey2 = "@"+targeIp+"@"+sourceIp+"@";
		 String strKey = null;
		 if(containers.containsKey(strKey1)){
			 strKey = strKey1;
		 }
		 if(containers.containsKey(strKey2)){
			 strKey = strKey2;
		 }
		 
		List<String> container = containers.get(strKey);
		if(container != null){
			 if(container.size() > 0){
					for(String message:container){
						bf.append(message);
					} 
				 }
		}
		 reMessage = bf.toString();
		  return reMessage;
	}
	
	
	/**
	 * 接收文件，给出提示信息： 例如：对方已经开始接收文件
	 * @param sourceUser
	 * @param targeUser
	 * @param font
	 * @param message
	 * @param color
	 */
//	@SuppressWarnings("unchecked")
//	public static void loadFileMessage(User sourceUser,User targeUser,Font font,String message,String color){
//		
//		String sourceIp = sourceUser.getIp();
//		String targeIp = targeUser.getIp();
//		String strKey1 = "@"+sourceIp+"@"+targeIp+"@";
//		String strKey2 = "@"+targeIp+"@"+sourceIp+"@";
//		String strKey = null;
//		
//		if(containers.containsKey(strKey1)){
//			strKey = strKey1;
//		}
//		
//		if(containers.containsKey(strKey2)){
//			strKey = strKey2;
//		}
//		
//		if(strKey == null){
//			strKey = strKey1;
//			initBody(strKey);
//		}
//		
//		List<String> container = containers.get(strKey);
//		int bit = 4;
//		if(bits.containsKey(strKey)){
//			bit = bits.get(strKey);
//			bit++;
//		}
//		
//		String unitMessage = "<font size='3' color='"+color+"'  style='"+font.getStyle()+"'>";	
//		unitMessage +=  "&nbsp;&nbsp;"+message+"</font><br>";
//		container.add(bit,unitMessage);
//		bits.put(strKey, bit);
//	}
	
	/**
	 * 为发送文件时，向面板写文件消息时调用
	 * @param message给出提示信息： 例如：对方已经开始接收文件
	 * @return
	 */
	public static  StyleUtil setStrMsgToStyleUtil(String message){
		StyleUtil att = new StyleUtil();
		att.setMsg(message+"*");//文本和表情信息
		att.setName("宋体");
		att.setSize(12);
		att.setColor(new Color(0, 0, 0));	
		return att;
	}
	
	/**
	 * Clear Container
	 */
	public static void clean(){
		containers.clear();
	}


	public static Map<String, List<LeaveMessage>> getRecordLeaveMsgs() {
		return recordLeaveMsgs;
	}


	public static void setRecordLeaveMsgs(
			Map<String, List<LeaveMessage>> recordLeaveMsgs) {
		MessageContainerUtil.recordLeaveMsgs = recordLeaveMsgs;
	}
	
}
