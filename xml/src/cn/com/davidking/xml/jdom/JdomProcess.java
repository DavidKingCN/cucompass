/**
 * 	      功能名称 ：xml反序列化解析1.0
 *       
 *        (C)   Copyright  DavidKing 2016
 *        All   Right  Reserved.
 * 
 * 		    注意	：使用该功能的可以联系作者
 *         联系方式 13621151569@yeah.net
 */
package cn.com.davidking.xml.jdom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.davidking.util.ReflectUtil;
import cn.com.davidking.util.StringUtil;
import cn.com.davidking.xml.AbsXmlProcess;
// TODO: Auto-generated Javadoc

/**
 * The Class JdomProcess.
 *
 * @param <T> the generic type
 */
@SuppressWarnings("all")
public class JdomProcess<T> extends AbsXmlProcess<T> {
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(JdomProcess.class);
	
	/**
	 * The Constructor.
	 */
	public JdomProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Constructor.
	 *
	 * @param file the file
	 * @param clazz the clazz
	 */
	public JdomProcess(String file, Class<T> clazz) {
		super(file, clazz);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Constructor.
	 *
	 * @param file the file
	 */
	public JdomProcess(String file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#getArrays()
	 */
	@Override
	public <T> List<T> getArrays() throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> kvs = new HashMap<String,String>();
		
		List<T> ts = new ArrayList<T>();
		try{
			Element root = new SAXBuilder().build(new File(this.getFile())).getRootElement();
			
			List nodes = root.getChildren(StringUtil.lowerFirstLetter(this.getClazz().getSimpleName()));
			
			for(int i=0;i<nodes.size();i++){
				T t = null;
				Element node = (Element)nodes.get(i);
				List attrs = node.getAttributes();
				for(int j=0;j<attrs.size();j++){
					Attribute attr = (Attribute)attrs.get(j);
					kvs.put(attr.getName(), attr.getValue());
				}
				
				List subNodes = node.getChildren();
				
				for(int k=0;k < subNodes.size();k++){
					Element subNode = (Element)subNodes.get(k);
					kvs.put(subNode.getName(), subNode.getValue());
				}
				t=(T)ReflectUtil.setFieldValue(this.getClazz().newInstance(), kvs);
				ts.add(t);
			}
		}catch(Exception e){
			LOG.error(e.getMessage());
		}
		return ts;
	}

}
