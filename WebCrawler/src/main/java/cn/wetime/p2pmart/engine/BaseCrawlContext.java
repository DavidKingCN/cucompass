/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.engine;

import cn.wetime.p2pmart.WebCrawler;
import cn.wetime.p2pmart.scan.dianrong.ParseBidList;
import cn.wetime.p2pmart.scan.dianrong.ParseTuanZhuanList;
import cn.wetime.p2pmart.scan.dianrong.ParseZhuanList;
import cn.wetime.p2pmart.scan.lujinsuo.ParseAllList;
import cn.wetime.p2pmart.scan.lujinsuo.ParseInvestIufaxList;
import cn.wetime.p2pmart.scan.lujinsuo.ParseLicaiList;
import cn.wetime.p2pmart.scan.lujinsuo.ParseZhuanxiangList;
import cn.wetime.p2pmart.scan.renrendai.ParseRenSbList;
import cn.wetime.p2pmart.scan.renrendai.ParseRenUPlanList;
import cn.wetime.p2pmart.scan.renrendai.ParseTransferList;
import cn.wetime.p2pmart.scan.yooli.ParseYueXiTongList;
import cn.wetime.p2pmart.scan.yooli.ParseZhuanrangList;

@SuppressWarnings("all")
public final class BaseCrawlContext {
	
	//1.测试用这个
	public static final WebCrawler[] WEB_CRAWLER_CONTEXT = {
//		new ParseBidList(),
//		new ParseTuanZhuanList(),
//		new ParseZhuanList(),
//		new ParseAllList(),
//		new ParseInvestIufaxList(),
//		new ParseLicaiList(),
//		new ParseZhuanxiangList(),
//		new ParseRenSbList(),
//		new ParseRenUPlanList(),
//		new ParseTransferList(),
//		new ParseYueXiTongList(),
//		new ParseZhuanrangList()
	};
	
	
	//2.反射用两个
	public static final String COMMON_PACKAGE_NAME = "cn.wetime.p2pmart.scan.";
	public static final String[] WEB_CRAWLER_CLASS_NAMES = {
		"lujinsuo.ParseInvestIufaxList",	//陆金所的投资列表
		"renrendai.ParseRenSbList",			//人人网散标
		"renrendai.ParseRenUPlanList"
		};
	
	//3.最后扫描路径的方式处理
}
