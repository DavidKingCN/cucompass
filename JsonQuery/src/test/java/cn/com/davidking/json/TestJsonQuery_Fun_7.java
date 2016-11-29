package cn.com.davidking.json;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.JsonParserBuilder;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
//parent->hop.[<father>] ->hop必须写，只支持这种形式哈，复杂的暂时没空写...
//场景就是数组后面的对象里面还有键值对的情形[{{},{}}]
public class TestJsonQuery_Fun_7 extends MethodExec {

	@Override
	public void process() {
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		String json = AsynTemplate.doGet("http://api.17u.cn/hotelservices/HotelPriceHandler.ashx?HotelId=93700&ComeDate=2016-11-29&LeaveDate=2016-11-30&ResFormat=json");
		String needParseJson = JsonParserBuilder.newJsonToolKit().getClosureJson(json, "HotelInfo");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		//$.RoomInfo[*].{RoomName,Bed,Breakfast,HasWindow,RoomId}
		List<Map<String,String>> resultMaps = JsonQuery.getMapVal(needParseJson, "$.RoomInfo[*].{RoomName,Bed,Breakfast,HasWindow,RoomId}");
		resultMaps.forEach(rtMap->{
			rtMap.forEach((k,v)->{
				System.out.print(k+":"+v+" ");
			});
			System.out.println();
		});
		
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_7());
	}

}
