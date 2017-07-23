package com.slf.crud.model.demo;

import com.slf.crud.model.demo.User;

public class UserAo extends User {
	private Integer pageNo;
	private Integer pageSize;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
