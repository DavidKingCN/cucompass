/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wetime.p2pmart.dao.ProductDao;
import cn.wetime.p2pmart.dao.UserDao;
public class BaseService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	
}
