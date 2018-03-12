/*
 *    功能名称   ： 工具类 封裝实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.CharsetNames;
import org.apache.log4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * 对文件操作的类.
 *
 * @author daikai
 */
@SuppressWarnings("all")
public class FileUtil {
	
	/** The Constant LOG. */
//	private static final Logger LOG = Logger.getLogger(FileUtil.class);
	public static String dir = "";
	static{
		InputStream is = FileUtil.class.getResourceAsStream("/jpg.properties");
		Properties p = new Properties();
		try {
			p.load(is);	
			dir = p.get("dir").toString();
		}catch(Exception e){}
		finally {
			try {is.close();} catch (Exception ignore) {}
		}
	}
	
	public static final String SLASH = "\\";
	public static final String BACK_SLANT = "/";
	/***
	 * 将斜杠改为反斜杠
	 * @param path
	 * @return
	 */
	private static String slashToBackSlant(String path){
		return path.replaceAll(SLASH, BACK_SLANT);
	}

	public static String backSlash(String path){
		return path.replace(SLASH, BACK_SLANT);
	}
	/**
	 * 通过路径获取文件名
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		return path.substring(path.lastIndexOf(BACK_SLANT)+1, path.length());
	}

	// 检测是否存在目录
	/**
	 * Exist directory.
	 *
	 * @param path the path
	 * @return true, if exist directory
	 */
	public static boolean existDirectory(String path) {
		return new File(path).exists();
	}

	// 检测是否存在制定文件
	/**
	 * Exist file.
	 *
	 * @param path the path
	 * @param fileName the file name
	 * @return true, if exist file
	 */
	public static boolean existFile(String path, String fileName) {
		return new File(path + File.separator + fileName).exists();
	}
	/***
	 * 
	 * @param pathPath
	 * @return
	 */
	public static boolean existFile(String pathPath) {
		return new File(pathPath).exists();
	}

	// 创建全路径
	/**
	 * Creates the directory.
	 *
	 * @param path the path
	 */
	public static void createDirectory(String path) {
		File file = new File(path);
		file.mkdirs();
	}

	public static File delAndNewFile(String path, String fileName) throws Exception {
		if(!existDirectory(path))
			createDirectory(path);
		if(existFile(path,fileName))
			deleteFile(path,fileName);
		File file = new File(path + File.separator + fileName);
		file.createNewFile();
		return file;
	}
	public static void newFile(String path, String fileName) throws Exception {
		if(!existDirectory(path))
			createDirectory(path);
		File file = new File(path + File.separator + fileName);
		file.createNewFile();
	}
	// 删除指定文件
	/**
	 * Delete file.
	 *
	 * @param path the path
	 * @param fileName the file name
	 */
	public static void deleteFile(String path, String fileName) {
		File file = new File(path + File.separator + fileName);
		file.delete();
	}

	/**
	 * Append buffered data.
	 *
	 * @param fileName the file name
	 * @param data the data
	 */
	public static void appendBufferedData(String fileName, String data) {
		nioWriteFile(fileName, data, false, false);
	}

	/**
	 * Nio write file.
	 *
	 * @param fileName the file name
	 * @param data the data
	 * @param append the append
	 * @param newLine the new line
	 */
	public static void nioWriteFile(String fileName, String data, boolean append, boolean newLine) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
//				LOG.error(e.getMessage());
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		// true:表示是追加的标志
		try {
			if (append)
				fw = new FileWriter(file, true);
			else
				fw = new FileWriter(file, false);

			bw = new BufferedWriter(fw);

			// 开始输出写入文件
			if (newLine)
				bw.newLine();
			fw.write(data);
			bw.close();
			fw.close();
		} catch (IOException e) {
//			LOG.error(e.getMessage());
		}
	}
	
	

	/**
	 * 得到文本所有的字节数.
	 *
	 * @param filePath the file path
	 * @return the file bytes
	 */
	private static int getFileBytes(String filePath) {
		int bytes = 0;
		try {

			File file = new File(filePath);

			if (file.exists()) {// 文件存在

				RandomAccessFile accessFile = new RandomAccessFile(file, "r");// 只赋予读的权限
				String line = "";
				while (null != (line = accessFile.readLine())) {
					bytes += line.getBytes().length;
				}
				accessFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
	/**
	 * Read file all load.
	 *
	 * @param file the file
	 * @return the string
	 */
	public static String readFileAllLoad(String file){
		return nioReadFile(file,"UTF-8",getFileBytes(file),true);
	}
	/**
	 * Read file all load.
	 *
	 * @param file the file
	 * @param charset the charset
	 * @return the string
	 */
	public static String readFileAllLoad(String file,String charset){
		return nioReadFile(file,charset,getFileBytes(file),true);
	}
	
	/**
	 * Nio read file.
	 *
	 * @param file the file
	 * @param charSet the char set
	 * @param bytes the bytes
	 * @param allLoad the all load
	 * @return the string
	 */
	public static String nioReadFile(String file,String charSet,int bytes,boolean allLoad) {
		
		
		if(allLoad){
			bytes += 2;
		}else{
			bytes = 1024;
		}
		StringBuffer sb = new StringBuffer();

		try {
			FileChannel in = new FileInputStream(new File(file)).getChannel();

			ByteBuffer buffer = ByteBuffer.allocate(bytes);
			while (in.read(buffer) != -1) {
				buffer.flip();
				sb.append(byteBufferToString(buffer,charSet));
				buffer.clear();
			}
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
		
	}
	
	/**
	 * Nio read file.
	 *
	 * @param file the file
	 * @return the string
	 */
	public static String nioReadFile(String file) {
		
		return nioReadFile(file,"UTF-8",0,false);
	}
	
	/**
	 * Byte buffer to string.
	 *
	 * @param buffer the buffer
	 * @param charSet the char set
	 * @return the string
	 */
	public static String byteBufferToString(ByteBuffer buffer,String charSet) {
		CharBuffer charBuffer = null;
		try {
			Charset charset = Charset.forName(charSet);
			CharsetDecoder decoder = charset.newDecoder();
			charBuffer = decoder.decode(buffer);
			buffer.flip();
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Byte buffer to string.
	 *
	 * @param buffer the buffer
	 * @return the string
	 */
	public static String byteBufferToString(ByteBuffer buffer) {
		return byteBufferToString(buffer,"UTF-8");
	}

	public static String readFile(String filepath) {
		StringBuffer sb = new StringBuffer("");
		InputStream is=null;BufferedReader reader=null;
		try {
			is=FileUtil.class.getResourceAsStream(filepath);
			String line; 
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line=reader.readLine()) != null)  
				sb.append(line); 
			
		} catch (Exception ignore) {}
		finally{
				try {
					reader.close();is.close();
				} catch (IOException ignore) {}
		}
		return sb.toString();
	}
	
	
	public static void writeJpgToDisk(InputStream is,String file){
        try {
        	BufferedImage image=ImageIO.read(is); 
        	File d = new File(dir);
        	if(!d.exists()) d.mkdirs();
			ImageIO.write(image, "jpg", new File(dir+file));
		} catch (Exception ignore) {}
	}
	
	public static void writeJpgToDisk(InputStream is,String category,String file){
        try {
        	BufferedImage image=ImageIO.read(is); 
        	String curDir = dir+category+"/";
        	File d = new File(curDir);
        	if(!d.exists()) d.mkdirs();
			ImageIO.write(image, "jpg", new File(curDir+file));
		} catch (Exception ignore) {}
	}
	
	public static void main(String[] args) {
		String path="/conf/j_d/data.html";
		String content = FileUtil.readFile(path);
		
		System.out.println(content);
	}
}



