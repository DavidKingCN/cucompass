/*
 *    功能名称   ： httpclient 封裝实现1.2
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import cn.com.davidking.http.core.HttpTemplate;
import cn.com.davidking.util.FileUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class URLCutter.
 */
public class URLCutter {

	/** The Constant REG_QU. */
	public static final String REG_QU = "\\?";
	
	/** The Constant MARK_AND. */
	public static final String MARK_AND = "&";
	//重整url ""+ 遇到first? 换行 遇到&换行 
	/**
	 * Refact url.
	 *
	 * @param filepath the filepath
	 * @return the string
	 */
	public static String refactUrl(String filepath){
		String targetStr = FileUtil.readFileAllLoad(filepath);
		String stSplit = null;
		String[] nds = null;
		if(targetStr.contains("?")){
			int st = targetStr.indexOf("?");
			stSplit = targetStr.substring(0, st);
			String ndSplit = targetStr.substring(st);
			
			if(ndSplit.contains("&")){
				nds = ndSplit.split("&");
			}
		}
		StringBuffer result = new StringBuffer();
		result.append("\t").append("String url=");
		
		
		if(stSplit!=null&&!stSplit.equals("")){
			result.append("\n").append("\t").append("\"").append(stSplit).append("\"+").append("\n");
		}else{
			result.append("\"\";").append("\n");
			return result.toString();
		}
		
		
		if(nds!=null && nds.length>1){
			int indx = 0;
			for(String nd:nds){
				result.append("\t\t").append("\"");
				if(indx!=0)
					result.append("&");
				result.append(nd).append("\"").append((indx++==nds.length-1)?";":"+").append("\n");
			}
		}else{
			return result.toString().replace("+", ";");
		}
		return result.toString();
	}
	
	
	/**
	 * Cut.
	 *
	 * @param URL the URL
	 * @param AARD the AARD
	 * @param useMultiThread the use multi thread
	 * @return the string
	 */
	public static String cut(final String URL,int AARD,boolean useMultiThread){
		String st = getStUrl(URL);	String nd = getNdUrl(URL);
		String url = st +URLCutter.MARK_AND+nd;
		String srcReq = httpReqRt(URL);	int lensSrc = srcReq.trim().length();
		String[] pkvs = getAllArgsArr(URL);
		return calcTarget(url,st,pkvs,lensSrc,AARD,useMultiThread);
	}
	
	//AARD 偏差
	/**
	 * Cut.
	 *
	 * @param URL the URL
	 * @param useMultiThread the use multi thread
	 * @return the string
	 */
	public static String cut(final String URL,boolean useMultiThread){
		String st = getStUrl(URL);String nd = getNdUrl(URL);
		String url = st +URLCutter.MARK_AND+nd;
		String srcReq = httpReqRt(URL);	int lensSrc = srcReq.trim().length();
		String[] pkvs = getAllArgsArr(URL);
		final int AARD = calcFinalAARD(url,pkvs,lensSrc);
		return calcTarget(url,st,pkvs,lensSrc,AARD,useMultiThread);
	}
	
	/**
	 * Calc target.
	 *
	 * @param url the url
	 * @param st the st
	 * @param pkvs the pkvs
	 * @param lensSrc the lens src
	 * @param AARD the AARD
	 * @param useMultiThread the use multi thread
	 * @return the string
	 */
	private static String calcTarget(String url,String st,String[] pkvs,int lensSrc,int AARD,boolean useMultiThread){
		List<String> mustArgsList = new ArrayList<String>();
		ExecutorService execs = null;
		if(useMultiThread)execs= Executors.newFixedThreadPool(32);
		for(String pkv:pkvs){
			if(!useMultiThread){
				String ndAg = getNeedArg(url,pkv,lensSrc,AARD);
				if(ndAg!=null&&!ndAg.equals(""))mustArgsList.add(ndAg);
			}else{
				Future<String> fs = execs.submit(()->{
					return getNeedArg(url,pkv,lensSrc,AARD);
				});
				try {
					String r = fs.get();
					if(r!=null)mustArgsList.add(r);
				} catch (Exception e) {}
			}
			
		}
		if(execs!=null)execs.shutdownNow();
		String mustArgStr =	mustArgsList.stream().map(Object::toString).collect(Collectors.joining(URLCutter.MARK_AND));
		String targetUrl = st;
		if(mustArgStr!=null && !mustArgStr.equals(""))
			targetUrl += URLCutter.MARK_AND+ mustArgStr;
		
		System.out.println("结果长度值："+httpReqRt(targetUrl).trim().length());
		int targetLen = httpReqRt(targetUrl).trim().length();
		
		if(Math.abs(targetLen-lensSrc)<=AARD) System.out.println("解析较为成功...");
		else System.out.println("解析效果一般般...");
		
		if(!targetUrl.contains("&"))targetUrl = targetUrl.replace("?1=1", "");
		else targetUrl = targetUrl.replace("1=1&", "");
		
		return targetUrl;
	}
	
