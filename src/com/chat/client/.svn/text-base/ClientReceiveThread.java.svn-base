package com.chat.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.chat.bean.User;
import com.chat.common.Pact;
import com.chat.frame.ChatFrame;
import com.chat.frame.UserListFrame;
import com.chat.ui.TimeDialog;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.JsonUtil;
import com.chat.util.MessageContainerUtil;
import com.chat.util.PersistenceContext;
import com.chat.util.SocketUtil;
import com.chat.util.StyleUtil;
import com.even.ReceiveGroupMessageEven;
import com.even.ReceiveMessageEven;

/**
 * Receive File thread
 * 
 * @author pccw
 * 
 */
public class ClientReceiveThread extends Thread {
	private Socket socket;
	private String savePath, pactType;
	private UserListFrame userListFrame;
	private DataInputStream inputStream;
	private DataOutputStream fileOut;
	private Date startDate,endDate; 
	private String sourceIP ,targeIp;
	private int sourcePort ,targePort;
	private User sourceUser , targeUser;
	private ChatFrame cf ;	
    long begainTime,endTime;
	public ClientReceiveThread(Socket socket, String saveFile,
			UserListFrame userListFrame) {
		this.socket = socket;
		this.savePath = saveFile;
		this.userListFrame = userListFrame;
	}

