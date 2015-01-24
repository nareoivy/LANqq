package com.lang;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchFrame extends CommonFrame
{
	
	
	   private static final long serialVersionUID = 1L;
	   private ResourceManager rm = ResourceManager.getInstance();     
       private JButton searchButton = new JButton();
       private JButton addButton = new JButton();
       private JTextField nameText = new JTextField();
       private JLabel  nameLab = new JLabel();
       private JLabel  nullLab = new JLabel(" ");
       private JComboBox jcb ;
       private boolean selectFlag = true;
		public void showPanel(){		
		
		jcb = new JComboBox();
			
	    this.setLanguage("en_US");
		Container 	containerPane = this.getContentPane();
			
		JPanel buttonPanel = new JPanel();  //两个button组成
		JPanel rightPanel = new JPanel();   //由 buttonPanel和textfield组成
		JPanel  leftPanel = new JPanel();   //由一个nameLab和nullLab组成
		JPanel northPanel = new JPanel();  //由rightPanel和 leftPanel组成
		
		
		jcb.setBounds(20, 20, 100,30);
		
		jcb.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				 if(e.getStateChange() == ItemEvent.SELECTED){
					 if(selectFlag){
						 String itemLan = (String) e.getItem();
						 String language = null;
						 if(itemLan.equals("中文")||itemLan.equals("Chinese")){
							 language = "zh_CN";
						 }else{
							 language = "en_US";
						 }
						 rm.changeLang(language);
					 }
				 }
			}
		});  //加入选项监听
		this.add(jcb);
		
		Box buttonBox = Box.createHorizontalBox();  //button水平布局
		buttonBox.add(Box.createHorizontalStrut(0));
		buttonBox.add(searchButton);
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(addButton);
		
		buttonPanel.add(buttonBox);  
		
		Box  rightBox = Box.createVerticalBox();
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(nameText);
		rightBox.add(Box.createVerticalStrut(2));
		rightBox.add(buttonPanel);
		
		rightPanel.add(rightBox);
		
		Box leftBox = Box.createVerticalBox();
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(nameLab);
		leftBox.add(Box.createVerticalStrut(2));
		leftBox.add(nullLab);
		
		leftPanel.add(leftBox);
		
		Box northBox = Box.createHorizontalBox();
		northBox.add(Box.createHorizontalStrut(20));
		northBox.add(leftPanel);
		northBox.add(Box.createHorizontalStrut(0));
		northBox.add(rightPanel);
		
		northPanel.add(northBox);
		
		containerPane.add(northPanel);
		
		this.setBounds(30, 30 , 250, 200);
		this.setSize(550, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rm.addListener(this);
		updateResource();
	}
	
	public  static  void main(String arg[]){
		new SearchFrame().showPanel();
	}

	@Override
	public void updateResource() {
		 String searchTitle = getString("patient.search");
		 String addTitle = getString("patient.addnew");
		 String nameTitle = getString("patient.name");
		 String  en = getString("language.english");
		 String cn = getString("language.chinese");

		 Font font = new Font("Serief",Font.PLAIN,10);
		 searchButton.setText(searchTitle);	
		 searchButton.setFont(font);
		 searchButton.setSize(50, 25);
		 addButton.setFont(font);
		 addButton.setText(addTitle);
		 nameLab.setText(nameTitle);
		 String[] language ={en,cn}; 

		 int defaultIndex = jcb.getSelectedIndex();
		 DefaultComboBoxModel aModel = new DefaultComboBoxModel(language);
		 jcb.setModel(aModel);		 
		 if(defaultIndex != -1){
			     selectFlag = false;
				 jcb.setSelectedIndex(defaultIndex);	
				 selectFlag = true;
		 }
	}
}
