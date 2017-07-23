package com.slf.crud.controller.demo;
import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.slf.crud.model.ResponseData;
import com.slf.crud.model.ServiceResult;
import com.slf.crud.model.demo.Group;
import com.slf.crud.model.demo.User;
import com.slf.crud.model.demo.UserAndGroupVo;
import com.slf.crud.model.demo.UserAo;
import com.slf.crud.service.demo.GroupService;
import com.slf.crud.service.demo.UserService;
@SuppressWarnings("all")
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	UserService userService;
	
	@Resource
	GroupService groupService;

	@RequestMapping(value="/page/{pagenow}/{pagesize}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseData findRecords(@RequestBody String record,@PathVariable("pagenow") int pagenow,@PathVariable("pagesize") int pagesize){
		User recordObj = new User();
		if(record!=null && !record.equals("")){
			recordObj  = JSON.parseObject(record, User.class);
		}
		ServiceResult<Object> result = userService.findRecords(recordObj, pagenow, pagesize);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
	//保存的方法
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseData addRecord(@RequestBody User record){
		ServiceResult result =userService.addRecord(record);
		
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;

	}
	
	@ResponseBody
	@RequestMapping(value="/{id}/del",method=RequestMethod.GET)
	public ResponseData delRecord(@PathVariable("id") Integer record){
		ServiceResult<Object> result = userService.delRecord(record);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseData updateRecord(@RequestBody User record){
		ServiceResult<Object> result = userService.updateRecord(record);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/addUserByGroup",method=RequestMethod.POST)
	public ResponseData addUserAndGroup(@RequestBody UserAndGroupVo userAndGroupVo){
		Group group = userAndGroupVo.getGroup();
		User user = userAndGroupVo.getUser();
		boolean success  = userService.addUserAndGroup(user,group);
		ResponseData rd = success?ResponseData.ok():ResponseData.serverInternalError();
		return rd;

	}
	
	
	@RequestMapping(value="/queryEntry",method=RequestMethod.GET)
	@ResponseBody
	public ResponseData queryEntry(UserAo userAo){
		User user = new User();
		
		BeanUtils.copyProperties(userAo, user);
		
		ServiceResult<Object> result = userService.findRecords(user, userAo.getPageNo(), userAo.getPageSize());
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData()).putDataValue("total", result.getTotal());
		}
		return responseData;
	}
	
	
	@RequestMapping(value="/queryUserAndGroup",method=RequestMethod.GET)
	@ResponseBody
	public ResponseData queryUserAndGroup(UserAo userAo){
		Integer userId = userAo.getUserId();
		
		UserAndGroupVo userAndGroupVo = new UserAndGroupVo();
		User user = userService.selectByPK(userId);
		userAndGroupVo.setUser(user);
		
		Integer groupId = user.getUserGroupFk();
		Group group = groupService.selectByPK(groupId);
		
		userAndGroupVo.setGroup(group);
		return ResponseData.ok().putDataValue("result", userAndGroupVo);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/modifyUserByGroup",method=RequestMethod.POST)
	public ResponseData modifyUserAndGroup(@RequestBody UserAndGroupVo userAndGroupVo){
		Group group = userAndGroupVo.getGroup();
		User user = userAndGroupVo.getUser();
		boolean success  = userService.modifyUserAndGroup(user,group);
		ResponseData rd = success?ResponseData.ok():ResponseData.serverInternalError();
		return rd;

	}
}
