package cn.com.davidking.extract.template.nd.xpath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.davidking.extract.ExtractDataTool;
import cn.com.davidking.extract.SqlsCreater;
import cn.com.davidking.model.Mx;
import cn.com.davidking.model.Zd;
//import cn.com.davidking.util.DelayUtil;
import cn.com.davidking.task.ExecCode;

class NdXpathTemp extends ExtractDataTool implements SqlsCreater {
	private static final Logger LOG = Logger.getLogger(NdXpathTemp.class);
	@Override
	public String extract(String confPath, Class<Mx> model, Map<String, String> args) {
		Mx mx2 = initMx(confPath, model);   
		if(mx2==null){
			LOG.error("错误码");
			return FLAG_FAIL;
		}//MxNull 模型空错误
		List<Zd> jlzds 	= alljlZds(mx2);    List<Zd> autoZds      = allAutoZds(mx2);
		String rootPath = mx2.getPath();    List<String> subPaths = getSubPathsByJlZds(jlzds);
		String url 	    = args.get("detailUrl"); String htm = httpRq(url); 
		if(htm!=null && !htm.equals("")){
			List<Map<String,String>> maps = this.doXpathQuery(htm, rootPath, subPaths);
			Map<String,String> kvs = null;
			if(maps != null && maps.size() > 0){
				for(Map<String,String> map:maps){
					try {
						kvs = new HashMap<String,String>();
						kvs = jlZdProc(map,kvs,this.zdToMap(mx2));
						kvs = autoZdProc(url,autoZds,args,kvs);
						insertSqlToFile(mx2,kvs); 
						insertQueue(mx2, kvs);
					} catch (Exception e) {
						continue;//SqlExecErr sql执行错误 这个不是全局的错误 只是对记录条数做处理
					}
				}
			}
		}else { return FLAG_FAIL;}//RespTxtErr 响应正文错误
		return FLAG_SUCCESS;//ExecOK 执行OK
	}

}
public class XXXXHelper extends ExtractDataTool implements Runnable, SqlsCreater {
	private static final Logger LOG = Logger.getLogger(XXXXHelper.class);
	@Override
	public String extract(String confPath, Class<Mx> model, Map<String, String> args) {
		Mx mx2 = initMx(confPath, model); if(mx2==null)return ExecCode.ReadConfErr.getCode();
		List<String> argsList = initArgsList(mx2);
		if (mx2.isSffy() && mx2.getQqfs().equals(REQ_GET)){
			int totalPages        = 1;	//PagePickErr
			List<Zd> jlzds 	      = alljlZds(mx2);
			List<Zd> autoZds      = allAutoZds(mx2);
			String rootPath       = mx2.getPath();
			List<String> subPaths = getSubPathsByJlZds(jlzds);
			for(int i = 1; i <= totalPages; i++){
				try {
					String url  = this.procReplaceArgs(mx2, i, argsList, args); 
					String htm  = httpRq(url);
					List<Map<String,String>> maps = doXpathQuery(htm, rootPath, subPaths);
					NdXpathTemp creater = null;
					if(maps!=null && maps.size()>0)
						for(Map<String,String> map:maps) {
							try {
								Map<String,String> kvs = new HashMap<String,String>();
								kvs     = this.jlZdProc(map, kvs, this.zdToMap(mx2));
								kvs     = this.autoZdProc(url, autoZds, args, kvs);
								kvs .put(URL_, "");
								kvs .put(WEBSITE, "http://dianying.2345.com/");
								
								creater = new NdXpathTemp();
								String conf = null;
								try {//readConfErr
									conf = NdXpathTemp.class.getResource("/template/nd/xpath/template.xml").getPath();
								} catch (Exception e) {	}
								creater.extract(conf, Mx.class, kvs);
							} catch (Exception e) {
								continue;
							}
						}
				} catch (Exception e) {
					continue;
				}
			}
		
		}
			
		return FLAG_SUCCESS;
	}

	public void run() {
		String errCode = main();
		insertQueueOver(new XXXXHelper(),errCode);//ExecFail
	}

	private String main(){
		XXXXHelper listHelper = new XXXXHelper();
		String confPath = null;
		try {
			confPath = XXXXHelper.class.getResource("/template/nd/xpath/XX-list.xml").getPath();
		} catch (Exception e1) {	return ExecCode.ReadConfErr.getCode();	}
		Map<String,String> kvs = new HashMap<String,String>();
		listHelper.extract(confPath, Mx.class, kvs);
		try {
			closeAsynService();
		} catch (Exception e) { return ExecCode.CloseErr.getCode(); }//CloseErr
		return ExecCode.ExecSucc.getCode();
	}
	public static void main(String[] args) {
		String errCode = new XXXXHelper().main();
		
		System.out.println(errCode);
	}
}


