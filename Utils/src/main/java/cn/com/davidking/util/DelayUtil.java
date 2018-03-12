package cn.com.davidking.util;

public final class DelayUtil {
	public static void delayOneSeconds(){
		delay(1000);
	}
	
	
	public static void delay(long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ingore) {}
	}
}
