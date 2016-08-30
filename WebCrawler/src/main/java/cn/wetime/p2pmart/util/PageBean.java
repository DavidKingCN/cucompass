/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @author daikai
 *
 */
public class PageBean {
	
	private Long total;
	private List<? extends Object> rows;
	private static final String ROWS = "rows"; 
	private static final int DEFAULT_ROWS = 10;
	private static final String PAGE = "page"; 
	
	public PageBean() {
		super();
	}
	public PageBean(List<? extends Object> rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}
	
	public List<? extends Object> getRows() {
		return rows;
	}
	public void setRows(List<? extends Object> rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	/**
	 * 得到每页的条目数
	 * @return
	 */
	public static int getPageSize(HttpServletRequest request){
		String pageSizeStr = request.getParameter(ROWS);
		int pageSize = DEFAULT_ROWS;// 每页条目数
		if (pageSizeStr != null) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		return pageSize;
	}
	/**
	 * 得到起始行数索引
	 * @param request
	 * @return
	 */
	public static int getFirstResult(HttpServletRequest request){
		String nextPage = request.getParameter(PAGE);
		int nextPageNo = 1;
		if (nextPage != null) {
			nextPageNo = Integer.parseInt(nextPage);
		}
		if (nextPageNo == 0)
			nextPageNo = 1;

		// 起始行数索引
		int firstResult = getPageSize(request) * (nextPageNo - 1);
		
		return firstResult;
	}
}
