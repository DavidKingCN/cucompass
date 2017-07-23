package com.slf.crud.service.demo.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slf.crud.dao.demo.GroupMapper;
import com.slf.crud.dao.demo.UserMapper;
import com.slf.crud.model.ServiceResult;
import com.slf.crud.model.demo.Group;
import com.slf.crud.model.demo.User;
import com.slf.crud.service.demo.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private GroupMapper groupDao;
	
	@Override
	public ServiceResult<Object> findRecords(User record, int pagenow, int pagesize) {
		int limitStart = (pagenow-1)*pagesize;
		int limitEnd = pagesize;
		
		List<User> list = userDao.findByRecord(record, limitStart, limitEnd);
		int count = userDao.findByRecordCount(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(list);
		result.setTotal(count);
		
		return result;
	}

	@Override
	public ServiceResult<Object> addRecord(User record) {
		
		userDao.insertSelective(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public ServiceResult<Object> delRecord(int record) {
		userDao.deleteLogicById(1, record);//1表示逻辑删除
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public ServiceResult<Object> updateRecord(User record) {
		userDao.updateByPrimaryKeySelective(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public boolean addUserAndGroup(User user, Group group) {
		int count = this.groupDao.findByRecordCount(group);
		Group needSavedGroup = null; 
		if(count>0){
			needSavedGroup = this.groupDao.findByRecord(group, 0, 1).get(0);
		}else{
			needSavedGroup = group;
		}
		//保存组信息
		int suc = this.groupDao.saveOrUpdate(needSavedGroup);
		if(suc!=1){
			return false;
		}
		int groupId = needSavedGroup.getGroupId();
		user.setUserGroupFk(groupId);
		//保存用户
		return this.userDao.insert(user)==1;
	}

	@Override
	public User selectByPK(Integer userId) {
		return this.userDao.selectByPrimaryKey(userId);
	}

	@Override
	public boolean modifyUserAndGroup(User user, Group group) {
		int rt = this.groupDao.saveOrUpdate(group);
		int rt2 = this.userDao.saveOrUpdate(user);
		return rt>0 && rt2>0;
	}

}
