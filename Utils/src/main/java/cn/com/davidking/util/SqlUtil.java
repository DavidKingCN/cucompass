package cn.com.davidking.util;

public class SqlUtil {

	public static String limitSql (int page,int pagesize){
		StringBuffer sql = new StringBuffer("");
		int offset = (page-1)*pagesize;
		sql .append(""+offset);
		sql .append(",");
		sql .append(""+pagesize);
		return sql.toString();
	}
}
