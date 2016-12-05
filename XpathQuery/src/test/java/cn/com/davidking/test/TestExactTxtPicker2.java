package cn.com.davidking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestExactTxtPicker2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// //div[@id='infostar']/span/p
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		AsynReqImpl.setCharset("gbk");
		//exe req..
		String rt = AsynTemplate.doGet("http://www.xgccm.com/star/HongKong/5.html");
		
		//release thread pool resource..
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(rt)
					.setRootPath("//div[@id='infostar']/span/p")
					.addSubPath("/font[@style='FONT-SIZE: 15px']<0>")
					.addSubPath("/font[@style='FONT-SIZE: 15px']<1>")
					.addSubPath("/font[@style='FONT-SIZE: 15px']<2>")
					.query();
			Map<String,String> target = new HashMap<>();
		results.forEach(result->{
			result.forEach((k,v)->{
				//System.out.println(k+"---"+v);
				target.put(k, v);
			});
		});
		
		System.out.println(target);
	}

}
