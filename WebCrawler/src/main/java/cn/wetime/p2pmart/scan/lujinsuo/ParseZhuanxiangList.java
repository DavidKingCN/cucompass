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
import cn.wetime.p2pmart.util.MatchUtils;
import cn.wetime.p2pmart.util.StringUtil;

public class ParseZhuanxiangList  extends  AbsLujinsuoList {

	private static final String URL = "https://list.lufax.com/list/zhuanxiang";
	
	
	public List<Product> getResultsByHtmlCrawler()throws Exception{
		List<Product> products = new ArrayList<Product>();
		int pages = getMaxPagesNo();
		if(pages==-1)
			return null;
		boolean hasBreak = false;
		for (int i = 1; i <= pages; i++) {
			if(hasBreak)
				break;
			lists = getPageItems(pages > 1,i);
			NodeList progressLists =  getProgressNodes( getUrl(pages > 1 , i ));
			
			int listsLen = lists.size();
			
			for (int j = 0; j < listsLen; j++) {
				//创建产品对象
				Product product = new Product();
				//获取要处理的迭代dom节点对象
				node = lists.elementAt(j);
				
				result = HtmlParseUtil.getNodeByTagFilter(node, HtmlContants.TAG_A);
				LinkTag aLinkTag = (LinkTag)result;
				//设置产品ID
				String productId = aLinkTag.getAttribute("data-productid");;
				product.setProductId(productId);
				//设置产品名称
				product.setProductName(result.toPlainTextString().trim());
				
				array =  HtmlParseUtil.getNodesByTagFilter(node, HtmlContants.TAG_P);
				//设置利率
				product.setRate(MatchUtils.replaceBlank(array[0].toPlainTextString()));
				//设置收益期限
                product.setInvestPeriod(MatchUtils.replaceBlank(array[1].toPlainTextString()));
				//设置收益方式
                product.setProfitMode("保本保息");
                //设置投资金额
                product.setInvestAmount(MatchUtils.replaceBlank(array[2].toPlainTextString()));
                //设置进度
                if(progressLists!=null && progressLists.size()!=0 && progressLists.size()>=j+1){
	                String progressTxt =  progressLists.elementAt(j).toPlainTextString();
	                if(progressTxt.equals(OVER_STATUS)){
	                	hasBreak = true;
	                	break;
	                }
					product.setProgress(progressTxt);
                }else
                	break;
				//设置平台名称
				product.setPlatformName("陆金所-共享理财");
				
				products.add(product);
			}
		}
		return products;
	}

	@Override
	protected String getSubUrl() {
		return URL;
	}

	/**
	 * 获取每一页的商品项
	 * @param multiPage
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	protected NodeList getPageItems(boolean multiPage,int pageNo)throws Exception{
		
		String curUrl = getUrl(multiPage,pageNo);
		
		lists = HtmlParseUtil.
				getNodeListByAttr(HtmlParseUtil.getParserByUrl(curUrl), 
						HtmlContants.ATTR_CLASS, 
						StringUtil.getStringSplitLastBlank(PRODUCT_ITEM_CLASS_NAME));
		return lists;
	}
}
