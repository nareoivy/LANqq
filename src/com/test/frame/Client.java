package com.test.frame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.chat.util.DataUtil;

/**
 * 客户端
 * 
 * @author lium
 */
public class Client extends JFrame {
	private static final long serialVersionUID = 1L;

	public Socket socket;
	private JTextField ip;
	private JTextField port;
	private JButton btn;
	private boolean isConnect = true;
	private JLabel volumnLabel;
	private JSlider volumnSlider;
    private FloatControl control; 
    private JButton bonSond;
    float v = 0;
	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Client frame = new Client();
		frame.setVisible(true);
	}

	/**
	 * Create the frame
	 */
	public Client() {
		super("客户端");
		getContentPane().setLayout(null);
		setBounds(100, 100, 300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btn = new JButton();
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					socket = new Socket(ip.getText(), Integer.parseInt(port
							.getText()));
					new ReceiveThread().start();
					new CaptureThread().start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});	
		
		btn.setText("开启");
		btn.setBounds(60, 102, 60, 28);
		getContentPane().add(btn);

		port = new JTextField("10086");
		port.setBounds(60, 30, 120, 22);
		getContentPane().add(port);

		final JLabel label = new JLabel();
		label.setText("端口号:");
		label.setBounds(14, 32, 54, 18);
		getContentPane().add(label);

		final JLabel label_1 = new JLabel();
		label_1.setText("IP地址:");
		label_1.setBounds(14, 67, 47, 18);
		getContentPane().add(label_1);

		ip = new JTextField("127.0.0.1");
		ip.setBounds(60, 63, 120, 22);
		getContentPane().add(ip);
		//
		volumnSlider = new JSlider(JSlider.HORIZONTAL, -80, 6, 6);		
		volumnSlider.setMinimum((int) -80);
		
		volumnSlider.setMaximum((int) 6);
        volumnSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int volumnSize = volumnSlider.getValue();
				System.out.println("音量大小是："+volumnSize);
				//得到control，将音量的值付给他
			    control.setValue(volumnSize);
			}
		});			
         volumnSlider.setBounds(60, 150, 100, 20);
         getContentPane().add(volumnSlider);
         
	}

	/**
	 * 收听
	 * 
	 * @author lium
	 * 
	 */
	class ReceiveThread extends Thread {
		public void run() {
			int bufferSize = 16000;
			byte[] audioData = new byte[bufferSize];
			System.out.println("Server Start");
			try {
				while (true) {
					btn.setEnabled(false);
					// 开通输出流到指定的Socket
					DataOutputStream dout = new DataOutputStream(
							new BufferedOutputStream(socket.getOutputStream()));
					float sampleRate = 8000;
					int sampleSizeInBits = 16;
					int channels = 1;
					boolean signed = true;
					boolean bigEndian = true;
					AudioFormat af = new AudioFormat(sampleRate,
							sampleSizeInBits, channels, signed, bigEndian);
					TargetDataLine.Info info = new DataLine.Info(
							TargetDataLine.class, af, bufferSize);
					TargetDataLine tdl = null;
					try {
						tdl = (TargetDataLine) AudioSystem.getLine(info);
						tdl.open(af);
						tdl.start();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
					int intBytes = 0;
					while (isConnect && intBytes != -1) {
						intBytes = tdl.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
						if (intBytes >= 0) {
							dout.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
							dout.flush();
						}
					}
					dout.close();
					socket.close();
					btn.setEnabled(true);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 发送
	 * 
	 * @author lium
	 */
	class CaptureThread extends Thread {
		public void run() {
			int bufferSize = 16000;
			byte[] audioData = new byte[bufferSize];
			float sampleRate = 8000;
			int sampleSizeInBits = 16;
			int channels = 1;
			boolean signed = true;
			boolean bigEndian = true;
			AudioFormat af1 = new AudioFormat(sampleRate, sampleSizeInBits,
					channels, signed, bigEndian);
			SourceDataLine.Info info1 = new DataLine.Info(SourceDataLine.class,
					af1, 16000);
			SourceDataLine sdl;
			int intBytes = 0;
			try {
				sdl = (SourceDataLine) AudioSystem.getLine(info1);
				sdl.open(af1);
				sdl.start();
				DataInputStream dis = new DataInputStream(
						new BufferedInputStream(socket.getInputStream()));
				control = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
//				control.setValue(50);
				while (isConnect && intBytes != -1) {
					intBytes = dis.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
					if (intBytes >= 0) {
						sdl.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
						sdl.flush();
					}
					// System.out.println("客服端发送声音");
				}
				sdl.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
