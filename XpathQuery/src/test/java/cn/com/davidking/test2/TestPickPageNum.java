package cn.com.davidking.test2;


import cn.com.davidking.html.HtmParseUtil;

public class TestPickPageNum {

	@SuppressWarnings("all")
	public static void main(String[] args) {
		String url = "https://list.lu.com/list/all";
		String path = "//input[@id=\"pageCount\"]/@value";
		int pageNo = HtmParseUtil.getTotalPageNum(url, path);
		
		System.out.println("总页码："+pageNo);
	}

}
