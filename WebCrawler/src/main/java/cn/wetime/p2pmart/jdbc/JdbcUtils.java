/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	private static  String url = "jdbc:mysql://192.168.1.141:3306/test";
	private static  String username = "root"; 
	private static  String password = "123456";
	private static  String driverName = "com.mysql.jdbc.Driver";
	
	//regist db driver
	static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	// get the connection
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	//release resouce
	public static void release(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		JdbcUtils.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		JdbcUtils.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		JdbcUtils.password = password;
	}
	
	
}
