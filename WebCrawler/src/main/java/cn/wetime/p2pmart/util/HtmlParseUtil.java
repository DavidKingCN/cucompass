/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

/**
 * 将html解析为节点信息处理
 * 
 * @author daikai
 * 
 */
public final class HtmlParseUtil {
    /**
     * 通过URL创建html解析器
     * @param URL
     * @return
     * @throws Exception
     */
    public static Parser getParserByUrl(final String URL)throws Exception{
    	Parser parser = new Parser();
		parser.setURL(URL);
		parser.setEncoding(parser.getEncoding());
    	return parser;
    }
    /**
     * 通过html文本创建html解析器
     * @param html
     * @return
     * @throws Exception
     */
    public static Parser getParserByHtml(String html) throws Exception{
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding(parser.getEncoding());
		
		return parser;
	}
    /**
     * 通过源dom节点及dom节点内的标签名获取一个Dom元素
     * @param src
     * @param tagName
     * @return
     */
    public static Node getNodeByTagFilter(Node src,String tagName){
    	Node result = null;
    	Node[] array = getNodesByTagFilter(src,tagName);
    	if(array!=null&&array.length!=0)
    		result = array[0];
    	return result;
    }
    /**
     * 
     * @param src
     * @param tagName
     * @return
     */
    public static Node[] getNodesByTagFilter(Node src,String tagName){
    	if(src==null)
    		return null;
    	if(tagName==null || tagName.equals(""))
    		return null;
    	
    	TagNameFilter tagNameFilter = new TagNameFilter(tagName);
    	NodeList temp = new NodeList();
    	src.collectInto(temp, tagNameFilter);
    	Node[] array = new Node[temp.size()];
		temp.copyToNodeArray(array);
    	return array;
    }
    /***
     * 根据属性名和属性值和解析器获取一个特定Dom节点
     * @param parser
     * @param tagName
     * @param tagVal
     * @return
     * @throws Exception
     */
    public static Node getNodeByHasAttr(Parser parser,String attrName,String attrVal)throws Exception{
    	Node result = null;
    	Node[] nodes = getNodesByHasAttr(parser, attrName, attrVal);
    	if(nodes!=null&&nodes.length!=0){
    		result = nodes[0];
    	}
    	return result;
    }
    /**
     * 根据属性名和属性值和解析器获取一组Dom节点
     * @param parser
     * @param tagName
     * @param tagVal
     * @return
     * @throws Exception
     */
    public static Node[] getNodesByHasAttr(Parser parser,String attrName,String attrVal)throws Exception{
    	Node[] result = null;
    	
		NodeList lists = getNodeListByAttr(parser,attrName,attrVal);
		if(lists!=null&&lists.size()!=0){
			result = new Node[lists.size()];
			lists.copyToNodeArray(result);
		}
		return result;
    }
    /**
     * 根据属性名和属性值和解析器获取Dom列表
     * @param parser
     * @param tagName
     * @param tagVal
     * @return
     * @throws Exception
     */
    public static NodeList getNodeListByAttr(Parser parser,String attrName,String attrVal)throws Exception{
    	NodeList result = null;
    	if(parser!=null)
	    	if(attrName!=null && !attrName.equals("")&&attrVal!=null&&!attrVal.equals("")){
		    	HasAttributeFilter hasAttributeFilter = 
		    			new HasAttributeFilter(attrName, attrVal);
				result = parser.extractAllNodesThatMatch(hasAttributeFilter);
	    	}
    	return result;
    }
  
}
