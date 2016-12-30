/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import cn.com.davidking.BeanFactory;
import cn.com.davidking.persist.pojo.ProxyIp;


// TODO: Auto-generated Javadoc
/**
 * The Class Persistence.
 */
public final class ProxyIpPersist {
	
	public static ProxyIpPersist newPersistence(){
		return new ProxyIpPersist();
	}
	
	public SortedMap<String,Integer> getProxyIp(){
		String sql = "select * from ProxyIp  ";
		
//		System.out.println(sql);
		List<String> cols = new ArrayList<>();
		cols.add("ip");
		cols.add("port");
		List<Map<String,Object>> result = null;
		try {
			result = BeanFactory.newPersistence().doQuerySql(sql, cols,false);
		} catch (Exception e) { }
		TreeMap<String,Integer> treeMap = new TreeMap<>();
		if(result!=null)
			result.forEach(r->{
				treeMap.put(r.get("ip").toString(), (Integer)r.get("port"));
			});
		return treeMap;
	}
	
	public LinkedBlockingQueue<ProxyIp> getIp2Queue(boolean needId){
		String sql = "select * from ProxyIp where runFlag='0' ";
		
//		System.out.println(sql);
		List<String> cols = new ArrayList<>();
		cols.add("ip");
		cols.add("port");
		List<Map<String,Object>> result = null;
		try {
			result = BeanFactory.newPersistence().doQuerySql(sql, cols,needId);
		} catch (Exception e) { }
		LinkedBlockingQueue<ProxyIp> queue = new LinkedBlockingQueue<ProxyIp>();
		if(result!=null)
			result.forEach(r->{
				ProxyIp pip = new ProxyIp();
				pip.setIp(r.get("ip").toString());
				pip.setPort((Integer)r.get("port"));
				queue.add(pip);
			});
		return queue;
	}
	
	
	
	
	public static void main(String[] args) {
		
		test004();
	}
	
	public static void test001(){
		String querySql = "select * from ProxyIp where ip='117.169.86.157' and port=8090";
		Map<String,Object> record  = null;
		try {
			record = BeanFactory.newPersistence().doQuerySql(querySql,null);
		} catch (Exception e) {}
		System.out.println(record);
	}
	
	public static void test002(){
		List<String> cols = new ArrayList<>();
		cols.add("star_name");
		cols.add("star_into_no");
		cols.add("star_into");
		Map<String,Object> record  = null;
		try {
			record = BeanFactory.newPersistence().doQuerySql("select * from t_stars_detail where id=14141",null);
		} catch (Exception e) {}
		System.out.println(record);
	}
	
	public static void test003(){
		String ip = "test";
		int port = 9000;
		String sql = "INSERT INTO ProxyIp(ip,port) VALUES ('"+ip+"', "+port+")";
		BeanFactory.newPersistence().doExecSql(sql);
	}
	public static void test004(){
		String sql = "DELETE FROM ProxyIp where ip = 'test'";
		BeanFactory.newPersistence().doExecSql(sql);
	}
}
