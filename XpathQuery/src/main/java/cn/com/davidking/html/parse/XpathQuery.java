/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

// TODO: Auto-generated Javadoc
/**
 * The Class XpathQuery.
 *
 * @author daikai
 */
public class XpathQuery {
	
	/** The xpath. */
	private String xpath;
	
	/** The xpaths. */
	private List<String> xpaths;
	
	/** The cleaner. */
	private HtmlCleaner cleaner;
	
	/** The htm. */
	private TagNode htm;
	
	/** The resp htm. */
	private String respHtm;
	
	/**
	 * The Constructor.
	 */
	public XpathQuery() {
		super();
		this.xpaths = new ArrayList<>();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param xpath the xpath
	 * @param xpaths the xpaths
	 */
	public XpathQuery(String xpath,List<String> xpaths) {
		super();
		this.xpath = xpath;
		this.xpaths = xpaths;
	}
	
	/**
	 * Sets the html.
	 *
	 * @param respHtm the html
	 * @return the xpath query
	 */
	public XpathQuery setHtml(String respHtm){
		this.respHtm = respHtm;
		return this;
	}
	
	/**
	 * Sets the root path.
	 *
	 * @param xpath the root path
	 * @return the xpath query
	 */
	public XpathQuery setRootPath(String xpath){
		this.xpath = xpath;
		return this;
	}
	
	public XpathQuery setSubPaths(List<String> xpaths){
		this.xpaths = xpaths;
		return this;
	}
	/**
	 * Adds the sub path.
	 *
	 * @param xpath the xpath
	 * @return the xpath query
	 */
	public XpathQuery addSubPath(String xpath){
		this.xpaths.add(xpath);
		return this;
	}
	
	/** The pickers. */
	static Map<String,DataPicker> pickers = new HashMap<>();
	static{	pickers = RegisterUtil.initXpathPickers(); }
	
	/**
	 * Inits the.
	 */
	private void init(){
		cleaner = new HtmlCleaner(); this.htm = cleaner.clean(respHtm);
	}
	
	/**
	 * Query.
	 *
	 * @return the list< map< string, string>>
	 */
	public List<Map<String,String>> query(){
		init();
		List<Map<String,String>> results  = new ArrayList<>();
		List<TagNode> nodes = XPathUtils.mutilNodes(htm, xpath);
		PickAgent pickAgent = new PickAgent();
		Map<String,String> kvs = null;
		
		for(TagNode node:nodes){
			kvs = new HashMap<>();
			for(String sub:xpaths) kvs = matchRule(pickAgent, node, sub, kvs);
			if(kvs!=null && kvs.size()>0) results.add(kvs);
		}
		return results;
	}
	
	
	/**
	 * Match rule.
	 *
	 * @param pickAgent the pick agent
	 * @param node the node
	 * @param sub the sub
	 * @param kvs the kvs
	 * @return the map< string, string>
	 */
	private Map<String,String> matchRule(PickAgent pickAgent,TagNode node,String sub,Map<String,String> kvs){
		pickAgent.init(cleaner, node, sub);
		
		for(Entry<String,DataPicker> entry:pickers.entrySet()){
			String rule  = entry.getKey(); DataPicker worker = entry.getValue();
			if(sub.matches(rule)){
				worker.init(pickAgent);	String rt = worker.get();
				if(pickAgent.isObeyRule()){	kvs.put(sub, rt); break;}
			}
		}
		return kvs;
	}
	
	/**
	 * New xpath query.
	 *
	 * @return the xpath query
	 */
	public static XpathQuery newXpathQuery(){
		return new XpathQuery();
	}
}