	/**
	 * Calc final aard.
	 *
	 * @param url the url
	 * @param pkvs the pkvs
	 * @param lensSrc the lens src
	 * @return the int
	 */
	private static int calcFinalAARD(String url,String[] pkvs,int lensSrc){
		List<Integer> ints = new ArrayList<Integer>();
		for(String pkv:pkvs)ints.add(calcAARD(url,pkv,lensSrc));
		
		Map<Object,Integer> rm =ints.parallelStream().filter(i->i>=0).collect(Collectors.groupingBy(p->p, Collectors.summingInt(p->1)) );
		int needInt = 0;
		int maxPd = 0; 
		for(Entry<Object, Integer> entry:rm.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
			int curPd = entry.getValue();
			if(curPd>maxPd){maxPd = curPd;needInt = (Integer)entry.getKey();}
		}
		return needInt;
	}
	
	/**
	 * Calc aard.
	 *
	 * @param url the url
	 * @param pkv the pkv
	 * @param lensSrc the lens src
	 * @return the int
	 */
	private static int calcAARD(String url,String pkv,int lensSrc){
		int aard = -1;
		String[] kv = pkv.split("=");
		if(kv!=null && kv.length==2){
			String curPkv = URLCutter.MARK_AND+pkv;
			String tmp = url.replace(curPkv, "");
			String tmpReq = httpReqRt(tmp);
			int lenTmp = tmpReq.trim().length();
			aard = Math.abs(lensSrc-lenTmp);
		}
		return aard;
	}
	
	
	/**
	 * Gets the need arg.
	 *
	 * @param url the url
	 * @param pkv the pkv
	 * @param lensSrc the lens src
	 * @param AARD the AARD
	 * @return the need arg
	 */
	private static String getNeedArg(String url,String pkv,int lensSrc,int AARD){
		int idx = pkv.indexOf("=");
		//String[] kv = pkv.split("=");
		//if(kv!=null && kv.length==2){
			
		String curPkv = URLCutter.MARK_AND+pkv;
		String tmp = url.replace(curPkv, "");
		String tmpReq = httpReqRt(tmp);
		int lenTmp = tmpReq.trim().length();
		
		System.out.println(lensSrc+":"+lenTmp+":"+pkv);
		int absVal = Math.abs(lensSrc-lenTmp);
		String k = pkv.substring(0, idx);	//kv[0];
		String v = pkv.substring(idx+1);	//kv[1];
		boolean containId =(k.contains("Id")||k.contains("id"))&& v.equals("0");
		boolean order = k.contains("order");
		boolean zeroVal = v.equals("0");
		if(containId&&absVal==0)return null;
		if(order&& absVal==0)return null;
		if(zeroVal&&absVal==0)return null;
		if(absVal>AARD)return pkv;
		//}
		return null;
	}
	
	/**
	 * Http req rt.
	 *
	 * @param url the url
	 * @return the string
	 */
	public static String httpReqRt(String url){
		String rt = HttpTemplate.doGet(url);
		return rt;
	}
	
	/**
	 * Req rt len.
	 *
	 * @param url the url
	 * @return the int
	 */
	public static int reqRtLen(String url){
		return httpReqRt(url).trim().length();
	}
	
	/**
	 * Gets the st url.
	 *
	 * @param URL the URL
	 * @return the st url
	 */
	private static String getStUrl(final String URL){
		String[] urlSplitSt = URL.split(URLCutter.REG_QU);
		String urlPrefix = urlSplitSt[0];
		urlPrefix += "?1=1";
		return urlPrefix;
	}
	//first second third  fouth
	/**
	 * Gets the nd url.
	 *
	 * @param URL the URL
	 * @return the nd url
	 */
	private static String getNdUrl(final String URL){
		String[] urlSplitSt = URL.split(URLCutter.REG_QU);
		String ndUrl = urlSplitSt[1] ;
		return ndUrl;
	}
	
	/**
	 * Gets the all args arr.
	 *
	 * @param URL the URL
	 * @return the all args arr
	 */
	private static String[] getAllArgsArr(final String URL){
		String args = getNdUrl(URL);
		String[] pkvs = args.split(URLCutter.MARK_AND);
		return pkvs;
	}
}