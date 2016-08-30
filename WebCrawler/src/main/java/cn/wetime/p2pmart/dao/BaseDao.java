/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.dao;

import java.io.Serializable;
import java.util.List;

import cn.wetime.p2pmart.util.PageBean;


public interface BaseDao {

	public <T> void save(T t);

	public <T> void saveBatch(List<T> t);

	public <T> void update(T t);

	public <T> void updateBatch(List<T> t);

	public <T, ID extends Serializable> T get(Class<T> entityClass, ID id);

	public <T, ID extends Serializable> void delete(Class<T> entityClass, ID id);

	public <T> List<T> findList(String hql, Object... params);

	public <T> Long count(String hql, Object... params);

	public <T> T findUnique(String hql, Object... params);

	public <T> List<T> findByPage(String hql, int firstResult, int maxResult, Object... params);

	public PageBean findPageBean(String hql, int firstResult, int maxResult, Object... params);
	
}
