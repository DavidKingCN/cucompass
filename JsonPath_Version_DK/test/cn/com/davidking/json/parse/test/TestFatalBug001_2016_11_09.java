package cn.com.davidking.json.parse.test;


import java.util.List;
import java.util.Map;

import cn.com.davidking.BeanFactory;
import cn.com.davidking.http.core.HttpTemplate;
import cn.com.davidking.json.parse.JsonQuery;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
/**
 * 测试致命bug001 
 * @author daikai
 *
 */
public class TestFatalBug001_2016_11_09 extends MethodExec {

	@Override
	public void process() {
		String json = HttpTemplate.doGet(
				"http://www.ly.com/hotel/handler/gethotelcomments.json?hotelid=93700&tagid=1", 
				null,
				null,
				null);
		String testJson = BeanFactory.newJsonToolKit().getClosureJson(json, "body");
		System.out.println("json val = "+testJson);
		List<Map<String,String>> rs = JsonQuery.getMapVal(testJson, "$.{starNum,goodNum}");
		System.out.println("multi keys vals = "+rs);
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestFatalBug001_2016_11_09());
	}

}
