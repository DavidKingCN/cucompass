package cn.com.davidking.generator.code;

import cn.com.davidking.util.ConstantUtil;
import cn.com.davidking.util.StringUtil;
/**
 * 
 * @author daikai
 *
 */
public enum ParseType {
	
	StJson ("st","json"), 
	StXpath("st","xpath"),
	NdJson ("nd","json"), 
	NdXpath("nd","xpath"),
	RdJson ("rd","json"), 
	RdXpath("rd","xpath");
	
	private String level;
	private String type;
	private ParseType(String level, String type) {
		this.level = level;
		this.type = type;
	}
	
	public String getLevel(){
		return this.level;
	}
	
	public String getType(){
		return this.type;
	}
	public String getPackagePath(){
		return ConstantUtil.TEMPLATE_DIR + ConstantUtil.DOT + this.level + ConstantUtil.DOT + this.type;
	}
	public String getTemplateDir(){
		
		return  ConstantUtil.SLASH + ConstantUtil.TEMPLATE_DIR + ConstantUtil.SLASH + this.level + ConstantUtil.SLASH + this.type;
	}
	//得到xml文件路径
	public String getXmlPath(){
		return getTemplateDir() + ConstantUtil.SLASH + ConstantUtil.TEMPLATE_DIR + ConstantUtil.DOT +ConstantUtil.XML_FILE_SUFFIX_NAME;
	}
	//得到模板文件名称
	public String getTemplateFileName(){
		return  StringUtil.upperFirstCapital(this.level) + StringUtil.upperFirstCapital(type) + ConstantUtil.TEMPLATE_NAME_PART;
	}
	//得到模板全名
	public String getTemplateAllName(){
		return  ConstantUtil.SLASH + getTemplateFileName() + ConstantUtil.DOT + ConstantUtil.JAVA_FILE_SUFFIX_NAME;
	}
	//得到模板相对路径
	public String getTemplateRelativePath(){
		return  this.getTemplateDir()+getTemplateAllName();
	}
	
	public static void main(String[] args) {
		testParseType(ParseType.NdXpath);
	}
	
	public static void testParseType(ParseType type){
		System.out.println("包的相对路径                                            <> "+type.getPackagePath());
		System.out.println("模板类(或模板配置文件)的相对路径 <> "+type.getTemplateDir());
		System.out.println("模板类的类名                                            <> "+type.getTemplateFileName());
		System.out.println("模板类相对路径                                        <> "+type.getTemplateRelativePath());
		System.out.println("模板xml配置文件相对路径                  <> "+type.getXmlPath());
	}
}