	public void run() {
		try {
			socket.setSoTimeout(8000);
			inputStream = SocketUtil.getDataInputStream(socket);
			/**
			 * Accept Pact
			 */
			byte[] bytePact = new byte[Constants.CON_PACT_LENGTH];
			inputStream.read(bytePact);

			String strPact = new String(bytePact);
			System.out.println(strPact);
			Pact pact = (Pact) JsonUtil.getObject4JsonString(strPact,
					Pact.class);

			/**
			 * get transfers type
			 */
			pactType = pact.getTransfersType();
			int chatType = pact.getChatType();
			System.out.println("Transfers Type=" + pact.getTransfersType());
			String fileName = pact.getFileName();

			/**
			 * Parse pact
			 */
			sourceIP = pact.getSourceIP();
			sourcePort = pact.getSourcePort();
			targeIp = pact.getTargeIP();
			targePort = pact.getTargePort();
			
			
			sourceUser = PersistenceContext.getEntity().getUserByIPAndPort(sourceIP, sourcePort);		
		    targeUser = PersistenceContext.getEntity().getUserByIPAndPort(targeIp, targePort); 
		   
		   cf = userListFrame.getUseFrames().get(sourceIP);
		   
			if (pactType.equals(Constants.CON_TRANSFERS_TYPE_MESSAGE)) {
				/**
				 * Accept Message
				 */
				int messageLen = pact.getMessageLen();
				byte[] buf = new byte[messageLen];
				inputStream.read(buf);
				String message = new String(buf,"UTF-8");

				/**
				 * Fire even
				 */
				if (chatType == Constants.CON_CHAT_TYPE_MESSAGE) {
					ReceiveMessageEven even = new ReceiveMessageEven(
							new String());
					even.setSourceUser(sourceUser);
					even.setTargeUser(targeUser);
					even.setMessage(message);
					userListFrame.fireReceiveEven(even);
				} else if (chatType == Constants.CON_CHAT_TYPE_GROUP) {
					ReceiveGroupMessageEven even = new ReceiveGroupMessageEven(
							new String());
					even.setGroupName(pact.getGroupName());
					even.setSourceUser(sourceUser);
					even.setMessage(message);
					userListFrame.fireReceiveGroupEvent(even);
				}else if(chatType == Constants.CON_CHAT_TYPE_HAND){
					/**
					 * Prepare to receive hand message
					 */
					if(message.equals(Constants.CON_CHAT_HAND_PREPARE)){
						
				        SwingUtilities.invokeLater(new Runnable() {  
				            public void run() {
				            	String info = "对方发送文件是否接受?";
								String returnHandMessage = "";
								/**
								 *   Confirm to choose Y or N
								 */
								   if(!userListFrame.getUseFrames().containsKey(sourceIP)){
									   cf = new ChatFrame(userListFrame.getCurUser(),sourceUser,userListFrame);
									   userListFrame.getUseFrames().put(sourceIP, cf);
									   String content = MessageContainerUtil.tString(targeIp,sourceIP);
									   cf.loadMessage(content);	
								   }
								   
								   int result = JOptionPane.showConfirmDialog(cf, info, "提示",JOptionPane.YES_NO_OPTION,
											         JOptionPane.INFORMATION_MESSAGE);
								  
								   if(result == 0){
									   //Y							   
									   returnHandMessage = Constants.CON_CHAT_HAND_Y;
									   cf.setReceiveFile(true);
								   }else{
									   //N
									   String ms = "取消接收文件！";
									   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
									   MessageContainerUtil.insert(cf.getShowMessagePane(), su);								       								  
									   cf.setReceiveFile(false);
									   returnHandMessage = Constants.CON_CHAT_HAND_N;
								   }
									/**
									 * Prepare File Transfer
									 */
								   System.out.println("发送握手回执！");
								   DataUtil.sendHandContent(targeIp, targePort, sourceIP, sourcePort,returnHandMessage);
				            }  
				        });  

		            /**
		             *  to Send 	   
		             */
					}else if(message.equals(Constants.CON_CHAT_HAND_Y)){
						cf.setPrepareFileStatus(Constants.CON_CHAT_PREPARE_Y);
						
				    	String ms = "对方已接收文件!";
				    	StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
				    	MessageContainerUtil.insert(cf.getShowMessagePane(), su);
						
						System.out.println("接收到回执是Y");
					}else if(message.equals(Constants.CON_CHAT_HAND_N)){
						cf.setPrepareFileStatus(Constants.CON_CHAT_PREPARE_N);
						
				    	String ms = "对方已取消接收文件!";
				    	StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
				    	MessageContainerUtil.insert(cf.getShowMessagePane(), su);
				    	

						System.out.println("接收到回执是N");
					}
					
					/**
					 * 判断是否是对方发来的语音请求
					 */
					if(message.equals(Constants.CON_CHAT_HAND_CHAT_VOICE)){
						 SwingUtilities.invokeLater(new Runnable() {  						
					            public void run() {
					            	String info = "对方想要和你语音是否接受?";
									String returnHandMessage = "";
									/**
									 *   Confirm to choose Y or N
									 */
									   if(!userListFrame.getUseFrames().containsKey(sourceIP)){
										   cf = new ChatFrame(userListFrame.getCurUser(),sourceUser,userListFrame);
										   userListFrame.getUseFrames().put(sourceIP, cf);
										   String content = MessageContainerUtil.tString(targeIp,sourceIP);
										   cf.loadMessage(content);	
									   }
									   TimeDialog d = new TimeDialog();
									   int result = d.showDialog(cf, info, 10);									  								 								    								
									   if(result == 0){
										   //Y	
										   String ms = "您接受了语音聊天！";
										   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
										   MessageContainerUtil.insert(cf.getShowMessagePane(), su);	
										   returnHandMessage = Constants.CON_CHAT_HAND_CHAT_VOICE_Y;
										   cf.setReceiveVoiceChat(true);
										   cf.getVolumnBox().setVisible(true);
										   cf.getStartChatVoice().setEnabled(false);
										   cf.getStopChatVoice().setEnabled(true);//接受语音之后，可以点击停止语音
										   DataUtil.sendVoice(targeIp,targePort,sourceIP,sourcePort);
									   }else if(result == -5){
										   //N
										   String ms = "由于您长时间未接收语音请求，系统已自动取消！";
										   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
										   MessageContainerUtil.insert(cf.getShowMessagePane(), su);								       								  
										   cf.setReceiveVoiceChat(false);
										   cf.getVolumnBox().setVisible(false);
										   cf.getStartChatVoice().setEnabled(true);
										   cf.getStopChatVoice().setEnabled(false);//取消语音之后，不可以点击停止语音
										   returnHandMessage = Constants.CON_CHAT_HAND_CHAT_VOICE_CANCEL;
									   } else{
										   //N
										   String ms = "您拒绝了语音！";
										   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
										   MessageContainerUtil.insert(cf.getShowMessagePane(), su);								       								  
										   cf.setReceiveVoiceChat(false);
										   cf.getVolumnBox().setVisible(false);
										   cf.getStartChatVoice().setEnabled(true);
										   cf.getStopChatVoice().setEnabled(false);//拒绝语音之后，不可以点击停止语音
										   returnHandMessage = Constants.CON_CHAT_HAND_CHAT_VOICE_N;
									   }
					            
										/**
										 * Prepare File Transfer
										 */ 
									   System.out.println("发送握手回执！");
									   DataUtil.sendHandContent(targeIp, targePort, sourceIP, sourcePort,returnHandMessage);
					            }  
					        });  
					}else if(message.equals(Constants.CON_CHAT_HAND_CHAT_VOICE_Y)){
						cf.setPrepareVoiceChat(Constants.CON_CHAT_CHAT_VOICE_Y);
						
						  String ms = "对方已接受语音申请！";
						   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
						   MessageContainerUtil.insert(cf.getShowMessagePane(), su);	
						   cf.getVolumnBox().setVisible(true);
						   
						   SwingUtilities.invokeLater(new Runnable() {  
					            public void run() {
					            	  cf.getStartChatVoice().setEnabled(false); //接受语音之后，不可以再点击开始语音
					            	  cf.getStopChatVoice().setEnabled(true);//接受语音之后，就可以点击结束语音了
					            }  
					        }); 
						    System.out.println("接收到回执是Y");
						}else if(message.equals(Constants.CON_CHAT_HAND_CHAT_VOICE_N)){
							cf.setPrepareVoiceChat(Constants.CON_CHAT_CHAT_VOICE_N);
							
						   String ms = "对方拒绝语音申请！";
						   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
						   MessageContainerUtil.insert(cf.getShowMessagePane(), su);
						   cf.getStartChatVoice().setEnabled(true);
						   cf.getStopChatVoice().setEnabled(false);//拒绝语音之后，不可以点击停止语音
							System.out.println("接收到回执是N");
						}else if(message.equals(Constants.CON_CHAT_HAND_CHAT_VOICE_CANCEL)){
							cf.setPrepareVoiceChat(Constants.CON_CHAT_CHAT_VOICE_N);
							
							   String ms = "由于对方长时间未接收语音请求，系统已自动取消！";
							   StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
							   MessageContainerUtil.insert(cf.getShowMessagePane(), su);
							   cf.getStartChatVoice().setEnabled(true);
							   cf.getStopChatVoice().setEnabled(false);//取消语音之后，不可以点击停止语音
								System.out.println("接收到回执是N");
							}
					
					
				}

				System.out.println("接收完成，消息为" + message.trim());
			} else if (pactType.equals(Constants.CON_TRANSFERS_TYPE_FILE)) {
				    if(cf.isReceiveFile()){
				    	String ms = "已经开始接收文件!";
				    	StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
				    	MessageContainerUtil.insert(cf.getShowMessagePane(), su);

						startDate = new Date();

						/**
						 * Accept
						 */
						int bufferSize = 1024;
						byte[] buf = new byte[bufferSize];
						/**
						 * Read fileName
						 */
						String path = savePath + fileName;
						File file = new File(savePath);
						if (!file.exists()) {
							file.mkdir();
						}
						fileOut = SocketUtil.getDateOutputStream(path);
						System.out.println("开始接收文件!" + "\n");
						int read = 0;
						while (true) {
							if (inputStream != null) {
								read = inputStream.read(buf);
							}
							if(read == -1){
								break;
							}
							fileOut.write(buf,0,read);
						}
						fileOut.flush();
				    }
				} else if(pactType.equals(Constants.CON_TRANSFERS_TYPE_VOICE)){
					//说明是语音消息
					//接受语音消息时也能发送语音信息
					 Thread.sleep(100);
					 int bufferSize=80000;
					   byte[] audioData = new byte[bufferSize]; 
					            float sampleRate = 8000;   
					            int sampleSizeInBits = 8;   
					            int channels = 2;     
					            boolean bigEndian = false;   
//					   AudioFormat af1 = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
					   AudioFormat af1 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate,sampleSizeInBits, channels, 2,sampleRate,bigEndian);
					 
					         SourceDataLine.Info info1 = new DataLine.Info(SourceDataLine.class, af1, bufferSize);
					         SourceDataLine sdl;
					        
					            int intBytes = 0;
					   try {
					    sdl = (SourceDataLine) AudioSystem.getLine(info1);
					    
					          sdl.open(af1);
					    sdl.start();
					    DataInputStream dis = SocketUtil.getDataInputStream(socket);
					   
						cf.setFloatControl((FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN));
					    while(intBytes != -1) {
					     intBytes = dis.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
					                 if (intBytes >= 0) {
					                  sdl.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
					                  sdl.flush();
					                 }
//					                 System.out.println("客服端发送声音");
					          }
					    dis.close();
					    sdl.close();
					    if(DataUtil.flag == 0){
					    	String ms = "对方取消了语音！";
							StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
							MessageContainerUtil.insert(cf.getShowMessagePane(), su);
							cf.getVolumnBox().setVisible(false);
							  SwingUtilities.invokeLater(new Runnable() {  
						            public void run() {
						            	cf.getStartChatVoice().setEnabled(true);//取消语音之后，可以点击开始语音
						            	cf.getStopChatVoice().setEnabled(false);//取消语音之后，不可以点击停止语音
						            }  
						        }); 
					    }else{
					    	DataUtil.flag = 0;
					    }
					   
					   } catch (Exception e) { 
					    e.printStackTrace();
					   } 
			
				}else if(pactType.equals(Constants.CON_TRANSFERS_TYPE_STOP_VOICE)){
					DataUtil.stopVoice(sourceIP, sourcePort,targeIp, targePort);
					String ms = "对方取消了语音！";
					StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
					MessageContainerUtil.insert(cf.getShowMessagePane(), su);
					cf.getVolumnBox().setVisible(false);
					 SwingUtilities.invokeLater(new Runnable() {  
				            public void run() {
				            	cf.getStartChatVoice().setEnabled(true);//取消语音之后，可以点击开始语音
				            	cf.getStopChatVoice().setEnabled(false);//取消语音之后，不可以点击停止语音
				            	
				            }  
				        });
				}else{
					return;
				}
		} catch (Exception e) {
              e.printStackTrace();
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (socket != null) {
					socket.close();
				}
				
				if(pactType.equals(Constants.CON_TRANSFERS_TYPE_FILE)){
					endDate = new Date();
					 float diff =endDate.getTime() - startDate.getTime();
	                 diff = diff/1000;
	                 String ms = "文件传送完毕!用时"+diff+"秒.";
				     StyleUtil su = MessageContainerUtil.setStrMsgToStyleUtil(ms);
				     MessageContainerUtil.insert(cf.getShowMessagePane(), su);
					System.out.println("文件接收完毕");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
