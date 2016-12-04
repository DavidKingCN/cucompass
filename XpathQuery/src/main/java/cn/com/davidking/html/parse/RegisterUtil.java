/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterUtil.
 */
public final class RegisterUtil {
	
	/**
	 * Inits the xpath pickers.
	 *
	 * @return the map< string, data picker>
	 */
	public static Map<String,DataPicker> initXpathPickers(){

		InputStream is = RegisterUtil.class.getResourceAsStream("/xpath-rule-picker.properties");
		Properties p = new Properties();
		Map<String,DataPicker> pickers = new HashMap<String,DataPicker>();
		try {
			p.load(is);
			Enumeration ks= p.keys();
			
			while(ks.hasMoreElements()){
				String rule = ks.nextElement().toString();
				String pickerClz = XPathUtils.PARSE_PACK;
				pickerClz += p.getProperty(rule);
				
				DataPicker picker = (DataPicker)Class.forName(pickerClz).newInstance();
				rule = rule.replace("&eq;", "=");
				pickers.put(rule, picker);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pickers;
	
	}
	
}
