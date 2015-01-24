package com.chat.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.chat.bean.DiscussGroup;
import com.chat.bean.User;
import com.chat.client.RefreshClient;
import com.chat.util.Constants;
import com.chat.util.DataUtil;
import com.chat.util.PersistenceContext;

public class ConfigChatGroupFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList leftPerson,rightPerson;
	private JButton toLeftBon,toRightBon,saveBon,exitBon;
	private JScrollPane leftUserScrollPane,rightUserScrollPane;
	private List<User> leftUsers,rightUsers;
	private ConfigChatGroupFrame self;
	private JLabel gruopNameLab;
	private JTextField gruopNameTex;
	private User curUser;
	private Toolkit kit;
	private int status = 0;
	private UserListFrame f;
	
	public ConfigChatGroupFrame(User curUser,UserListFrame f){
		this.f = f;
		this.status = 0;
		gruopNameTex = new JTextField();
		self = this;
		this.curUser = curUser;
		leftUsers = PersistenceContext.getEntity().getCopyUsers();
		for(int i = 0; i < leftUsers.size(); i++){
			if(leftUsers.get(i).getNo().equals(curUser.getNo())){
				leftUsers.remove(i);
			}
		}
		leftPerson = new JList();
		DataUtil.setListByUsers(leftUsers, leftPerson, -1);
		rightUsers = new ArrayList<User>();
		rightPerson = new JList();
	}
	
	public ConfigChatGroupFrame(User curUser,DiscussGroup discussGroup){
		this.curUser = curUser;
		this.status = 1;
		self = this;
		leftUsers = new ArrayList<User>();
		leftPerson = new JList();
		rightUsers = new ArrayList<User>();
		rightPerson = new JList();
		gruopNameTex = new JTextField();
		gruopNameTex.setText(discussGroup.getDiscussGroupName());
		gruopNameTex.setEditable(false);
		
		List<User> allUsers = PersistenceContext.getEntity().getUsers();
		for(int i = 0; i < allUsers.size(); i++){
			if(discussGroup.getUserNos().indexOf(allUsers.get(i).getNo()) > -1){
				if(!allUsers.get(i).getNo().equals(curUser.getNo())){
					rightUsers.add(allUsers.get(i));
				}
			}else{
				leftUsers.add(allUsers.get(i));
			}
		}
		DataUtil.setListByUsers(leftUsers, leftPerson, -1);
		DataUtil.setListByUsers(rightUsers, rightPerson, -1);

	}
	
	public void init(){
		
		toRightBon = new JButton(">>");
		toLeftBon = new JButton("<<");
		saveBon = new JButton("保存"); 
		exitBon = new JButton("退出");
		
		leftUserScrollPane = new JScrollPane(leftPerson);
		leftUserScrollPane.setBorder(BorderFactory.createTitledBorder("用户列表"));
		rightUserScrollPane = new JScrollPane(rightPerson);
		rightUserScrollPane.setBorder(BorderFactory.createTitledBorder("会议列表"));
		
		gruopNameLab = new JLabel("讨论组名称:");
		
		
		this.getContentPane().setLayout(null);
		
		leftUserScrollPane.setBounds(20, 48, 160, 340);
		rightUserScrollPane.setBounds(330, 48, 160, 340);
		
		gruopNameLab.setBounds(20, 15, 80, 20);
		gruopNameTex.setBounds(100, 15, 130, 20);
		toRightBon.setBounds(200, 120, 105, 20);
		toLeftBon.setBounds(200, 270, 105, 20);
		saveBon.setBounds(330, 400, 70, 20);
		exitBon.setBounds(417, 400, 70, 20);
		
		this.getContentPane().add(leftUserScrollPane);
		this.getContentPane().add(rightUserScrollPane);
		this.getContentPane().add(gruopNameLab);
		this.getContentPane().add(gruopNameTex);
		this.getContentPane().add(toRightBon);
		this.getContentPane().add(toLeftBon);
		this.getContentPane().add(saveBon);
		this.getContentPane().add(exitBon);
		
		this.setSize(new Dimension(520,460));
		kit=Toolkit.getDefaultToolkit();//设置顶层容器框架为居中
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-500)/2;
        int y=(height-460)/2;
        this.setLocation(x,y);
        this.setTitle("创建讨论组");
		this.setVisible(true);
		
		toRightBon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectIndexs = leftPerson.getSelectedIndices();
				List<User> selectUsers = new ArrayList<User>();
				if(selectIndexs.length > 0){
					for(int selectIndex:selectIndexs){
						selectUsers.add(leftUsers.get(selectIndex));
					}
					leftUsers.removeAll(selectUsers);
					rightUsers.addAll(selectUsers);
					DataUtil.setListByUsers(leftUsers, leftPerson, -1);	
					DataUtil.setListByUsers(rightUsers, rightPerson, -1);
				}else{
					JOptionPane.showMessageDialog(self, "Please select one");
				}
			}
		});
		
		toLeftBon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectIndexs = rightPerson.getSelectedIndices();
				List<User> selectUsers = new ArrayList<User>();
				if(selectIndexs.length > 0){
					for(int selectIndex:selectIndexs){
						selectUsers.add(rightUsers.get(selectIndex));
					}
					rightUsers.removeAll(selectUsers);
					leftUsers.addAll(selectUsers);
					DataUtil.setListByUsers(rightUsers, rightPerson, -1);
					DataUtil.setListByUsers(leftUsers, leftPerson, -1);
				}else{
					JOptionPane.showMessageDialog(self, "Please select one");
				}
			}
		});
		
		saveBon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gruopNameTex.getText().length() > 0){
					DiscussGroup discussGroup = new DiscussGroup();
					String userNos = curUser.getNo();
					discussGroup.setGroupMaster(userNos);
					discussGroup.setDiscussGroupName(gruopNameTex.getText());
					for(User user:rightUsers){
						userNos += ","+user.getNo();
					}
					discussGroup.setUserNos(!userNos.equals("")?userNos.substring(0, userNos.length()):userNos);
					discussGroup.setStatus("Y");
					if(status == 0){
						if(rightUsers.size() > 0){
							DataUtil.sendCreateGroupInfo(PersistenceContext.getEntity().getServerIP(),PersistenceContext.getEntity().getServerPort()-1,discussGroup);
							while(true){
								if(PersistenceContext.getEntity().getCreateGroupStatus() == Constants.CON_CREATE_GROUP_STATUS_SECCESS){
									JOptionPane.showMessageDialog(self, "创建成功");
									PersistenceContext.getEntity().getGroups().add(discussGroup);
									RefreshClient.refresh(curUser, f);
									self.dispose();
									PersistenceContext.getEntity().setCreateGroupStatus(Constants.CON_CREATE_GROUP_STATUS_UNLOGINED);
									break;
								}else if(PersistenceContext.getEntity().getCreateGroupStatus() == Constants.CON_CREATE_GROUP_STATUS_FAILTURE){
									JOptionPane.showMessageDialog(self, "创建失败 ");
									break;
								}
							}
						}else{
							JOptionPane.showMessageDialog(self, "组成员不能为空 ");
						}
					}else if(status == 1){
						if(rightUsers.size() > 0){
							DataUtil.sendUpdateGroupInfo(PersistenceContext.getEntity().getServerIP(),PersistenceContext.getEntity().getServerPort()-1, discussGroup);
							while(true){
								if(PersistenceContext.getEntity().getUpdateGroupStatus() == Constants.CON_UPDATE_GROUP_STATUS_SECCESS){
									JOptionPane.showMessageDialog(self, "更新成功");
									List<DiscussGroup> discussGroups = PersistenceContext.getEntity().getGroups();
									for(int i = 0; i < discussGroups.size(); i++){
										if(discussGroups.get(i).getDiscussGroupName().equals(discussGroup.getDiscussGroupName())){
											discussGroups.remove(i);
											discussGroups.add(discussGroup);
										}
									}
									PersistenceContext.getEntity().setGroups(discussGroups);
									self.dispose();
									PersistenceContext.getEntity().setUpdateGroupStatus(Constants.CON_UPDATE_GROUP_STATUS_UNLOGINED);
									break;
								}else if(PersistenceContext.getEntity().getUpdateGroupStatus() == Constants.CON_UPDATE_GROUP_STATUS_FAILTURE){
									JOptionPane.showMessageDialog(self, "更新失败 ");
									break;
								}
							}
						}else{
							JOptionPane.showMessageDialog(self, "组成员不能为空 ");
						}
					}
					
				}else{
					JOptionPane.showMessageDialog(self, "请输入讨论组名称");
				}
			}
		});
		exitBon.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
	}
}
