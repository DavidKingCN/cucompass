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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import cn.com.davidking.jdbc.pool.MysqlJDBCPool;
import cn.com.davidking.model.ProxyIp;


// TODO: Auto-generated Javadoc
/**
 * The Class Persistence.
 */
public final class Persistence {
	
	public static Persistence newPersistence(){
		return new Persistence();
	}
	
	public SortedMap<String,Integer> getProxyIp(){
		String sql = "select * from ProxyIp  ";
		
//		System.out.println(sql);
		List<String> cols = new ArrayList<>();
		cols.add("ip");
		cols.add("port");
		List<Map<String,Object>> result = null;
		try {
			result = Persistence.newPersistence().doQuerySql(sql, cols,false);
		} catch (Exception e) { }
		TreeMap<String,Integer> treeMap = new TreeMap<>();
		if(result!=null)
			result.forEach(r->{
				treeMap.put(r.get("ip").toString(), (Integer)r.get("port"));
			});
		return treeMap;
	}
	
	public LinkedBlockingQueue<ProxyIp> getIp2Queue(boolean needId){
		
		//NOTE:the del flag ...
		String sql = "select * from ProxyIp where runFlag='0' and delFlag='0' order by id";
		
//		System.out.println(sql);
		List<String> cols = new ArrayList<>();
		cols.add("ip");
		cols.add("port");
		List<Map<String,Object>> result = null;
		try {
			result = Persistence.newPersistence().doQuerySql(sql, cols,needId);
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
	public Map<String,Object> doQuerySqlByDbPool(String sql,List<String> cols)throws Exception{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String,Object> map = null;
		try {
			conn = MysqlJDBCPool.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				map = new HashMap<>(); map.put("id", rs.getInt("id")+"");
				for(String col:cols){
					Object obj = rs.getObject(col);	map.put(col, obj);
				}
			}
		} catch (SQLException ignore) {			
		} finally {	JdbcUtils.release(rs, st, conn); }
	
		return map;
	}
	
	public List<Map<String,Object>> doQuerySql(String sql,List<String> cols,boolean needId)throws Exception{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Map<String,Object>> results = new ArrayList<>();
		Map<String,Object> map = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				map = new HashMap<>(); 
				if(needId)map.put("id", rs.getInt("id")+"");
				if(cols!=null)
					for(String col:cols){
						Object obj = rs.getObject(col);	
						map.put(col, obj);
						
					}
				if(map!=null && map.size()>0)
					results.add(map);
			}
		} catch (SQLException ignore) {			
		} finally {	JdbcUtils.release(rs, st, conn); }
	
		return results;
	}
	public Map<String,Object> doQuerySql(String sql,List<String> cols)throws Exception{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String,Object> map = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				map = new HashMap<>(); 
				map.put("id", rs.getInt("id")+"");
				if(cols!=null)
					for(String col:cols){
						Object obj = rs.getObject(col);	
						map.put(col, obj);
					}
			}
		} catch (SQLException ignore) {			
		} finally {	JdbcUtils.release(rs, st, conn); }
	
		return map;
	}
	/**
	 * Do exec sql.
	 *
	 * @param sql the sql
	 * @return true, if do exec sql
	 */
	public boolean doExecSql(String sql) {
		return doExecSql(null,sql);
	}
	/**
	 * 
	 * @param skTab
	 * @param sql
	 * @param objs
	 * @return
	 */
	public boolean doExecSql(String skTab, String sql,List<Object> objs) {
		boolean exeRt = false;
		Connection conn = null;
		PreparedStatement ps=null;
		synchronized(JdbcUtils.class){
			try {
				conn = JdbcUtils.getConnection(); conn.setAutoCommit(false);
				ps= conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
				int idx = 1;
				for(Object obj:objs) ps.setObject(idx++, obj);
				ps.executeUpdate(); conn.commit();
			} catch (Exception e) {
				System.out.println("异常数目："+(++JdbcUtils.exeErrs)+" sql="+sql);
				try { conn.rollback();} catch (SQLException ignore) {}
			} finally {
				JdbcUtils.release(null, ps, conn);
			}
		}
		return exeRt;
	}
	/**
	 * Do exec sql.
	 *
	 * @param skTab the sk tab
	 * @param sql the sql
	 * @return true, if do exec sql
	 */
	public boolean doExecSql(String skTab, String sql) {
		Connection conn = null;
		Statement st = null;
		boolean exeRt = false;
		ResultSet rs = null;
		synchronized(JdbcUtils.class){
			try {
				conn = JdbcUtils.getConnection();
				conn.setAutoCommit(false);
				st = conn.createStatement();
				//sql = StringUtil.replSpclSymbols(StringUtil.sbcToDbcStr(sql));
				st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				rs = st.getGeneratedKeys();
//				if(ExtractDataTool.test)
				//System.out.println("提示信息：[插入sql:" + sql + "]");
				conn.commit();
			} catch (SQLException e) {
				
				System.out.println("异常数目："+(++JdbcUtils.exeErrs)+" sql="+sql);
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			} finally {
				JdbcUtils.release(rs, st, conn);
			}
		}
		return exeRt;

	}
	
	/**
	 * Do exec sql.
	 *
	 * @param skTab the sk tab
	 * @param sql the sql
	 * @return true, if do exec sql
	 */
	public boolean doExecSqlByDbPool(String skTab, String sql) {
		Connection conn = null;
		Statement st = null;
		boolean exeRt = false;
		ResultSet rs = null;
		synchronized(MysqlJDBCPool.class){
			try {
				conn = MysqlJDBCPool.getConnection();
				conn.setAutoCommit(false);
				st = conn.createStatement();
				//sql = StringUtil.replSpclSymbols(StringUtil.sbcToDbcStr(sql));
				st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				rs = st.getGeneratedKeys();
//				if(ExtractDataTool.test)
				//System.out.println("提示信息：[插入sql:" + sql + "]");
				conn.commit();
			} catch (SQLException e) {
				
				System.out.println("异常数目："+(++JdbcUtils.exeErrs)+" sql="+sql);
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			} finally {
				JdbcUtils.release(rs, st, conn);
			}
		}
		return exeRt;

	}
	
}
