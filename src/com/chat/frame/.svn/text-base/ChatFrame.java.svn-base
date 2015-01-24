package com.chat.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.chat.bean.ChatPic;
import com.chat.bean.PicInfo;
import com.chat.bean.User;
import com.chat.client.ImagePlayThread;
import com.chat.client.SendMessage;
import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.CoolToolTip;
import com.chat.util.DataUtil;
import com.chat.util.MessageContainerUtil;
import com.chat.util.MessageUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.PicsJWindow;
import com.chat.util.StyleUtil;


public class ChatFrame extends SuperFrame{

	private static final long serialVersionUID = 1L;
	private User curUser, curClickedUser;
	private JLabel curUserLabel, curClickedUserLabel,volumnLabel;
	private JScrollPane showMessageScrollPane;
	private JButton send, close;
	private JSlider volumnSlider;
	private ChatFrame chatFrame;
	private Boolean isMinimize = false;
	private ImagePlayThread imgTagThread;
	private Image imgEnable, imgDisable, curUserImage, clickedUserImage;
	private TrayIcon trayIcon;
	private String serverIP = PersistenceContext.getEntity().getServerIP();
	private int serverPort = PersistenceContext.getEntity().getServerPort();
	private UserListFrame ulf;
	private JMenu menuFile,menuVoice;
	private JMenuBar menuBar;
	private JMenuItem sendFile,startChatVoice,stopChatVoice;	
    private JComboBox fontName = null, fontSize = null,fontColor = null;   /*字体名称;字号大小;文字颜色*/  	
	private StyledDocument docMsg = null; 
    private PicsJWindow picWindow;   /*表情框*/
    private CoolToolTip error_tip;   /* 错误信息气泡提示*/ 
    private StyleUtil myStyle = null;
    private static final Color TIP_COLOR = new Color(255, 255, 225); 
    private Box  box,volumnBox;
	private int prepareFileStatus,prepareVoiceChat;
	private boolean isReceiveFile = false;
	private boolean isReceiveVoiceChat = false;
	private FloatControl floatControl ;

	public ChatFrame(User curUser, Object curClickedUser, UserListFrame ulf) {
		this.curUser = curUser;
		this.curClickedUser = (User) curClickedUser;
		this.ulf = ulf;
		if (curClickedUser != null) {
			intChatFrame();
		}
	}

