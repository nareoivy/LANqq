package com.chat.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceConstants.ImageWatermarkKind;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

import com.chat.client.Listener;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PicCompression;
import com.chat.util.StringUtil;

@SuppressWarnings("serial")
public class ClientLoginFrame extends javax.swing.JFrame{
	
	private JLabel nolab,passlab,iplab,portlab,imageLabel,registerUser;
	private JTextField nametex,iptex,porttex;
	private JPasswordField passtex;
	private JButton loginbon;
	private JButton configbon;
	private ClientLoginFrame clientLoginFrame;
	
	public ClientLoginFrame(){
		clientLoginFrame = this;
		init();
		initSelfListener();
	}
	
	int count = 0;
	public void init(){
		InitGlobalFont(new Font("alias", Font.PLAIN, 12));  //统一设置字体
	
		nolab = new JLabel("员工号:");
		passlab = new JLabel("密   码:");
		iplab = new JLabel("服务器IP:");
		portlab = new JLabel("服务器端口号:");
		registerUser = new JLabel("注册账号");
		registerUser.setForeground(Color.blue);
		nametex = new JTextField();
		nametex.setText("80352891");
		passtex = new JPasswordField();
		passtex.setText("1");
		iptex = new JTextField();
		porttex = new JTextField();	
		
		/**
		 * INIT Config server parameter
		 */
		iptex.setText(Constants.CON_SERVER_ADDRESS);
		porttex.setText(Constants.CON_SERVER_ACCEPT_PORT+"");
		
		loginbon = new JButton("登录");
		configbon = new JButton("配置");
		
		imageLabel = new JLabel();
		Toolkit kit=Toolkit.getDefaultToolkit();		
		Image image=kit.getImage(DataUtil.getImgPath(getClass(),"2012.jpg"));
		image =  new PicCompression().imageZipProce(image, 760, 300, 1);
		Image qqLogo=kit.getImage(DataUtil.getImgPath(getClass(),"h001.png"));
		this.setIconImage(qqLogo);
		Icon icon1 = new ImageIcon(image);
		imageLabel.setIcon(icon1);					
		
		registerUser.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseExited(MouseEvent e) {
				registerUser.setText("注册账号");
				registerUser.setForeground(Color.blue);				
			}			
			@Override
			public void mouseEntered(MouseEvent e) {
				registerUser.setText("<html><u>注册账号</u><html>");
				registerUser.setForeground(Color.cyan);
				
			}			
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegisterFrame(clientLoginFrame);
				clientLoginFrame.setVisible(false);				
			}
		});
		
		this.getContentPane().setLayout(null);
		
		imageLabel.setBounds(0, 0, 376, 109);
		registerUser.setBounds(290, 129, 60, 20);
		nolab.setBounds(40, 129, 80, 20);
		nametex.setBounds(100, 129, 180, 20);
		passlab.setBounds(40, 159, 80, 20);
		passtex.setBounds(100, 159, 180, 20);
		loginbon.setBounds(230, 189, 80, 25);		
		configbon.setBounds(230, 219, 80, 25);
		iplab.setBounds(40, 259, 80, 20);		
		iptex.setBounds(120, 259, 180, 20);	
		portlab.setBounds(40, 289, 80, 20);		
		porttex.setBounds(120, 289, 180, 20);
		
		//只能出入数字
		porttex.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {
				char keyCh = e.getKeyChar();
                if((keyCh < '0')||(keyCh > '9')){
                    if(keyCh != '\n')   //回车字符
                        e.setKeyChar('\0');
                }
			}			
		});
		
		this.getContentPane().add(nolab);
		this.getContentPane().add(nametex);
		this.getContentPane().add(passlab);
		this.getContentPane().add(passtex);
		this.getContentPane().add(loginbon);
		this.getContentPane().add(configbon);		
		this.getContentPane().add(iplab);
		this.getContentPane().add(iptex);
		this.getContentPane().add(portlab);
		this.getContentPane().add(porttex);
		this.getContentPane().add(imageLabel);		
		this.getContentPane().add(registerUser);
		
		this.setTitle("QQ2020");
		this.setSize(new Dimension(380,280));
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-380)/2;
        int y=(height-280)/2;
        this.setLocation(x,y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginbon.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String userNo = nametex.getText();
				String passWord = new String(passtex.getPassword());
				String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
							  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
							  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
							  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
				Pattern pattern = Pattern.compile(regex);
				if(!userNo.equals("") && !passWord.equals("")){
					String ip = iptex.getText();
					String strPort = porttex.getText();
                     if(!StringUtil.isNull(ip) && !StringUtil.isNull(strPort)){
                    	 Matcher matcher = pattern.matcher(ip);
     					int port = Integer.valueOf(strPort);
     					if(!matcher.matches()){
     						JOptionPane.showMessageDialog(clientLoginFrame, "ServerIP Error!!!");
     					}else if(port <= 0 || port >= 65535){
     						JOptionPane.showMessageDialog(clientLoginFrame, "Port Error!!!");
     					}else{
     						clientLoginFrame.setVisible(false);
     						new WaittingLogin(userNo,passWord,clientLoginFrame,ip,port);
     					}
                     }else{
                    	 JOptionPane.showMessageDialog(clientLoginFrame, "Config error!");
                     }
				}		
			}
		
		});
		
		configbon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				count++;
				if(count%2 == 1){
					setSize(new Dimension(380,350));
				}else{
					setSize(new Dimension(380,280));
//					iptex.setText("");
//					porttex.setText("");
				}
			}
		});
	}
	
	/**
	 * Self Listener
	 */
	private void initSelfListener(){
		 Listener.getInstance().startRefreshListener();
	}
	
	
	/**
	 * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
	 */
	private static void InitGlobalFont(Font font) {
		  FontUIResource fontRes = new FontUIResource(font);
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys();
		  keys.hasMoreElements(); ) {
		  Object key = keys.nextElement();
		  Object value = UIManager.get(key);
		  if (value instanceof FontUIResource) {
		  UIManager.put(key, fontRes);
		  }
		  }
	}  
	
	public static void main(String[] args){		
		 JFrame.setDefaultLookAndFeelDecorated(true); 
		 JDialog.setDefaultLookAndFeelDecorated(true);
	        try {  
	            SubstanceImageWatermark watermark  =   new  SubstanceImageWatermark(ClientLoginFrame.class.getClassLoader().getResourceAsStream("image/bg4.jpg"));
	            watermark.setKind(ImageWatermarkKind.SCREEN_CENTER_SCALE);
	            watermark.setOpacity(0.5f);// 设置水印透明度   
	            SubstanceSkin skin  =   new  OfficeBlue2007Skin().withWatermark(watermark);   //初始化有水印的皮肤         
	            UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());  
	            SubstanceLookAndFeel.setSkin(skin);  //设置皮肤
	        } catch (Exception e) {  
	            System.out.println("Substance Raven Graphite failed to initialize");  
	        }  
	        SwingUtilities.invokeLater(new Runnable() {  
	            public void run() {
	            	new ClientLoginFrame().setVisible(true);
	            }  
	        });  
			  	
		
	}

	public JTextField getNametex() {
		return nametex;
	}

	public JTextField getIptex() {
		return iptex;
	}

	public void setIptex(JTextField iptex) {
		this.iptex = iptex;
	}

	public JTextField getPorttex() {
		return porttex;
	}

	public void setPorttex(JTextField porttex) {
		this.porttex = porttex;
	}
	
}
