/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import cn.com.davidking.BeanFactory;
import cn.com.davidking.JsonParserBuilder;
import cn.com.davidking.html.parse.XpathQuery;
import cn.com.davidking.http.core.$;
import cn.com.davidking.http.core.$.Callback;
import cn.com.davidking.http.core.AsynReqImpl;
import cn.com.davidking.http.core.AsynServices;
import cn.com.davidking.http.core.AsynTemplate;
import cn.com.davidking.http.core.FluentReq;
import cn.com.davidking.json.JsonQuery;
import cn.com.davidking.model.Mx;
import cn.com.davidking.model.Zd;
import cn.com.davidking.task.SqlExecRt;
import cn.com.davidking.task.SqlTask;
import cn.com.davidking.task.SqlTaskSink;
import cn.com.davidking.util.FileUtil;
import cn.com.davidking.util.JaxbUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class ExtractDataTool.
 */
public abstract class ExtractDataTool {
	
	public static ExecutorService exeService=Executors.newFixedThreadPool(4);
	
	/** The Constant ARG_PAGE. */
	public static final String ARG_PAGE="$page$";
	
	/** The Constant ARG_OFFSET. */
	public static final String ARG_OFFSET = "$offset$";
	
	/** The Constant ARG_PAGESIZE. */
	public static final String ARG_PAGESIZE="$pagesize$";
	
	/** The Constant ARG_PARENT_ID. */
	public static final String ARG_PARENT_ID="parentId";
	
	public static final String ARG_PARENT_NAME="parentName";
	
	/** The Constant URL_. */
	public static final String URL_ = "url";
	
	/** The Constant MARK_FG. */
	public static final String MARK_FG= "#;#";//分割符
	
	/** The Constant IDX_FG. */
	public static final String IDX_FG= "#i#";//分割符
	
	/** The Constant ID_PLACE. */
	public static final String ID_PLACE = "#id#";
	
	/** The Constant DOWNLOAD_TIME. */
	public static final String DOWNLOAD_TIME = "downloadTime";
	
	/** The Constant UPDATE_TIME. */
	public static final String UPDATE_TIME = "updateTime";
	
	/** The Constant WEBSITE. */
	public static final String WEBSITE = "website";
	
	public static final String FLAG_SUCCESS = "SUCCESS";
	
	public static final String FLAG_FAIL = "FAIL";
	
	public static final String REQ_GET = "get";
	
	public static final String REQ_POST = "post";
	
	public static final boolean test = true;
	
	public static int CNT = 0;
	
	
	
	/**
	 * Gets the mx by conf.
	 *
	 * @param confPath the conf path
	 * @param model the model
	 * @return the mx by conf
	 */
	public Mx getMxByConf(String confPath, Class<Mx> model){
		//读取xml配置文件并转化成模型对象
		String xml = FileUtil.nioReadFile(confPath);
		Mx mx2 = JaxbUtil.convertToPojo(xml,model);
		return mx2;
	}
	
	/**
	 * Check mx valid.
	 *
	 * @param mx the mx
	 * @return true, if check mx valid
	 */
	protected boolean checkMxValid(Mx mx){
		boolean valid = false;
		if(mx!=null){
			String url = mx.getBaseUrl();
			if(url!=null&&!url.equals(""))	valid = true;
		}
		return valid;
	}
	
	/**
	 * Inits the mx null val.
	 *
	 * @param mx the mx
	 * @return the mx
	 */
	protected Mx initMxNullVal(Mx mx){
			String splitKey = mx.getSplitKey();
			String path = mx.getPath();
			String qqfs = mx.getQqfs();
			String pagePath = mx.getPagePath();
			
			mx.setSplitKey(splitKey==null?"":splitKey);
			mx.setPath(path==null?"":path);
			mx.setQqfs(qqfs==null?"get":qqfs);
			mx.setPagePath(pagePath==null?"":pagePath);
			if(mx.getZds()!=null && mx.getZds().size()>0){
				for(Zd zd:mx.getZds()){
					if(zd.getPath()!=null && zd.getPath().contains("#space#")){
						zd.setPath(zd.getPath().replace("#space#", "&nbsp;"));
					}
				}
			}
			return mx;
	}
	protected Mx initMx(String confPath,Class<Mx> model){
		Mx mx = this.getMxByConf(confPath, model);
		if (!checkMxValid(mx))	return null;
		Mx mx2 = initMxNullVal(mx);
		return mx2;
	}
	/**
	 * Inits the args list.
	 *
	 * @param mx the mx
	 * @return the list< string>
	 */
	protected List<String> initArgsList(Mx mx){
		if(mx.getArgsLen()==0) return null;
		List<String> argsList = new ArrayList<String>();
		for(int i=0;i<mx.getArgsLen();i++) argsList.add("$arg"+i+"$");
		return argsList;
	}
	
