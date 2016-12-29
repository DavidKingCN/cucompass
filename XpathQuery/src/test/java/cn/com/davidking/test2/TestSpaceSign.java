package cn.com.davidking.test2;

import cn.com.davidking.util.MatchUtils;

public class TestSpaceSign {

	public static void main(String[] args) {
		String s = "\r\n";
		System.out.println(s.matches("\\s*"));
		s+= "        test\r\n";
		s+= " ";
		System.out.println(s);
		
		boolean rt = s.matches("\\s*\\S+\\s*");
		System.out.println(rt);
		
		String test = "//dd/ul/li[@class='invest-period']/p.text()//span.text()//span/span.text()";
		
		
		
		String ss[]   = test.split(".text\\(\\)", 0);
		
		System.out.println(ss.length);
		System.out.println(ss[0]);
		System.out.println(ss[1]);
	}

}
