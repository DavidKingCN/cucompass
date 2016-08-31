/**
 * 	      功能名称 ：xml反序列化解析1.0
 *       
 *        (C)   Copyright  DavidKing 2016
 *        All   Right  Reserved.
 * 
 * 		    注意	：使用该功能的可以联系作者
 *         联系方式 13621151569@yeah.net
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
	public abstract <T> List<T> getArrays()	throws Exception;

}
