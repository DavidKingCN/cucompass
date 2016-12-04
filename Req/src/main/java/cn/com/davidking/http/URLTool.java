/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http;

import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.davidking.util.MatchUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class URLTool.
 */
public class URLTool {
	
	/** The Constant match_reg. */
	static final String DOMAIN_NAME_REG_EXP = "http[s]?:\\/\\/([a-zA-Z0-9\\-]+[\\.]?)+(:\\d+)?[/]?";

	/**
	 * Ext domain.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String extDomain(String url){
		return MatchUtils.getOnlyMatchs(url, DOMAIN_NAME_REG_EXP);
	}

	/**
	 * Try connect.
	 *
	 * @param urlStr the url str
	 * @return true, if try connect
	 */
	public static boolean tryConnect(String urlStr) {
		boolean canConn = false;
		int counts = 0;
		URL url = null;
		HttpURLConnection con  = null;
		if (urlStr == null || urlStr.length() <= 0) {
			return canConn;
		}
		do {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				int state = con.getResponseCode();
				if (state == 200) canConn = true; break;
			} catch (Exception ex) {
				counts++;
				urlStr = null;
				continue;
			}finally{
				con.disconnect();
			}
		}while (counts < 3);
		return canConn;
	}

}
