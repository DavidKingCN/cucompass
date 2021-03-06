/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.yooli;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.util.HtmlContants;
import cn.wetime.p2pmart.util.HtmlParseUtil;
@SuppressWarnings("all")
public abstract class AbsYooliList implements WebCrawler {
	//分页参数之一
	protected static final String PAGINATION_KEY_1 = "PAGE_COUNT_KEY";
	//提交分页的Form的ID
	protected static final String PAGE_FORM = "pageForm";
	//条目样式
	protected static final String ITEMS_CLASS_NAME = "items";
	//第一列样式
	protected static final String COL_1_CLASS_NAME = "col_1";
	//第二列样式
	protected static final String COL_2_CLASS_NAME = "col_2";
	//第三列样式
	protected static final String COL_3_CLASS_NAME = "col_3";
	//第四列样式
	protected static final String COL_4_CLASS_NAME = "col_4";
	//第五列样式
	protected static final String COL_5_CLASS_NAME = "col_5";
	//第六列样式
	protected static final String COL_6_CLASS_NAME = "col_6";
	//循环外层一组Dom节点
	protected NodeList lists = null;	
	//循环内层一组Dom节点
	protected NodeList temp = null;
	//单个Dom节点
	protected Node node = null;	
	//结果Dom节点
	protected Node result = null;
	//dom节点数组
	protected Node[] array = null;
	protected static final String OVER_STATUS = "100%";
	/***
	 * 得到最大页码数
	 * @return
	 * @throws Exception
	 */
	protected int getMaxPagesNo()throws Exception{
		int pages = 1;
		
		//获取页码数
		node = HtmlParseUtil.getNodeByHasAttr(
				HtmlParseUtil.getParserByUrl(getSubUrl()), 
				HtmlContants.ATTR_ID,
				PAGINATION_KEY_1);
		InputTag inputTag = (InputTag) node;
		
		String pagesInfo = inputTag.getAttribute(HtmlContants.ATTR_VALUE).toString();
		pages = Integer.parseInt(pagesInfo);
		return pages;
	}
	/**
	 * 得到请求头参数键值对
	 * @return
	 */
	protected Map<String,String> getFormParams()throws Exception{
		
		node = HtmlParseUtil.getNodeByHasAttr(
				HtmlParseUtil.getParserByUrl(getSubUrl()), 
				HtmlContants.ATTR_ID,
				PAGE_FORM);
		
		lists = node.getChildren();
		array = new Node[lists.size()];
		lists.copyToNodeArray(array);
		
		InputTag inputTag = null;
		Map<String, String> params = new HashMap<String, String>();
		for (Node temp : array) {
			if (temp instanceof InputTag) {
				inputTag = (InputTag) temp;
				params.put(
						inputTag.getAttribute(HtmlContants.ATTR_ID),	
						inputTag.getAttribute(HtmlContants.ATTR_VALUE));
			}
		}
		return params;
	}
	/**
	 * 得到每行提交的正确的参数
	 * @return
	 * @throws Exception
	 */
	protected Map<String,String> getRightParams(Map<String, String> params,int i)throws Exception{
		Map<String, String> kvs = new HashMap<String, String>();
		for (Map.Entry<String, String> kv : params.entrySet()) {
			if (kv.getKey().equals(PAGINATION_KEY_1))
				kvs.put(kv.getKey(), String.valueOf(i));
			else
				kvs.put(kv.getKey(), kv.getValue());
		}
		return kvs;
	}
	protected  abstract String getSubUrl();
}
