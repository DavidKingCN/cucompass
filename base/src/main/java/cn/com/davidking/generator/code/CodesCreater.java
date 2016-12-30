package cn.com.davidking.generator.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.Consts;

import cn.com.davidking.http.URLTool;
import cn.com.davidking.util.ConstantUtil;
import cn.com.davidking.util.FileUtil;
import cn.com.davidking.util.MatchUtils;
import cn.com.davidking.util.StringUtil;

public class CodesCreater {
	
	
	public static void main(String[] args) {
//		testGenerateStJsonCodes();
//		testGenerateStCodes();
//		testGenerateNdCodes();
//		testGenerateNdCreditBlackCodes();
		entry("credit","credit.basic",ParseType.NdXpath,"http://www.creditchina.gov.cn");
	}
	
	public static void entry(String module , String aimKey , ParseType type , String website){
		if(type.getLevel().equals("st")){
			generateStCodes(module , aimKey , type , website);
		} else if (type.getLevel().equals("nd")){
			generateNdCodes(module , aimKey , type , website);
		} else {
			
		}
			
	}
	
	public static void testGenerateStJsonCodes(){
		String module  = "credit";
		String aimKey  = "publiInfo";
		ParseType type = ParseType.StJson;
		String website = "http://www.creditchina.gov.cn";
		generateStCodes(module , aimKey , type , website);
	}
	// test generate first level list page crawl codes..
	public static void testGenerateStXpathCodes(){
		String module  = "test";
		String aimKey  = "movie";
		ParseType type = ParseType.StXpath;
		String website = "http://www.baidu.com";
		generateStCodes(module , aimKey , type , website);
	}
	
	public static void testGenerateNdCreditBlackCodes(){
		String module  = "credit";
		String aimKey  = "credit.black";
		ParseType type = ParseType.NdXpath;
		String website = "http://www.creditchina.gov.cn";
		//要算出
		//XXXXListHelper.java 类文件内的XXXXListHelper 以及类文件/template/nd/xpath/XXXX-list.xml XXXX由aimKey替换
		//NdXpathTemp 改为aimKey 大写后拼接SqlsCreater 生成类名 类内其他处替换
		//WEBSITE 替换
		//package 相对路径 template.st.xpath 替换为 模块名称
		generateNdCodes(module , aimKey , type , website);
	}
	// test generate second level list page crawl codes..
	public static void testGenerateNdCodes(){
		String module  = "dianying";
		String aimKey  = "movie";
		ParseType type = ParseType.NdXpath;
		String website = "http://www.dianying.com";
		//要算出
		//XXXXListHelper.java 类文件内的XXXXListHelper 以及类文件/template/nd/xpath/XXXX-list.xml XXXX由aimKey替换
		//NdXpathTemp 改为aimKey 大写后拼接SqlsCreater 生成类名 类内其他处替换
		//WEBSITE 替换
		//package 相对路径 template.st.xpath 替换为 模块名称
		generateNdCodes(module , aimKey , type , website);
	}

	
	
