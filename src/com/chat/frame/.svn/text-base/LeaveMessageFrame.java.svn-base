package com.chat.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import com.chat.bean.LeaveMessage;
import com.chat.bean.PicInfo;
import com.chat.bean.User;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.StyleUtil;
/**
 * 留言功能，当好友不在线时，可以给他留言
 * 上线之后会收到该留言
 * @author karen
 */
public class LeaveMessageFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextPane message;
	private JButton button;
	private JScrollPane viewLeaveMsgScrollPane;
	private User curUser,clickedUser;
	private JLabel countFonts;
	private Document doc;
	private String serverIP = PersistenceContext.getEntity().getServerIP();
    private int serverPort = PersistenceContext.getEntity().getServerPort();
    private String buttonText;
    protected List<PicInfo> myPicInfo = new LinkedList<PicInfo>(); 
    private LeaveMessageFrame lmf;
	private StyledDocument docMsg = null; 
	
	public  LeaveMessageFrame(User curUser,User clickedUser,String buttonText){
		this.curUser = curUser;
		this.clickedUser = clickedUser;
		this.buttonText = buttonText;
		init();		
	}	
	
	public void init(){
		lmf = this;	
		message = new JTextPane();
		message.setOpaque(false);
		docMsg = message.getStyledDocument();		
		button = new JButton();
		countFonts = new JLabel("0/140字");
		
		this.getContentPane().setLayout(null);
		
		button.setBounds(270,140, 60, 20);
		countFonts.setBounds(210,140,60,20);
			

	    this.getContentPane().add(button);
	    this.getContentPane().add(countFonts);
	    	    	  		
		this.setSize(new Dimension(350,200));
		Toolkit kit=Toolkit.getDefaultToolkit();//设置顶层容器框架为居中
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-350)/2;
        int y=(height-200)/2;
        this.setLocation(x,y);
        if(buttonText.equals("send")){
            this.setTitle("给"+clickedUser.getUsername()+"留言");
            button.setText("发送");
            message.setBounds(1,1,340,130);
            message.setBorder(BorderFactory.createTitledBorder(""));
    	    this.getContentPane().add(message);
            message.addKeyListener(new KeyAdapter() {
				
    			@Override
    			public void keyReleased(KeyEvent e) {
    				System.out.println("keyReleased");
    				doc = message.getStyledDocument();
    				int length = doc.getLength();
    				countFonts.setText(length+"/140字");
    				if(length >= 140){
    					message.setEditable(false);
    				}else{
    					message.setEditable(true);
    				}
    				
    			}
    		
    		});
        }else{
        	this.setTitle("来自"+clickedUser.getUsername()+"的留言");
        	button.setText("关闭");
        	countFonts.setVisible(false);
        	message.setEditable(false);
        	viewLeaveMsgScrollPane = new JScrollPane(message);	
        	viewLeaveMsgScrollPane.setHorizontalScrollBar(null);
        	viewLeaveMsgScrollPane.setBorder(BorderFactory.createTitledBorder(""));
        	viewLeaveMsgScrollPane.setBounds(1,1,340,130);
    	    this.getContentPane().add(viewLeaveMsgScrollPane);
        }    
		this.setVisible(true);
		this.setResizable(false); 
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttonText.equals("send")){  //发送留言
					String curDateAndTime = DataUtil.getCurDateAndTime();
					LeaveMessage lm = new  LeaveMessage();
					lm.setIsRead("N");
					//msg为StyleUtil类转换的字符串
					lm.setMessage(getFontAttrib().toString());					
					lm.setSender(curUser);
					lm.setReceiver(clickedUser);
					lm.setSendTime(curDateAndTime);
					DataUtil.sendLeaveMessage(serverIP,serverPort-1,lm);
					/**
					 * 根据服务器的返回值，判断是否留言成功
					 */
					while(true){
						if(PersistenceContext.getEntity().getSaveLeaveMessageStatus() == Constants.CON_SAVE_LEAVE_MESSAGTE_STATUS_SECCESS){											
							JOptionPane.showMessageDialog(lmf, Constants.CON_TIPS_SAVE_LEAVE_MESSAGE_SUCCESS, "提示",1);	
							lmf.setVisible(false);									
							break;

						}else if(PersistenceContext.getEntity().getRegisterStatus() == Constants.CON_REGISTER_STATUS_FAILTURE){				
							JOptionPane.showMessageDialog(lmf, Constants.CON_TIPS_SAVE_LEAVE_MESSAGE_FAILTURE, "提示",1);
							break;
						}
					}
					
				}else{//读取留言之后关闭窗口
					lmf.setVisible(false);
				}
			}
		});
	}

	/**
	 * 获取所需要的文字设置，包括表情全部以字符串的形式保存
	 * 
	 * @return FontAttrib
	 */
	private StyleUtil getFontAttrib() {
		StyleUtil att = new StyleUtil();
		String curDateAndTime = DataUtil.getCurDateAndTime();
		att.setMsg(curDateAndTime+" "+message.getText()+"*"+buildPicInfo());//文本和表情信息
		att.setName("宋体");
		att.setSize(12);
		att.setColor(new Color(0, 0, 0));	
		return att;
	}
	 /**
	 * 重组发送的表情信息
	 * @return 重组后的信息串  格式为   位置|代号+位置|代号+……
	 */
	private String buildPicInfo(){
		StringBuilder sb = new StringBuilder("");
//		  for(int i = 0; i < this.message.getText().length(); i++){ 
//              if(docMsg.getCharacterElement(i).getName().equals("icon")){
//            	  Icon icon = StyleConstants.getIcon(message.getStyledDocument().getCharacterElement(i).getAttributes());
//            	  ChatPic cupic = (ChatPic)icon;
//            	  PicInfo picInfo= new PicInfo(i,cupic.getIm()+"");
//            	  myPicInfo.add(picInfo);
//            	  sb.append(i+"&"+cupic.getIm()+"+");
//             } 
//          }
		  System.out.println(sb.toString());
		  return sb.toString();
		 
	}
	 /** 
     * 插入图片 
     *  
     * @param icon 
     */  
    public void insertSendPic(ImageIcon imgIc) {
    	
    	System.out.println("##############"+message.getCaretPosition());
    	message.setCaretPosition(docMsg.getLength());
    	message.insertIcon(imgIc); // 插入图片 
        System.out.print(imgIc.toString());  
       
    }

	public JTextPane getMessage() {
		return message;
	}

	public void setMessage(JTextPane message) {
		this.message = message;
	} 
    
}
