package com.chat.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import com.chat.util.DataUtil;
import com.chat.util.PicCompression;
/**
 * 设置背景图片方法:传入一个背景图片名称，然后切换背景
 * @author PCCW
 *
 */
public class BackgroundPane  extends   JPanel{

	private static final long serialVersionUID = 1L;
	private String bgName;
    public BackgroundPane(String bgName){
    	this.bgName = bgName;
    	
    }
	
			public  void   paintComponent(Graphics   g)
	        {	              
	                super.paintComponent(g);
	                Image   img   =   Toolkit.getDefaultToolkit().getImage( DataUtil.getImgPath(getClass(),bgName));
	                img =  new PicCompression().imageZipProce(img, 610, 480, 1);
	                g.drawImage(img, 0,0,null,this);
	        }
	}
	

