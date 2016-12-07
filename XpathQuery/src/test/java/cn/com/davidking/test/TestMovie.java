package cn.com.davidking.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;

public class TestMovie extends MethodExec {

	@Override
	public void process() {

		//
		ExecutorService exeService= Executors.newFixedThreadPool(4);
		AsynReqImpl.setExeService(exeService);
		String htm = AsynTemplate.doGet("http://dianying.2345.com/mingxing/808-88/");
		new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
		if(htm!=null&&!htm.equals("")&&!htm.equals("-1")){
			System.out.println(htm);
		}
		List<Map<String,String>> results = 
				XpathQuery.newXpathQuery()
					.setHtml(htm)
					.setRootPath("//div[@class='picConBox']/ul/li")
					.addSubPath("/div[@class='pic']/img/@loadsrc") 		//图片
					.addSubPath("//span[@class='pRightBottom']/em")	//档期
					.addSubPath("//a[@class='aPlayBtn']/@href")		//电视详情页
					.addSubPath("//span[@class='sTit']")			//标题
					.addSubPath("//span[@class='sDes']")			//主演
					.query();
			
		results.forEach(result->{
			result.forEach((k,v)->{
				System.out.println(k+"---"+v.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\\s+", " "));
			});
		});
	
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestMovie());
	}

}
