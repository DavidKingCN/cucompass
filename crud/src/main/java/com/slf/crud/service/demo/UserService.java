package com.slf.crud.service.demo;



import com.slf.crud.model.ServiceResult;
import com.slf.crud.model.demo.Group;
import com.slf.crud.model.demo.User;


public interface UserService {

	ServiceResult<Object> findRecords(User record, int pagenow, int pagesize);
	
	public ServiceResult<Object> addRecord(User record);
	
	public ServiceResult<Object> delRecord(int record);
	
	public ServiceResult<Object> updateRecord(User record);

	boolean addUserAndGroup(User user, Group group);
	
	User selectByPK(Integer userId);

	boolean modifyUserAndGroup(User user, Group group);
	
	
}
