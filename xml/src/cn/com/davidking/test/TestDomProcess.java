/**
 * 	      功能名称 ：xml反序列化解析1.0
 *       
 *        (C)   Copyright  DavidKing 2016
 *        All   Right  Reserved.
 * 
 * 		    注意	：使用该功能的可以联系作者
 *         联系方式 13621151569@yeah.net
 */
package cn.com.davidking.test;

import java.io.File;
import java.util.List;

import cn.com.davidking.pojo.Book;
import cn.com.davidking.xml.XmlProcess;
import cn.com.davidking.xml.dom.DomProcess;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDomProcess.
 */
public class TestDomProcess {

	/**
	 * The main method.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{

		//指定要解析的xml的文件路径
		String file = new File("src/cn/com/davidking/test/book.xml").getAbsolutePath();
		//通过指定路径的xml文件以及类类型构建sax解析的实现
		XmlProcess xmlProcess = new DomProcess(	file, Book.class);
		//检查xml文件是否合法
		boolean valid = xmlProcess.checkSchemaValid();
		if(valid){
			//xml文件合法则调用获取该文件的类类型数据
			 List<Book> books = xmlProcess.getArrays();
			 if(books!=null && books.size()!=0)
				for(Book book : books){
					System.out.println(book.toString());
				}
		}
	
		
	}
}
