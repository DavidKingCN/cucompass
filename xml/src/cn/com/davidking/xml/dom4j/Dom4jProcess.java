/**
 * 	      功能名称 ：xml反序列化解析1.0
 *       
 *        (C)   Copyright  DavidKing 2016
 *        All   Right  Reserved.
 * 
 * 		    注意	：使用该功能的可以联系作者
 *         联系方式 13621151569@yeah.net
 */
package cn.com.davidking.xml.dom4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cn.com.davidking.util.ReflectUtil;
import cn.com.davidking.util.StringUtil;
import cn.com.davidking.xml.AbsXmlProcess;

// TODO: Auto-generated Javadoc
/**
 * The Class Dom4jProcess.
 *
 * @param <T> the generic type
 */
public class Dom4jProcess<T> extends AbsXmlProcess<T> {

	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(Dom4jProcess.class);

	/**
	 * The Constructor.
	 */
	public Dom4jProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Constructor.
	 *
	 * @param file the file
	 * @param clazz the clazz
	 */
	public Dom4jProcess(String file, Class<T> clazz) {
		super(file, clazz);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Constructor.
	 *
	 * @param file the file
	 */
	public Dom4jProcess(String file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#getArrays()
	 */
	@Override
	public <T> List<T> getArrays() throws Exception {

		Map<String, String> kvs = new HashMap<String, String>();

		List<T> ts = new ArrayList<T>();
		try {
			Element root = new SAXReader().read(new File(this.getFile()))
					.getRootElement();
			Iterator<Element> nodes = root.elementIterator(StringUtil
					.lowerFirstLetter(this.getClazz().getSimpleName()));

			while (nodes.hasNext()) {
				T t = null;
				Element node = nodes.next();
				Iterator<Attribute> attrs = node.attributeIterator();
				while (attrs.hasNext()) {
					Attribute attr = attrs.next();
					kvs.put(attr.getName(), attr.getData().toString());
				}
				Iterator<Node> subNodes = node.nodeIterator();

				while (subNodes.hasNext()) {
					Node subNode = subNodes.next();
					if (subNode.getName() != null)
						kvs.put(subNode.getName(), subNode.getText());
				}
				t = (T) ReflectUtil.setFieldValue(
						this.getClazz().newInstance(), kvs);
				ts.add(t);
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return ts;
	}

}
