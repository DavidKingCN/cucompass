package reg.exp.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegExpMatch {

	public static void main(String[] args) {
//		test001();
//		test002();
//		test003();
//		test004();
//		test005();
//		test006();
		test007();
//		test008();
//		test009();
//		test010();
	}

	public static void test001() {
		System.out.println();
		System.out.println("test001()");
		// 查找以Java开头,任意结尾的字符串
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java不是人");
		boolean b = matcher.matches();
		// 当条件满足时，将返回true，否则返回false
		System.out.println(b);
	}

	public static void test002() {
		System.out.println();
		System.out.println("test002()");
		//◆以多条件分割字符串时
		Pattern pattern = Pattern.compile("[, |]+");
		String[] strs = pattern.split("Java Hello World  Java,Hello,,World|Sun");
		for (int i=0;i<strs.length;i++) {
		    System.out.println(strs[i]);
		} 
	}

	public static void test003() {
		System.out.println();
		System.out.println("test003()");
		//◆文字替换（首次出现字符）
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World");
		//替换第一个符合正则的数据
		System.out.println(matcher.replaceFirst("Java"));
	}

	public static void test004() {
		System.out.println();
		System.out.println("test004()");
//		◆文字替换（全部）
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World");
		//替换第一个符合正则的数据
		System.out.println(matcher.replaceAll("Java"));
	}

	public static void test005() {
		System.out.println();
		System.out.println("test005()");
		
//		◆文字替换（置换字符）
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World ");
		StringBuffer sbr = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sbr, "Java");
		}
		matcher.appendTail(sbr);
		System.out.println(sbr.toString());
	}

	public static void test006() {
		System.out.println();
		System.out.println("test006()");
		
//		◆验证是否为邮箱地址

		String str="ceponline@yahoo.com.cn";
		Pattern pattern = Pattern.compile("[//w//.//-]+@([//w//-]+//.)+[//w//-]+",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.matches());
	}

	public static void test007() {
		System.out.println();
		System.out.println("test007()");
		
//		◆去除html标记
		Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher("<a href=\"index.html\">主页</a><div>test</div>");
		String string = matcher.replaceAll(" ");
		System.out.println(string);
		
		System.out.println("<a href=\"index.html\">主页</a>".replaceAll("<.+?>", ""));;
	}

	public static void test008() {
		System.out.println();
		System.out.println("test008()");
		
//		◆查找html中对应条件字符串
		Pattern pattern = Pattern.compile("href=\"(.+?)\"");
		Matcher matcher = pattern.matcher("<a href=\"index.html\">主页</a>");
		if(matcher.find()){
		  System.out.println(matcher.group(1));
		}
	}

	public static void test009() {
		System.out.println();
		System.out.println("test009()");
//		◆截取http://地址
			//截取url
		Pattern pattern = Pattern.compile("(http://|https://){1}[//w//.//-/:]+");
		Matcher matcher = pattern.matcher("dsdsds<http://dsds//gfgffdfd>fdf");
		StringBuffer buffer = new StringBuffer();
		while(matcher.find()){             
		    buffer.append(matcher.group());       
		    buffer.append("/r/n");             
		System.out.println(buffer.toString());
		}
	}
	
	public static void test010() {
		System.out.println();
		System.out.println("test010()");
		String str = "Java目前的发展史是由{0}年-{1}年";
		String[][] object={new String[]{"//{0//}","1995"},new String[]{"//{1//}","2007"}};
		System.out.println(replace(str,object));
	}
	
	public static String replace(final String sourceString,Object[] object) {
        String temp=sourceString;   
        for(int i=0;i<object.length;i++){
        	String[] result=(String[])object[i];
        Pattern    pattern = Pattern.compile(result[0]);
        Matcher matcher = pattern.matcher(temp);
        temp=matcher.replaceAll(result[1]);
        }
        return temp;
}
}
