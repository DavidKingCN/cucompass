/*
 *    功能名称   ： 工具类 封裝实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesUtil.
 */
@SuppressWarnings("all")
public class PropUtil {
	
	public static void main(String[] args) {
		DbConfig dbConfig = PropUtil.newInstanceByConf(DbConfig.class,"/mysql.config.properties");
		
		System.out.println(dbConfig);
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static<T> T newInstanceByConf(Class<T> clz,String confCLzPath) {
		Properties p = new Properties();
		InputStream in = Object.class.getResourceAsStream(confCLzPath);
		T t = null;
		try {
			p.load(in);
			
			
			Enumeration ks= p.keys();
		
			Map<String,String> rtKvs = new HashMap<String,String>();
			while(ks.hasMoreElements()){
				String curKey = ks.nextElement().toString();
				String curVal = p.getProperty(curKey);
				rtKvs.put(curKey, curVal);
			}
			t = clz.newInstance();
			ReflectUtil.setFieldValue(t, rtKvs);
		} catch (Exception e) {
			t = null;
		}
		return t;
	}
	public static class DbConfig{
		private String url;
		private String username;
		private String password;
		private String driverName;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
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
		public String getDriverName() {
			return driverName;
		}
		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}
		@Override
		public String toString() {
			return "DbConfig [url=" + url + ", username=" + username + ", password=" + password + ", driverName="
					+ driverName + "]";
		}
		
		
	}
}
