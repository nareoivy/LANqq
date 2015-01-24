package com.chat.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.LoginInfo;
import com.chat.bean.RegisterInfo;
import com.chat.bean.User;
import com.chat.client.SendCreateGroupHander;
import com.chat.client.SendDeleteGroupHander;
import com.chat.client.SendFile;
import com.chat.client.SendHander;
import com.chat.client.SendLeaveMessageHander;
import com.chat.client.SendMessage;
import com.chat.client.SendRefreshHander;
import com.chat.client.SendRegisterHander;
import com.chat.client.SendStopVoiceMessage;
import com.chat.client.SendUpdateGroupHander;
import com.chat.client.SendVoiceThread;
import com.chat.common.Hander;
import com.chat.common.Pact;
import com.chat.frame.UserListFrame;
import com.chat.frame.WaittingLogin;
import com.chat.ui.GroupListUI;

public class DataUtil {
	private static Map<String,SendVoiceThread> clientVoiceThread = new HashMap<String,SendVoiceThread>();
	public static int flag = 0;
	
	
	public static synchronized void setListByUsers(List<User> users,final JList list,final int selectIndex){
		final DefaultListModel userModel = new DefaultListModel();
		final Icon icons[] = new Icon[users.size()];
		ImageIcon imagico = null;
		if(users != null){
			for(int i = 0;i<users.size();i++){
					byte[] userImage = users.get(i).getUserImage();
					Image  image =Toolkit.getDefaultToolkit().createImage(userImage);
					 //进行图片压缩			
					imagico=new ImageIcon(image);
					image = new PicCompression().imageZipProce(imagico.getImage(), 30, 30, 1);
					imagico=new ImageIcon(image);
					
					if(users.get(i).getStatus().equals("N")){
						imagico = new ChangeImageLight(image).getPixels(0, 0, imagico.getIconWidth(), imagico.getIconHeight());
					}
					icons[i] = imagico;
					userModel.addElement(users.get(i).getUsername()+"("+users.get(i).getNo()+")");	
			}
			if(list != null){
		        SwingUtilities.invokeLater(new Runnable() {  
		            public void run() {  	            	
		            	list.setCellRenderer(new GetUserImageUtil(icons));//使用自己的CellRenderer
						list.setModel(userModel);
						list.setSelectedIndex(selectIndex);
		            }  
		        });  
			}
		}
	}
	
	public static void setListByUsers(List<User> users,JList list,String userNo){
		DefaultListModel userModel = new DefaultListModel();
		Icon icons[] = new Icon[users.size()];
		ImageIcon imagico = null;
		if(users != null){
			for(int i = 0;i<users.size();i++){
					byte[] userImage = users.get(i).getUserImage();
					Image  image =Toolkit.getDefaultToolkit().createImage(userImage);
					 //进行图片压缩			
					imagico=new ImageIcon(image);
					image = new PicCompression().imageZipProce(imagico.getImage(), 30, 30, 1);
					imagico=new ImageIcon(image);
					
					if(users.get(i).getNo().equals(userNo) || users.get(i).getStatus().equals("N")){
						icons[i] = new ChangeImageLight(image).getPixels(0, 0, imagico.getIconWidth(), imagico.getIconHeight());
					}else{
						icons[i] = imagico;
					}
					
					userModel.addElement(users.get(i).getUsername()+"("+users.get(i).getNo()+")");	
			}
			if(list != null){
				GetUserImageUtil getUserImageUtil = (GetUserImageUtil)list.getCellRenderer();
				getUserImageUtil.setIcons(icons);
				list.setCellRenderer(getUserImageUtil);
				list.repaint();
			}
		}
	}
	
	public static void setListByUsers(List<User> users,JList list){
		DefaultListModel userModel = new DefaultListModel();
		Icon icons[] = new Icon[users.size()];
		ImageIcon imagico = null;
		if(users != null){
			for(int i = 0;i<users.size();i++){
					byte[] userImage = users.get(i).getUserImage();
					Image  image =Toolkit.getDefaultToolkit().createImage(userImage);
					 //进行图片压缩			
					imagico=new ImageIcon(image);
					image = new PicCompression().imageZipProce(imagico.getImage(), 30, 30, 1);
					imagico=new ImageIcon(image);
					
					if(users.get(i).getStatus().equals("N")){
						imagico = new ChangeImageLight(image).getPixels(0, 0, imagico.getIconWidth(), imagico.getIconHeight());
					}
					
					icons[i] = imagico;
					userModel.addElement(users.get(i).getUsername()+"("+users.get(i).getNo()+")");	
			}
			if(list != null){
				GetUserImageUtil getUserImageUtil = (GetUserImageUtil)list.getCellRenderer();
				getUserImageUtil.setIcons(icons);
				list.setCellRenderer(getUserImageUtil);
				list.repaint();
			}
		}
	}
	
