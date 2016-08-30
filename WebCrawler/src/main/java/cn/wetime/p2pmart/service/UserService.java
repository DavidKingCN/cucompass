/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wetime.p2pmart.pojo.User;
@SuppressWarnings("all")
@Service
@Transactional
public class UserService extends BaseService{
	
	public void addUser(User user){
		this.getUserDao().save(user);
	}
	
	public void updateUser(int id,User user){
		User entity = this.getUserDao().get(User.class, id);
		entity.setUserName(user.getUserName());
		entity.setPassword(user.getPassword());
		this.getUserDao().update(entity);
		
	}
	
	public List<User> findUsers(){
		return this.getUserDao().findList("from User", null);
	}

	public void removeUser(int id) {
		this.getUserDao().delete(User.class, id);
		
	}
}