	/**
	 * 计算argsLen.
	 *
	 * @param n the n
	 * @return the int[]
	 */
	public  static int[] computeArgsLen(final int n){
		int[] intRt = {0,0};
		int cnt = 1;
		int temp = n;
		while(temp/10!=0){
			cnt ++;temp/=10;
		}
		switch (cnt) {
			case 2:
				intRt[1]=n%10; break;
			case 3:
				intRt[0]=n/100; intRt[1]=n%10; break;
			default:
				break;
		}
		
		return intRt;
	}
	
	/**
	 * http request.
	 *
	 * @param rqUrl the rq url
	 * @return the string
	 */
	
	protected String httpRq(String rqUrl){
//		String rqRt = HttpTemplate.doGet(rqUrl,null,null,null);
		if(test) AsynReqImpl.setExeService(exeService);
		String rqRt = AsynTemplate.doGet(rqUrl);
		//String rqRt = HttpTemplate.doGet(rqUrl);
		if(rqRt==null || rqRt.equals("") || rqRt.equals("-1")){
			System.out.println("响应正文为空;程序处理出错;非json格式!");
			rqRt = null;
		}
		return rqRt;
	}
	
	
	protected String httpRq$(String rqUrl){
		String rqRt = null;
		if(test) rqRt = $.get(rqUrl, new Callback());
		if(rqRt==null || rqRt.equals("") || rqRt.equals("-1")){
			System.out.println("响应正文为空;程序处理出错;非json格式!");
			rqRt = null;
		}
		return rqRt;
	}
	
	protected String httpProxyRq(String url,String ip,int port){
		String rqRt = FluentReq.get(url, ip, port);
		if(rqRt==null || rqRt.equals("") || rqRt.equals("-1")){
			System.out.println("响应正文为空;程序处理出错;非json格式!");
			rqRt = null;
		}
		return rqRt;
	}
	
	/**
	 * Check valid.
	 *
	 * @param src the src
	 * @return true, if check valid
	 */
	protected boolean checkValid(String src){
		boolean containMark_FG = src.contains(MARK_FG);
		String[] srcArr = src.split(MARK_FG);
		boolean lenValid = srcArr.length==2;
		return containMark_FG && lenValid;
	}
	
	protected List<Zd> alljlZds(Mx mx2){
		return mx2.getZds().stream().filter(zd -> zd.isJl() && zd.getPath() != null && !zd.getPath().equals("")).collect(Collectors.toList());
	}
	protected List<Zd> allbjlZds(Mx mx2){
		return mx2.getZds().stream().filter(zd -> !zd.isJl() && !zd.isAuto() && zd.getPath() != null&&!zd.getPath().equals("")).collect(Collectors.toList());
	}
	protected List<Zd> allAutoZds(Mx mx2){
		return mx2.getZds().stream().filter(zd -> zd.isAuto() ).collect(Collectors.toList());
	}
	
	protected List<String> getSubPathsByJlZds(List<Zd> jlzds){
		return jlzds.stream().map(Zd::getPath).collect(Collectors.toList());
	}
	
