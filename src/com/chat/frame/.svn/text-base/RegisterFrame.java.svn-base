package com.chat.frame;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.chat.bean.RegisterInfo;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;
/**
 * 注册用户
 * @author PCCW
 *
 */
public class RegisterFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel username,password,chooseImage,rePassword,ipAddress,userno,welcome;
	private JTextField usernameField,ipAddressField,usernoField;
	private JComboBox chooseImageBox;
	private JPasswordField passwordField,rePasswordField;
	private JButton register,cancel;
	private RegisterFrame rf;
	private ClientLoginFrame clf;
	
	public RegisterFrame(ClientLoginFrame clf){
		this.clf = clf;
		init();
	}
	
	public void init(){
		rf = this;
		rf.setTitle("注册新用户");
		final Toolkit kit=Toolkit.getDefaultToolkit();
		welcome = new JLabel("现在注册,赶快开始你的山寨QQ之旅吧！");
		username = new JLabel("昵       称:");
		userno = new JLabel("员工号码:");
		password = new JLabel("密       码:");
		rePassword = new JLabel("确认密码:");
		ipAddress = new JLabel("IP  地  址:");
		chooseImage = new JLabel("选择头像:");
		usernameField = new JTextField();
		ipAddressField = new JTextField();
		usernoField = new JTextField();
		passwordField = new JPasswordField();
		rePasswordField = new JPasswordField();
		chooseImageBox = new JComboBox();
		String temp = "";
		String zero = "";
		for(int i=1;i<=130;i++){
			temp = i+" ";
			if(3-temp.length() == -1){
				zero = "";
			} else if(3-temp.length() == 0){
				zero = "0";
			}else{
				zero = "00";
			}				
			chooseImageBox.addItem(new ImageIcon(DataUtil.getImgPath(getClass(),"h"+zero+i+".png")));
		}
		register = new JButton("注  册");
		register.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/**在保存之前需要判断输入框是否都有值***/
				/**在保存之前需要判断两次输入密码是否一致***/
				String name = usernameField.getText();
				String uno = usernoField.getText();
				String pas1 = new String(passwordField.getPassword());
				String pas2 = new String(rePasswordField.getPassword());
				String ip = ipAddressField.getText();
				Image imageyes = kit.getImage(DataUtil.getImgPath(getClass(),"on4.gif"));								
				Icon yes = new ImageIcon(imageyes);	
				Image imageno = kit.getImage(DataUtil.getImgPath(getClass(),"on9.gif"));
				Icon no = new ImageIcon(imageno);	
				String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
					  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					  +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		        Pattern pattern = Pattern.compile(regex);
		        Matcher matcher = pattern.matcher(ip);
				if(!(name.equals("") || no.equals("") || pas1.equals("") || pas2.equals("") || ip.equals(""))){
					if(!pas1.equals(pas2)){
						JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_PAS_ERR, "提示",1 ,no);	
						return;
					}	
					if(pas1.length()>10){
						JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_PAS_TOOLONG, "提示",1 ,no);	
						return;
					}	
					if(!matcher.matches()){
						JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_IP_ERR, "提示",1 ,no);	
					    return;
					}
				}else{
					JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_NULL, "提示",1 ,no);	
					return;
				}
				
				RegisterInfo registerInfo = new RegisterInfo();
				registerInfo.setName(name);
				registerInfo.setUserno(uno);
				registerInfo.setIpAdress(ip);
				registerInfo.setPassword(pas1);
				registerInfo.setUserImage(DataUtil.image2Bytes((ImageIcon)chooseImageBox.getSelectedItem()));
				int serverPort = Integer.parseInt(clf.getPorttex().getText());
				String serverIp = clf.getIptex().getText();
				DataUtil.sendRequestRegisterUser(serverIp, serverPort-1, registerInfo);
				/**
				 * 根据服务器的返回值，判断是否注册成功
				 */
				while(true){
					if(PersistenceContext.getEntity().getRegisterStatus() == Constants.CON_REGISTER_STATUS_SECCESS){											
						JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_SUCCESS, "提示",1 ,yes);	
						rf.setVisible(false);			
						new ClientLoginFrame().main(null);
						break;

					}else if(PersistenceContext.getEntity().getRegisterStatus() == Constants.CON_REGISTER_STATUS_FAILTURE){				
						JOptionPane.showMessageDialog(rf, Constants.CON_TIPS_REGISTER_FAILTURE, "提示",1 ,no);
						break;
					}
				}
				
				
			}
		});
		
		cancel = new JButton("返  回");
		cancel.addActionListener(new ActionListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClientLoginFrame().main(null);
				rf.setVisible(false);
			}
		});
		
		
		welcome.setBounds(10, 5,300, 20);
		username.setBounds(80, 40, 80, 20);
		usernameField.setBounds(150, 40, 120, 20);
		userno.setBounds(80, 70, 80, 20);
		usernoField.setBounds(150, 70, 120, 20);
		password.setBounds(80, 100, 80, 20);
		passwordField.setBounds(150, 100, 120, 20);
		rePassword.setBounds(80, 130, 80, 20);
		rePasswordField.setBounds(150, 130, 120, 20);
		ipAddress.setBounds(80, 160, 80, 20);
		ipAddressField.setBounds(150, 160, 120, 20);
		chooseImage.setBounds(80, 190, 80, 20);
		chooseImageBox.setBounds(150, 190, 120, 42);
		register.setBounds(200, 240, 70, 20);
		cancel.setBounds(290, 240, 70, 20);
		

		
		this.setLayout(null);
		this.getContentPane().add(welcome);
		this.getContentPane().add(username);
		this.getContentPane().add(usernameField);		
		this.getContentPane().add(userno);
		this.getContentPane().add(usernoField);
		this.getContentPane().add(password);
		this.getContentPane().add(passwordField);
		this.getContentPane().add(rePassword);
		this.getContentPane().add(rePasswordField);
		this.getContentPane().add(ipAddress);
		this.getContentPane().add(ipAddressField);
		this.getContentPane().add(chooseImage);
		this.getContentPane().add(chooseImageBox);
		this.getContentPane().add(register);
		this.getContentPane().add(cancel);
		
		this.setSize(new Dimension(400,320));	
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-400)/2;
        int y=(height-320)/2;
        this.setLocation(x,y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}

}
