package com.chat.frame;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.chat.bean.LoginInfo;
import com.chat.client.WaittingLoginThread;
import com.chat.util.DataUtil;

public class WaittingLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton closeJButton;
	private JLabel Label1, imageJLabel;
	private WaittingLogin wl;
	private WaittingLoginThread wlt;
	private ClientLoginFrame clientLoginFrame;
	private boolean isCancel = false;
    private String userNo,passWord; 

	private String ip;
	private int port;

	public WaittingLogin(final String userNo, final String passWord,
			ClientLoginFrame clientLoginFrame) {
		wl = this;

		createUserInterface();
		wlt = new WaittingLoginThread(userNo, passWord, wl);
		wlt.start();
	}
	
	public WaittingLogin(final String userNo, final String passWord,
			ClientLoginFrame clientLoginFrame,String ip,int port) {
		this.ip = ip;
		this.port = port;
		this.userNo = userNo;
		this.passWord = passWord;
		createUserInterface();
		this.clientLoginFrame = clientLoginFrame;
		wl = this;
		wlt = new WaittingLoginThread(userNo, passWord, wl);
		wlt.start();
	}
	

//	public WaittingLogin() {
//		wl = this;
//		createUserInterface();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		wl.dispose();
//		new UserListFrame();
//	}

	public void createUserInterface() {

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image image = kit.getImage(DataUtil.getImgPath(getClass(),
				"Logining.gif"));
		Icon ico = new ImageIcon(image);
		imageJLabel = new JLabel();

		imageJLabel.setIcon(ico);

		Label1 = new JLabel("正在登录..........");
		closeJButton = new JButton("取消");

		closeJButton.setBounds(56, 300, 100, 30);
		Label1.setBounds(65, 250, 100, 20);
		imageJLabel.setBounds(0, 0, 260, 150);

		this.getContentPane().setLayout(null);
		this.getContentPane().add(imageJLabel);
		this.getContentPane().add(Label1);
		this.getContentPane().add(closeJButton);

		Label1.setOpaque(false);
		imageJLabel.setOpaque(false);
		closeJButton.setOpaque(false);
		closeJButton.addActionListener(new ActionListener() {

			@SuppressWarnings({ "deprecation" })
			@Override
			public void actionPerformed(ActionEvent e) {
				isCancel = true;
				LoginInfo loginInfo = new LoginInfo();
				loginInfo.setUserNo(userNo);
				loginInfo.setPassword(passWord);
				DataUtil.sendOperationRequest(ip, port-1, loginInfo);
				wlt.stop();
				dispose();
				System.out.println("Cancel Login !");
				clientLoginFrame.setVisible(true);
			}
		});
		this.setTitle("登录中");
		this.setVisible(true);
		this.setSize(new Dimension(228, 600));
		int x = 1000;
		int y = 100;
		this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public ClientLoginFrame getClientLoginFrame() {
		return clientLoginFrame;
	}

	public void setClientLoginFrame(ClientLoginFrame clientLoginFrame) {
		this.clientLoginFrame = clientLoginFrame;
	}
//	public static void main(String[] args) {
//		new WaittingLogin();
//	}

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

}
