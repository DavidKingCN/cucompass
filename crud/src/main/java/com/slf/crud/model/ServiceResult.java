package com.slf.crud.model;

import java.io.Serializable;
/**
 * 数据返回返回结果
 * @author ywb
 *
 * @param <T>
 */
public class ServiceResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	private Integer total;
	
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
