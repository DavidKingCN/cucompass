/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.lujinsuo;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.HtmlContants;
import cn.wetime.p2pmart.util.HtmlParseUtil;
import cn.wetime.p2pmart.util.HttpContants;
import cn.wetime.p2pmart.util.MatchUtils;

public class ParseAllList extends AbsLujinsuoList {
	//解析的url地址
	private static String URL = "https://list.lufax.com/list/all";
	
	public List<Product> getResultsByHtmlCrawler() throws Exception {
		List<Product> products = new ArrayList<Product>();
		
		int pages = getMaxPagesNo();
		if(pages==-1)
			return null;
		
		boolean hasBreak = false;
		//循环每一页
		for(int i=1;i<pages;i++){
			
			if(hasBreak)
				break;
			lists = getPageItems(pages > 1,i);
			NodeList progressLists =  getProgressNodes( getUrl(pages > 1 , i ));
			
			int listsLen = lists.size();
			
			for(int j=0;j<listsLen;j++){
				Product product = new Product();
				node = lists.elementAt(j);
				result = HtmlParseUtil.getNodeByTagFilter(node, HtmlContants.TAG_A);
				if(result instanceof LinkTag){
					LinkTag linkTag = (LinkTag)result;
					String href = linkTag.getAttribute(HtmlContants.ATTR_HREF);
					String productId = href.substring(href.lastIndexOf(HttpContants.SYMBOL_EQUAL)+1);
					product.setProductId(productId);
				}
				//设置产品名称
				product.setProductName(result.toPlainTextString().trim());
				
				array =  HtmlParseUtil.getNodesByTagFilter(node, HtmlContants.TAG_P);
				//设置利率
				product.setRate(MatchUtils.replaceBlank(array[0].toPlainTextString()));
				//设置收益期限
                product.setInvestPeriod(MatchUtils.replaceBlank(array[1].toPlainTextString()));
				//设置收益方式
                product.setProfitMode(MatchUtils.replaceBlank(array[2].toPlainTextString()));
				//设置投资额
                product.setInvestAmount(MatchUtils.replaceBlank(array[3].toPlainTextString()));
				//设置进度
                if(progressLists!=null && progressLists.size()!=0 && progressLists.size()>=j+1){
	                String progressTxt = progressLists.elementAt(j).toPlainTextString();
	                if(progressTxt.equals(OVER_STATUS)){
	                	hasBreak = true;
	                	break;
	                }
					product.setProgress(progressTxt);
                }else
                	break;
				
				//设置平台名称
				product.setPlatformName("陆金所-ALL");
				
				products.add(product);
			}
			
		}
		return products;
	}
	
	@Override
	protected String getSubUrl() {
		return URL;
	}
	public static void main(String[] args)throws Exception {
		ParseAllList parseAllList = new ParseAllList();
		
		List<Product>products = parseAllList.getResultsByHtmlCrawler();
		if(products!=null)
			for(Product product:products){
				System.out.println(product.getProductName());
			}
	}
}
