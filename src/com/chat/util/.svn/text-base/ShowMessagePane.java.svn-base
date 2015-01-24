package com.chat.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * 显示消息记录的EditPane，对这个pane进行初始化
 * @author PCCW
 *
 */
public class ShowMessagePane extends JEditorPane implements HyperlinkListener {
	
	
	private static final long serialVersionUID = 1L;
	private Desktop desktop;
	
	public ShowMessagePane(String type,String text) throws IOException{
           super(type,text);
           addHyperlinkListener(this);
           
       }

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            try{       
                desktop.browse(e.getURL().toURI());
                
            } catch (IOException ex){
                ex.printStackTrace();
            } catch (URISyntaxException ex){
                ex.printStackTrace();
            }

        }
		
	}

	

}
