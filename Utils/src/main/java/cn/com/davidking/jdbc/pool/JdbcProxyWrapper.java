/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.jdbc.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
// TODO: Auto-generated Javadoc

/**
 * 
 * @version 1.0
 */
public class JdbcProxyWrapper implements InvocationHandler {
	
	/** The Constant CLOSE_METHOD_NAME. */
	private final static String CLOSE_METHOD_NAME = "close";
	
	/** The connection. */
	public Connection connection = null;
	
	/** The origin connection. */
	private Connection originConnection = null;
	
	/** The last access time. */
	public long lastAccessTime = System.currentTimeMillis();

	/**
	 * The Constructor.
	 *
	 * @param con the con
	 */
	JdbcProxyWrapper(Connection con) {
		this.connection = (Connection) Proxy.newProxyInstance(con.getClass().getClassLoader(), new Class[]{Connection.class},	this);
		originConnection = con;
	}

	/**
	 * Close.
	 *
	 * @throws SQLException the SQL exception
	 */
	void close() throws SQLException {
		originConnection.close();
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method m, Object[] args)	throws Throwable {
		Object obj = null;
		if (CLOSE_METHOD_NAME.equals(m.getName())) MysqlJDBCPool.pushConnectionBackToPool(this);
		else obj = m.invoke(originConnection, args);
		
		lastAccessTime = System.currentTimeMillis();
		return obj;
	}
}