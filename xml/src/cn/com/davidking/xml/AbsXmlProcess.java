/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.xml;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
// TODO: Auto-generated Javadoc
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class AbsXmlProcess.
 *
 * @param <T> the generic type
 * @author DavidKing
 */
public abstract class AbsXmlProcess<T> implements XmlProcess {
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(AbsXmlProcess.class);
	
	/** The file. */
	private String file;
	
	/** The clazz. */
	private Class<T> clazz;
	
	/**
	 * The Constructor.
	 */
	public AbsXmlProcess() {
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param file the file
	 */
	public AbsXmlProcess(String file) {
		super();
		this.file = file;
	}
	
	
	
	/**
	 * The Constructor.
	 *
	 * @param file the file
	 * @param clazz the clazz
	 */
	public AbsXmlProcess(String file, Class<T> clazz) {
		super();
		this.file = file;
		this.clazz = clazz;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public String getFile() {
		return file;
	}



	/**
	 * Sets the file.
	 *
	 * @param file the file
	 */
	@Override
	public void setFile(String file) {
		this.file = file;
	}


	/**
	 * Gets the clazz.
	 *
	 * @return the clazz
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * Sets the clazz.
	 *
	 * @param clazz the clazz
	 */
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	/* (non-Javadoc)
	 * @see cn.com.davidking.xml.XmlProcess#checkSchemaValid()
	 */
	public boolean checkSchemaValid() throws Exception {
		boolean valid = true;
		try{
    	    SAXReader reader = new SAXReader();
//    	    reader.setFeature("http://apache.org/xml/features/validation/schema", true);
    	    reader.read(file);/*读取xml文件，获得document对象*/
    	    LOG.info("Load "+file+" success!");
    	    

		}catch (Exception e){
			System.out.println(e.getMessage());
			LOG.error(e.getMessage());
			valid = false;
		}
		return valid;
	}

}
