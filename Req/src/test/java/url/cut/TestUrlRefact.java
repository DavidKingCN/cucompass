package url.cut;

import cn.com.davidking.http.URLCutter;

public class TestUrlRefact {

	public static void main(String[] args) {
		String path = TestUrlRefact.class.getResource("/url.txt").getPath();
		String result = URLCutter.refactUrl(path);
		
		System.out.println(result);
	}

}
