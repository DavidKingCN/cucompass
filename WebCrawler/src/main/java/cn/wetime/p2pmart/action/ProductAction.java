/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.cino.cache.ICache;

import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.service.ProductService;
import cn.wetime.p2pmart.util.JsonUtil;

@Controller
@RequestMapping("/product")
public class ProductAction {
	@Autowired
	private ProductService productService;
	
//	@Autowired
//	private ICache cache;	//redis 缓存
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> test() {
		// 响应头
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Language", "zh-CN");
		headers.add("Content-Type", "text/html;charset=UTF-8");
		
		List<Product> products = productService.saveOrUpdateBatchProducts();
		
		//逐个放入缓存中
		for(Product p:products){
			System.out.println(p.getPlatformName()+"-"+p.getProductName()+"-"+p.getProfitMode());
//			cache.set(p.getProductId(), JsonUtil.serialOffClass(p));
			
		}
		String resultStatus = "OK";
		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("status", resultStatus);
		result.put("products", products);

		return new ResponseEntity(
				JsonUtil.deepSerialOffClass(result), 
				headers,
				HttpStatus.OK);
	}
}
