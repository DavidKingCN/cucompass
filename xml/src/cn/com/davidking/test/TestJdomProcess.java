/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.test;

import java.io.File;
import java.util.List;

import cn.com.davidking.pojo.Book;
import cn.com.davidking.xml.XmlProcess;
import cn.com.davidking.xml.jdom.JdomProcess;
// TODO: Auto-generated Javadoc

/**
 * The Class TestJdomProcess.
 */
@SuppressWarnings("all")
public class TestJdomProcess {

	/**
	 * The main method.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub


		//指定要解析的xml的文件路径
		String file = new File("src/cn/com/davidking/test/book.xml").getAbsolutePath();
		//通过指定路径的xml文件以及类类型构建sax解析的实现
		XmlProcess xmlProcess = new JdomProcess(	file, Book.class);
		//检查xml文件是否合法
		boolean valid = xmlProcess.checkSchemaValid();
		if(valid){
			//xml文件合法则调用获取该文件的类类型数据
			 List<Book> books = xmlProcess.deserialArrays();
			 if(books!=null && books.size()!=0)
				for(Book book : books){
					System.out.println(book.toString());
				}
		}
	
	}

}
