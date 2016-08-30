/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.yooli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import cn.wetime.http.core.HttpTemplate;
import cn.wetime.http.core.ResponseTextType;
import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.HtmlContants;
import cn.wetime.p2pmart.util.HtmlParseUtil;
import cn.wetime.p2pmart.util.MatchUtils;
@SuppressWarnings("all")
public class ParseZhuanrangList extends AbsYooliList {

	private static final String URL = "http://www.yooli.com/zhuan/";

	public List<Product> getResultsByHtmlCrawler() throws Exception {
		int pages = getMaxPagesNo();
		Map<String, String> params = getFormParams();

		List<Product> overall = new ArrayList<Product>();
		// 解析所有页面的产品列表项
		for (int i = 1; i <= pages; i++) {
			
			Map<String, String> kvs = getRightParams(params,i);
			String result = 
					HttpTemplate.doPost(
							URL, null, kvs,null, ResponseTextType.CONTENT_TYPE_HTML);
			// 解析响应的正文部分
			List<Product> products = getProductsByHtml(result);
			if(products!=null&&products.size()!=0){
				overall.addAll(products);
				
			}else
				break;
		}

		return overall;
	}

	private List<Product> getProductsByHtml(String result)
			throws Exception {
		NodeList list = null;

		LinkTag linkTag = null;

		String currNodeHtml = "";

		list = HtmlParseUtil.getNodeListByAttr(
				HtmlParseUtil.getParserByHtml(result), HtmlContants.ATTR_CLASS,
				ITEMS_CLASS_NAME);
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < list.size(); i++) {
			Product product = new Product();
			node = list.elementAt(i);
			currNodeHtml = node.toHtml();

			// productId 产品id
			array = HtmlParseUtil.getNodesByTagFilter(node,
					HtmlContants.TAG_A);

			linkTag = (LinkTag) array[0];
			String href = linkTag.getAttribute(HtmlContants.ATTR_HREF);
			String htmlName = href.substring(href.lastIndexOf("/") + 1);
			product.setProductId(htmlName.substring(0, htmlName.indexOf(".")));
			// productName 产品名称
			lists = HtmlParseUtil.getNodeListByAttr(
					HtmlParseUtil.getParserByHtml(currNodeHtml),
					HtmlContants.ATTR_CLASS, COL_1_CLASS_NAME);
			product.setProductName(MatchUtils.replaceBlank(lists.elementAt(0)
					.toPlainTextString().trim()));

			// rate 利率
			lists = HtmlParseUtil.getNodeListByAttr(
					HtmlParseUtil.getParserByHtml(currNodeHtml),
					HtmlContants.ATTR_CLASS, COL_2_CLASS_NAME);
			product.setRate(MatchUtils.replaceBlank(lists.elementAt(0)
					.toPlainTextString().trim()));
			// investPeriod 投资期限

			lists = HtmlParseUtil.getNodeListByAttr(
					HtmlParseUtil.getParserByHtml(currNodeHtml),
					HtmlContants.ATTR_CLASS, COL_3_CLASS_NAME);
			product.setInvestPeriod(MatchUtils.replaceBlank(lists.elementAt(0)
					.toPlainTextString().trim()));
			// profitMode 收益 模式
			product.setProfitMode("按月付息");
			// investAmount 投资额
			lists = HtmlParseUtil.getNodeListByAttr(
					HtmlParseUtil.getParserByHtml(currNodeHtml),
					HtmlContants.ATTR_CLASS, COL_4_CLASS_NAME);
			product.setInvestAmount(MatchUtils.replaceBlank(lists.elementAt(0)
					.toPlainTextString().trim()));
			// progress 进度

			lists = HtmlParseUtil.getNodeListByAttr(
					HtmlParseUtil.getParserByHtml(currNodeHtml),
					HtmlContants.ATTR_CLASS, COL_5_CLASS_NAME);
			String progressTxt = MatchUtils.replaceBlank(lists.elementAt(0)
					.toPlainTextString().trim());
			if(progressTxt.equals(OVER_STATUS))
				break;
			product.setProgress(progressTxt);
			// platformName 平台名称
			product.setPlatformName("有利网-转让");

			products.add(product);
		}

		return products;
	}

	@Override
	protected String getSubUrl() {
		return URL;
	}
	
	public static void main(String[] args) throws Exception{
		WebCrawler webCrawler = new ParseZhuanrangList();
		
		for(Product product:(List<Product>)webCrawler.getResultsByHtmlCrawler()){
			System.out.println(product);
		}
	}
}
