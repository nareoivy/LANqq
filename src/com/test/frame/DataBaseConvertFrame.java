package com.test.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 数据库转换工具
 * @author PCCW
 *
 */
public class DataBaseConvertFrame extends JFrame {

	private static final long serialVersionUID = 1L;
    private JLabel dataBaseName,hostName,portNumber,userName,password;
    private JTextField hostNameField,portNumberField,userNameField,passwordField;
    private JComboBox dataBaseNameBox;
    private JButton conncetTest, confirm,cancel;
    private JPanel panel;
    private DataBaseConvertFrame dbcf = this;
	
	public DataBaseConvertFrame(){
		this.getContentPane().setLayout(null);
		dataBaseName = new JLabel("数据库名:");
		hostName = new JLabel("主机名/IP地址:");
		portNumber = new JLabel("端口号:");
		userName = new JLabel("用户名:");
		password = new JLabel("密码:");
		hostNameField = new JTextField();
		portNumberField = new JTextField();
		userNameField = new JTextField();
		passwordField = new JTextField();
		dataBaseNameBox = new JComboBox();
		conncetTest = new JButton("连接测试");
		confirm = new JButton("确定");
		cancel = new JButton("取消");
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
				
		dataBaseName.setBounds(30,30,80,20);
		hostName.setBounds(30,60,100,20);
		portNumber.setBounds(30,90,80,20);
		userName.setBounds(30,120,80,20);
		password.setBounds(30,150,80,20);
		dataBaseNameBox.setBounds(150,30,200,20);
		hostNameField.setBounds(150,60,200,20);
		portNumberField.setBounds(150,90,200,20);
		userNameField.setBounds(150,120,200,20);
		passwordField.setBounds(150,150,200,20);
		conncetTest.setBounds(30,330,100,20);
		confirm.setBounds(280,330,65,20);
		cancel.setBounds(360,330,65,20);
		panel.setBounds(20,20,390,300);
		
		this.getContentPane().add(dataBaseName);
		this.getContentPane().add(hostName);
		this.getContentPane().add(portNumber);
		this.getContentPane().add(userName);
		this.getContentPane().add(password);
		this.getContentPane().add(dataBaseNameBox);
		this.getContentPane().add(hostNameField);
		this.getContentPane().add(portNumberField);
		this.getContentPane().add(userNameField);
		this.getContentPane().add(passwordField);
		this.getContentPane().add(conncetTest);
		this.getContentPane().add(confirm);
		this.getContentPane().add(cancel);
		this.getContentPane().add(panel);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width - 440) / 2;
		int y = (height - 400) / 2;
		this.setLocation(x, y);
		this.setSize(440, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new DataBaseConvertFrame();

	}

}