	/**
	 * Comput single keys.
	 *
	 * @param path the path
	 * @param zds the zds
	 * @return the string
	 */
	protected String computSingleKeys(String path,List<Zd> zds){
		String singleKeys = path;
		singleKeys += ".";
		singleKeys += zds.get(0).getPath();
		return singleKeys;
	}
	//计算$.{name,id} or $.xxxList[*].{id,name,addr}
	/**
	 * Comput multi keys.
	 *
	 * @param path the path
	 * @param zds the zds
	 * @return the string
	 */
	protected String computMultiKeys(String path,List<Zd> zds){
		if(zds==null) return null;
		String multiKeys = path;
		multiKeys += ".{";
		multiKeys += zds.stream().map(Zd::getPath).collect(Collectors.joining(","));
		multiKeys += "}";
		return multiKeys;
	}
	
	protected int computTotalPages2( List<String> argsList, Map<String,String> args, Mx mx){
		String rqUrl = mx.getBaseUrl()
				.replace(ARG_PAGE, mx.getPage()+"")
				.replace(ARG_PAGESIZE, mx.getPagesize()+"")
				.replace(ARG_OFFSET, "0");
		int totalPages =0;
		rqUrl = replaceArgs(rqUrl,argsList,args);
		String data = httpRq(rqUrl);
		if(data == null) return totalPages;
		
		String aimJson = getAimJson(data, mx.getSplitKey());
		
		String totalItems = JsonQuery.getSingleVal(aimJson, mx.getPagePath());
		
		if(!totalItems.matches("\\d+")) return totalPages;
		
		int items  = Integer.parseInt(totalItems); 
		return items/mx.getPagesize();
	}
	/**
	 * Comput total pages2.
	 *
	 * @param url the url
	 * @param argsList the args list
	 * @param args the args
	 * @param splitKey the split key
	 * @param mx the mx
	 * @return the int
	 */
	protected int computTotalPages2(String url, List<String> argsList, Map<String,String> args,	String splitKey, Mx mx){
		String rqUrl = url
				.replace(ARG_PAGE, mx.getPage()+"")
				.replace(ARG_PAGESIZE, mx.getPagesize()+"")
				.replace(ARG_OFFSET, "0");
		int totalPages =0;
		rqUrl = replaceArgs(rqUrl,argsList,args);
		String data = httpRq(rqUrl);
		if(data==null) return totalPages;
		
		String aimJson = getAimJson(data, splitKey);
		
		String totalItems = JsonQuery.getSingleVal(aimJson, mx.getPagePath());
		
		if(!totalItems.matches("\\d+")) return totalPages;
		
		int items  = Integer.parseInt(totalItems); 
		return items/mx.getPagesize();
	}
	
	
	protected int computTotalPages(
			Mx mx,
			List<String> argsList,
			Map<String,String> args
			){
		/**开始处理第一页的请求URL获取总页码数**/
		//TODO
		//url-variable
		String rqUrl = mx.getBaseUrl()
					.replace(ARG_PAGE, mx.getPage()+"")
					.replace(ARG_PAGESIZE, mx.getPagesize()+"");
		rqUrl = replaceArgs(rqUrl, argsList, args);
		
		
		int totalPages =0;
		String data = httpRq(rqUrl);//http request
		if(data==null) return totalPages;
		
		String aimJson = getAimJson(data, mx.getSplitKey());
		
		String totalPagesStr = JsonQuery.getSingleVal(aimJson, mx.getPagePath());
		if(!totalPagesStr.matches("\\d+")) return totalPages;
		//总页码数
		return Integer.parseInt(totalPagesStr);
		/**结束处理第一页的请求URL获取总页码数**/
	}
	/**
	 * 计算总页码数.
	 *
	 * @param url the url
	 * @param argsList the args list
	 * @param args the args
	 * @param splitKey the split key
	 * @param mx the mx
	 * @return the int
	 */
	protected int computTotalPages(
			String url,
			List<String> argsList,
			Map<String,String> args,
			String splitKey,
			Mx mx){
		/**开始处理第一页的请求URL获取总页码数**/
		//TODO
		//url-variable
		String rqUrl = url
					.replace(ARG_PAGE, mx.getPage()+"")
					.replace(ARG_PAGESIZE, mx.getPagesize()+"");
		rqUrl = replaceArgs(rqUrl,argsList,args);
		
		
		int totalPages = 0;
		String data = httpRq(rqUrl);//http request
		if(data==null) return totalPages;
		
		String aimJson = getAimJson(data, splitKey);
		
		String totalPagesStr = JsonQuery.getSingleVal(aimJson, mx.getPagePath());
		if(!totalPagesStr.matches("\\d+")) return totalPages;
		//总页码数
		return Integer.parseInt(totalPagesStr);
		/**结束处理第一页的请求URL获取总页码数**/
	}
	protected String replacePageArgs(Mx mx2,int i){
		return mx2.getBaseUrl().replace(ARG_PAGE, i+"").replace(ARG_PAGESIZE, mx2.getPagesize()+"").replace(ARG_OFFSET, ""+(i*mx2.getPagesize()));
	}
	/**
	 * url = http://www.xxx.com?cs1=$arg0$&cs2=$arg1$
	 * List[$arg0$,$arg1$,...,$arg10$,...] Map<$arg0$,val0><id,#id#>
	 *
	 * @param url the url
	 * @param argsList the args list
	 * @param args the args
	 * @return the string
	 */
	protected String replaceArgs(String url ,List<String> argsList,Map<String,String> args){
		if(argsList==null || args==null) return url;
		for(String arg:argsList)
			for(Map.Entry<String, String> kv:args.entrySet())
				if(kv.getKey().equals(arg))	url = url .replace(arg, kv.getValue());
		
		return url;
	}
	
