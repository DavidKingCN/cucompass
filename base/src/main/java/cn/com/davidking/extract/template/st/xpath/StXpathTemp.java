package cn.com.davidking.extract.template.st.xpath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.davidking.extract.ExtractDataTool;
import cn.com.davidking.extract.SqlsCreater;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.model.Mx;
import cn.com.davidking.model.Zd;
import cn.com.davidking.util.DelayUtil;

public class StXpathTemp extends ExtractDataTool  implements Runnable, SqlsCreater {

	@Override
	public String extract(String confPath, Class<Mx> model, Map<String, String> args) {
		Mx mx2 = initMx(confPath, model); if(mx2==null)return FLAG_FAIL;
		
		List<String> argsList = initArgsList(mx2);
		if (mx2.isSffy() && mx2.getQqfs().equals(REQ_GET)) {
			int totalPages = 1;
			List<Zd> jlzds 	 = alljlZds(mx2);
			List<Zd> bjlzds  = allbjlZds(mx2);
			List<Zd> autoZds = allAutoZds(mx2);
			
			String rootPath = mx2.getPath();
			List<String> subPaths = this.getSubPathsByJlZds(jlzds);
			for(int i=1;i<=totalPages;i++){
				String url = procReplaceArgs(mx2, i, argsList, args);
				String htm = httpRq(url);
				DelayUtil.delay(500);
				if(htm !=null && !htm.equals("")){
					List<Map<String,String>> maps = doXpathQuery(htm, rootPath, subPaths);
					this.procDataItems(maps, mx2, url, htm, bjlzds, autoZds, args);	
				}
			}
		}else{return FLAG_FAIL;}
		return FLAG_SUCCESS;
	}

	@Override
	public void run() {
		main();
		insertQueueOver(this);
	}
	
	private void main(){
		StXpathTemp sqlCreater = new StXpathTemp();
		
		String conf = StXpathTemp.class.getResource("/template/st/xpath/template.xml").getPath();
		AsynReqImpl.setCharset("gbk");
		
		Map<String,String> vals = new HashMap<>();
		vals .put(URL_, "");
		vals .put(WEBSITE, "http://www.xgccm.com");
		
		sqlCreater.extract(conf, Mx.class, vals);
		
		this.closeAsynService();
	}
	
	
	public static void main(String[] args){
		new StXpathTemp().main();
	}
}