	public void intChatFrame() {
		chatFrame = this;
		chatFrame.setTitle("与" + curClickedUser.getUsername() + "("+ curClickedUser.getNo() + ")对话中");
           
		showMessagePane = new JTextPane();				
		showMessagePane.setEditable(false);
		showMessagePane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
			}
		});
		showMessageScrollPane = new JScrollPane(showMessagePane);	
		showMessageScrollPane.setBorder(BorderFactory.createTitledBorder("消息记录"));
		showMessageScrollPane.setHorizontalScrollBar(null);
		
		writeMessageArea = new JTextPane();
		writeMessageArea.setBorder(BorderFactory.createTitledBorder(""));
		docMsg = writeMessageArea.getStyledDocument();		
		error_tip = new CoolToolTip(this, writeMessageArea, TIP_COLOR, 3, 10);  
		writeMessageArea.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseEntered(MouseEvent e) {
				error_tip.setVisible(false);		
			}
		});
		
		send = new JButton("发送");
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String strMessage = writeMessageArea.getText();
				
				if(strMessage == null || strMessage.equals("")){
					error_tip.setText("发送内容不能为空");
					error_tip.setVisible(true);
					return ;
				}else{
					myStyle = getFontAttrib();   
					MessageContainerUtil.loadMessage(curUser, curClickedUser,myStyle,"ME");
				
					if (sendMessage(myStyle.toString(), curClickedUser)) {
						MessageUtil mu = new MessageUtil(chatFrame);
						mu.addMeg(curUser,myStyle);
						writeMessageArea.setText("");
					}
				}
			}
		});
		
		
		menuFile = new JMenu("文件");
		menuFile.setIcon(new ImageIcon(DataUtil.getImgPath(getClass(),"file.gif")));
		menuFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
			}
		});
		
		
		menuBar = new JMenuBar();
		sendFile = new JMenuItem("传送文件");		
		menuFile.add(sendFile);
		sendFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				String path = null;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("请选择要上传的文件...");
				fileChooser.setApproveButtonText("确定");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(chatFrame);
				if (JFileChooser.APPROVE_OPTION == result) {
					path = fileChooser.getSelectedFile().getPath();
					System.out.println("path: " + path);
					if (sendFile(path)) {
						return;
					}

				}

			}
		});
		
		menuVoice = new JMenu("语音");
		menuVoice.setIcon(new ImageIcon(DataUtil.getImgPath(getClass(),"voice.png")));
		startChatVoice = new JMenuItem("开始语音聊天");
		stopChatVoice = new JMenuItem("结束语音聊天");
		menuVoice.add(startChatVoice);
		menuVoice.add(stopChatVoice);	
		stopChatVoice.setEnabled(false);
		startChatVoice.addActionListener(new ActionListener() {
			//开始语音事件
			@Override
			public void actionPerformed(ActionEvent e) {
				sendVoiceChat();
				
			}
		});	
		stopChatVoice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//结束语音聊天线程
				DataUtil.stopVoice(curUser.getIp(),curUser.getPort(),curClickedUser.getIp(),curClickedUser.getPort());
				String ms = "您取消了语音！";
				StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
				MessageContainerUtil.insert(showMessagePane, su);
				startChatVoice.setEnabled(true);
				stopChatVoice.setEnabled(false);
				chatFrame.getVolumnBox().setVisible(false);
				DataUtil.flag = 1;
			}
		});
		
		menuBar.add(menuFile);
		menuBar.add(menuVoice);
		this.setJMenuBar(menuBar);
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		
		/**
		 * 设置音量部分 初始化音量进度条
		 */
		volumnLabel = new JLabel();
		Image img = kit.getImage(DataUtil.getImgPath(getClass(),"volumn.png"));
		Icon icon3 = new ImageIcon(img);
		volumnLabel.setIcon(icon3);
		volumnSlider = new JSlider (JSlider.HORIZONTAL, -80, 6, 6);//设置方向,最小值，最大值，初始值
        volumnSlider.setMinimum((int) -80);
		
		volumnSlider.setMaximum((int) 6);
		volumnBox =Box.createHorizontalBox();
		volumnBox.add(volumnLabel);
		volumnBox.add(volumnSlider);
		volumnBox.setVisible(false);
		volumnSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int volumnSize = volumnSlider.getValue();
				System.out.println("音量大小是："+volumnSize);
				//得到control，将音量的值付给他								
				chatFrame.getFloatControl().setValue(volumnSize);							
			}
		});			
								
		byte[] clickedUserImg = curClickedUser.getUserImage();
		byte[] curUserImg = curUser.getUserImage();
		clickedUserImage = kit.createImage(clickedUserImg);
		curUserImage = kit.createImage(curUserImg);

		Icon icon1 = new ImageIcon(clickedUserImage);
		Icon icon2 = new ImageIcon(curUserImage);
		curUserLabel = new JLabel(icon2);
		curClickedUserLabel = new JLabel(icon1);
		close = new JButton("关闭");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chatFrame.setVisible(false);
				ulf.getUseFrames().remove(curClickedUser.getIp());
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
		showMessageScrollPane.setBounds(5, 5, 440, 300);
		writeMessageArea.setBounds(7, 337, 437, 80);
		curClickedUserLabel.setBounds(460, 30, 100, 100);
		curUserLabel.setBounds(460, 315, 100, 100);
		close.setBounds(310, 420, 60, 20);
		send.setBounds(380, 420, 60, 20);
		box.setBounds(7, 307, 437, 25);
		volumnBox.setBounds(460,200,120,20);
		
		showMessageScrollPane.setOpaque(false);
		showMessageScrollPane.getViewport().setOpaque(false);
		writeMessageArea.setOpaque(false);
		showMessagePane.setOpaque(false);
		curClickedUserLabel.setOpaque(false);
		curUserLabel.setOpaque(false);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		send.setOpaque(false);
		send.setContentAreaFilled(false);
		box.setOpaque(false);
		menuBar.setOpaque(false);
		menuFile.setOpaque(false);
		fontName.setOpaque(false);
		fontSize.setOpaque(false);
		fontColor.setOpaque(false);
		volumnBox.setOpaque(false);
		
		this.getContentPane().add(showMessageScrollPane);
		this.getContentPane().add(writeMessageArea);
		this.getContentPane().add(curUserLabel);
		this.getContentPane().add(curClickedUserLabel);
		this.getContentPane().add(close);
		this.getContentPane().add(send);
		this.getContentPane().add(box);
		this.getContentPane().add(volumnBox);


		this.setIconImage(clickedUserImage);
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width - 540) / 2;
		int y = (height - 510) / 2;
		this.setLocation(x, y);
		this.setSize(610, 500);
		this.setResizable(false);
		this.setVisible(true);
		
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 picWindow.setVisible(false);	
				 picWindowIsOpen = false;
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				 picWindow.setVisible(false);	
				chatFrame.setVisible(false);
				ulf.getUseFrames().remove(curClickedUser.getIp());
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


	/**
	 * 判断好友是否在线
	 * 
	 * @param strMessage
	 * @param curClickedUser
	 * @return
	 */
	@SuppressWarnings("unused")
	private synchronized boolean sendMessage(String strMessage,
			User curClickedUser) {

		try {
			if (curClickedUser.getStatus().equals("Y")) {
				Pact pact = new Pact();
				pact.setSourceIP(DataUtil.getHostIPAndName()[1]);
				pact.setSourcePort(curUser.getPort());
				pact.setTargeIP(curClickedUser.getIp());
				pact.setTargePort(curClickedUser.getPort());
				pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
				pact.setChatType(Constants.CON_CHAT_TYPE_MESSAGE);
				SendMessage send = new SendMessage(serverIP, serverPort,
						strMessage, pact);
				return true;
			} else {
				JOptionPane.showMessageDialog(this, "好友不在线!!!暂时不能发送消息~");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *  Send file 
	 * @param filePath
	 * @return
	 */
	private synchronized boolean sendFile(String filePath) {
		try {
			this.setPrepareFileStatus(Constants.CON_CHAT_PREPARE_DEFAULT);
			System.out.println("发送握手协议-Prepare");
			DataUtil.sendHandPrepare(curUser.getIp(),curUser.getPort(),curClickedUser.getIp(),curClickedUser.getPort());
			System.out.println("根据接收到的握手回执，判定是否传输文件");
			while (true) {
				Thread.sleep(100);
				System.out.println("isPrepareFile="+prepareFileStatus);
				if(prepareFileStatus == Constants.CON_CHAT_PREPARE_Y){
					DataUtil.sendFile(curUser.getIp(),curUser.getPort(),curClickedUser.getIp(),curClickedUser.getPort(),filePath);
					break;
				}else if(prepareFileStatus == Constants.CON_CHAT_PREPARE_N){
					break;
				}else if(prepareFileStatus == Constants.CON_CHAT_PREPARE_DEFAULT){
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 *  Send Voice 
	 * @param filePath
	 * @return
	 */
	private synchronized boolean sendVoiceChat() {
		try {
			this.setPrepareVoiceChat(Constants.CON_CHAT_CHAT_VOICE_DEFAULT);
			System.out.println("发送语音聊天握手协议-Prepare");
			DataUtil.sendHandVoiceChat(curUser.getIp(),curUser.getPort(),curClickedUser.getIp(),curClickedUser.getPort());
			System.out.println("根据接收到的握手回执，判定是否进行语音聊天");
			while (true) {
				Thread.sleep(100);
				prepareVoiceChat = this.getPrepareVoiceChat();
				System.out.println("isVoiceChat="+prepareVoiceChat);
				if(prepareVoiceChat == Constants.CON_CHAT_CHAT_VOICE_Y){
					DataUtil.sendVoice(curUser.getIp(),curUser.getPort(),curClickedUser.getIp(),curClickedUser.getPort());
					break;
				}else if(prepareVoiceChat == Constants.CON_CHAT_CHAT_VOICE_N){
					break;
				}else if(prepareVoiceChat == Constants.CON_CHAT_CHAT_VOICE_DEFAULT){
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

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
	 * 点火的时候调用
	 * 
	 * @param content
	 */
	public void loadMessage(String content) {
		showMessagePane.setText(content);
		writeMessageArea.setText("");
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

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public void setTrayIcon(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	public int getPrepareFileStatus() {
		return prepareFileStatus;
	}

	public void setPrepareFileStatus(int prepareFileStatus) {
		this.prepareFileStatus = prepareFileStatus;
	}

	public boolean isReceiveFile() {
		return isReceiveFile;
	}

	public void setReceiveFile(boolean isReceiveFile) {
		this.isReceiveFile = isReceiveFile;
	}

	public int getPrepareVoiceChat() {
		return prepareVoiceChat;
	}

	public void setPrepareVoiceChat(int prepareVoiceChat) {
		this.prepareVoiceChat = prepareVoiceChat;
	}

	public boolean isReceiveVoiceChat() {
		return isReceiveVoiceChat;
	}

	public void setReceiveVoiceChat(boolean isReceiveVoiceChat) {
		this.isReceiveVoiceChat = isReceiveVoiceChat;
	}

	public JMenuItem getStartChatVoice() {
		return startChatVoice;
	}

	public void setStartChatVoice(JMenuItem startChatVoice) {
		this.startChatVoice = startChatVoice;
	}

	public FloatControl getFloatControl() {
		return floatControl;
	}

	public void setFloatControl(FloatControl floatControl) {
		this.floatControl = floatControl;
	}

	public Box getVolumnBox() {
		return volumnBox;
	}

	public void setVolumnBox(Box volumnBox) {
		this.volumnBox = volumnBox;
	}

	public JMenuItem getStopChatVoice() {
		return stopChatVoice;
	}

	public void setStopChatVoice(JMenuItem stopChatVoice) {
		this.stopChatVoice = stopChatVoice;
	}



}
