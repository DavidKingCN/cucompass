package cn.com.davidking.json;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.JsonParserBuilder;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;

public class TestJsonQuery_Fun_8 extends MethodExec {

	@Override
	public void process() {
		String path="$.pageInfo.totalPage";
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		String json = AsynTemplate.doGet("http://www.ly.com/hotel/handler/gethotelcomments.json?pagesize=10&page=1&hotelid=93700&tagid=1");
		String needParseJson = JsonParserBuilder.newJsonToolKit().getClosureJson(json, "body");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		String resultStr = JsonQuery.getSingleVal(needParseJson, path);
		
		System.out.println(resultStr);
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_8());
	}

}