	protected String procReplaceArgs(Mx mx2,int i,List<String> argsList,Map<String,String> args){
		String url  = replacePageArgs(mx2, i); 
		url 		= replaceArgs(url, argsList, args);
		return url;
	}
	
	/**
	 * 字段对象列表项转换成->Map(path,字段名称).
	 *
	 * @param mx the mx
	 * @return the map< string, string>
	 */
	
	protected Map<String,String> zdToMap(Mx mx){
		Map<String,String> rm = new HashMap<String,String>();
		mx.getZds().forEach(zd->{rm.put(zd.getPath(), zd.getName());});
		return rm;
	}
	
	/**
	 * *
	 * 得到目标json串.
	 *
	 * @param data the data
	 * @param splitKey the split key
	 * @return the aim json
	 */
	
	protected String getAimJson(String data,String splitKey){
		String aimJson = "";
		if(splitKey!=null && !splitKey.equals(""))
			aimJson = JsonParserBuilder.newJsonToolKit().getClosureJson(data, splitKey);
		else aimJson =  data;
		
		return aimJson;
	}
	protected List<Map<String,String>> doJsonQuery(Mx mx2,String data,String mapsPath){
		return JsonQuery.getMapVal(getAimJson(data, mx2.getSplitKey()), mapsPath);
	}
	
	protected List<Map<String,String>> doXpathQuery(String htm,String rootPath,List<String> subPaths){
		return XpathQuery.newXpathQuery().setHtml(htm).setRootPath(rootPath).setSubPaths(subPaths).query();
	}
	
	protected Map<String,String> jlZdProc(Map<String,String> map,Map<String,String> kvs,Map<String, String> ms){
		for(Entry<String, String> kv:map.entrySet()) 
			kvs.put(ms.get(kv.getKey()), kv.getValue().trim().replace(" ", ""));
		return kvs;
	}
	
	protected Map<String,String> bjlZdProc(Mx mx2,List<Zd>bjlzds,String data,Map<String,String> kvs,int idx){
		
		for(Zd bjlZd:bjlzds) 
			kvs.put(bjlZd.getName(), JsonQuery.getSingleVal(getAimJson(data, mx2.getSplitKey()), bjlZd.getPath().replace(IDX_FG, "" + idx)));
		return kvs;
	}
	
