package com.chat.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.chat.frame.UserListFrame;

/**
 * 最小化图标工具类
 * @author PCCW
 *
 */
public class ToMinimizeUtil {

	public static void initTray(final UserListFrame frame) {
		final SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘		
		Toolkit kit=Toolkit.getDefaultToolkit();
		final Image imgEnable=kit.getImage(DataUtil.getImgPath(frame.getClass(),"pccw.gif"));
//		final Image imgDisable=kit.getImage(DataUtil.getImgPath(frame.getClass(),"6.gif"));
		final TrayIcon trayIcon = new TrayIcon(imgEnable, "chatFrame");
		
		
		try {
				tray.add(trayIcon);
				frame.setTrayIcon(trayIcon);
				// 创建弹出菜单
				PopupMenu popup = new PopupMenu();
				MenuItem defaultItem = new MenuItem("Main");
				trayIcon.setPopupMenu(popup);
				defaultItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						tray.remove(trayIcon);
						frame.setVisible(true);
						frame.setExtendedState(JFrame.NORMAL); // 还原窗口
						frame.toFront();
						frame.setIsMinimize(false);						
						/**
						 * 结束最小化图标闪动线程
						 */
						if (frame.getImgTagThread() != null) {
							frame.getImgTagThread().setStatus(0);
						}
					}
				});

				MenuItem exitItem = new MenuItem("Exit");
				exitItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (JOptionPane.showConfirmDialog(null, "确定退出系统") == 0) {
							System.exit(0);
						}
					}
				});

				popup.add(defaultItem);
				popup.add(exitItem);
				trayIcon.addActionListener(new ActionListener() { // 双击图标会移除托盘下的小图
					@Override
					public void actionPerformed(ActionEvent e) {
						tray.remove(trayIcon);
						frame.setVisible(true);
						frame.setExtendedState(JFrame.NORMAL); // 还原窗口
						frame.toFront();
						frame.setIsMinimize(false);
						/**
						 * 结束最小化图标闪动线程
						 */
						if (frame.getImgTagThread() != null) {
							frame.getImgTagThread().setStatus(0);
						}
					}
				});
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Minimize frame status
	 * @param frame
	 */
	public static void miniTray(final UserListFrame frame){
		frame.setIsMinimize(true);
	}
	
	/**
	 * Remove first TrayIcon
	 */
	public static void remove(){
		final SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘
		if(tray.getTrayIcons().length >0){
			tray.remove(tray.getTrayIcons()[0]);	
		}		
	}
	
}
