/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wetime.p2pmart.util.PageBean;

@Service
@Transactional
public class PageService extends BaseService {

	public PageBean findPageUsers(String hql, int firstResult, int maxResult, Object... params) {
		return this.getUserDao().findPageBean(hql, firstResult, maxResult, params);
	}
	
	
	public PageBean findPageProducts(String hql, int firstResult, int maxResult, Object... params) {
		return this.getUserDao().findPageBean(hql, firstResult, maxResult, params);
	}
}
