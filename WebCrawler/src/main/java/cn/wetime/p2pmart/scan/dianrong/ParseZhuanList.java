/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.dianrong;

import java.util.List;

import cn.wetime.http.core.HttpTemplate;
import cn.wetime.http.core.ResponseTextType;
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.JsonValidator;

public class ParseZhuanList extends AbsDianRongList {
	private static final String URL = "https://www.dianrong.com/mobile/searchSecuritizedLoans"
			+ "?pageSize=20"
			+ "&includeFullyFunded=true"
			+ "&primaryTabShow=false"
			+ "&tabSwitch=true"
			+ "&page=";
	public List<Product> getResultsByHtmlCrawler() throws Exception {
		
		int page = 0;
		
		String data = HttpTemplate.doGet(
				URL+page, null,null,null,
				ResponseTextType.CONTENT_TYPE_JSON);
		if(!JsonValidator.checkJsonValid(data))
			return null;	
		return null;
	}

}
