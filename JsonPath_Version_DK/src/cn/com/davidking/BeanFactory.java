package cn.com.davidking;

import cn.com.davidking.json.JsonToolKit;
/**
 * 
 * @author daikai
 *
 */
public final class BeanFactory {
	public static JsonToolKit newJsonToolKit(){
		return new JsonToolKit();
	}
}
