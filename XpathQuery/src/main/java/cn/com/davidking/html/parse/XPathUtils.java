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
import java.util.List;
import java.util.stream.Collectors;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
// TODO: Auto-generated Javadoc

/**
 * The Class XPathUtils.
 *
 * @author daikai
 */
public class XPathUtils {
	
	/** The Constant NODE_RULE. */
	public static final String NODE_RULE = "(/{1,2}[a-zA-Z]+(\\[@.+='.+'\\])?)+";
	
	/** The Constant ATTR_RULE. */
	public static final String ATTR_RULE = "(/{1,2}[a-zA-Z]+(\\[@.+='.+'\\])?)*/{1,2}@.+";
	
	/** The Constant PARSE_PACK. */
	public static final String PARSE_PACK = "cn.com.davidking.html.parse.";

	
	
	/**
	 * Checks if is attr.
	 *
	 * @param path the path
	 * @return true, if checks if is attr
	 */
	public static boolean isAttr(String path){
		return path.matches(ATTR_RULE)&&!path.matches(NODE_RULE);
	}
	
	/**
	 * Checks if is node.
	 *
	 * @param path the path
	 * @return true, if checks if is node
	 */
	public static boolean isNode(String path){
		return !path.matches(ATTR_RULE)&&path.matches(NODE_RULE);
	}
	
	/**
	 * Path val by idx.
	 *
	 * @param htmNode the htm node
	 * @param path the path
	 * @param idx the idx
	 * @param isAttr the is attr
	 * @return the string
	 * @throws XPatherException the x pather exception
	 */
	public static String pathValByIdx(TagNode htmNode, String path,int idx,boolean isAttr) throws XPatherException {
		String result = "";	Object[] nodes = htmNode.evaluateXPath(path);
		if (nodes != null && nodes.length > 0) {
			int len = nodes.length;	if (len == 1) result = singleVal(nodes,path,isAttr); else result = selectOneVal(nodes,path,idx,isAttr);
		}
		return result;
	}
	
	/**
	 * Select one val.
	 *
	 * @param nodes the nodes
	 * @param path the path
	 * @param idx the idx
	 * @param isAttr the is attr
	 * @return the string
	 */
	private static String selectOneVal(Object[] nodes,String path,int idx,boolean isAttr){
		String result  = ""; int cursor = 0;
		for (Object obj : nodes) {
			String tmp = null; if(obj instanceof String) tmp=obj.toString(); else if(obj instanceof TagNode)	tmp=getNodeVal((TagNode) obj, path,isAttr);
			if(cursor==idx)	result = tmp; cursor ++;
		}
		return result;
	}
	
	/**
	 * Path val.
	 *
	 * @param htmNode the htm node
	 * @param path the path
	 * @param isAttr the is attr
	 * @return the string
	 * @throws XPatherException the x pather exception
	 */
	public static String pathVal(TagNode htmNode, String path,boolean isAttr) throws XPatherException {
		String result = "";	Object[] nodes = htmNode.evaluateXPath(path);
		if (nodes != null && nodes.length > 0) {
			int len = nodes.length;	if (len == 1) result = singleVal(nodes,path,isAttr); else result = concatMoreVal(nodes,path,isAttr);
		}
		return result;
	}
	
	/**
	 * Mutil nodes.
	 *
	 * @param htmNode the htm node
	 * @param path the path
	 * @return the list< tag node>
	 */
	public static List<TagNode> mutilNodes(TagNode htmNode, String path) {
		List<TagNode> results = new ArrayList<>();
		try {
			Object[] nodes = htmNode.evaluateXPath(path); if (nodes != null && nodes.length > 0) { for (Object obj : nodes) { TagNode node = (TagNode) obj; results.add(node);}}
		} catch (Exception ignore) {}
		return results;
	}
	
	
	
	/**
	 * Single val.
	 *
	 * @param nodes the nodes
	 * @param path the path
	 * @param isAttr the is attr
	 * @return the string
	 */
	private static String singleVal(Object[] nodes,String path,boolean isAttr){
		String result = "";	Object obj = nodes[0];
		if(obj instanceof String) result = obj.toString(); else if(obj instanceof TagNode) result = getNodeVal((TagNode) obj,path,isAttr);
		return result;
	}
	
	/**
	 * Concat more val.
	 *
	 * @param nodes the nodes
	 * @param path the path
	 * @param isAttr the is attr
	 * @return the string
	 */
	private static String concatMoreVal(Object[] nodes,String path,boolean isAttr){
		String result  = ""; List<String> rs = new ArrayList<>();
		for (Object obj : nodes) 
			if(obj instanceof String) rs.add(obj.toString()); else if(obj instanceof TagNode)	rs.add(getNodeVal((TagNode) obj, path,isAttr));
		result = rs.stream().map(Object::toString).collect(Collectors.joining(","));
		return result;
	}
	
	/**
	 * *.
	 *
	 * @param node the node
	 * @param path the path
	 * @param isAttr the is attr
	 * @return the node val
	 */
	private static String getNodeVal(TagNode node,String path,boolean isAttr){
		String result  = "";
		if (isAttr)	result = node.getAttributes().get(path); else result = node.getText().toString();
		return result ;
	}
}
