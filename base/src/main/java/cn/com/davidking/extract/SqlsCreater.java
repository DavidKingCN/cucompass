/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.extract;

import java.util.Map;

import cn.com.davidking.model.Mx;



// TODO: Auto-generated Javadoc
/**
 * 萃取sql集合.
 *
 * @author dk
 */
public interface SqlsCreater {
	
	/**
	 * Extract.
	 *
	 * @param confPath the conf path
	 * @param model the model
	 * @param args the args
	 * @return the string
	 */
	String extract(String confPath, Class<Mx> model, Map<String, String> args);

}
