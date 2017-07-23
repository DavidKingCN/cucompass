package com.slf.crud.service.demo;



import com.slf.crud.model.ServiceResult;
import com.slf.crud.model.demo.Group;


public interface GroupService {

	ServiceResult<Object> findRecords(Group record, int pagenow, int pagesize);
	
	public ServiceResult<Object> addRecord(Group record);
	
	public ServiceResult<Object> delRecord(int record);
	
	public ServiceResult<Object> updateRecord(Group record);
	
	Group selectByPK(Integer groupId);
}
