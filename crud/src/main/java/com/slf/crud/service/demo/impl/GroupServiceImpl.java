package com.slf.crud.service.demo.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slf.crud.dao.demo.GroupMapper;
import com.slf.crud.model.ServiceResult;
import com.slf.crud.model.demo.Group;
import com.slf.crud.service.demo.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService{

	@Autowired
	private GroupMapper groupDao;
	
	@Override
	public ServiceResult<Object> findRecords(Group record, int pagenow, int pagesize) {
		int limitStart = (pagenow-1)*pagesize;
		int limitEnd = pagesize;
		
		List<Group> list = groupDao.findByRecord(record, limitStart, limitEnd);
		int count = groupDao.findByRecordCount(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(list);
		
		return result;
	}

	@Override
	public ServiceResult<Object> addRecord(Group record) {
		
		groupDao.insertSelective(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public ServiceResult<Object> delRecord(int record) {
		groupDao.deleteLogicById(1, record);//1表示逻辑删除
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public ServiceResult<Object> updateRecord(Group record) {
		groupDao.updateByPrimaryKeySelective(record);
		
		ServiceResult<Object> result = new ServiceResult<Object>();
		result.setData(null);
		
		return result;
	}

	@Override
	public Group selectByPK(Integer groupId) {
		return groupDao.selectByPrimaryKey(groupId);
	}

}
