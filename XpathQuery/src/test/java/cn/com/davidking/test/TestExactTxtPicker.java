package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestExactTxtPicker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// //div[@id='infostar']/span/p
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		AsynReqImpl.setCharset("gbk");
		//exe req..
		String rt = AsynTemplate.doGet("http://www.xgccm.com/star/HongKong/2.html");
		
		//release thread pool resource..
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(rt)
					.setRootPath("//div[@id='infostar']/span/p")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【个人资料】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【星路历程】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【入行经历】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【作品年表】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【荣誉记录】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【经典角色】)")
					.addSubPath("/font[@style='FONT-SIZE: 15px'](【公益活动】)")
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v);
			});
		});
	}

}
