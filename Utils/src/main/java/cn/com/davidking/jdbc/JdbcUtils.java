/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.davidking.util.PropUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcUtils.
 */
public class JdbcUtils {
	
	/** The url. */
	private static  String url;// = "jdbc:mysql://192.168.20.239:3306/Spider";
	
	/** The username. */
	private static  String username;// = "root"; 
	
	/** The password. */
	private static  String password;// = "9cf.com";
	
	/** The driver name. */
	private static  String driverName;// = "com.mysql.jdbc.Driver";
	
	/** The exe errs. */
	public static int exeErrs = 0;
	
	private static PropUtil.DbConfig dbconfig = PropUtil.newInstanceByConf(PropUtil.DbConfig.class, "/mysql.config.properties");
	
	//regist db driver
	static {
		try {
			Class.forName(dbconfig.getDriverName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	// get the connection
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPassword());
	}
	
	//release resouce
	/**
	 * Release.
	 *
	 * @param rs the rs
	 * @param st the st
	 * @param conn the conn
	 */
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
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public static String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url.
	 *
	 * @param url the url
	 */
	public static void setUrl(String url) {
		JdbcUtils.url = url;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the username
	 */
	public static void setUsername(String username) {
		JdbcUtils.username = username;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the password
	 */
	public static void setPassword(String password) {
		JdbcUtils.password = password;
	}
	
	public static void main(String[] args) {
		Persistence.newPersistence().doExecSql("test", "insert into user(username,password) values('daikai','daikai')");
	}

	public static String getDriverName() {
		return driverName;
	}

	public static void setDriverName(String driverName) {
		JdbcUtils.driverName = driverName;
	}
	
	
}
