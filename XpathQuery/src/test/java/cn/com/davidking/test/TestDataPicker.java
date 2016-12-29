package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestDataPicker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		AsynReqImpl.setCharset("gbk");
		//exe req..
		String htm = AsynTemplate.doGet("http://www.xgccm.com/search.asp");
		
		//release thread pool resource..
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(htm)
					.setRootPath("//div[@class='starlist']/div")
					.addSubPath("/a")
					.addSubPath("/a/img/@src")
					.addSubPath("//p<0,【(.+)】>")
					.addSubPath("//p<1,职&nbsp;&nbsp;业：(.*)>")
					.addSubPath("//p<2,\\d+.+/.+（.+）>")
					.addSubPath("//p{3,html[/images/star(\\d+).gif]}")
					.addSubPath("//p<4,\\d+万/.*年（参考价）>")
					.addSubPath("//p{5,html[/images/star(\\d+).gif]}")
					.addSubPath("//a[@class='u']/@href")
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"<--->"+v);
			});
		});
	}

}
