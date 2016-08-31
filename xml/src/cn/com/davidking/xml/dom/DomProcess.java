/**
 * 	      功能名称 ：xml反序列化解析1.0
 *       
 *        (C)   Copyright  DavidKing 2016
 *        All   Right  Reserved.
 * 
 * 		    注意	：使用该功能的可以联系作者
 *         联系方式 13621151569@yeah.net
 */
package cn.com.davidking.xml.dom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.davidking.pojo.Book;
import cn.com.davidking.util.ReflectUtil;
import cn.com.davidking.util.StringUtil;
import cn.com.davidking.xml.AbsXmlProcess;
import cn.com.davidking.xml.XmlProcess;
// TODO: Auto-generated Javadoc

/**
 * The Class DomProcess.
 */
@SuppressWarnings("all")
public class DomProcess extends AbsXmlProcess {
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(DomProcess.class);
	
	/**
	 * The Constructor.
	 */
	public DomProcess() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param <T> the generic type
	 * @param file the file
	 * @param clazz the clazz
	 */
	public<T> DomProcess(String file, Class<T> clazz) {
		super(file, clazz);
	}

	/**
	 * The Constructor.
	 *
	 * @param file the file
	 */
	public DomProcess(String file) {
		super(file);
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#getArrays()
	 */
	@Override
	public <T> List<T> getArrays() throws Exception {
		
		Map<String,String> kvs = new HashMap<String,String>();
		
		List<T> ts = new ArrayList<T>();
		try {
			File f = new File(this.getFile());
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
			
			NodeList subNodes = doc.getElementsByTagName(StringUtil.lowerFirstLetter(this.getClazz().getSimpleName()));

			for (int i = 0; i < subNodes.getLength(); i++) {
				T t = null;
				Node subNode = subNodes.item(i);
				
				NamedNodeMap subNodeAttrs = subNode.getAttributes();
				if(subNodeAttrs!=null && subNodeAttrs.getLength()!=0)
					 for(int j = 0; j<subNodeAttrs.getLength(); j++){
					    	Node attr = subNodeAttrs.item(j);
					    	//通过Node对象的getNodeName()和getNodeValue()方法获取属性名和属性值
					    	kvs.put(attr.getNodeName(), attr.getNodeValue());
					    }
				
				
				//所有的子节点
				 NodeList subchilds = subNode.getChildNodes();
				 
				 for(int k = 0; k<subchilds.getLength(); k++){
					 if(subchilds.item(k).getNodeType() == Node.ELEMENT_NODE){
						 kvs.put(subchilds.item(k).getNodeName(), subchilds.item(k).getFirstChild().getNodeValue());
					 }
				 }
				t=(T)ReflectUtil.setFieldValue(this.getClazz().newInstance(), kvs);
				ts.add(t);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return ts;
	}
	
}
