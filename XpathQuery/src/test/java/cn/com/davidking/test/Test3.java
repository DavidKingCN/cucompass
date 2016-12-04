package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		AsynReqImpl.setCharset("utf8");
		//exe req..
		String rt = AsynTemplate.doGet("https://list.lu.com/list/all");
		
		//release thread pool resource..
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(rt)
					.setRootPath("//div[@class='main-list']/li")
					.addSubPath("/dl[@class='product-info']/dt[@class='product-name']/a/@href")
					.addSubPath("/dl[@class='product-info']/dt[@class='product-name']/a")
					.addSubPath("/ul[@class='clearfix']/li[@class='interest-rate']/p")
//					.addSubPath("/dl/dt[@class='pad_5']/p{2,html}")
//					.addSubPath("/dl/dt[@class='pad_5']/p{3,html[/images/star(\\d+).gif]}")
//					.addSubPath("/dl/dt[@class='pad_5']/p<4>")
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v);
			});
		});
	}

}
