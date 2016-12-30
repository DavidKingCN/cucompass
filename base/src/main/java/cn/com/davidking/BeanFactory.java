/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */

package cn.com.davidking;



import cn.com.davidking.jdbc.DBMetaHelper;
import cn.com.davidking.jdbc.Persistence;
import cn.com.davidking.persist.ProxyIpPersist;

// TODO: Auto-generated Javadoc



/**
 * *.
 *
 * @author daikai
 */

public class BeanFactory {

	/**
	 * New db meta helper.
	 *
	 * @return the DB meta helper
	 */

	public static DBMetaHelper newDBMetaHelper(){
		return new DBMetaHelper();
	}

	

	/**
	 * New persistence.
	 *
	 * @return the persistence
	 */
	public static Persistence newPersistence(){
		return new Persistence();
	}

	
	public static ProxyIpPersist newProxyIpPersist(){
		return new ProxyIpPersist();
	}
}
