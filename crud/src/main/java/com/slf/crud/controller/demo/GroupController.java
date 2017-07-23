package com.slf.crud.controller.demo;
import java.util.List;

import javax.annotation.Resource;

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
import com.slf.crud.service.demo.GroupService;
@SuppressWarnings("all")
@Controller
@RequestMapping("/group")
public class GroupController {
	@Resource
	GroupService groupService;

	@RequestMapping(value="/page/{pagenow}/{pagesize}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseData findRecords(@RequestBody String record,@PathVariable("pagenow") int pagenow,@PathVariable("pagesize") int pagesize){
		Group recordObj = new Group();
		if(record!=null && !record.equals("")){
			recordObj  = JSON.parseObject(record, Group.class);
		}
		ServiceResult<Object> result = groupService.findRecords(recordObj, pagenow, pagesize);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
	//保存的方法
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseData addRecord(@RequestBody Group record){
		ServiceResult result =groupService.addRecord(record);
		
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;

	}
	
	@ResponseBody
	@RequestMapping(value="/{id}/del",method=RequestMethod.GET)
	public ResponseData delRecord(@PathVariable("id") Integer record){
		ServiceResult<Object> result = groupService.delRecord(record);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseData updateRecord(@RequestBody Group record){
		ServiceResult<Object> result = groupService.updateRecord(record);
		ResponseData responseData = ResponseData.ok();
		if(result.getData() !=null){
			responseData.putDataValue("result", result.getData());
		}
		return responseData;
	}
	
}
