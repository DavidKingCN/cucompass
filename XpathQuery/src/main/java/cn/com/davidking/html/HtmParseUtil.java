package cn.com.davidking.html;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.com.davidking.html.parse.XPathUtils;
import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.$.Callback;

public class HtmParseUtil {
	
	public static int getTotalPageNum(String url,String path){
		int totalPages = 0;
		String htm = $.get(url, new Callback(){});
		HtmlCleaner clean = new HtmlCleaner();
		TagNode htmNode = clean.clean(htm);
		try {
			String result = XPathUtils.pathVal(htmNode, path, XPathUtils.isAttr(path));
			if(result.matches("\\d+")) 
				totalPages = Integer.parseInt(result);
		} catch (Exception ignore) { }
		
		return totalPages;
	}
}
