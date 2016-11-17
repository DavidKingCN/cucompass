package cn.com.davidking.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.com.davidking.json.Constant;
import cn.com.davidking.json.parse.JsonPicker;

public final class RegisterUtil {
	
	public static Map<String,JsonPicker> initJsonPickers(){

		InputStream is = RegisterUtil.class.getResourceAsStream("/rule-picker.properties");
		Properties p = new Properties();
		Map<String,JsonPicker> pickers = new HashMap<String,JsonPicker>();
		try {
			p.load(is);
			Enumeration ks= p.keys();
			
			while(ks.hasMoreElements()){
				String curKey = ks.nextElement().toString();
				String pickerClz = Constant.PARSE_PACK;
				pickerClz += p.getProperty(curKey);
				
				Class cnstClz = Class.forName(Constant.CONSTANT_CLASS);
				JsonPicker picker = (JsonPicker)Class.forName(pickerClz).newInstance();
				Field field = cnstClz.getDeclaredField(curKey);
				String rule = field.get(cnstClz).toString();
				pickers.put(rule, picker);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pickers;
	
	}
	

}
