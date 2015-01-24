package com.chat.util;

import java.applet.Applet;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;

public class ChangeImageLight extends Applet{
	 /**
	 * 将图片变成灰色
	 */
	private static final long serialVersionUID = -5947677733945424213L;
	private Image image;
	  public ChangeImageLight(Image image){
		  this.image = image;
	  }
	  public ImageIcon getPixels(int x,int y,int w,int h){
		  int[] pixels = new int[w*h];
		  int gray;
		  PixelGrabber pg = new PixelGrabber(image,x,y,w,h,pixels,0,w);
		  try{
			  pg.grabPixels();
		  }catch(InterruptedException e){
			  System.out.println("中断");
		  }
		  for(int j = 0; j < h; j++){
			  for(int i = 0; i < w; i++){
				  gray = (int)(((pixels[w*j+i]>>16)&0xff)*0.8);
				  gray += (int)(((pixels[w*j+i]>>8)&0xff)*0.1);
				  gray += (int)(((pixels[w*j+i])&0xff)*0.1);
				  pixels[w*j+i] = (255<<24)|(gray<<16)|(gray<<8)|gray;
			  }
		  }
		  Image pic = createImage(new MemoryImageSource(w,h,pixels,0,w));
		  
		  ImageIcon imageIco = new ImageIcon(pic);
		  
		  return imageIco;
		  
	  }
}
