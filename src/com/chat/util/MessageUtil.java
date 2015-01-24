package com.chat.util;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import com.chat.bean.PicInfo;
import com.chat.bean.User;
import com.chat.frame.SuperFrame;



public class MessageUtil {
	
	private JTextPane showMessagePane;
	@SuppressWarnings("unused")
	private JTextPane writeMessageArea;
	@SuppressWarnings("unused")
	private StyledDocument docChat = null;
	private List<PicInfo> receivdPicInfo = new LinkedList<PicInfo>();
	@SuppressWarnings("unused")
	private List<PicInfo> myPicInfo;
	int pos1,pos2;
	StyleUtil sourceFont = null;
	StyleUtil targetFont = null;
	
	/**
	 * 有参构造
	 * @param showMessageEditPane
	 * @param writeMessageArea
	 */
	public MessageUtil(SuperFrame sf){
		writeMessageArea = sf.getWriteMessageArea();
		showMessagePane = sf.getShowMessagePane();
		docChat = showMessagePane.getStyledDocument();
		myPicInfo = sf.getMyPicInfo();
		sourceFont = new StyleUtil("","Arial",14,Color.green);	
		targetFont = new StyleUtil("","Arial",14,Color.blue);	
		
	}
	/**
	 * 无参构造
	 */
	public MessageUtil(){}
	
	/**
	 * 在发送者发送消息之后，将消息显示到消息面板中
	 * @param curUser  当前用户（发送者）
	 * @param myFont   消息对象，包含了各种字体，大小，以及表情
	 */
	public void addMeg(User curUser,StyleUtil myFont) {
			
		String curTime = DataUtil.getCurTime();
		String msg =  curUser.getUsername()+"(" +curUser.getNo() +")"+"  " + curTime;
		sourceFont.setMsg(msg);
		MessageContainerUtil.insert(showMessagePane, sourceFont);
		MessageContainerUtil.insert(showMessagePane, myFont);
	}
	

	/**
	 * 在监听到消息到来的时候调用，获取发送者的信息
	 * @param curUser  发送者对象(消息是谁发送的)
	 * @param message  发送的消息+表情  统一转为字符串的格式
	 * 处理之后显示到消息面板
	 */	
	public void addRecMsg(User curUser,StyleUtil message){
		String curTime = DataUtil.getCurTime();
	   String msg =  curUser.getUsername()+"(" +curUser.getNo() +")"+"  " + curTime;
	   targetFont.setMsg(msg);
		
	   MessageContainerUtil.insert(showMessagePane, targetFont);
	   MessageContainerUtil.insert(showMessagePane, message);
	}
	
	
	/**
	 * 重组收到的表情信息串，将各个图片对象加入到receivdPicInfo 中
	 */
	public void receivedPicInfo(String picInfos){
		String[] infos = picInfos.split("[+]");
		for(int i = 0 ; i < infos.length ; i++){
			String[] tem = infos[i].split("[&]");
			if(tem.length==2){
				PicInfo pic = new PicInfo(Integer.parseInt(tem[0]),tem[1]);
				receivdPicInfo.add(pic);
			}
		}
	}
	/**
	 * 将收到的消息转化为自定义的字体类对象
	 * @param message 收到的聊天信息
	 * @return  字体类对象
	 */
	public StyleUtil getRecivedFont(String message){
		String[] msgs = message.split("[|]");
		String fontName = "";
		int fontSize = 0;
		String[] color;
		String text = message;
		Color fontC = new Color(222,222,222);
		if(msgs.length>=4){           /*这里简单处理，表示存在字体信息*/
			fontName = msgs[0];
			fontSize = Integer.parseInt(msgs[1]);
			color = msgs[2].split("[-]");
			if(color.length==3){
				int r = Integer.parseInt(color[0]);
				int g = Integer.parseInt(color[1]);
				int b = Integer.parseInt(color[2]);
				fontC = new Color(r,g,b);
			}
			text = "";
			for(int i = 3; i < msgs.length ; i++){
				text = text + msgs[i];
			}
		}
		StyleUtil attr = new StyleUtil();		
		attr.setName(fontName);
		attr.setSize(fontSize);
		attr.setColor(fontC);		
		attr.setMsg(text);		
		System.out.println("getRecivedFont(String message):"+attr.toString());
		return attr;
	}
	
	
	

}
