/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.listener;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.wetime.p2pmart.jdbc.JdbcUtilFactory;
import cn.wetime.p2pmart.timer.ScanTimer;
import cn.wetime.p2pmart.util.Utils;
@SuppressWarnings("all")
public class StartupListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {

		Properties pro = System.getProperties();
		String osName = pro.getProperty("os.name");// 获得当前操作系统的名称
		String filePath = "";

		if ("Linux".equals(osName) || "linux".equals(osName)
				|| "LINUX".equals(osName)) {
			filePath = arg0.getServletContext().getRealPath("/"); // linux环境下的路径
			System.out.println(filePath);
		}
		if (osName.contains("Windows")) {
			filePath = arg0.getServletContext().getRealPath("/");
			System.out.println("window file path:" + filePath);
		}
		try {
			Utils.loadJdbcProperties(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JdbcUtilFactory.createPersistence().doDeleteAllProducts();
		
//		new Timer().schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				ScanTimer.newInstance().execute();
//			}
//		}, 100000, 2000);
	}

}
