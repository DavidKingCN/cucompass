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
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.JsonValidator;

public class ParseTransferList extends AbsRenDaiList {
	private static final String URL = "http://www.renrendai.com/transfer/transferList!json.action?pageIndex=";

	public List<Product> getResultsByHtmlCrawler() throws Exception {

		int pageIndex = 1;
		boolean hasBreak = false;

		List<Product> products = new ArrayList<Product>();
		while (true) {
			if (hasBreak)
				break;
			String data = HttpTemplate.doGet(URL + pageIndex, getReqHeaders(), null,
					getRespHeaders(),
					ResponseTextType.CONTENT_TYPE_JSON);
			System.out.println(data);
			if(!JsonValidator.checkJsonValid(data))
				return null;
			RenRenLoanPojo pojo = null;
			try{
				pojo = deserial(data);
			}catch(Exception e){
				return null;
			}
			for (TransferLoan loan : pojo.getData().getTransferList()) {
				if (loan.getShare().equals(SHARE_LEFT)) {
					hasBreak = true;
					break;
				}
				Product product = new Product();
				product.setProductId(loan.getId());
				product.setProductName(loan.getTitle());
				product.setRate(loan.getInterest());
				product.setInvestPeriod(loan.getLeftPhaseCount());
				product.setPlatformName("人人贷-转让");

				products.add(product);
			}

			pageIndex++;
		}
		return products;
	}
	
}