	protected Map<String,String> autoZdProc(String url,List<Zd> autoZds,Map<String,String> args,Map<String,String> kvs){
		if(autoZds!=null && autoZds.size()>0)
			for(Zd zd:autoZds)
				for(Entry<String,String>arg:args.entrySet()){
					String argK = arg.getKey();	String argV = arg.getValue();
					if(zd.getName().equals(argK)){
						if(argK.equals(URL_))kvs.put(URL_, url);else kvs.put(argK, argV);
						break;
					}
				}
		return kvs;
	}
	/**
	 * 根据map存放的<字段名称:字段值> 来生成一条sql.
	 *
	 * @param mx the mx
	 * @param items the items
	 * @return the string
	 */
	protected String sqlByMap(Mx mx ,Map<String,String> items){
		//INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append(mx.getMz()+"(");
		sql.append(mx.getZds().stream().map(Zd::getName).collect(Collectors.joining(",")));
		sql.append(") VALUES (");
		List<String> zdVals = new ArrayList<String>();
		mx.getZds().forEach(zd->{
			String zdName = zd.getName();
			zdVals.add("'"+items.get(zdName) + "'");
		});
		
		sql.append(zdVals.stream().map(Object::toString).collect(Collectors.joining(",")));
		sql.append(")");
		
		return sql.toString();
	}
	
	
	protected String sqlByMap2(Mx mx ,Map<String,String> items){
		//INSERT INTO table_name (列1, 列2,...) VALUES (?, ?,....)
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append(mx.getMz()+"(");
		sql.append(mx.getZds().stream().map(Zd::getName).collect(Collectors.joining(",")));
		sql.append(") VALUES (");
		List<String> zdVals = new ArrayList<String>();
		mx.getZds().forEach(zd->{ zdVals.add("?"); });
		
		sql.append(zdVals.stream().map(Object::toString).collect(Collectors.joining(",")));
		sql.append(")");
		
		return sql.toString();
	}
	protected void insertSqlToFile(Mx mx2, Map<String,String> kvs){
		if(test){ 
			String sql = this.sqlByMap(mx2, kvs);
			if(sql!=null){
				System.out.println(++CNT + ":" + sql);
			}
			FileUtil.nioWriteFile("D://test.sql", sql, true, true);
		}
	}
	
	protected void insertTestSqlToDb(Mx mx2,Map<String,String> kvs){
		if(test){ 
			String sql = this.sqlByMap(mx2, kvs);
			if(sql!=null){
				System.out.println(++CNT + ":" + sql);
			}
			//FileUtil.nioWriteFile("D://test.sql", sql, true, true);
			BeanFactory.newPersistence().doExecSql(sql);
		}
	}
	protected void insertSqlToDb(Mx mx2,Map<String,String> kvs){
		if(test){ 
			String sql = this.sqlByMap(mx2, kvs);
			if(sql!=null){
				System.out.println(++CNT + ":" + sql);
				BeanFactory.newPersistence().doExecSql(sql);
			}
			
		}
	}
	protected void insertQueue(Mx mx2,Map<String, String> kvs){
		if(!test)
		SqlTaskSink.getInstance().inflow(
				new SqlTask(
						new SqlExecRt().execSql(mx2.getMz(), 
								this.sqlByMap(mx2, kvs))));
	}
	
	protected void procDataItems(List<Map<String, String>> maps, Mx mx2, String url, String data, List<Zd> bjlzds, List<Zd> autoZds, Map<String, String> args){
		int idx = 0;
		if(maps != null && maps.size() > 0)
		for (Map<String, String> map : maps) {
			Map<String, String> kvs = new HashMap<String, String>();
			kvs = this.jlZdProc(map, kvs, zdToMap(mx2));
			if(bjlzds!=null)kvs = bjlZdProc(mx2, bjlzds, data, kvs, idx); idx++;
			kvs = autoZdProc(url, autoZds, args, kvs);
//			this.insertSqlToFile(mx2, kvs);
//			this.insertQueue(mx2, kvs);
			this.insertSqlToDb(mx2, kvs);
		}
	}
	
	protected void insertQueueOver(SqlsCreater sub){
		if(!test)SqlTaskSink.getInstance().inflow(new SqlTask(new SqlExecRt().finalOver(sub)));
	}
	
	protected void insertQueueOver(SqlsCreater sub,String errCode){
		if(!test)SqlTaskSink.getInstance().inflow(new SqlTask(new SqlExecRt().finalOver(sub , errCode)));
	}
	
	protected void closeAsynService(){
		if(test) new AsynServices(exeService,AsynReqImpl.getfReqExeService()).close();
	}
}
