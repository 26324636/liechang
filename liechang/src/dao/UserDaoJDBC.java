package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.LieChangException;
import model.User;
import Util.SqlDB;

public class UserDaoJDBC implements IUserDao {

	/*
	 *用户信息增加
	 * */
	@Override
	public void add(User user) {
		ResultSet rs = null;
		try {
			//判断用户是否存在
			String sql = "select count(*) from t_user where username='"+user.getUsername()+"'";
			rs = SqlDB.execQuery(sql);
			int count = 0;
			while(rs.next()) {
				count = rs.getInt(1);
			}
			if(count>0) throw new LieChangException("要添加的用户已经存在，不能添加!");
			//添加用户
			sql = "insert into t_user values ('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getNickname()+"','" + user.getPass() + "')";
			//System.out.println(sql);
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*
	 *用户信息删除
	 * */
	@Override
	public void delete(String username) {
		try {
			String sql = "delete from t_user where username='"+username+"'";
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	
	
	/*
	 *用户信息更新
	 * */
	@Override
	public void update(User user) {
		try {
			String sql = "update t_user set password='"+user.getPassword()+"',nickname='"+user.getNickname()+"',pass='" + user.getPass() +"' where username='"+user.getUsername()+"'";
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 用户信息加载
	 * */
	@Override
	public User load(String username) {
		ResultSet rs = null;
		User u = null;
		try {
			String sql = "select * from t_user where username='" + username + "'";
			rs = SqlDB.execQuery(sql);
			while(rs.next()) {
				u = new User();
				u.setNickname(rs.getString("nickname"));
				u.setPassword(rs.getString("password"));
				u.setUsername(rs.getString("username"));
				u.setPass(rs.getString("pass"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return u;
	}
	
	/**
	 * 用户信息查询所有并且按照管理员/普通用户排序
	 * */
	@Override
	public List<User> list() {
		List<User> users = new ArrayList<User>();
		ResultSet rs = null;
		try {
			String sql = "select * from t_user order by pass asc";
			rs = SqlDB.execQuery(sql);
			User u = null;
			while(rs.next()) {
				u = new User();
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setNickname(rs.getString("nickname"));
				u.setPass(rs.getString("pass"));
				users.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return users;
	}
	
	/*
	 * 用户登录身份认证
	 * */
	@Override
	public User login(String username, String password) {
		ResultSet rs = null;
		User u = null;
		try {
			String sql = "select * from t_user where username='"+username+"' and password='" + password + "'";
			rs = SqlDB.execQuery(sql);		
			while(rs.next()) {				
				u = new User();
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setNickname(rs.getString("nickname"));
				u.setPass(rs.getString("pass"));
			}
			if(u==null) throw new LieChangException("用户名或者密码不正确");
			else{
				System.out.println("登录成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return u;
	}

	

}
