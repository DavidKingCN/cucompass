/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.util;

import java.io.File;
import java.io.IOException;

/**
 * 对文件操作的类
 * @author daikai
 *
 */
@SuppressWarnings("all")
public class FileUtil {
	//检测是否存在目录
	public static boolean existDirectory(String path){
		return new File(path).exists();
	}
	//检测是否存在制定文件
	public static boolean existFile(String path,String fileName){
		return new File(path+File.separator+fileName).exists();
	}
	//创建全路径
	public static void createDirectory(String path){
		File file = new File(path);
		file.mkdirs();
	}
	//删除指定文件
	public static void deleteFile(String path ,String fileName){
		File file = new File(path+File.separator+fileName);
		file.delete();
	}
	public static void main(String[] args) {
		System.out.println(existDirectory("F:\\test\\1"));
		String path = "F:\\test\\1";
		String fileName = "test.html";
		if(existDirectory(path+File.separator+fileName)){
			deleteFile(path,fileName);
		}else{
			System.out.println("not exist...");
		}
	}
}
