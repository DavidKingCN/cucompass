/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.renrendai;

import java.util.ArrayList;
import java.util.List;

import cn.wetime.http.core.HttpTemplate;
import cn.wetime.http.core.ResponseTextType;
import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.JsonValidator;
@SuppressWarnings("all")
public class ParseRenSbList extends AbsRenDaiList {

	private final static String URL = "http://www.renrendai.com/"
			+ "lend/loanList!json.action?pageIndex=";

	public List<Product> getResultsByHtmlCrawler() throws Exception {

		int pageIndex = 1;
		boolean hasBreak = false;

		List<Product> products = new ArrayList<Product>();
		while (true) {
			String data = HttpTemplate.doGet(URL + pageIndex, getReqHeaders(),
					null,getRespHeaders(), ResponseTextType.CONTENT_TYPE_JSON);
			if(!JsonValidator.checkJsonValid(data))
				return null;
			System.out.println(data);
			RenRenLoanPojo pojo = null;
			try{
				pojo = deserial(data);
			}catch(Exception e){
				return null;
			}
			for (RenSbLoan loan : pojo.getData().getLoans()) {
				if (loan.getFinishedRatio() == FINISHED_RATIO) {
					hasBreak = true;
					break;
				}
				Product product = new Product();
				product.setProductId(String.valueOf(loan.getLoanId()));// 产品ID
				product.setProductName(loan.getTitle()); // 产品名
				product.setInvestAmount(String.valueOf(loan.getAmount())); // 投资金额
				product.setInvestPeriod(String.valueOf(loan.getMonths())); // 投资期限
				product.setProfitMode(""); // 收益方式
				product.setRate(String.valueOf(loan.getInterest())); // 利率
				product.setProgress(String.valueOf(loan.getFinishedRatio())); // 进度
				product.setPlatformName("人人贷-散标"); // 平台名称
				products.add(product);

			}
			if (hasBreak) {
				break;
			}
			pageIndex++;
		}
		return products;
	}
	
}
