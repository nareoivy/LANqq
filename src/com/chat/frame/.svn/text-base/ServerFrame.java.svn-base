package com.chat.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.api.SubstanceConstants.ImageWatermarkKind;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

import com.chat.server.Listener;
import com.chat.util.Constants;
import com.chat.util.DataUtil;

public class ServerFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
    private ServerFrame serverFrame;
    private JLabel ipLabel,portLabel,label1,ipField;
    private JLabel stop,start;
    private JTextField portField;
    private boolean isServerStart = false;
	public ServerFrame(){
		
	}
	
	public void init(){
		serverFrame = this;
		this.setTitle("服务器");
		ipLabel = new JLabel("服务器IP:");
		portLabel = new JLabel("端 口 号:");
		label1 = new JLabel("服务器后台管理");
		start = new JLabel();
		stop = new JLabel();
		Toolkit kit=Toolkit.getDefaultToolkit();		
		Image imagestartEnable=kit.getImage(DataUtil.getImgPath(getClass(),"start_enable.png"));
		Image imagestartDisable=kit.getImage(DataUtil.getImgPath(getClass(),"start_disable.png"));
		Image imagestop=kit.getImage(DataUtil.getImgPath(getClass(),"stop.png"));
		final Icon iconstartEnable = new ImageIcon(imagestartEnable);
		final Icon iconstartDisable = new ImageIcon(imagestartDisable);
		Icon iconstop = new ImageIcon(imagestop);
		
		start.setIcon(iconstartEnable);
		stop.setIcon(iconstop);
		
		ipField = new JLabel();
		portField = new JTextField(Constants.CON_SERVER_ACCEPT_PORT+"");
		String[] hostAndName = DataUtil.getHostIPAndName();
		if(hostAndName != null){
			ipField.setText(hostAndName[1]);
		}
		
		this.getContentPane().setLayout(null);
		label1.setBounds(5, 5, 200, 20);
		ipLabel.setBounds(15, 30,60, 20);
		ipField.setBounds(80, 30,120, 20);
		portLabel.setBounds(15, 65,60, 20);	
		portField.setBounds(80, 65,120, 20);
		start.setBounds(150, 100,41, 41);
		stop.setBounds(200, 100,41, 41);
		
		this.getContentPane().add(label1);
		this.getContentPane().add(ipLabel);
		this.getContentPane().add(portLabel);
		this.getContentPane().add(ipField);
		this.getContentPane().add(portField);
		this.getContentPane().add(start);
		this.getContentPane().add(stop);
			
		this.setSize(new Dimension(300,200));	
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-300)/2;
        int y=(height-200)/2;
        this.setLocation(x,y);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		start.addMouseListener(new MouseAdapter() {				
			@Override
			public void mouseExited(MouseEvent e) {				
				start.setBorder(null);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if(start.getIcon() == iconstartEnable){
				   start.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 250)));
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(start.getIcon() == iconstartEnable){
				if(e.getClickCount() == 1){
				    String ip = ipField.getText();
				    String portStr = portField.getText();
				    int port = 0;						
					  System.out.println("ip: "+ip+" port: "+portStr);
						if( !ip.equals("") && !portStr.equals("")){
							   port = Integer.valueOf(portStr);							
							 if(port <= 0 || port >= 65535){
									JOptionPane.showMessageDialog(serverFrame, "端口号输入错误或者已被占用");
									return;
								}else{
									/**
									 * 启动监听器
									 */
									 Listener.getInstance().start(port,Constants.CON_SERVER_FILE_SAVEPATH);
									 
									start.setEnabled(false);
									isServerStart = true;
									start.setIcon(iconstartDisable);
								}
								
					}else{
						JOptionPane.showMessageDialog(serverFrame, "请输入端口号或者IP地址！");
						return;
					}
				}
				
			 }
			}
		});

		
		stop.addMouseListener(new MouseAdapter() {			
			
			@Override
			public void mouseExited(MouseEvent e) {
				stop.setBorder(null);
				
			}			
			@Override
			public void mouseEntered(MouseEvent e) {
				stop.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 250)));				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					if(isServerStart){
					int result  = JOptionPane.showConfirmDialog(serverFrame, "请确定停止服务？", "提示",JOptionPane.YES_NO_OPTION,
						         JOptionPane.INFORMATION_MESSAGE);
						if(result == 0){
							 Listener.getInstance().stop();
							start.setEnabled(true);
							isServerStart = false;
							start.setIcon(iconstartEnable);
						} else{		 		
					             }
					}	
				}				
			}
		});	
	}
		
	
	public static void main(String arg[]) {
		
		JFrame.setDefaultLookAndFeelDecorated(true); 
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
	            	
	            	ServerFrame sf = new ServerFrame();
	        		sf.init();
	            }  
	        });  
	}

	public boolean isServerStart() {
		return isServerStart;
	}

	public void setServerStart(boolean isServerStart) {
		this.isServerStart = isServerStart;
	}
	

}
