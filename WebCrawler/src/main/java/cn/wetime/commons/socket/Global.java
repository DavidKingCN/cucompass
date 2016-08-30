/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.commons.socket;

import java.util.Timer;
import java.util.TimerTask;

public class Global {
	
	public static int globalConstant = 0;
	private static Global global = new Global(); 
	
	private Global(){
		new Timer().schedule(new Counter(), 0, 5000);
	}
	
	public static Global newInstance(){
		if(global==null)
			global = new Global();
		return global;
	}
	
	public static void main(String[] args) {
		while(true){
			System.out.println(Global.newInstance().globalConstant);
		}
	}
}

class Counter extends TimerTask{
	
	public Counter() {
	}



	@Override
	public void run() {
		synchronized (this) {
			Global.globalConstant++;
		}
	}
	
}
