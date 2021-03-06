package com.slf.crud.sys;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.slf.crud.util.JSONUtil;

@SuppressWarnings("all")
public class Dom4jUtil {
	
	public static void main(String[] args) {
//		Dom4jUtil.initSysDic();
//		LinkedHashMap<String, String> industry = SysDicUtil.getAllFields("project", "proType");
//		
//		System.out.println(industry);
//		
//		System.out.println(SysDicUtil.getFieldDesc("project", "proType","01"));
		
		Dom4jUtil.initSysDics();
		System.out.println("测试一下数据。。");
		LinkedHashMap<String, String> industry = SysDicUtil.getAllFields("project", "proType");

		System.out.println(JSONUtil.toJson(industry));
		System.out.println(SysDicUtil.getFieldDesc("project", "proType","01"));
		//System.out.println(SysDicUtil.getFieldDesc("test", "type","01"));
		
		
		System.out.println("--------------------测试一下表的所有数据字典");
		//System.out.println(SysDicUtil.getAllFields("project"));
		
		//System.out.println(JSONUtil.toJson(SysDicUtil.getAllFields("monthTj")));
	}
	/**
	 * 初始化数据字典
	 */
	public static void initSysDic() {
		try {
			SAXReader reader = new SAXReader();
			Document document =reader.read(Dom4jUtil.class.getResourceAsStream("/sys/data-dic.xml"));
//			Document document = reader.read(new File(
//					"src/main/resources/system/sysDic.xml"));

			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> sysDic = buildSysDic(document);

			SysDicUtil.setSysDic(sysDic);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据字典
	 */
	public static void initSysDics() {
		try {
			URL url = Dom4jUtil.class.getResource("/sys");
			String file = url.getFile();
			System.out.println("file = "+file);
			File sysDir = new File(file);
			File[] xmls = sysDir.listFiles();
			
			for(File xml:xmls){
				
				System.out.println("xml路径："+xml.getPath());
			}
			//SAXReader reader = new SAXReader();
			//Document document =reader.read(Dom4jUtil.class.getResourceAsStream("/sys/data-dic.xml"));
//			Document document = reader.read(new File(
//					"src/main/resources/system/sysDic.xml"));

			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> sysDic = buildSysDics(sysDir);

			SysDicUtil.setSysDic(sysDic);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> buildSysDic(Document document) {
		String xpath = "dics/table";

		
		List<Node> tables = document.selectNodes(xpath);

		return handleTables(tables);
	}
	/**
	 * 添加所有的数据字典
	 * @param document
	 * @return
	 */
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> buildSysDics(File sysDir)throws Exception {
		
		File[] xmls = sysDir.listFiles();
		List<Node> tables = new ArrayList<Node>();
		for(File xml:xmls){
			
			System.out.println("xml路径："+xml.getPath());
			SAXReader reader = new SAXReader();
			Document document = reader.read(xml);
			String xpath = "dics/table";
			tables.add((Node)document.selectNodes(xpath).get(0)) ;
		}
		
		return handleTables(tables);
	}

	
    /**
	 * 处理一组table
	 * 
	 * @param tables
	 * @return
	 */
	
	private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> handleTables(
			List<Node> tables) {
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> tableMap = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>();

		Node table = null;
		String id = null;
		LinkedHashMap<String, LinkedHashMap<String, String>> field = null;
		for (int i = 0, len = tables.size(); i < len; i++) {
			table = tables.get(i);

			id = table.selectSingleNode("@id").getText();
			System.out.println(id);
			
			field = handleFields(table.selectNodes("field"));

			tableMap.put(id, field);
		}
		return tableMap;
	}


/**
	 * 处理一组field
	 * 
	 * @param fields
	 * @return
	 */
	private static LinkedHashMap<String, LinkedHashMap<String, String>> handleFields(
			List<Node> fields) {
		LinkedHashMap<String, LinkedHashMap<String, String>> fieldMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		Node field = null;
		String id = null;
		LinkedHashMap<String, String> row = null;
		for (int i = 0, len = fields.size(); i < len; i++) {
			field = fields.get(i);

			id = field.selectSingleNode("@id").getText();
			System.out.println(id);
			row = handleRows(field.selectNodes("row"));

			fieldMap.put(id, row);

		}

		return fieldMap;
	}


/**
	 * 处理一组row
	 * 
	 * @param rows
	 * @return
	 */
	private static LinkedHashMap<String, String> handleRows(List<Node> rows) {
		LinkedHashMap<String, String> rowMap = new LinkedHashMap<String, String>();

		Node row = null;
		String key = null;
		String value = null;
		for (int i = 0, len = rows.size(); i < len; i++) {
			row = rows.get(i);

			key = row.selectSingleNode("value").getText();
			value = row.selectSingleNode("desc").getText();
			rowMap.put(key, value);

			System.out.println(key + "  " + value);
		}

		return rowMap;
	}
}
