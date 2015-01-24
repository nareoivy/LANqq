package com.chat.util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class GetGroupImageUtil extends JLabel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private  Icon[] icons;
	 private String groupName;
	 public GetGroupImageUtil(){}
	 public GetGroupImageUtil(Icon[] icons) {  
		 this.icons = icons;
		 
	 }
	 public GetGroupImageUtil(Icon[] icons,String groupName) {  
		  this.icons = icons;
		  this.groupName = groupName;
	} 
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));//加入宽度为3的空白边框 
		
		if (isSelected) {  
			   setBackground(list.getSelectionBackground());  
			   setForeground(list.getSelectionForeground());  
			  } else {  
			   setBackground(list.getBackground());  
			   setForeground(list.getForeground());  
			  }  
			if(index >= icons.length){
				return this;
			}else{
				ImageIcon image =  (ImageIcon) icons[index];
				image.getImage().getScaledInstance(10,10,Image.SCALE_FAST);
				
				setIcon(image); //设置图片  
				setEnabled(list.isEnabled()); 
				setFont(list.getFont());  
				setOpaque(true);  
				return this;
			}
		
		
	}
	
	public Icon[] getIcons() {
		return icons;
	}
	public void setIcons(Icon[] icons) {
		this.icons = icons;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
