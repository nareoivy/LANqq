package com.chat.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.chat.bean.ChatPic;
import com.chat.bean.DiscussGroup;
import com.chat.bean.PicInfo;
import com.chat.bean.User;
import com.chat.client.RefreshClient;
import com.chat.client.SendMessage;
import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.MessageContainerUtil;
import com.chat.util.MessageUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.PicsJWindow;
import com.chat.util.StyleUtil;


public class ChatGroupFrame extends SuperFrame{

	private static final long serialVersionUID = 1L;
	private List<User> gruopUsers;
	private JList gruopUsersList;
	private JScrollPane gruopListPane,messagescrollPane;
	private User curUser;
	private UserListFrame ulf;
	private DiscussGroup discussGroup;//群信息
	private JMenuBar menuBar;
	private JMenu menuHandle;
	private JMenuItem exitGroup;	
	private JComboBox fontName = null, fontSize = null,fontColor = null;   /*字体名称;字号大小;文字颜色*/  	
	private PicsJWindow picWindow;   /*表情框*/
	private Box  box;
	private StyleUtil myStyle = null;
	private StyledDocument docMsg = null; 
	private JButton send;
	private ChatGroupFrame f;
	
	private String serverIP = PersistenceContext.getEntity().getServerIP();
    private int serverPort = PersistenceContext.getEntity().getServerPort();
    private String groupName = "";
	
	public ChatGroupFrame(List<User> gruopUsers,User curUser,String groupName,UserListFrame ulf,DiscussGroup discussGroup){
		this.gruopUsers = gruopUsers;
		this.curUser = curUser;
		this.groupName = groupName;
		this.ulf = ulf;
		this.discussGroup = discussGroup;
		this.f = this;
	}
	
