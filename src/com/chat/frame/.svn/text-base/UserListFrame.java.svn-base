package com.chat.frame;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.LoginInfo;
import com.chat.bean.User;
import com.chat.client.GroupPlayThread;
import com.chat.client.ImagePlayThread;
import com.chat.client.Listener;
import com.chat.client.RefreshClient;
import com.chat.client.UserPlayThread;
import com.chat.ui.BackgroundPane;
import com.chat.ui.GroupListUI;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.JsonUtil;
import com.chat.util.MessageContainerUtil;
import com.chat.util.MessageUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.ToMinimizeUtil;
import com.even.ReceiveGroupMessageEven;
import com.even.ReceiveMessageEven;
import com.even.ReceiveRefreshMessageEvent;
import com.listener.ReceiveGroupMessageListener;
import com.listener.ReceiveMessageListener;
import com.listener.ReceiveRefreshMessageListener;

public class UserListFrame  extends SuperFrame{
	
	private static final long serialVersionUID = 1L;
	private JScrollPane userListPane,groupListPane;
	private JList person;
	private GroupListUI group;
	private JButton addGroup;
	private List<User> users;
	private List<DiscussGroup>  discussGroup;
	private JLabel curUserLabel,username,userno,viewLeaveMessage,msgLen;	
	private UserListFrame f = null;
	private  Boolean isMinimize = false;
	private ImagePlayThread imgTagThread;
	private GroupPlayThread groupPlayThread;
	private UserPlayThread userPlayThread;
	private Image imgEnable,imgDisable,curUserImage;	 
	private TrayIcon trayIcon;
	private User curUser;
	private JTabbedPane tabPane;
	private int  index = 0;
    private Box box;
	
	private List<ReceiveMessageListener> receiveListenerList = null;
	private ReceiveMessageListener messageListener;
	private List<ReceiveGroupMessageListener> receiveGroupListenerList = null;
	private ReceiveGroupMessageListener groupMessageListener;
	private List<ReceiveRefreshMessageListener> receiveRefreshListenerList = null;
	private ReceiveRefreshMessageListener receiveRefreshMessageListener;
	private Map<String,ChatFrame> useFrames = new HashMap<String,ChatFrame>();
	private Map<String,ChatGroupFrame> groupFrames = new HashMap<String,ChatGroupFrame>();
	private Map<String,GroupPlayThread> groupPlayThreads = new HashMap<String,GroupPlayThread>();
	private Map<String,UserPlayThread> userPlayThreads = new HashMap<String,UserPlayThread>();
//	private List<LeaveMessage> lm = PersistenceContext.getEntity().getLeaveMessages();
	private String[]  messageKey = null;
	
	
	 public Map<String, ChatGroupFrame> getGroupFrames() {
		return groupFrames;
	}
	
	public UserListFrame(User curUser,List<User> users,List<DiscussGroup> discussGroup){
		 		this.curUser = curUser;
		 		this.users = users;
		 		this.discussGroup = discussGroup;
				initImg();
				initLeaveMessage(); //先把留言放入内存
				getMsgKey();  //从内存得到key集，然后放入数组
				initComp();
				initAccept();
				PersistenceContext.getEntity().setUserListFrame(f);		
		}
	 
	/**
	 * 从内存得到key集，然后放入数组
	 */
	public void getMsgKey(){
		Object[] obj =MessageContainerUtil.getRecordLeaveMsgs().keySet().toArray();
		messageKey = new String[obj.length];
		for(int i = 0 ; i<obj.length;i++){
			messageKey[i] = obj[i].toString();
		}
	}
	
