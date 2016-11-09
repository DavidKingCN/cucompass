package cn.com.davidking.json.parse.test;

import java.util.List;

import cn.com.davidking.BeanFactory;
import cn.com.davidking.http.core.HttpTemplate;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
/**
 * 测试致命bug002 
 * @author daikai
 *
 */
public class TestFatalBug002_2016_11_09 extends MethodExec {

	@Override
	public void process() {
		String json = HttpTemplate.doGet("http://www.ly.com/hotel/handler/GetHotelList.json?page=1&txtCityId=374&orderby=4", null,null,null);
		String testJsonArr = BeanFactory.newJsonToolKit().getClosureJson(json, "hotelList");
		List<String> jsons = BeanFactory.newJsonToolKit().getElementsByJsonArr(testJsonArr);
		for(String gson:jsons){
			System.out.println(gson);
		}
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestFatalBug002_2016_11_09());
	}

}
