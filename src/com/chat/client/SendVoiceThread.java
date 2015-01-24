package com.chat.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.chat.common.Pact;
import com.chat.util.Constants;
import com.chat.util.JsonUtil;

public class SendVoiceThread extends Thread{
	private ClientSocket cs = null;
	private String ip;// 设置成服务器IP
	private int port;
	private boolean flag = true;
	
	private Pact pact;
	private DataOutputStream ps;
	
	public SendVoiceThread(String ip, int port,Pact pact) throws Exception{
		this.ip = ip;
		this.port = port;
		this.pact = pact;
	}
	
	private boolean createConnection() throws Exception
	{
		cs = new ClientSocket(ip, port);
		cs.CreateConnection();
		System.out.print("连接服务器成功!" + "\n");
		return true;
	}
	
	 public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void run() {
		 try {
			if (createConnection())
			{
				//向服务器写Pact
				 ps = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
				 String strPact = JsonUtil.getJsonString(pact);
				 byte[] bytePact = new byte[Constants.CON_PACT_LENGTH];
				 bytePact = strPact.getBytes();
				 System.out.println(bytePact.length);
				 /**
		           * write pact			
		          */
				 ps.write(bytePact);
				 ps.flush();
				
				 
				 //写语音信息
				 
				 int bufferSize=80000;
				   byte[] audioData = new byte[bufferSize]; 
				   System.out.println("Server Start");
				   try { 
				    while(flag){ 
				     //开通输出流到指定的Socket
				    	System.out.println(flag);
				     DataOutputStream dout= new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));   
				              float sampleRate = 8000;   
				              int sampleSizeInBits = 8;   
				              int channels = 2;      
				              boolean bigEndian = false;    
				            //  AudioFormat af = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian); 
				              AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate,sampleSizeInBits, channels, 2,sampleRate,bigEndian);
				           TargetDataLine.Info info = new DataLine.Info(TargetDataLine.class, af, bufferSize);   
				           TargetDataLine tdl=null; 
				     try {
				      tdl = (TargetDataLine)AudioSystem.getLine(info);
				            tdl.open(af);   
				            tdl.start(); 
				     } catch (LineUnavailableException e1) { 
				      e1.printStackTrace();
				     }    
				              int intBytes = 0;
				              while(intBytes != -1) {
				                   intBytes = tdl.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
				                   if (intBytes >= 0) {
				                       dout.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
				                       dout.flush();
				                   }
				                   if(!flag){
				                	   intBytes = -1;
				                   }
				           }
				           dout.close();
				           cs.close();
				    }
				   } catch (Exception e1){
				    e1.printStackTrace();
				   }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
