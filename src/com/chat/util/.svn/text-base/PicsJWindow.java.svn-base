package com.chat.util;

import   java.awt.*;   
import   javax.swing.*;   
import com.chat.bean.ChatPic;
import com.chat.frame.SuperFrame;
  
import   java.awt.event.*;   
import java.net.URL;
/**  
  *   <p> Title:   pictures</p>  
  *  
  *   <p> Description:   </p>  
  *  
  *   <p> Copyright:   Copyright   (c)   2011 </p>  
  *  
  *   <p> Company:   </p>  
  *  
  *   @author   not   attributable  
  *   @version   1.0  
  */   
public   class   PicsJWindow  extends   JWindow   {   
    private static final long serialVersionUID = 1L;  
    GridLayout   gridLayout1   =   new   GridLayout(7,15);   
    JLabel[]   ico=new   JLabel[105]; /*放表情*/  
    int  i;  
//    ChatFrame   chatFrame;  
//    ChatGroupFrame chatGroupFrame;
    SuperFrame sf;
    String[] intro = {"","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",  
            "","","","","","","","","","","","","","","",};/*图片描述*/  
    
    /**
     * 单独与好友聊天时显示调用此方法显示表情
     * @param chatFrame
     */
    public   PicsJWindow(SuperFrame   sf)   {   
        super(sf);   
        this.sf=sf;   
        try   {   
            init();   
            this.setAlwaysOnTop(true);   
        }   
        catch   (Exception   exception)   {   
            exception.printStackTrace();   
        }   
    }   
    
    
    /**
     * 初始化表情框
     * @throws Exception
     */
    private   void   init()   throws   Exception   {   
        this.setPreferredSize(new Dimension(28*15,28*7));  
        JPanel p = new JPanel();  
        p.setOpaque(false);  
        this.setContentPane(p);  
        p.setLayout(gridLayout1);  
        p.setBackground(SystemColor.text);        
        URL iconURL = null;
        for(i=0;i <ico.length;i++){   
        	iconURL=DataUtil.getIconPath(getClass(), i+".gif");   /*修改图片路径*/
            ico[i] =new   JLabel(new  ChatPic(iconURL,i),SwingConstants.CENTER);  
            ico[i].setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));  
            ico[i].setToolTipText(i+"");  
            ico[i].addMouseListener(new   MouseAdapter(){   
                public   void   mouseClicked(MouseEvent  e){   
                    if(e.getButton()==1){  
                        JLabel cubl = (JLabel)(e.getSource());  
                        ChatPic cupic = (ChatPic)(cubl.getIcon());  
                        sf.insertSendPic(cupic);  
                        cubl.setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));  
                        sf.setPicWindowIsOpen(false);
                        getObj().dispose();  
                    }  
                }  
                @Override  
                public void mouseEntered(MouseEvent e) {  
                    ((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLUE));  
                }  
                @Override  
                public void mouseExited(MouseEvent e) {  
                    ((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));  
                }   
                  
            });   
            p.add(ico[i]);   
        }   
        p.addMouseListener(new MouseAdapter(){  
            @Override  
            public void mouseExited(MouseEvent e) {  
                getObj().dispose();   
            }  
              
        });
    }   
    @Override  
    public void setVisible(boolean show) {  
        if (show) {  
            determineAndSetLocation();  
        }  
        super.setVisible(show);  
    }     
    
    /**
     * 设置表情框显示的相对位置
     */
    private void determineAndSetLocation() {  
        Point loc = sf.getShowPic().getLocationOnScreen();/*控件相对于屏幕的位置*/  
        setBounds(loc.x-getPreferredSize().width/3, loc.y-getPreferredSize().height,  
                getPreferredSize().width, getPreferredSize().height);  
    }  
    private   JWindow   getObj(){   
        return   this;   
    }   
  
}
