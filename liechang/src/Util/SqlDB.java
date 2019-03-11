package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * 数据库连接
 * */
public class SqlDB {
	private static String username = "root";
	private static String password = "26324636";
	private static String database = "liechang";
	
	private static String driver="com.mysql.jdbc.Driver";
	private static Connection conn;
	private static Statement comm;
	private static ResultSet rs;
	
	public static void regDriver() {
		try {
			Class.forName(driver).newInstance();
		} catch(Exception e) {
			System.out.println("unable to drive the entity!");
		}
	}
	
	public static void conBuild() {
		try {
			SqlDB.regDriver();
			String url = "jdbc:mysql://localhost:3306/"+database;
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(true);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("unable to connect the database");
		}
	}
	
	public static ResultSet execQuery(String stmt) {
		try {
			SqlDB.conBuild();
			comm = conn.createStatement();
			rs = comm.executeQuery(stmt);
			return rs;
		} catch (Exception e) {
			System.out.println("unable to create statement!");
			return null;
		}
	}
	
	public static void execUpdate(String UpdateString) {
		try {
			SqlDB.conBuild();
			comm = conn.createStatement();
			comm.executeUpdate(UpdateString);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void closeDB() {
		try {
			comm.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