	public static synchronized void setListByGroups(List<DiscussGroup> discussGroup,final UserListFrame list,final int selectIndex){
		final DefaultListModel groupModel = new DefaultListModel();
		final Icon icons[] = new Icon[discussGroup.size()];
		ImageIcon imagico = null;
		if(discussGroup != null){
			for(int i = 0;i<discussGroup.size();i++){
				imagico = new ImageIcon(DataUtil.getImgPath(list.getClass(),"comment.png"));
				icons[i] = imagico;
				groupModel.addElement(discussGroup.get(i).getDiscussGroupName());
			}
			if(list != null){
				
				 SwingUtilities.invokeLater(new Runnable() {  
			            public void run() {
			            	
							list.getGroup().setModel(groupModel);
							list.getGroup().setSelectedIndex(selectIndex);
							list.getGroup().setCellRenderer(new  GetGroupImageUtil(icons));
							list.repaint();
			            }  
			        });  
			}
		}
	}
	
	public static void setListByGroups(List<DiscussGroup> discussGroup,GroupListUI list,String groupName){
		Icon icons[] = new Icon[discussGroup.size()];
		ImageIcon imagico = null;
		if(discussGroup != null){
			for(int i = 0;i<discussGroup.size();i++){
				byte[] groupImage = discussGroup.get(i).getGroupImage();
				Image  image =Toolkit.getDefaultToolkit().createImage(groupImage);
				imagico = new ImageIcon(image);
//				image = new PicCompression().imageZipProce(imagico.getImage(), 30, 30, 1);
//				imagico = new ImageIcon(image);
				if(discussGroup.get(i).getDiscussGroupName().equals(groupName)){
					icons[i] = new ChangeImageLight(image).getPixels(0, 0, imagico.getIconWidth(), imagico.getIconHeight());
				}else{
					icons[i] = imagico;
				}
			}
			if(list != null){
				GetGroupImageUtil getGroupImageUtil =  (GetGroupImageUtil)list.getCellRenderer();
				getGroupImageUtil.setIcons(icons);
				list.setCellRenderer(getGroupImageUtil);
				list.repaint();
			}
		}
	}
	
	public static void setListByGroups(List<DiscussGroup> discussGroup,GroupListUI list){
		Icon icons[] = new Icon[discussGroup.size()];
		ImageIcon imagico = null;
		if(discussGroup != null){
			for(int i = 0;i<discussGroup.size();i++){
				byte[] groupImage = discussGroup.get(i).getGroupImage();
				Image  image =Toolkit.getDefaultToolkit().createImage(groupImage);
				imagico = new ImageIcon(image);
//				image = new PicCompression().imageZipProce(imagico.getImage(), 30, 30, 1);
//				imagico = new ImageIcon(image);
				icons[i] = imagico;
			}
			if(list != null){
				GetGroupImageUtil getGroupImageUtil = (GetGroupImageUtil)list.getCellRenderer();
				getGroupImageUtil.setIcons(icons);
				list.setCellRenderer((GetGroupImageUtil)list.getCellRenderer());
				list.repaint();
			}
		}
	}
	
	public static String[] getUserNameNo(String strUser){
		String[] userInfo = new String[2];
		if(strUser != null){
			int index = strUser.indexOf("(");
			if(index > 0){
				userInfo[0] = strUser.substring(0, index);
				userInfo[1] = strUser.substring(index+1,strUser.length()-1);
			}
		}
		return userInfo;
	}
	
