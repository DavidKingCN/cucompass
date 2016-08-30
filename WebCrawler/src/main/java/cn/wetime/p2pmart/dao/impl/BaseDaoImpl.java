/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.wetime.p2pmart.dao.BaseDao;
import cn.wetime.p2pmart.util.PageBean;
/**
 * 
 * @author daikai
 *
 */

@SuppressWarnings("all")
public class BaseDaoImpl implements BaseDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource
	protected SessionFactory sessionFactory;

	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		
		return sessionFactory.getCurrentSession();
	}
	public <T> void save(T t){
		Session session = getSession();
		session.save(t);
	}
	
	public <T> void update(T t){
		getSession().update(t);
	}
	
	public <T, ID extends Serializable> T get(Class<T> entityClass, ID id){
		T entity = (T)getSession().get(entityClass, id);
		return entity;
	}
	
	public <T, ID extends Serializable> void delete(Class<T> entityClass, ID id){
		getSession().delete(this.get(entityClass, id));
	}

	public <T> List<T> findList(String hql, Object... params){
		Query query = createQueryByHqlAndParams(hql,params);
		return (List<T>)query.list();
	}
	
	public <T> T findUnique(String hql, Object... params){
		Query query = createQueryByHqlAndParams(hql,params);
		query.setMaxResults(1);
		return (T)query.uniqueResult();
	}
	
	
	public <T> List<T> findByPage(String hql,int firstResult, int maxResult, Object... params){
		Query query = createQueryByHqlAndParams(hql,params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return (List<T>)query.list();
	}

	public <T> Long count(String hql, Object... params) {
		hql = "select count(*) " +hql;
		Query query = getSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}

	private Query createQueryByHqlAndParams(String hql,Object... params){
		Query query = getSession().createQuery(hql);
		if(params!=null){
			Object[] param = (Object[])params[0];
			if(param!=null&&param.length!=0)
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i,param[i]);
				}
		}
		return query;
	}

	public PageBean findPageBean(String hql, int firstResult, int maxResult, Object... params) {
		return new PageBean(this.findByPage(hql, firstResult, maxResult, params),this.count(hql, params));
	}

	public <T> void saveBatch(List<T> t) {
		Session session = getSession();
		for(int i=0;i<t.size();i++){
			session.save(t.get(i));
			if(i%50==0){
				session.flush();
				session.clear();
			}
		}
	}

	public <T> void updateBatch(List<T> t) {
		Session session = getSession();
		for(int i=0;i<t.size();i++){
			session.update(t.get(i));
			if(i%50==0){
				session.flush();
				session.clear();
			}
		}
	}
}