	public void initComp(){
		menuBar = new JMenuBar();
		menuHandle = new JMenu("操作");
		ImageIcon imageIcon = new ImageIcon(DataUtil.getImgPath(getClass(),"setting.png"));
		menuHandle.setIcon(imageIcon);
		//设置退出讨论组按钮
		if(!discussGroup.getGroupMaster().equals(curUser.getNo())){
		
			exitGroup = new JMenuItem("退出该群");		
			exitGroup.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String[] userNos = discussGroup.getUserNos().split(",");
					String strUserNos = "";
					for(int i = 0; i < userNos.length; i++){
						if(!userNos[i].equals(curUser.getNo())){
							strUserNos += userNos[i]+",";
						}
					}
					strUserNos = strUserNos.substring(0, strUserNos.length()-1);
					if(strUserNos.indexOf(",") > -1){
						//讨论组里还有1个以上的人
						discussGroup.setUserNos(strUserNos);
						DataUtil.sendUpdateGroupInfo(serverIP, serverPort-1, discussGroup);
						while(true){
							if(PersistenceContext.getEntity().getUpdateGroupStatus() == Constants.CON_UPDATE_GROUP_STATUS_SECCESS){
								List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
								for(int i = 0; i < discussGroups.size(); i++){
									if(discussGroups.get(i).getDiscussGroupName().equals(discussGroup.getDiscussGroupName())){
										discussGroups.remove(i);
										discussGroups.add(discussGroup);
									}
								}
								PersistenceContext.getEntity().setGroups(discussGroups);
								PersistenceContext.getEntity().setUpdateGroupStatus(Constants.CON_UPDATE_GROUP_STATUS_UNLOGINED);
								RefreshClient.refresh(curUser, ulf);
								dispose();
								break;
							}else if(PersistenceContext.getEntity().getUpdateGroupStatus() == Constants.CON_UPDATE_GROUP_STATUS_FAILTURE){
								JOptionPane.showMessageDialog(f, "退出失败 ");
								break;
							}
						}
						
					}else{
						//讨论组里只有一个人
						DataUtil.sendDeleteGroupInfo(serverIP, serverPort-1, groupName);
						while(true){
							if(PersistenceContext.getEntity().getDeleteGroupStatus() == Constants.CON_DELETE_GROUP_STATUS_SECCESS){
								
								List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
								for(int i = 0; i < discussGroups.size(); i++){
									if(discussGroups.get(i).getDiscussGroupName().equals(groupName.toString())){
										discussGroups.remove(i);
									}
								}
								PersistenceContext.getEntity().setGroups(discussGroups);
								PersistenceContext.getEntity().setDeleteGroupStatus(Constants.CON_DELETE_GROUP_STATUS_UNLOGINED);
								JOptionPane.showMessageDialog(f, "讨论组已删除");		
								RefreshClient.refresh(curUser, ulf);
								dispose();
								break;	
							}else if(PersistenceContext.getEntity().getDeleteGroupStatus() == Constants.CON_DELETE_GROUP_STATUS_FAILTURE){
								JOptionPane.showMessageDialog(f, "退出失败");
								break;
							}
						}
					}
					dispose();
				}
			});
		}else{
			exitGroup = new JMenuItem("删除该群");			
			exitGroup.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					DataUtil.sendDeleteGroupInfo(serverIP, serverPort-1, discussGroup.getDiscussGroupName());
					while(true){
						if(PersistenceContext.getEntity().getDeleteGroupStatus() == Constants.CON_DELETE_GROUP_STATUS_SECCESS){
							
							List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
							for(int i = 0; i < discussGroups.size(); i++){
								if(discussGroups.get(i).getDiscussGroupName().equals(groupName.toString())){
									discussGroups.remove(i);
								}
							}
							PersistenceContext.getEntity().setGroups(discussGroups);
							PersistenceContext.getEntity().setDeleteGroupStatus(Constants.CON_DELETE_GROUP_STATUS_UNLOGINED);
							JOptionPane.showMessageDialog(f, "讨论组已删除");
							RefreshClient.refresh(curUser, ulf);
							dispose();
							break;	
						}else if(PersistenceContext.getEntity().getDeleteGroupStatus() == Constants.CON_DELETE_GROUP_STATUS_FAILTURE){
							JOptionPane.showMessageDialog(f, "退出失败");
							break;
						}
					}
				}
			});
			
		}
		menuHandle.add(exitGroup);
		menuBar.add(menuHandle);
		this.setJMenuBar(menuBar);
		
		//设置用户列表
		gruopUsersList = new JList();
		DataUtil.setListByUsers(gruopUsers, gruopUsersList,-1);
		gruopListPane = new JScrollPane(gruopUsersList);
		gruopListPane.setBorder(BorderFactory.createTitledBorder("用户列表"));
	
		showMessagePane = new JTextPane();
		showMessagePane.setEditable(false);
		showMessagePane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
			}
		});
		messagescrollPane = new JScrollPane(showMessagePane);
		messagescrollPane.setBorder(BorderFactory.createTitledBorder("消息记录"));
		messagescrollPane.setHorizontalScrollBar(null);
		
		//设置发送信息
		writeMessageArea =new JTextPane();
		writeMessageArea.setBorder(BorderFactory.createTitledBorder(""));
		docMsg = writeMessageArea.getStyledDocument();	
		//设置发送按钮
		send = new JButton("发送");
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 myStyle = getFontAttrib();
				 MessageContainerUtil.loadGroupMessage(groupName, curUser,myStyle,"ME");
				for(User user:gruopUsers){
					sendMessage(myStyle.toString(),user,groupName);	
				}
				MessageUtil mu = new MessageUtil(f);
				mu.addMeg(curUser,myStyle);
				writeMessageArea.setText("");
			}
		});
		
		 /**
         * 设置中间的工具栏：字体，字体颜色，表情等
         */
		box = Box.createHorizontalBox();      
        String[] str_name = { "宋体", "黑体", "Arial", "Gulim" };  
        String[] str_Size = { "12", "14", "18", "22", "30", "40" };   
        String[] str_Color = { "黑色", "红色", "蓝色", "黄色", "绿色" };  
        picWindow = new PicsJWindow(this); 
        fontName = new JComboBox(str_name);  
        fontSize = new JComboBox(str_Size);  
        fontColor = new JComboBox(str_Color);  
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image imgSimle = kit.getImage(DataUtil.getImgPath(getClass(),"0.gif"));
        Icon iconSmile = new ImageIcon(imgSimle);
        showPic  = new JLabel(iconSmile);  
        showPic.addMouseListener(new MouseAdapter() {				
			@Override
			public void mouseClicked(MouseEvent e) {				
				if(picWindowIsOpen){
					
					 picWindow.setVisible(false);	
					 picWindowIsOpen = false;
				}else{					    
						 picWindow.setVisible(true);	
						 picWindowIsOpen = true;
					}	
					
			}					
		});      
        
        showPic.setFocusable(false);  
        box.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));  
        box.add(new JLabel("字体:"));  
        box.add(fontName);    
        box.add(Box.createHorizontalStrut(3));  
        box.add(new JLabel("字号:"));  
        box.add(fontSize);  
        box.add(Box.createHorizontalStrut(3));  
        box.add(new JLabel("颜色:"));  
        box.add(fontColor);  
        box.add(Box.createHorizontalStrut(3));  
        box.add(showPic);  
        
       				
		this.getContentPane().setLayout(null);
		gruopListPane.setBounds(5,2, 150, 440);
		messagescrollPane.setBounds(160,2, 440, 300);
		writeMessageArea.setBounds(162,334, 435, 80);
		send.setBounds(525,419, 65, 20);
		box.setBounds(160,302,435,30);
		
		gruopListPane.getViewport().setOpaque(false);
		messagescrollPane.getViewport().setOpaque(false);
		writeMessageArea.setOpaque(false);
		showMessagePane.setOpaque(false);
		box.setOpaque(false);
				
		
		this.getContentPane().add(messagescrollPane);
		this.getContentPane().add(send);
		this.getContentPane().add(gruopListPane);
		this.getContentPane().add(writeMessageArea);
		this.getContentPane().add(box);
		
		Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-610)/2;
        int y=(height-500)/2;
        this.setLocation(x,y);
        this.setSize(610,500);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle(groupName);
        
        this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
				ulf.getGroupFrames().remove(groupName);
			}
		});
    	this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
			}
		});
    	
     Component[] childrens = this.getContentPane().getComponents();
		
		for(Component children:childrens){
			children.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					 picWindow.setVisible(false);	
					 picWindowIsOpen = false;
				}
			});
		}
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				picWindow.dispose();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				picWindow.dispose();
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				picWindow.dispose();
			}			
		});
	}
	
	private synchronized  void sendMessage(String strMessage,User curClickedUser,String groupName){
		
		try {							
			if(curClickedUser.getStatus().equals("Y")){
				Pact pact = new Pact();
				pact.setGroupName(groupName);
				pact.setSourceIP(DataUtil.getHostIPAndName()[1]);
				pact.setSourcePort(curUser.getPort());
				pact.setTargeIP(curClickedUser.getIp());
				pact.setTargePort(curClickedUser.getPort());
				pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
				pact.setChatType(Constants.CON_CHAT_TYPE_GROUP);
				@SuppressWarnings("unused")
				SendMessage send = new SendMessage(serverIP,serverPort,strMessage,pact);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * 获取所需要的文字设置，包括表情全部以字符串的形式保存
	 * 
	 * @return FontAttrib
	 */
	private StyleUtil getFontAttrib() {
		StyleUtil att = new StyleUtil();
		att.setMsg(writeMessageArea.getText()+"*"+buildPicInfo());//文本和表情信息
		att.setName((String) fontName.getSelectedItem());
		att.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
		String temp_color = (String) fontColor.getSelectedItem();
		if (temp_color.equals("黑色")) {
			att.setColor(new Color(0, 0, 0));
		} else if (temp_color.equals("红色")) {
			att.setColor(new Color(255, 0, 0));
		} else if (temp_color.equals("蓝色")) {
			att.setColor(new Color(0, 0, 255));
		} else if (temp_color.equals("黄色")) {
			att.setColor(new Color(255, 255, 0));
		} else if (temp_color.equals("绿色")) {
			att.setColor(new Color(0, 255, 0));
		}
		return att;
	}
	
	/**
	 * 重组发送的表情信息
	 * @return 重组后的信息串  格式为   位置|代号+位置|代号+……
	 */
	private String buildPicInfo(){
		StringBuilder sb = new StringBuilder("");
		  for(int i = 0; i < this.writeMessageArea.getText().length(); i++){ 
              if(docMsg.getCharacterElement(i).getName().equals("icon")){
            	  Icon icon = StyleConstants.getIcon(writeMessageArea.getStyledDocument().getCharacterElement(i).getAttributes());
            	  ChatPic cupic = (ChatPic)icon;
            	  PicInfo picInfo= new PicInfo(i,cupic.getIm()+"");
            	  myPicInfo.add(picInfo);
            	  sb.append(i+"&"+cupic.getIm()+"+");
             } 
          }
		  System.out.println(sb.toString());
		  return sb.toString();
		 
	}
	/**
	 * 点火的时候调用
	 * @param content
	 */
	public void loadMessage(String content){
		showMessagePane.setText(content);
		writeMessageArea.setText("");
	}
	

	
   
	
}
