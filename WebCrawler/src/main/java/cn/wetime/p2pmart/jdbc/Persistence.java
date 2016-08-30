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
import java.sql.SQLException;
import java.sql.Statement;



public final class Persistence {
	public void doDeleteAllProducts() {
		Connection conn = null;
		Statement st = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			String sql = "delete from t_product";
				
			System.out.println("提示信息：[插入sql:"+sql+"]");
			st.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JdbcUtils.release(null, st, conn);
		}
		
	}
	
}
