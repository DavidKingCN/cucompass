package cn.com.davidking.extract.template.st.json;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.davidking.extract.ExtractDataTool;
import cn.com.davidking.extract.SqlsCreater;
import cn.com.davidking.model.Mx;
import cn.com.davidking.model.Zd;
import cn.com.davidking.util.DateUtil;

public class StJsonTemp extends ExtractDataTool implements Runnable, SqlsCreater {

	@Override
	public String extract(String confPath, Class<Mx> model, Map<String, String> args) {
		try {
			Mx mx2 = initMx(confPath, model); if(mx2==null)return FLAG_FAIL;
			
			List<String> argsList = initArgsList(mx2);
			if (mx2.isSffy() && mx2.getQqfs().equals(REQ_GET)) {
				int totalPages = computTotalPages(mx2, argsList, args);
				
				List<Zd> jlzds 	 = alljlZds(mx2);
				List<Zd> bjlzds  = allbjlZds(mx2);
				List<Zd> autoZds = allAutoZds(mx2);
				
				String mapsPath = computMultiKeys(mx2.getPath(), jlzds);
				
				for(int i=1;i<=totalPages;i++){
					try {
						String url = this.procReplaceArgs(mx2, i, argsList, args);
						String data = httpRq(url);
						if(data!=null&&!data.equals("")){
							List<Map<String, String>> maps = this.doJsonQuery(mx2, data, mapsPath);
							int idx = 0;
							if(maps!=null&&maps.size()>0)
							for (Map<String, String> map : maps) {
								try {
									Map<String, String> kvs = new HashMap<String, String>();
									kvs = this.jlZdProc(map, kvs, zdToMap(mx2));
									if(bjlzds!=null)kvs = bjlZdProc(mx2, bjlzds, data, kvs, idx); idx++;
									kvs = autoZdProc(url, autoZds, args, kvs);
									this.insertSqlToFile(mx2, kvs);
									this.insertQueue(mx2, kvs);
								} catch (Exception e) {
									continue;
								}
							}
						}
					} catch (Exception e) {
						continue;
					}
				}
				
			}else{return FLAG_FAIL;}
		} catch (Exception ignore) {}
		return FLAG_SUCCESS;
		
	}

	@Override
	public void run() {
		main();
		insertQueueOver(this);
	}
	private void main() {
		StJsonTemp sqlCreater = new StJsonTemp();
		String file = StJsonTemp.class.getResource("/template/st/json/template.xml").getPath();
		
		Map<String,String> vals = new HashMap<String,String>();
		vals .put(WEBSITE, "http://www.ly.com");
		vals .put(URL_, "");
		vals .put(DOWNLOAD_TIME, DateUtil.toTimeString(new Date()));
		vals .put(UPDATE_TIME, DateUtil.toTimeString(new Date()));
		vals .put("$arg0$", "93700");
		vals .put("dpHotelId", "93700");
		vals .put("dpHotelName", "金泉酒店");
		
		sqlCreater.extract(file, Mx.class, vals);
		this.closeAsynService();
	}
	public static void main(String[] args) {
		new StJsonTemp().main();
	}
}
