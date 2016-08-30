/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.lujinsuo;


import org.htmlparser.Node;
import org.htmlparser.util.NodeList;

import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.util.HtmlContants;
import cn.wetime.p2pmart.util.HtmlParseUtil;
import cn.wetime.p2pmart.util.HttpContants;
import cn.wetime.p2pmart.util.MatchUtils;
/**
 * 
 * @author daikai
 *
 */
@SuppressWarnings("all")
public abstract class AbsLujinsuoList implements WebCrawler {
	//分页参数一
	protected static final String PAGINATION_KEY_1 = "currentPage";
	//产品项的css class name
	protected static final String PRODUCT_ITEM_CLASS_NAME = "product-list clearfix  ";
	//进度的样式
	protected static final String PROGRESS_CLASS_NAME = "progress-txt";
	//分页数字的样式
	protected static final String PAGENUM_CLASS_NAME = "pageNum";
	//循环外层一组Dom节点
	protected NodeList lists = null;	
	//循环内层一组Dom节点
//	protected NodeList temp = null;
	//单个Dom节点
	protected Node node = null;	
	//结果Dom节点
	protected Node result = null;
	//dom节点数组
	protected Node[] array = null;
	
	protected static final String OVER_STATUS = "100%";
	
	protected NodeList getProgressNodes(String url)throws Exception{
		
		return HtmlParseUtil.getNodeListByAttr(
				HtmlParseUtil.getParserByUrl(url), 
				HtmlContants.ATTR_CLASS,
				PROGRESS_CLASS_NAME); 
	}
	/**
	 * 获取每一页的商品项
	 * @param multiPage
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	protected NodeList getPageItems(boolean multiPage,int pageNo)throws Exception{
		
		String curUrl = getUrl(multiPage, pageNo);
		lists = HtmlParseUtil.
				getNodeListByAttr(
						HtmlParseUtil.getParserByUrl(curUrl), 
						HtmlContants.ATTR_CLASS, 
						PRODUCT_ITEM_CLASS_NAME);
		return lists;
	}
	
	protected String getUrl(boolean multiPage,int pageNo){
		String curUrl = getSubUrl();
		if(multiPage){
			curUrl += HttpContants.SYMBOL_Q_MARK;
			curUrl += PAGINATION_KEY_1;
			curUrl += HttpContants.SYMBOL_EQUAL;
			curUrl += pageNo ;
		}
		return curUrl;
	}
	
	/***
	 * 得到最大页码数
	 * @return
	 * @throws Exception
	 */
	protected int getMaxPagesNo()throws Exception{
		int pages = 1;
		
		try{
		lists = HtmlParseUtil
				.getNodeListByAttr(
						HtmlParseUtil.getParserByUrl(getSubUrl()), 
						HtmlContants.ATTR_CLASS, 
						PAGENUM_CLASS_NAME);
		}catch(Exception e){
			return -1;
		}
		
		if(lists!=null && lists.size()!=0){
			String pageInfo = lists.elementAt(0).toPlainTextString();

			pages = Integer.parseInt(
					MatchUtils
					.getMatchString(pageInfo, "\\d", 2, 1));
		}
		return pages;
	}
	
	protected  abstract String getSubUrl();
}
