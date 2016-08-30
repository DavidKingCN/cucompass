/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wetime.p2pmart.service.PageService;
import cn.wetime.p2pmart.util.JsonUtil;
import cn.wetime.p2pmart.util.PageBean;

@SuppressWarnings("all")
@Controller
@RequestMapping("/page")
public class PageAction {
	
	@Autowired
	private PageService pageService;
	@RequestMapping(value = "/users")
	public @ResponseBody
	ResponseEntity<String> pageUsers(HttpServletRequest request,HttpServletResponse response) {
		//响应头
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Language", "zh-CN");
		headers.add("Content-Type", "text/html;charset=UTF-8");
		String hql = "from User";//request.getParameter("hql");
		String paramsPara = request.getParameter("params");
		String orderssPara = request.getParameter("orders");
		
		Object[] params = null;
		Object[] orders = null;
		
		PageBean pageUsers = pageService.findPageUsers(hql, PageBean.getFirstResult(request), PageBean.getPageSize(request), params,orders);
		
		return new ResponseEntity<String>(JsonUtil.deepSerialOffClass(pageUsers), headers,
				HttpStatus.OK);
	}
	

	@RequestMapping(value = "/products")
	public @ResponseBody
	ResponseEntity<String> pageProducts(HttpServletRequest request,HttpServletResponse response) {
		//响应头
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Language", "zh-CN");
		headers.add("Content-Type", "text/html;charset=UTF-8");
		String hql = "from Product";//request.getParameter("hql");
		String paramsPara = request.getParameter("params");
		String orderssPara = request.getParameter("orders");
		
		Object[] params = null;
		Object[] orders = null;
		
		PageBean pageProducts = pageService.findPageProducts(hql, PageBean.getFirstResult(request), PageBean.getPageSize(request), params,orders);
		
		return new ResponseEntity<String>(JsonUtil.deepSerialOffClass(pageProducts), headers,
				HttpStatus.OK);
	}
}
