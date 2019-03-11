package model;
/*
 * 用户方法
 * */
public class User {
	private String username;
	private String password;
	private String nickname;
	private String pass;
	 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", nickname=" + nickname + ", pass=" + pass + "]";
	}
	
	
}
