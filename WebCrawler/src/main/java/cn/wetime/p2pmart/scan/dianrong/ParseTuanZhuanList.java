/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.dianrong;

import java.util.ArrayList;
import java.util.List;

import cn.wetime.http.core.HttpTemplate;
import cn.wetime.http.core.ResponseTextType;
import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.JsonValidator;
import cn.wetime.p2pmart.util.StringUtil;
import flexjson.JSONDeserializer;
@SuppressWarnings("all")
public class ParseTuanZhuanList extends AbsDianRongList {
	private static final String URL = "https://www.dianrong.com/mobile/searchPlans";
	public List<Product> getResultsByHtmlCrawler() throws Exception {
		
		String data = HttpTemplate.doGet(
				URL, null,null,null,
				ResponseTextType.CONTENT_TYPE_JSON);
		
		System.out.println(data);
		if(!JsonValidator.checkJsonValid(data))
			return null;
		JSONDeserializer<DianRongPlanList> loans = new JSONDeserializer<DianRongPlanList>();
		DianRongPlanList pojo = loans.deserialize(data, DianRongPlanList.class);
		List<Product> products = new ArrayList<Product>();
		for(PlanObj plan:pojo.getContent().getLoans()){
			Product product = new Product();
			double amount = plan.getAmount();								//已投金额
			double appAmount = plan.getAppAmount(); 						//总金额
			double minInvestAmount = plan.getMinInvestAmount();				//最小起投金额
			if(appAmount-amount>minInvestAmount){
				product.setProgress(StringUtil.getPercentStr(amount/appAmount));
			}else{
				continue;
			}
			product.setProductId(String.valueOf(plan.getId())); 			//产品ID
			product.setProductName(plan.getName()); 
			product.setInvestPeriod(plan.getInvestPeriod());
			product.setInvestAmount(String.valueOf(plan.getAmount()));
			product.setRate(String.valueOf(plan.getInterstFixedRate()));
			product.setPlatformName("点融网-团团赚");	
			products.add(product);
		}
		return products;
	}
	public static void main(String[] args) throws Exception{
		WebCrawler webCrawler = new ParseTuanZhuanList();
		for(Product product:(List<Product>)webCrawler.getResultsByHtmlCrawler()){
			System.out.println(product);
		}
	}
}
