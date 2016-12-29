package cn.com.davidking.test2;

public class TestSpaceSign {

	public static void main(String[] args) {
		String s = "\r\n";
		System.out.println(s.matches("\\s*"));
		s+= "        test\r\n";
		s+= " ";
		System.out.println(s);
		
		boolean rt = s.matches("\\s*\\S+\\s*");
		System.out.println(rt);
	}

}
