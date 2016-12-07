package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestStarList extends MethodExec {

	@Override
	public void process() {
		//http://dianying.2345.com/mingxing/list/
		

		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		String htm = AsynTemplate.doGet("http://dianying.2345.com/mingxing/list/");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(htm)
					.setRootPath("//div[@id='contentList']/ul/li")
					.addSubPath("//span/em[@class='emTit']/a") 		
					.addSubPath("//span/em[@class='emTit']/a/@href")	
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\\s+", " "));
			});
		});
	
	
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestStarList());
	}

}
