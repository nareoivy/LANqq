package com.chat.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class StyleUtil {
	
	
	public static final int GENERAL = 0; // 常规
	private String msg = "", name = "宋体"; // 要输入的文本和字体名称
	private int size = 0; //字号
	private Color color = new Color(225,225,225); // 文字颜色
	private SimpleAttributeSet attrSet = null; // 属性集
	public StyleUtil() {
	}
	public StyleUtil(String msg,String fontName,int fontSize,Color color){
		this.msg = msg;
		this.name = fontName;
		this.size = fontSize;
		this.color = color;
	}
	
	
	/**
	 * 返回属性集
	 * 
	 * @return
	 */
	public SimpleAttributeSet getAttrSet() {
		attrSet = new SimpleAttributeSet();
		if (name != null){
			StyleConstants.setFontFamily(attrSet, name);
		}
		StyleConstants.setBold(attrSet, false);
		StyleConstants.setItalic(attrSet, false);
		StyleConstants.setFontSize(attrSet, size);
		if (color != null)
			StyleConstants.setForeground(attrSet, color);
		return attrSet;
	}
	public String toString(){
		return name+"|"
		+size+"|"
		+color.getRed()+"-"+color.getGreen()+"-"+color.getBlue()+"|"
		+msg;
	}
	/**
	 * 设置字体
	 * @return
	 */
	public static  Font setFont(){
		Font font =  new Font("Serief",Font.PLAIN,11);
		return font;
	}

	/**
	 * 设置当前登录用户发送消息的字体颜色
	 */
	public static Color setCurUserFontColor(){
		Color color = new Color(225,0,255);
		return color;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setAttrSet(SimpleAttributeSet attrSet) {
		this.attrSet = attrSet;
	}

	
}
