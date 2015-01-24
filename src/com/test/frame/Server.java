package com.test.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 服务端
 * 
 * @author lium
 * 
 */
public class Server extends JFrame {
	private static final long serialVersionUID = 1L;

	ServerSocket server = null;
	private JTextField port;
	private JButton btn;
	private Socket socket;
	private JLabel text;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame
	 */
	public Server() {
		super("服务端");
		getContentPane().setLayout(null);
		setBounds(100, 100, 213, 163);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btn = new JButton();
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					server = new ServerSocket(Integer.parseInt(port.getText()),
							1);
					new ReceiveThread().start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn.setText("开启");
		btn.setBounds(70, 58, 60, 28);
		getContentPane().add(btn);

		port = new JTextField("10086");
		port.setBounds(60, 30, 120, 22);
		getContentPane().add(port);

		final JLabel label = new JLabel();
		label.setText("端口号:");
		label.setBounds(14, 32, 54, 18);
		getContentPane().add(label);

		text = new JLabel();
		text.setBounds(15, 95, 166, 18);
		getContentPane().add(text);
		//
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
					text.setText("等待加入");
					btn.setEnabled(false);
					socket = server.accept();
					new CaptureThread().start();
					text.setText("服务器开启");
					// 开通输出流到指定的Socket
					DataInputStream dis = new DataInputStream(
							new BufferedInputStream(socket.getInputStream()));
					float sampleRate = 8000;
					int sampleSizeInBits = 16;
					int channels = 1;
					boolean signed = true;
					boolean bigEndian = true;
					AudioFormat af = new AudioFormat(sampleRate,
							sampleSizeInBits, channels, signed, bigEndian);
					SourceDataLine.Info info = new DataLine.Info(
							SourceDataLine.class, af, bufferSize);
					SourceDataLine sdl = (SourceDataLine) AudioSystem
							.getLine(info);
					sdl.open(af);
					sdl.start();
					int intBytes = 0;
					while (intBytes != -1) {
						intBytes = dis.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
						if (intBytes >= 0) {
							sdl.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
							// System.out.println("服务端收到声音");
						}
					}
					sdl.drain();
					sdl.stop();
					dis.close();
					socket.close();
					btn.setEnabled(true);
					text.setText("断开连接");
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
	 * 
	 */
	class CaptureThread extends Thread {
		public void run() {
			int bufferSize = 16000;
			byte[] audioData = new byte[bufferSize];
			try {
				while (true) {
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
					while (intBytes != -1) {
						intBytes = tdl.read(audioData, 0, bufferSize);// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
						if (intBytes >= 0) {
							dout.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
							dout.flush();
						}
						// System.out.println("服务端发送声音");
					}
					dout.close();
					socket.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
}