	/**
	 * Get IP and hostName
	 */
	public static String[]   getHostIPAndName(){
		String[] arrInfoms = null;
		InetAddress clientInform;
		try {
			clientInform = InetAddress.getLocalHost();
			if(clientInform != null){
				String informs = clientInform.toString();
				arrInfoms = informs.split("/");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return arrInfoms;
	}
	
	/**
	 * Get Image path
	 */
	@SuppressWarnings("rawtypes")
	public static  URL getImgPath(Class clazz,String imgName){
		URLClassLoader loader = (URLClassLoader) clazz.getClassLoader();
		URL imageUrl = loader.getResource("image/"+imgName);
//		System.out.println("imageUrl="+imageUrl.getPath());
		return imageUrl;	
	}
	
	
	@SuppressWarnings("rawtypes")
	public static  URL getIconPath(Class clazz,String imgName){
		URLClassLoader loader = (URLClassLoader) clazz.getClassLoader();
		URL imageUrl = loader.getResource("image/qqdefaultface/"+imgName);
//		System.out.println("iconPath=" +imageUrl.getPath());
		return imageUrl;	
	}
	
	
	public static String getCurTime(){
		Date  curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
		String curTime = sdf.format(curDate);
		return curTime;
	}
	
	public static String getCurDateAndTime(){
		Date  curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		String curDateAndTime = sdf.format(curDate);
		return curDateAndTime;
	}
	
	/**
	 * Get file size
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileSize(File file) {
		FileInputStream fis = null;
		long s = 0;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				s = fis.available();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != fis) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return s;
	}

	/**
	 * Send Hand prepare
	 */
	@SuppressWarnings("unused")
	public static boolean sendHandPrepare(String sourceIP, int sourcePort,
			String targeIP, int targePort) {
		try {
			/**
			 * Prepare File Transfer
			 */
			Pact pact = new Pact();
			pact.setSourceIP(sourceIP);
			pact.setSourcePort(sourcePort);
			pact.setTargeIP(targeIP);
			pact.setTargePort(targePort);
			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
			pact.setChatType(Constants.CON_CHAT_TYPE_HAND);

			SendMessage send = new SendMessage(PersistenceContext.getEntity().getServerIP(),
					PersistenceContext.getEntity().getServerPort(),
					Constants.CON_CHAT_HAND_PREPARE, pact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	public static boolean sendHandVoiceChat(String sourceIP, int sourcePort,
			String targeIP, int targePort) {
		try {
			/**
			 * Prepare File Transfer
			 */
			Pact pact = new Pact();
			pact.setSourceIP(sourceIP);
			pact.setSourcePort(sourcePort);
			pact.setTargeIP(targeIP);
			pact.setTargePort(targePort);
			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
			pact.setChatType(Constants.CON_CHAT_TYPE_HAND);

			SendMessage send = new SendMessage(PersistenceContext.getEntity().getServerIP(),
					PersistenceContext.getEntity().getServerPort(),
					Constants.CON_CHAT_HAND_CHAT_VOICE, pact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Send voice Message
	 */
	public static boolean sendVoice(String sourceIP, int sourcePort,String targeIP, int targePort){
		if(!clientVoiceThread.containsKey(targeIP))
		{
			try{
				Pact pact = new Pact();
				pact.setSourceIP(sourceIP);
				pact.setSourcePort(sourcePort);
				pact.setTargeIP(targeIP);
				pact.setTargePort(targePort);
				pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_VOICE);
				pact.setChatType(Constants.CON_CHAT_TYPE_VOICE);
				@SuppressWarnings("static-access")
				SendVoiceThread svt = new SendVoiceThread(PersistenceContext.getEntity().getServerIP(), PersistenceContext.getEntity().getEntity().getServerPort(), pact);
				clientVoiceThread.put("@"+sourceIP+"@"+targeIP+"@", svt);
				svt.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	/**
	 * stop voice thread
	 */
	@SuppressWarnings("static-access")
	public static boolean stopVoice(String sourceIP, int sourcePort,String targeIP, int targePort){
		//如果包含说明是主动向对方发起的语音信息，否则就是被动接受对方发来的语音信息
		String strKey1 = "@"+sourceIP+"@"+targeIP+"@";
		String strKey2 = "@"+targeIP+"@"+sourceIP+"@";
		String strKey = null;
		
		if(clientVoiceThread.containsKey(strKey1)){
			strKey = strKey1;
		}
		
		if(clientVoiceThread.containsKey(strKey2)){
			strKey = strKey2;
		}
		
		if(strKey == null){
			strKey = strKey1;
		}
		
		if(clientVoiceThread.containsKey(strKey)){
			SendVoiceThread svt = clientVoiceThread.get(strKey);
			svt.setFlag(false);
			clientVoiceThread.remove(strKey);
		}
		if(getHostIPAndName()[1].equals(sourceIP)){
			//向服务器发信息说明要取消语音信息
			try{
				Pact pact = new Pact();
				pact.setSourceIP(sourceIP);
				pact.setSourcePort(sourcePort);
				pact.setTargeIP(targeIP);
				pact.setTargePort(targePort);
				pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_STOP_VOICE);
				pact.setChatType(Constants.CON_CHAT_TYPE_VOICE);
				new SendStopVoiceMessage(PersistenceContext.getEntity().getServerIP(), PersistenceContext.getEntity().getEntity().getServerPort(), pact);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	
	
	/**
	 * Send File Pact
	 */
	@SuppressWarnings("static-access")
	public static boolean sendFile(String sourceIP, int sourcePort,String targeIP, int targePort,String filePath) {
		try {
			
			Pact pact = new Pact();
			pact.setSourceIP(sourceIP);
			pact.setSourcePort(sourcePort);
			pact.setTargeIP(targeIP);
			pact.setTargePort(targePort);
			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_FILE);
			pact.setChatType(Constants.CON_CHAT_TYPE_MESSAGE);
			pact.setFileLen(DataUtil.getFileSize(new File(filePath)));
			new SendFile(PersistenceContext.getEntity().getServerIP(), PersistenceContext.getEntity().getEntity().getServerPort(), filePath, pact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	/**
	 * Send Hand Content
	 */
	@SuppressWarnings("unused")
	public static boolean sendHandContent(String sourceIP, int sourcePort,
			String targeIP, int targePort,String handContent) {
		try {
			/**
			 * Prepare File Transfer
			 */
			Pact pact = new Pact();
			pact.setSourceIP(sourceIP);
			pact.setSourcePort(sourcePort);
			pact.setTargeIP(targeIP);
			pact.setTargePort(targePort);
			pact.setTransfersType(Constants.CON_TRANSFERS_TYPE_MESSAGE);
			pact.setChatType(Constants.CON_CHAT_TYPE_HAND);

			SendMessage send = new SendMessage(PersistenceContext.getEntity().getServerIP(),
					PersistenceContext.getEntity().getServerPort(),handContent, pact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * Send a request of validate userInfo to server 
	 */
	public static boolean sendRequestValidateUserInfo(String serverIP,int serverPort,LoginInfo loginInfo){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_VALIDATE);
		try {
			SendHander sendHander = new SendHander(serverIP,serverPort,loginInfo,hander);
			sendHander.sendLoginRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Send a request of refresh userInfo to server 
	 * @param serverIP
	 * @param serverPort
	 * @param loginInfo
	 * @return
	 */
	public static boolean sendRequestRefreshInfo(String serverIP,int serverPort,User curUser){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_REFRESH);
		try {
			SendRefreshHander sendRefreshHander = new SendRefreshHander(serverIP,serverPort,curUser,hander);
			sendRefreshHander.sendRefreshRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Send a request for create group
	 * @param serverIP
	 * @param serverPort
	 * @param discussGroup
	 * @return
	 */
	public static boolean sendCreateGroupInfo(String serverIP,int serverPort,DiscussGroup discussGroup){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_CREATE_GROUP);
		try {
			SendCreateGroupHander sendCreateGroupHander = new SendCreateGroupHander(serverIP,serverPort,discussGroup,hander);
			sendCreateGroupHander.sendCreateGroupRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Send a request for update group
	 * @param serverIP
	 * @param serverPort
	 * @param discussGroup
	 * @return
	 */
	public static boolean sendUpdateGroupInfo(String serverIP,int serverPort,DiscussGroup discussGroup){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_UPDATE_GROUP);
		try {
			SendUpdateGroupHander sendUpdateGroupHander = new SendUpdateGroupHander(serverIP,serverPort,discussGroup,hander);
			sendUpdateGroupHander.sendUpdateGroupRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Send a request for delete group
	 * @param serverIP
	 * @param serverPort
	 * @param groupName
	 * @return
	 */
	public static boolean sendDeleteGroupInfo(String serverIP,int serverPort,String groupName){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_DELETE_GROUP);
		try {
			SendDeleteGroupHander sendDeleteGroupHander = new SendDeleteGroupHander(serverIP,serverPort,groupName,hander);
			sendDeleteGroupHander.sendDeleteGroupRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Send a request of operation,i.e. offline 
	 */
	public static boolean sendOperationRequest(String serverIP,int serverPort,LoginInfo loginInfo){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_SETTING);
		hander.setMessage(Constants.CON_HANDER_OPERATION_OFFLINE);
		try {
			SendHander sendHander = new SendHander(serverIP,serverPort,loginInfo,hander);
			sendHander.sendLoginRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Send a request to register a user
	 */
	public static boolean sendRequestRegisterUser(String serverIP,int serverPort,RegisterInfo registerInfo){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.CON_HANDER_METHOD_REGISTER);
	
		try {
			SendRegisterHander sendRegisterHander = new SendRegisterHander(serverIP,serverPort,registerInfo,hander);
			sendRegisterHander.sendRegisterRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * send a request to save leave message
	 */
	public static boolean sendLeaveMessage(String serverIP,int serverPort,LeaveMessage leaveMessage){
		Hander hander = new Hander();
		hander.setTargeIP(serverIP);
		hander.setTargePort(serverPort);
		hander.setType(Constants.CON_HANDER_TYPE_REQUEST);
		hander.setMethod(Constants.COM_HANDER_METHOD_SAVE_LEAVE_MESSAGE);
	
		try {
			SendLeaveMessageHander sendLeaveMessageHander = new SendLeaveMessageHander(serverIP,serverPort,leaveMessage,hander);
			sendLeaveMessageHander.sendLeaveMessageRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 根据图片的字节流判断图片的编码规则
	 * @param imageBytes
	 * @return
	 */
	public static String checkImageType(byte[] imageBytes) {
		  ByteArrayInputStream bais = null;
		  MemoryCacheImageInputStream mcis = null;
		  try {
		   bais = new ByteArrayInputStream(imageBytes);
		   mcis = new MemoryCacheImageInputStream(bais);
		   Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
		   while (itr.hasNext()) {
		    ImageReader reader = (ImageReader) itr.next();
		    String imageName = reader.getClass().getSimpleName();
		    if (imageName != null && ("JPEGImageReader".equalsIgnoreCase(imageName))) {
		     return "jpeg";
		    }else if (imageName != null && ("JPGImageReader".equalsIgnoreCase(imageName))) {
		     return "jpg";
		    }else if (imageName != null && ("pngImageReader".equalsIgnoreCase(imageName))) {
		     return "png";
		    }
		   }
		  } catch (Exception e) {
		  }
		  return null;
		 }
	
	/**
	 * 将一个ImageIcon对象转化为字节流
	 * @param imagePath
	 * @return
	 */
	@SuppressWarnings("unused")
	public static byte[] image2Bytes(ImageIcon ima) {		
         Image img = ima.getImage();
		  BufferedImage bu = new BufferedImage(ima.getImage().getWidth(null), ima.getImage().getHeight(null), BufferedImage.TYPE_INT_ARGB);
		 Graphics g = bu.createGraphics();
		 g.drawImage(img, 0, 0, null);
		 g.dispose();
			 
		  ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
		  try {
		   /**
		    * 把这个jpg图像写到这个流中去,这里可以转变图片的编码格式
		    */
		   boolean resultWrite = ImageIO.write(bu, "png", imageStream);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  byte[] tagInfo = imageStream.toByteArray();
		  return tagInfo;
		 }

	/**
	 * Send Message font style
	 */
	public static Font sendFont(){
		Font sf = new Font("宋体",Font.PLAIN,4);
		return sf;
	}
	
	/**
	 * 把msg转换成StyleUtil
	 */
	public static StyleUtil msgToStyleUtil(String msg){
		StyleUtil su = new StyleUtil();
		String[] arr_msg = msg.split("\\|");
		su.setName(arr_msg[0]);
		su.setSize(Integer.parseInt(arr_msg[1]));
		String[] color = arr_msg[2].split("-");
		su.setColor(new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2])));
		su.setMsg(arr_msg[3]);
		return su;
	}
	
	/**
	 * Error Util 
	 * View message in login
	 */
	public static void LoginErrorView(final WaittingLogin wl,final String error){
		SwingUtilities.invokeLater(new Runnable() {  
			public void run() {  		            	
            	JOptionPane.showMessageDialog(wl, error);
            	wl.getClientLoginFrame().setVisible(true);
            	wl.dispose();
            }  
        });  
	}
}
