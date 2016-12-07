package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestStarProducts extends MethodExec {

	@Override
	public void process() {
		//http://dianying.2345.com/mingxing/808/

		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		String htm = AsynTemplate.doGet("http://dianying.2345.com/mingxing/808/");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(htm)
					.setRootPath("//div[@class='tabList']")
					.addSubPath("/a[@id='tab_2']/em[\\d+]") 		
					.addSubPath("/a[@id='tab_4']/em[\\d+]")	
					.addSubPath("/a[@id='tab_3']/em[\\d+]")		
					.addSubPath("/a[@id='tab_5']/em[\\d+]")			
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v);
			});
		});
	
	
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestStarProducts());
	}

}
