package cn.com.davidking.test2;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.com.davidking.html.parse.XPathUtils;
import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.$.Callback;

public class TestPickPageNum {

	@SuppressWarnings("all")
	public static void main(String[] args) {
		int totalPages = 0;
		String url = "https://list.lu.com/list/all";
		String htm = $.get(url, new Callback(){});
		HtmlCleaner clean = new HtmlCleaner();
		TagNode htmNode = clean.clean(htm);
		String path = "//input[@id=\"pageCount\"]/@value";
		try {
			String result = XPathUtils.pathVal(htmNode, path, XPathUtils.isAttr(path));
			if(result.matches("\\d+")) totalPages = Integer.parseInt(result);
		} catch (Exception ignore) { }
		
		System.out.println("总页码数：" + totalPages);
	}

}
