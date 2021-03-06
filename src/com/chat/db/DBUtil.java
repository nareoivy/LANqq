package com.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.chat.bean.DiscussGroup;
import com.chat.bean.LeaveMessage;
import com.chat.bean.User;
import com.chat.util.DataUtil;

public class DBUtil {
	Connection conn = null;

	/**
	 * 得到连接
	 * 
	 * @return
	 */
	public Connection getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://192.168.3.51:3306/chat?characherEncoding=gbk",
							"root", "admin");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭资源
	 * 0
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户名和密码查询User
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public User getLoginUser(String no, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		conn = getConn();
		String sql = "select * from user where no=? and password=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new User();
					user.setPassword(rs.getString("password"));
					user.setUsername(rs.getString("username"));
					user.setUserImage(rs.getBytes("userImage"));
					user.setPort(rs.getInt("port"));
					user.setIp(rs.getString("ip"));
					user.setStatus(rs.getString("status"));
					user.setNo(rs.getString("no"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return user;
		}
	}

	/**
	 * 获取所有User
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public List<User> getUsers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User user = null;
		List<User> users = new ArrayList<User>();

		conn = getConn();
		String sql = "select * from user order by status desc";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					user = new User();
					user.setPassword(rs.getString("password"));
					user.setUsername(rs.getString("username"));
					user.setUserImage(rs.getBytes("userImage"));
					user.setPort(rs.getInt("port"));
					user.setIp(rs.getString("ip"));
					user.setStatus(rs.getString("status"));
					user.setNo(rs.getString("no"));
					users.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return users;
		}

	}
	
	/**
	 * 获取在线User
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public List<User> getOnlineUsers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User user = null;
		List<User> users = new ArrayList<User>();

		conn = getConn();
		String sql = "select * from user where status = 'Y'";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					user = new User();
					user.setPassword(rs.getString("password"));
					user.setUsername(rs.getString("username"));
					user.setUserImage(rs.getBytes("userImage"));
					user.setPort(rs.getInt("port"));
					user.setIp(rs.getString("ip"));
					user.setStatus(rs.getString("status"));
					user.setNo(rs.getString("no"));
					users.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return users;
		}

	}

	/**
	 * 根据userno 获取好友列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public List<User> getFriends(User curUser) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<User>();

		conn = getConn();
		String sql = "select friendNos from friend  where userNo =?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, curUser.getNo());
			rs = ps.executeQuery();

			String friendNos = "";
			if (rs != null) {
				while (rs.next()) {
					friendNos = rs.getString("friendNos");
				}
				String sql2 = "select * from user where no in (" + friendNos
						+ ") order by status desc";
				ps = conn.prepareStatement(sql2);
				rs = ps.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						user = new User();
						user.setPassword(rs.getString("password"));
						user.setUsername(rs.getString("username"));
						user.setUserImage(rs.getBytes("userImage"));
						user.setPort(rs.getInt("port"));
						user.setIp(rs.getString("ip"));
						user.setStatus(rs.getString("status"));
						user.setNo(rs.getString("no"));
						users.add(user);
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return users;
		}
	}

	/**
	 * 根据编号，获得用户
	 */
	@SuppressWarnings("finally")
	public User getUserByNo(String no) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		conn = getConn();
		String sql = "select * from user where no=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new User();
					user.setPassword(rs.getString("password"));
					user.setUsername(rs.getString("username"));
					user.setUserImage(rs.getBytes("userImage"));
					user.setPort(rs.getInt("port"));
					user.setIp(rs.getString("ip"));
					user.setStatus(rs.getString("status"));
					user.setNo(rs.getString("no"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return user;
		}
	}

	/**
	 * 根据IP和Port，获得用户
	 */
	@SuppressWarnings("finally")
	public User getUserByIpPort(String ip, int port) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		conn = getConn();
		String sql = "select * from user where ip=? and port=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, ip);
			ps.setInt(2, port);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new User();
					user.setPassword(rs.getString("password"));
					user.setUsername(rs.getString("username"));
					user.setUserImage(rs.getBytes("userImage"));
					user.setPort(rs.getInt("port"));
					user.setIp(rs.getString("ip"));
					user.setStatus(rs.getString("status"));
					user.setNo(rs.getString("no"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return user;
		}
	}

	/**
	 * 设置用户状态
	 */
	public synchronized void setUserStatus(User user, String status) {
		Connection conn = null;
		PreparedStatement ps = null;

		conn = getConn();
		String sql = "update user set status = '" + status + "' where no=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNo());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}
	}

	/**
	 * 创建讨论组
	 */
	public synchronized boolean createDiscussGroup(DiscussGroup discussGroup) {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConn();
		try {
			ImageIcon ii = new ImageIcon(DataUtil.getImgPath(getClass(),
					"comment.png"));
			byte[] bytImages = DataUtil.image2Bytes(ii);
			ps = conn
					.prepareStatement("insert into discuss_group values(?,?,?,?,?)");
			ps.setString(1, discussGroup.getGroupMaster());
			ps.setString(2, discussGroup.getDiscussGroupName());
			ps.setString(3, discussGroup.getUserNos());
			ps.setString(4, discussGroup.getStatus());
			ps.setBytes(5, bytImages);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}
		return false;
	}

	/**
	 * 修改讨论组
	 */
	@SuppressWarnings("finally")
	public synchronized boolean updateDiscussGroup(DiscussGroup discussGroup) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isUpdated = false;
		conn = getConn();

		try {
			ps = conn
					.prepareStatement("update discuss_group set userNos=? where discussGroupName=?");
			ps.setString(1, discussGroup.getUserNos());
			ps.setString(2, discussGroup.getDiscussGroupName());
			ps.executeUpdate();
			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
			return isUpdated;
		}
	}

	/**
	 * 查询所有的讨论组
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public List<DiscussGroup> getGroup() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DiscussGroup discussGroup = null;
		List<DiscussGroup> discussGroups = new ArrayList<DiscussGroup>();

		conn = getConn();
		String sql = "select * from  discuss_group order by status desc";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					discussGroup = new DiscussGroup();
					discussGroup.setGroupMaster(rs.getString("groupMaster"));
					discussGroup.setDiscussGroupName(rs
							.getString("discussGroupName"));
					discussGroup.setUserNos(rs.getString("userNos"));
					discussGroup.setStatus(rs.getString("status"));
					discussGroup.setGroupImage(rs.getBytes("groupImage"));
					discussGroups.add(discussGroup);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return discussGroups;
		}
	}

	/**
	 * 查询当前用户所在的讨论组
	 */
	public List<DiscussGroup> getGroupByCurUser(User curUser) {
		List<DiscussGroup> allGroup = null;
		try {
			allGroup = getGroup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<DiscussGroup> curGroup = new ArrayList<DiscussGroup>();
		if (null != allGroup) {
			for (DiscussGroup discussGroup : allGroup) {
				if (discussGroup.getUserNos().indexOf(curUser.getNo()) > -1) {
					curGroup.add(discussGroup);
				}
			}
		}
		return curGroup;
	}

	/**
	 * 获取当前讨论组的所有User
	 * 
	 * @param discussGroup
	 * @param curUser
	 * @return
	 */
	public List<User> getUsersByDiscussGroup(DiscussGroup discussGroup,
			User curUser) {
		List<User> allUsers = null;
		List<User> users = new ArrayList<User>();
		try {
			allUsers = getUsers();
			for (User user : allUsers) {
				if (discussGroup.getUserNos().indexOf(user.getNo()) > -1
						&& !user.getNo().equals(curUser.getNo())) {
					users.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 通过groupName获取讨论组
	 * 
	 * @param groupName
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	public DiscussGroup getDiscussGroupByGroupName(String groupName)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DiscussGroup discussGroup = null;

		conn = getConn();
		String sql = "select * from discuss_group where discussGroupName=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, groupName);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					discussGroup = new DiscussGroup();
					discussGroup.setGroupMaster(rs.getString("groupMaster"));
					discussGroup.setDiscussGroupName(rs
							.getString("discussGroupName"));
					discussGroup.setUserNos(rs.getString("userNos"));
					discussGroup.setStatus(rs.getString("status"));
					discussGroup.setGroupImage(rs.getBytes("groupImage"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return discussGroup;
		}

	}

	/**
	 * 删除讨论组
	 */
	@SuppressWarnings("finally")
	public synchronized boolean dropGroup(String groupName) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isUpdated = false;
		conn = getConn();
		String sql = "delete from discuss_group where discussGroupName=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, groupName);
			ps.execute();
			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
			return isUpdated;
		}
	}

	/**
	 * 保存一个用户
	 */
	@SuppressWarnings("finally")
	public synchronized boolean saveUser(String username, String userno,
			String password, byte[] userImage, String ipAddress) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isInserted = false;
		conn = getConn();
		try {
			ps = conn
					.prepareStatement("insert into user values(?,?,?,?,?,?,?)");
			ps.setString(1, "N");
			ps.setString(2, "7001");
			ps.setString(3, ipAddress);
			ps.setString(4, password);
			ps.setString(5,new String(username.getBytes(),"UTF-8"));
			ps.setString(6, userno);
			ps.setBytes(7, userImage);
			ps.executeUpdate();
			isInserted = true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close(null, ps, conn);
			return isInserted;
		}
	}
	
	/**
	 * 保存一条留言信息
	 */
	@SuppressWarnings("finally")
	public synchronized boolean saveLeaveMessage(LeaveMessage lm) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isInserted = false;
		conn = getConn();
		try {
			ps = conn.prepareStatement("insert into leave_message(senderNo,receiverNo,message,sendTime,isRead) values(?,?,?,?,?)");
			ps.setString(1, lm.getSender().getNo());
			ps.setString(2, lm.getReceiver().getNo());
			ps.setString(3, lm.getMessage());
			ps.setString(4,lm.getSendTime() );
			ps.setString(5, lm.getIsRead());
			ps.executeUpdate();
			isInserted = true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close(null, ps, conn);
			return isInserted;
		}
	}
	
	/**
	 * 根据用户no获得该用户的所有留言信息
	 */
	@SuppressWarnings("finally")
	public List<LeaveMessage> getLeaveMessages(String userNo) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LeaveMessage leaveMessage = null;
		List<LeaveMessage> leaveMessages = new ArrayList<LeaveMessage>();

		conn = getConn();
		String sql = "select * from  leave_message where receiverNo ='"+userNo+"' and isRead = 'N'";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					leaveMessage = new LeaveMessage();
					leaveMessage.setIsRead(rs.getString("isRead"));
					leaveMessage.setMessage(rs.getString("message"));
					User sender = getUserByNo(rs.getString("senderNo"));
					leaveMessage.setSender(sender);
					User receiver = getUserByNo(rs.getString("receiverNo"));
					leaveMessage.setReceiver(receiver);
					leaveMessage.setSendTime(rs.getString("sendTime"));				
					leaveMessages.add(leaveMessage);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
			return leaveMessages;
		}
	}
   
	/**
	 * 根据留言接收者的no将所有该接受者的留言置为已读：isRead = 'Y'
	 */
	public synchronized void setLeaveMessageIsRead(String receiverNo,String status) {
		Connection conn = null;
		PreparedStatement ps = null;

		conn = getConn();
		String sql = "update leave_message set  isRead = '" + status + "' where receiverNo=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, receiverNo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}
	}

}