	 public void initComp() {
			f = this;
		    f.setTitle("山寨QQ2012");	
		    BackgroundPane   p   =   new   BackgroundPane("");  //设置背景图片为""表示不设置
            this.getContentPane().add(p);
            Toolkit kit = Toolkit.getDefaultToolkit();
            
            byte[] curUserImg = curUser.getUserImage();          
            curUserImage = kit.createImage(curUserImg);
            Icon icon2 = new ImageIcon(curUserImage);
    		curUserLabel = new JLabel(icon2);   		  		
    		username = new JLabel("姓名:"+curUser.getUsername()); 		
    		userno = new JLabel("员工号:"+curUser.getNo()); 
    		
    		Image viewMsg=kit.getImage(DataUtil.getImgPath(getClass(),"leaveMessage.png"));
    		Icon icon3 = new ImageIcon(viewMsg);
    		viewLeaveMessage = new JLabel(icon3); 
    	
    		
    		msgLen = new JLabel();
    	    box = Box.createHorizontalBox(); 
    		box.add(viewLeaveMessage);
    		if(messageKey.length != 0){   			
    			msgLen.setText(messageKey.length+"");    			
    			box.add(msgLen);            						
    		}else{
    			box.setVisible(false);
    		}      		
    		box.addMouseListener(new MouseAdapter() {															
				@Override
				public void mouseExited(MouseEvent e) {
					box.setBorder(null);					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					box.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 250)));	
					box.setToolTipText("查看留言");
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					   if(index<messageKey.length){
								String user =messageKey[index].split("@")[1];
								User u = (User) JsonUtil.getObject4JsonString(user,User.class);													
							    //读留言
						    	LeaveMessageFrame lmf = new LeaveMessageFrame(curUser,u,"close");
							    MessageContainerUtil.writeLeaveMsgToPane(u, lmf.getMessage());
							    index = index+1;
							    msgLen.setText((messageKey.length-index)+"");
							     //清空内存的留言
							   MessageContainerUtil.cleanRecordMsgs(u);
							   if(msgLen.getText().equals("0")){
								   box.setVisible(false);								   
							   }
							
					   }else{
						 //清空持久化上下文的留言
							PersistenceContext.getEntity().setLeaveMessages(null);							
					   }
						
					
					
				}
			});
    		
			person = new JList();		
			DataUtil.setListByUsers(users, person,-1);
			person.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置单一选择模式（每次只能 选中一个）
            person.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {				
						if(e.getClickCount() ==2){						
								Object  u =	person.getSelectedValue();		
								if(u != null){
									User curClickedUser = null;
									int index = u.toString().indexOf("(");
									if(index > -1){
									    String uno = u.toString().substring(index+1,u.toString().length()-1);
									    
									    if(userPlayThreads.containsKey(uno)){
									    	userPlayThreads.get(uno).setStatus(0);
									    	userPlayThreads.remove(uno);
									    }									    
								    	List<User> users = PersistenceContext.getEntity().getUsers();
								    	for(User user:users){
								    		if(user.getNo().equals(uno)){
								    			curClickedUser = user;
								    		}
								    	}
									}																	
									if(curClickedUser.getStatus().equals("Y")){		
										String targeIp = curClickedUser.getIp();
										if(useFrames.containsKey(targeIp)){
											return;
										}else{	
											    ChatFrame useFrame = new ChatFrame(curUser,curClickedUser,f);
												MessageContainerUtil.writeRecordToPane(curUser, curClickedUser, useFrame.getShowMessagePane());
												useFrames.put(targeIp, useFrame);
										}
									} else{                                 
											//我要留言											
											new LeaveMessageFrame(curUser,curClickedUser,"send");																																		
									}		
								}								      
					  }												
			 }								
		});
            
            group = new GroupListUI();
            DataUtil.setListByGroups(discussGroup, f, -1);
            group.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);                               
            group.addMouseListener(new MouseAdapter(){
            	@Override
				public void mouseClicked(MouseEvent e) {
            		if(e.getClickCount() ==2){
            			Object  u =	group.getSelectedValue();
            			if(u != null){
            				
							DiscussGroup discussGroup = null;
							List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
							List<User> users = PersistenceContext.getEntity().getUsers();
							List<User> groupUsers = new ArrayList<User>();
							
							for(DiscussGroup dg:discussGroups){
								if(dg.getDiscussGroupName().equals(u.toString())){
									discussGroup = dg;
									break;
								}
							}
							String strUserNos = discussGroup.getUserNos();
					
							for(int i = 0; i < users.size(); i++){
								if(strUserNos.indexOf(users.get(i).getNo())>-1 && !users.get(i).getNo().equals(curUser.getNo())){
									groupUsers.add(users.get(i));
								}					
							}
							
							String frameName = u.toString();
							if(!groupFrames.containsKey(frameName)){
								ChatGroupFrame cgf = new ChatGroupFrame(groupUsers,curUser,frameName,f,discussGroup);
								cgf.initComp();
								groupFrames.put(frameName, cgf);
								MessageContainerUtil.writeRecordToGroupPane(curUser, u.toString(), cgf.getShowMessagePane());
							} 
							if(null != groupPlayThreads.get(u.toString())){
								groupPlayThreads.get(u.toString()).setStatus(0);
								groupPlayThreads.remove(u.toString());
								RefreshClient.refresh(getCurUser(), f);
							}
            			}       			
            		}
            	}
            	
            	@Override
            	public void mouseReleased(MouseEvent e) {
            		JPopupMenu popupMenu = new JPopupMenu();
            		JMenuItem menuEdit = new JMenuItem("修改该群");
            		JMenuItem menuDrop = new JMenuItem("删除该群");
            		
            		popupMenu.add(menuEdit);
            		popupMenu.add(menuDrop);
          			group.add(popupMenu);
            		
            		int index = group.locationToIndex(e.getPoint()); 
            		group.setSelectedIndex(index);             		
            		final Object groupName = group.getSelectedValue();           		
            		menuDrop.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							DataUtil.sendDeleteGroupInfo(PersistenceContext.getEntity().getServerIP(),PersistenceContext.getEntity().getServerPort()-1, groupName.toString());
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
									RefreshClient.refresh(curUser, f);
									JOptionPane.showMessageDialog(f, "删除成功");
									if(groupFrames.containsKey(groupName.toString())){
										ChatGroupFrame groupFrame = groupFrames.get(groupName.toString());
										groupFrame.dispose();
										groupFrames.remove(groupName.toString());
									}
									
									break;	
								}else if(PersistenceContext.getEntity().getDeleteGroupStatus() == Constants.CON_DELETE_GROUP_STATUS_FAILTURE){
									JOptionPane.showMessageDialog(f, "删除失败");
									break;
								}
							}
						}	
          			});
            		
            		menuEdit.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							DiscussGroup discussGroup = null;
							List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
							
							for(DiscussGroup dg:discussGroups){
								if(dg.getDiscussGroupName().equals(groupName.toString())){
									discussGroup = dg;
									break;
								}
							}
							new ConfigChatGroupFrame(curUser,discussGroup).init();
						}
            		});
            		
            		
            		DiscussGroup discussGroup = null;
            		List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
            		for(DiscussGroup dg:discussGroups){
						if(dg.getDiscussGroupName().equals(groupName.toString())){
							discussGroup = dg;
							break;
						}
					}
					if(discussGroup != null && discussGroup.getGroupMaster().equals(curUser.getNo())){
						if(e.getButton() == 3 && group.getSelectedIndex() >=0)   {//判断是否是鼠标右键 
	            			popupMenu.show(group, e.getX(), e.getY());        			
	            		}     
					}
            	}
            });
    
			addGroup = new JButton("创建讨论组");						
            addGroup.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
				        new ConfigChatGroupFrame(curUser,f).init();
					
				}
			});			         
            
			groupListPane = new JScrollPane(group);			
			userListPane = new JScrollPane(person);										
			tabPane = new JTabbedPane();
			tabPane.addTab("用户列表", userListPane);
			tabPane.addTab("讨论组", groupListPane);
			
			
			curUserLabel.setBounds(5, 10, 60, 60);
			username.setBounds(70, 10, 100, 20);
			userno.setBounds(70, 35, 100, 20);   	
			box.setBounds(180, 65, 33, 20); 
			addGroup.setBounds(0, 548, 220, 20);		
			tabPane.setBounds(2,87, 220, 462);
  
			curUserLabel.setOpaque(false);
			username.setOpaque(false);
			userno.setOpaque(false);	
			viewLeaveMessage.setOpaque(false);
			msgLen.setOpaque(false);
			box.setOpaque(false);
			tabPane.setOpaque(false);
			userListPane.setOpaque(false);
			groupListPane.setOpaque(false);
			groupListPane.getViewport().setOpaque(false);
			userListPane.getViewport().setOpaque(false);
			
			
			p.setLayout(null);			
			p.add(addGroup);
			
			p.add(tabPane);	
			p.add(curUserLabel);
            p.add(username);
            p.add(userno);
        	p.add(box);
        	
            
	        this.setBounds(1000, 100, 230, 600);
	        this.setResizable(false); 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.addWindowListener(new WindowAdapter(){		
				 @Override
				 public void windowIconified(WindowEvent e) { // 窗口最小化事件
				      f.setVisible(false);			      
				      ToMinimizeUtil.remove();
				      ToMinimizeUtil.initTray(f);
				      ToMinimizeUtil.miniTray(f);			    
				     }
				 @Override
				 public void windowClosing(WindowEvent e){
					 LoginInfo loginInfo = new LoginInfo();
					 loginInfo.setPassword(curUser.getPassword());
					 loginInfo.setUserName(curUser.getUsername());
					 loginInfo.setUserNo(curUser.getNo());
					 DataUtil.sendOperationRequest(PersistenceContext.getEntity().getServerIP(),PersistenceContext.getEntity().getServerPort()-1, loginInfo);
					 System.exit(0);
				 }
			});
			
			/**
			 * Receive Listener
			 */
			receiveListenerList = new ArrayList<ReceiveMessageListener>();
			messageListener = new ReceiveMessageListener() {
				@Override
				public void receiveMessage(ReceiveMessageEven e) {
					if(getIsMinimize() == true){                                            //判断是否是最小化，若是，那么右下角的图标会闪动
						imgTagThread = new ImagePlayThread(f);  //启动变换小图标的线程
						imgTagThread.start();
					}
					
					/**
					 * 处理监听
					 */
					User targeUser = e.getTargeUser();
					User sourceUser = e.getSourceUser();
					String sourceIp = sourceUser.getIp();
					String message = e.getMessage();
				
					MessageContainerUtil.loadMessage(sourceUser,targeUser,DataUtil.msgToStyleUtil(message),"FRIEND");
					
					ChatFrame useFrame = useFrames.get(sourceIp);
					if(useFrame != null){						
						 MessageUtil mu = new MessageUtil(useFrame);
						 mu.addRecMsg(sourceUser, DataUtil.msgToStyleUtil(message));
					}else{
						/**
						 * 闪烁 未打开窗体
						 * 如果已经闪烁就不用在执行线程了
						 */
						if(null == userPlayThreads.get(e.getSourceUser().getNo())){
							userPlayThread = new UserPlayThread(f,e.getSourceUser().getNo());
							userPlayThread.start();
							userPlayThreads.put(e.getSourceUser().getNo(), userPlayThread);
						}
					}
				}
			};
			
			/**
			 * ReceiveGroup Listener
			 */
			receiveGroupListenerList = new ArrayList<ReceiveGroupMessageListener>();
			groupMessageListener = new ReceiveGroupMessageListener(){
				@Override
				public void receiveGroupMessage(ReceiveGroupMessageEven e) {
					if(getIsMinimize() == true){                                            //判断是否是最小化，若是，那么右下角的图标会闪动
						imgTagThread = new ImagePlayThread(f);  //启动变换小图标的线程
						imgTagThread.start();
					}
					
					/**
					 * 处理监听
					 */
					User sourceUser = e.getSourceUser();
					String message = e.getMessage();
					MessageContainerUtil.loadGroupMessage(e.getGroupName(), sourceUser, DataUtil.msgToStyleUtil(message),"FRIEND");
					
					ChatGroupFrame groupFrame = groupFrames.get(e.getGroupName());
					if(groupFrame != null){
						 MessageUtil mu = new MessageUtil(groupFrame);
						 mu.addRecMsg(sourceUser, DataUtil.msgToStyleUtil(message));
					}else{
						/**
						 * 闪烁 未打开窗体
						 * 如果已经闪烁就不用在执行线程了
						 */
						if(null == groupPlayThreads.get(e.getGroupName())){
							groupPlayThread = new GroupPlayThread(f,e.getGroupName());
							groupPlayThread.start();
							groupPlayThreads.put(e.getGroupName(), groupPlayThread);
						}
					}
				}
			};
			
			/**
			 * ReceiveRefresh Listener
			 */
			receiveRefreshListenerList = new ArrayList<ReceiveRefreshMessageListener>();
			receiveRefreshMessageListener = new ReceiveRefreshMessageListener(){

				@Override
				public void receiveRefreshMessage(ReceiveRefreshMessageEvent event) {
					//关闭已经被提出讨论组的窗口
					 List<DiscussGroup> groups = PersistenceContext.getEntity().getGroups();
					 Map<String,DiscussGroup> map = new HashMap<String,DiscussGroup>();
					 for(int i = 0; i < groups.size(); i++){
						 map.put(groups.get(i).getDiscussGroupName(), groups.get(i));
					 }
					 
					Set<String> groupNames = groupFrames.keySet();
					 
					
					for (@SuppressWarnings("rawtypes")
					Iterator iterator = groupNames.iterator(); iterator.hasNext();) {
						String string = (String) iterator.next();
						if(!map.containsKey(string)){
							 ChatGroupFrame groupFrame = groupFrames.get(string);
							 groupFrame.dispose();
							 groupFrames.remove(string);
						 }
					}
					 
					RefreshClient.refresh(curUser, f);		
				}
			};
			
			this.addReceiveMessageLister(messageListener);
			this.addReceiveGroupMessageLister(groupMessageListener);
			this.addReceiveRefreshMessageListener(receiveRefreshMessageListener);
		}
	
		/**
		 * 开启客户端接收消息的线程和开启客户端监听好友在线状态的线程
		 */
		private void initAccept(){
			int curUserPort = curUser.getPort();
			Listener.getInstance().startReceiveListener(curUserPort, Constants.CON_CLIENT_FILE_SAVEPATH,this);
			RefreshClient.refresh(curUser, f);
		}
		
		/**
		 * 初始化系统托盘信息，当窗口最小化的时候，系统托盘显示的图标为pccw.gif
		 * 当消息来时，图标切换
		 */
		public void initImg(){
			Toolkit kit=Toolkit.getDefaultToolkit();
			 imgEnable=kit.getImage(DataUtil.getImgPath(getClass(),"pccw.gif"));
			 imgDisable=kit.getImage(DataUtil.getImgPath(getClass(),"6.gif"));
			 trayIcon = new TrayIcon(imgEnable, "chatFrame");
		}
		
		/**
		 * 监听接收消息 进行点火触发
		 * @param e
		 */
		public void fireReceiveEven(ReceiveMessageEven e){
			if(receiveListenerList != null && receiveListenerList.size() >0){
				for(ReceiveMessageListener listener: receiveListenerList){
					listener.receiveMessage(e);
				}
			}
		}
		
		/**
		 * 监听讨论组消息，点火触发
		 * @param e
		 */
		public void fireReceiveGroupEvent(ReceiveGroupMessageEven e){
			if(receiveGroupListenerList != null && receiveGroupListenerList.size() >0){
				for(ReceiveGroupMessageListener listener: receiveGroupListenerList){
					listener.receiveGroupMessage(e);
				}
			}
		}  
		
		/**
		 * 监听讨论组消息，点火触发
		 * @param e
		 */
		public void fireReceiveRefreshEvent(ReceiveRefreshMessageEvent e){
			if(receiveRefreshListenerList != null && receiveRefreshListenerList.size() >0){
				for(ReceiveRefreshMessageListener listener: receiveRefreshListenerList){
					listener.receiveRefreshMessage(e);
				}
			}
		}  
		
		/**
		 * 处理没有读的留言，将每个人发送的留言分别存入相应的内存中
		 */
		public void initLeaveMessage(){
			List<LeaveMessage> lm = PersistenceContext.getEntity().getLeaveMessages();	

			  for(int i=0; i<lm.size(); i++){						  
				  MessageContainerUtil.loadLeaveMessage(lm.get(i));			
			  }			 
		}
		
		
		
		public List<DiscussGroup> getDiscussGroup() {
			return discussGroup;
		}
		public void setDiscussGroup(List<DiscussGroup> discussGroup) {
			this.discussGroup = discussGroup;
		}
	   public Map<String, ChatFrame> getUseFrames() {
			return useFrames;
		}
		public void setUseFrames(Map<String, ChatFrame> useFrames) {
			this.useFrames = useFrames;
		}
		
		public GroupListUI getGroup() {
			return group;
		}
		public void setGroup(GroupListUI group) {
			this.group = group;
		}
		public JList getPerson() {
			return person;
		}
		public void setPerson(JList person) {
			this.person = person;
		}
		public TrayIcon getTrayIcon() {
			return trayIcon;
		}
		public void setTrayIcon(TrayIcon trayIcon) {
			this.trayIcon = trayIcon;
		}
		public Boolean getIsMinimize() {
			return isMinimize;
		}
		public void setIsMinimize(Boolean isMinimize) {
			this.isMinimize = isMinimize;
		}
		public ImagePlayThread getImgTagThread() {
			return imgTagThread;
		}
		public void setImgTagThread(ImagePlayThread imgTagThread) {
			this.imgTagThread = imgTagThread;
		}
		public Image getImgEnable() {
			return imgEnable;
		}
		public void setImgEnable(Image imgEnable) {
			this.imgEnable = imgEnable;
		}
		public Image getImgDisable() {
			return imgDisable;
		}
		public void setImgDisable(Image imgDisable) {
			this.imgDisable = imgDisable;
		}
		
		public void addReceiveMessageLister(ReceiveMessageListener listener){
			this.receiveListenerList.add(listener);
		}
		
		public void removeReceiveMessageLister(ReceiveMessageListener listener){
			this.receiveListenerList.remove(listener);
		}
		
		public void addReceiveGroupMessageLister(ReceiveGroupMessageListener listener){
			this.receiveGroupListenerList.add(listener);
		}
		
		public void removeReceiveGroupMessageLister(ReceiveGroupMessageListener listener){
			this.receiveGroupListenerList.remove(listener);
		}
		
		public void addReceiveRefreshMessageListener(ReceiveRefreshMessageListener listener){
			this.receiveRefreshListenerList.add(listener);
		}
		
		public void removeReceiveRefreshMessageListener(ReceiveRefreshMessageListener listener){
			this.receiveRefreshListenerList.remove(listener);
		}
		
		public User getCurUser() {
			return curUser;
		}
		
		public List<User> getUsers() {
			return users;
		}
		public void setUsers(List<User> users) {
			this.users = users;
		}
	
		
		/**
		 * 主方法，可以直接启动不获取本人的在线信息
		 * @param args
		 */
//		@SuppressWarnings("unused")
//	    public static void main(String[] args){
//			UserListFrame frame = new UserListFrame();
//		}
}