	public static void generateStCodes(String module,String aimKey,ParseType type,String website){
		
		String path = ConstantUtil.PREFIX_WORK_DIR+ConstantUtil.SLASH+module;
		String aimAllName = StringUtil.upperFirstCapital(aimKey)+ConstantUtil.TARGET_NAME_PART+ConstantUtil.DOT+ConstantUtil.JAVA_FILE_SUFFIX_NAME;
		
		String aimName = aimAllName.substring(0, aimAllName.indexOf(ConstantUtil.DOT));
		
		File aimFile = null;
		try {
			aimFile = FileUtil.delAndNewFile(path,aimAllName);
		} catch (Exception e1) { System.out.println(e1); return;}
		
		//src java file
		///cn/com/davidking/extract
//		File srcFile = new File(ConstantUtil.PREFIX_WORK_DIR+type.getTemplateRelativePath());
//		String relativePath = "/cn/com/davidking/extract"+type.getTemplateRelativePath();
//		System.out.println(relativePath);
		String srcFilePath = CodesCreater.class.getResource("/cn/com/davidking/extract"+type.getTemplateRelativePath()).getPath();
		
		System.out.println(srcFilePath);
		
		BufferedReader reader=null;
		BufferedWriter writer = null;
		if(srcFilePath!=null && !srcFilePath.equals("")){
			String line = "";
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFilePath)),Consts.UTF_8.displayName()));
				
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aimFile),Consts.UTF_8.displayName()));
				while((line=reader.readLine())!=null){
					System.out.println(line);
					//must
					if(line.contains("package")){
						line = line.replace(type.getPackagePath(), module);
					}
					//option
					if(line.contains(type.getTemplateFileName())){
						line = line.replace(type.getTemplateFileName(), aimName);
					}
					//option
					if(line.contains(type.getTemplateDir())){
						line = line.replace(type.getXmlPath(), ConstantUtil.SLASH+"conf"+ConstantUtil.SLASH+module+ConstantUtil.SLASH+aimKey+ConstantUtil.PARSE_SQLS_XML);
					}
					//must
					if(line.contains("WEBSITE")){
						String target = MatchUtils.getOnlyMatchs(line, URLTool.DOMAIN_NAME_REG_EXP);
						line = line.replace(target, website);
					}
					writer.write(line);
					writer.newLine();
				}
			} catch (Exception e) {
			} finally{
				if(reader!=null)try{reader.close();}catch(Exception e){}
				if(writer!=null)try{writer.close();}catch(Exception e){}
			}
		}
	
	}
	
	
	public static void generateNdCodes(String module,String aimKey,ParseType type,String website){
		String[]keys = aimKey.split("\\.");
		String preKey = keys[0];//star
		String sufKey = keys[1];//movie
		
		
		String className =  StringUtil.upperFirstCapital(sufKey)+StringUtil.upperFirstCapital(preKey) + ConstantUtil.LIST_HELPER;
		
		String classFile = className + ConstantUtil.DOT + ConstantUtil.JAVA_FILE_SUFFIX_NAME;
		String path = ConstantUtil.PREFIX_WORK_DIR + ConstantUtil.SLASH + module;
		
		String createrClzName = StringUtil.upperFirstCapital(sufKey) + ConstantUtil.TARGET_NAME_PART;
		
		File aimFile = null;
		try {
			aimFile = FileUtil.delAndNewFile(path , classFile);
		} catch (Exception e1) { System.out.println(e1); return;}
		
		//src java file
//		File srcFile = new File(ConstantUtil.PREFIX_WORK_DIR + type.getTemplateDir() + ConstantUtil.SLASH + "XXXX" + ConstantUtil.LIST_HELPER + ConstantUtil.DOT + ConstantUtil.JAVA_FILE_SUFFIX_NAME);
		String srcFilePath = "/cn/com/davidking/extract"+type.getTemplateDir() + ConstantUtil.SLASH + "XXXX" + ConstantUtil.LIST_HELPER + ConstantUtil.DOT + ConstantUtil.JAVA_FILE_SUFFIX_NAME;
		BufferedReader reader=null;
		BufferedWriter writer = null;
		if(srcFilePath!=null && !srcFilePath.equals("")){
			String line = "";
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFilePath)),Consts.UTF_8.displayName()));
				
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aimFile),Consts.UTF_8.displayName()));
				while((line=reader.readLine())!=null){
					System.out.println(line);
					
					if(line.contains("package")){
						line = line.replace(type.getPackagePath(), module);
					}
					if(line.contains("XXXX")){
						line = line.replace("XXXX", StringUtil.upperFirstCapital(sufKey)+StringUtil.upperFirstCapital(preKey));
					}
					
					if(line.contains(type.getTemplateFileName())){
						line = line.replace(type.getTemplateFileName(), createrClzName);
					}
					if(line.contains(type.getXmlPath())){
						line = line.replace(type.getXmlPath(), ConstantUtil.SLASH+"conf"+ConstantUtil.SLASH+module+ConstantUtil.SLASH+sufKey+ConstantUtil.PARSE_SQLS_XML);
					}
					//option
					if(line.contains(type.getTemplateDir()+"/XX-list.xml")){
						line = line.replace(type.getTemplateDir()+"/XX-list.xml", ConstantUtil.SLASH+"conf"+ConstantUtil.SLASH+module+ConstantUtil.SLASH+preKey+"-list.xml");
					}
					//must
					if(line.contains("WEBSITE")){
						String target = MatchUtils.getOnlyMatchs(line, URLTool.DOMAIN_NAME_REG_EXP);
						line = line.replace(target, website);
					}
					writer.write(line);
					writer.newLine();
				}
			} catch (Exception e) {
			} finally{
				if(reader!=null)try{reader.close();}catch(Exception e){}
				if(writer!=null)try{writer.close();}catch(Exception e){}
			}
		}
	
	}
}
