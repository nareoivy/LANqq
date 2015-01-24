package com.chat.util;

import java.awt.Graphics2D;
import java.awt.Image;  
import java.awt.Transparency;
import java.awt.image.BufferedImage;  

   
 public class PicCompression {
     /** 
     * 压缩图片方法 ,压缩后之后还是保持png格式
     *  
     */  
     public Image imageZipProce(Image srcFile, int width, int height, float quality) {  
         if (srcFile == null) {  
             return null;  
         }  
             int new_w=0,new_h=0;  
             //获取图片的实际大小 高度  
             int h=(int)srcFile.getHeight(null); 
             //获取图片的实际大小 宽度  
             int w=(int)srcFile.getWidth(null);  
             // 为等比缩放计算输出的图片宽度及高度  
             if((((double)w) >(double)width)||(((double)h)>(double) height))  
             {  
                 double rate=0;//算出图片比例值  
                 //宽度大于等于高度  
                 if(w>=h){  
                   rate = ((double) w) / (double) width;  
                 }  
                 //宽度小于高度  
                 else if(h>w) {  
                     rate = ((double) h) / (double) height;  
                 }  
                 //构造新的比例的图片高度与宽度值  
                 new_w = (int) (((double) w) / rate);  
                 new_h = (int) (((double) h) / rate);  
                 /** 宽,高设定 */  
                 BufferedImage tag = new BufferedImage(new_w, new_h,BufferedImage.TYPE_INT_RGB);                
                 Graphics2D g2d = tag.createGraphics();
                 tag = g2d.getDeviceConfiguration().createCompatibleImage(new_w, new_h, Transparency.TRANSLUCENT);    
                 tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
                 return tag.getScaledInstance(new_w, new_h,Image.SCALE_FAST);
             }
         return srcFile;
     }
     
     
}  