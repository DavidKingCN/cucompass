/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.xml;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface XmlProcess.
 *
 * @author DavidKing
 */
public interface XmlProcess {
	
	/**
	 * Check schema valid.
	 *
	 * @return true, if check schema valid
	 * @throws Exception the exception
	 */
	public boolean checkSchemaValid() throws Exception;
	
	/**
	 * Gets the arrays.
	 *
	 * @param <T> the generic type
	 * @return the arrays
	 * @throws Exception the exception
	 */
	public abstract <T> List<T> deserialArrays()	throws Exception;

}
