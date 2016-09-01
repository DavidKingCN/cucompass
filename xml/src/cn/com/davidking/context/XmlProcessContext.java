package cn.com.davidking.context;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import cn.com.davidking.xml.XmlProcess;
import cn.com.davidking.xml.sax.SaxProcess;
@Component
public class XmlProcessContext {
	
	public static final Logger LOG = Logger.getLogger(XmlProcessContext.class);
	
	private XmlProcess xmlProcess;
	
//	@Bean
//	XmlProcess newXmlProcess(){
//		return new SaxProcess();
//	}

	@Autowired
	public XmlProcessContext(XmlProcess xmlProcess) {
		super();
		this.xmlProcess = xmlProcess;
	}

	public XmlProcess getXmlProcess() {
		return xmlProcess;
	}

	public <T> List<T> getArrays(String file, Class<T> clazz)throws Exception {

		xmlProcess.setClazz(clazz);
		xmlProcess.setFile(file);
		
		// 通过指定路径的xml文件以及类类型构建sax解析的实现
		// XmlProcess xmlProcess = new SaxProcess( file, clazz);
		// 检查xml文件是否合法
		boolean valid = xmlProcess.checkSchemaValid();
		List<T> books = null;
		if (valid) {
			// xml文件合法则调用获取该文件的类类型数据
			books = xmlProcess.deserialArrays();
			
		}else{
			LOG.error("error:File is not valid!");
		}
		return books;
	}
}
