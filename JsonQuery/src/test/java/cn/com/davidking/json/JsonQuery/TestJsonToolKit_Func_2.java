/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.json.JsonQuery;

import java.util.List;

import cn.com.davidking.JsonToolKitBuilder;
import cn.com.davidking.http.core.HttpTemplate;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
// TODO: Auto-generated Javadoc

/**
 * The Class TestJsonToolKit_Func_2.
 *
 * @author daikai
 */
public class TestJsonToolKit_Func_2 extends MethodExec {

	/* (non-Javadoc)
	 * @see cn.com.davidking.test.ExecProc#process()
	 */
	@Override
	public void process() {
		String json = HttpTemplate.doGet("http://www.ly.com/hotel/handler/GetHotelList.json?page=1&txtCityId=374&orderby=4", null,null,null);
		String testJsonArr = JsonToolKitBuilder.newJsonToolKit().getClosureJson(json, "hotelList");
		List<String> jsons = JsonToolKitBuilder.newJsonToolKit().getElementsByJsonArr(testJsonArr);
		for(String gson:jsons){
			System.out.println(gson);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonToolKit_Func_2());
	}

}
