package cn.com.davidking.poi;

import java.io.IOException;
import java.util.List;

public interface ExlUtilApi {

	/**
	 * read the Excel file
	 * @param path the path of the Excel file
	 * @return
	 * @throws IOException
	 */
	<T> List<T> readExl(String path, List<String> orderFields, Class<T> clz, boolean isClsPath) throws Exception;

	/**
	 * Write the Excel 2010
	 * @param path the path of the excel file
	 * @return
	 * @throws IOException
	 */
	<T> void writeXlsx(String path, boolean needHead, List<String> headers, List<String> orderFields, Class<T> clz,
			List<T> datas);

	/**
	 * Write the Excel 2003-2007
	 * @param path the path of the Excel
	 * @return
	 * @throws IOException
	 */
	<T> void writeXls(String path, boolean needHead, List<String> headers, List<String> orderFields, Class<T> clz,
			List<T> datas) throws Exception;

	<T> List<T> readXlsx(String path, List<String> orderFields, Class<T> clz) throws Exception;

	/**
	 * Read the Excel 2010
	 * @param path the path of the excel file
	 * @return
	 * @throws IOException
	 */
	<T> List<T> readXlsx(String path, List<String> orderFields, Class<T> clz, boolean isClsPath) throws Exception;

	/***
	 * Read the Excel 2003-2007
	 * @param path
	 * @param orderFields
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	<T> List<T> readXls(String path, List<String> orderFields, Class<T> clz) throws Exception;

	/**
	 * Read the Excel 2003-2007
	 * @param path the path of the Excel
	 * @return
	 * @throws IOException
	 */
	<T> List<T> readXls(String path, List<String> orderFields, Class<T> clz, boolean isClsPath) throws Exception;

}