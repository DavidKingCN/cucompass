/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.jdbc.pool;

import java.sql.*;
import java.util.*;

import cn.com.davidking.util.PropUtil;

import java.io.*;
// TODO: Auto-generated Javadoc

/**
 * @version 1.0
 */
public class MysqlJDBCPool {
	
	/** The idle connections. */
	private static LinkedList<JdbcProxyWrapper> idleConnections = new LinkedList<JdbcProxyWrapper>();
	
	/** The used connections. */
	private static HashSet<JdbcProxyWrapper> usedConnections = new HashSet<JdbcProxyWrapper>();
	
	private static String driverClass = "com.mysql.jdbc.Driver";
	
	/** The url. */
	private static String url = "jdbc:mysql://127.0.0.1:3306/test";
	
	/** The user. */
	private static String username = "admin";
	
	/** The password. */
	private static String password = "";
	
	/** The last clear time. */
	static private long lastClearTime = System.currentTimeMillis();
	
	/** The CHEC k_ connectio n_ time. */
	public static long CHECK_CONNECTION_TIME = 4 * 60 * 60 * 1000; // 4 hours

	private static PropUtil.DbConfig dbconfig = null;//PropUtil.newInstanceByConf(PropUtil.DbConfig.class, "/mysql.config.properties");
	static {
		dbconfig = PropUtil.newInstanceByConf(PropUtil.DbConfig.class, "/mysql.config.properties");
		driverClass = dbconfig.getDriverName();
		url = dbconfig.getUrl();
		username = dbconfig.getUsername();
		password = dbconfig.getPassword();
		initDriver();
	}

	/**
	 * The Constructor.
	 */
	private MysqlJDBCPool() {
	}

	/**
	 * Inits the driver.
	 */
	private static void initDriver() {
		try { Class.forName(driverClass).newInstance(); } catch (Exception e) {}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public static synchronized Connection getConnection() {
		clearClosedConnection();
		while (idleConnections.size() > 0) {
			try {
				JdbcProxyWrapper wrapper = idleConnections.removeFirst();
				if (wrapper.connection.isClosed()) { continue; }
				usedConnections.add(wrapper);
				return wrapper.connection;
			} catch (Exception ignore) {}
		}
		int newCount = getIncreasingConnectionCount();
		LinkedList<JdbcProxyWrapper> list = new LinkedList<JdbcProxyWrapper>();
		JdbcProxyWrapper wrapper = null;
		for (int i = 0; i < newCount; i++) {
			wrapper = getNewConnection();
			if (wrapper != null) { list.add(wrapper); }
		}
		if (list.size() == 0) { return null; }
		wrapper = (JdbcProxyWrapper) list.removeFirst();
		usedConnections.add(wrapper);

		idleConnections.addAll(list);
		list.clear();

		return wrapper.connection;
	}

	/**
	 * Gets the new connection.
	 *
	 * @return the new connection
	 */
	private static JdbcProxyWrapper getNewConnection() {
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			JdbcProxyWrapper wrapper = new JdbcProxyWrapper(con);
			return wrapper;
		} catch (Exception e) { e.printStackTrace();}
		return null;
	}

	/**
	 * Push connection back to pool.
	 *
	 * @param con the con
	 */
	static synchronized void pushConnectionBackToPool(JdbcProxyWrapper con) {
		boolean exist = usedConnections.remove(con);
		if (exist) { idleConnections.addLast(con); }
	}

	/**
	 * Clear closed connection.
	 */
	private static void clearClosedConnection() {
		long time = System.currentTimeMillis();
		// sometimes user change system time,just return
		if (time < lastClearTime) {	time = lastClearTime; return; }
		// no need check very often
		if (time - lastClearTime < CHECK_CONNECTION_TIME) {	return; }
		lastClearTime = time;

		// begin check
		Iterator<JdbcProxyWrapper> iterator = idleConnections.iterator();
		while (iterator.hasNext()) {
			JdbcProxyWrapper wrapper = iterator.next();
			try {
				if (wrapper.connection.isClosed()) { iterator.remove();	}
			} catch (Exception e) { iterator.remove(); }
		}

		// make connection pool size smaller if too big
		int decrease = getDecreasingConnectionCount();
		if (idleConnections.size() < decrease) { return; }

		while (decrease-- > 0) {
			JdbcProxyWrapper wrapper = (JdbcProxyWrapper) idleConnections.removeFirst();
			try { wrapper.connection.close(); } catch (Exception ignore) { }
		}
	}

	/**
	 * get increasing connection count, not just add 1 connection.
	 *
	 * @return count
	 */
	public static int getIncreasingConnectionCount() {
		int count = 1;
		int current = getConnectionCount();
		count = current / 4;
		if (count < 1) { count = 1;	}
		return count;
	}

	/**
	 * get decreasing connection count, not just remove 1 connection.
	 *
	 * @return count
	 */
	public static int getDecreasingConnectionCount() {
		int current = getConnectionCount();
		if (current < 10) {	return 0;}
		return current / 3;
	}

	/**
	 * Prints the debug msg.
	 */
	public synchronized static void printDebugMsg() {
		printDebugMsg(System.out);
	}

	/**
	 * Prints the debug msg.
	 *
	 * @param out the out
	 */
	public synchronized static void printDebugMsg(PrintStream out) {
		StringBuffer msg = new StringBuffer();
		msg.append("total connections: " + getConnectionCount()).append("\r\n");
		msg.append("idle connections: " + getNotUsedConnectionCount()).append("\r\n");
		msg.append("used connections: " + getUsedConnectionCount());
		out.println(msg);
	}

	/**
	 * Gets the not used connection count.
	 *
	 * @return the not used connection count
	 */
	public static synchronized int getNotUsedConnectionCount() {
		return idleConnections.size();
	}

	/**
	 * Gets the used connection count.
	 *
	 * @return the used connection count
	 */
	public static synchronized int getUsedConnectionCount() {
		return usedConnections.size();
	}

	/**
	 * Gets the connection count.
	 *
	 * @return the connection count
	 */
	public static synchronized int getConnectionCount() {
		return idleConnections.size() + usedConnections.size();
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
	 * @param _url the url
	 */
	public static void setUrl(String _url) {
		url = _url.trim();
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public static String getUser() {
		return username;
	}

	/**
	 * Sets the user.
	 *
	 * @param _user the user
	 */
	public static void setUser(String _user) {
		username = _user.trim();
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
	 * @param _password the password
	 */
	public static void setPassword(String _password) {
		password = _password.trim();
	}

}

