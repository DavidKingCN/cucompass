/*
 *    功能名称   ： xml反序列化实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.context;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.davidking.pojo.Book;
import cn.com.davidking.xml.XmlProcess;
import cn.com.davidking.xml.sax.SaxProcess;
import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class SaxProcessAppTest.
 */
@Configuration
@ComponentScan
public class SaxProcessAppTest  extends TestCase{

	//指定要解析的xml的文件路径
		/** The Constant file. */
	public static final String file = new File("src/cn/com/davidking/test/book.xml").getAbsolutePath();
		
		/**
		 * The main method.
		 *
		 * @throws Exception the exception
		 */
		@Test
		public void testParseXml() throws Exception{

			
			//通过指定路径的xml文件以及类类型构建sax解析的实现
			XmlProcess xmlProcess = new SaxProcess(	file, Book.class);
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
		
		
		/**
		 * Test parse xml by spring config.
		 *
		 * @throws Exception the exception
		 */
		@Test
		public void testParseXmlBySpringConfig()throws Exception{
//			ApplicationContext context =
//					new AnnotationConfigApplicationContext(TestSaxProcess.class);
			
			
			ApplicationContext context = 
					new ClassPathXmlApplicationContext("classpath:spring.xml");
			
			XmlProcess xmlProcess = (XmlProcess)context.getBean("saxProcess");
			
			xmlProcess.setClazz(Book.class);
			xmlProcess.setFile(file);
			
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
	
	/**
	 * New xml process.
	 *
	 * @return the xml process
	 */
	@Bean
	XmlProcess newXmlProcess(){
		return new SaxProcess();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(SaxProcessAppTest.class);
		
		XmlProcessContext xmlProcessContext = context.getBean(XmlProcessContext.class);
		try {
			List<Book>books = xmlProcessContext.getArrays(file, Book.class);
			
			for(Book book:books){
				System.out.println(book);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Test sax process by spring annotation.
	 */
	@Test
	public void testSaxProcessBySpringAnnotation(){
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(SaxProcessAppTest.class);
		
		XmlProcessContext xmlProcessContext = context.getBean(XmlProcessContext.class);
		try {
			List<Book>books = xmlProcessContext.getArrays(file, Book.class);
			
			for(Book book:books){
				System.out.println(book);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
