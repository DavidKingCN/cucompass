/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.xml.sax;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.com.davidking.util.ReflectUtil;
import cn.com.davidking.util.StringUtil;
import cn.com.davidking.xml.AbsXmlProcess;

// TODO: Auto-generated Javadoc
/**
 * The Class SaxProcess.
 */
@SuppressWarnings("all")
//@Component
public class SaxProcess extends AbsXmlProcess {
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(SaxProcess.class);

	/**
	 * The Constructor.
	 */
	public SaxProcess() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param <T> the generic type
	 * @param file the file
	 * @param clazz the clazz
	 */
	public <T> SaxProcess(String file, Class<T> clazz) {
		super(file, clazz);
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#deserialArrays()
	 */
	@Override
	public List deserialArrays() throws Exception {
		// TODO Auto-generated method stub
		try {
			SaxParser parser = new SaxParser(this.getClazz());
			SAXParserFactory.newInstance().newSAXParser()
					.parse(new FileInputStream(this.getFile()), parser);
			return parser.getResults();

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return null;

	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#getArrays()
	 */
	

}

class SaxParser<T> extends DefaultHandler {
	
	public static final Logger LOG = Logger.getLogger(SaxParser.class);
	private List<T> ts = null;
	private T t = null;
	private Class<T> clazz = null;
	private String preTag = null;

	private Map<String, String> kvs = null;

	public SaxParser(Class<T> clazz) {
		super();
		this.clazz = clazz;

	}

	public List<T> getResults() {
		return ts;
	}

	@Override
	public void startDocument() throws SAXException {
		ts = new ArrayList<T>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		String iterName = clazz.getSimpleName();
		if (StringUtil.lowerFirstLetter(iterName).equals(qName)) {
			kvs = new HashMap<String, String>();
			int length = attributes.getLength();
			for (int i = 0; i < length; i++) {
				kvs.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		preTag = qName;// 将正在解析的节点名称赋给preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (StringUtil.lowerFirstLetter(clazz.getSimpleName()).equals(qName)) {
			try {
				t = ReflectUtil.setFieldValue(clazz.newInstance(), kvs);
			} catch (InstantiationException e) {
				LOG.error(e.getMessage());
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage());
			}
			ts.add(t);
			t = null;
		}
		preTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);

			String fieldName = "";
			for (Field field : clazz.getDeclaredFields()) {
				fieldName = field.getName();
				if (preTag.equalsIgnoreCase(fieldName))
					kvs.put(preTag, content);
			}
		}
	}

}