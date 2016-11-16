/*
 *    功能名称   ： Json Query 2.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

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
	private static final Logger LOG = Logger.getLogger(FileUtil.class);

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
				LOG.error(e.getMessage());
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
			LOG.error(e.getMessage());
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

}


