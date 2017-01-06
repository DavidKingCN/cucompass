/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.generator.conf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;

import cn.com.davidking.BeanFactory;
import cn.com.davidking.model.Mx;
import cn.com.davidking.model.Zd;
import cn.com.davidking.util.FileUtil;
import cn.com.davidking.util.JaxbUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class TestMakeConfs.
 */
@SuppressWarnings("all")
public class MakeConf {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		createConf("CnCredit","credit_list","credit","credit");
	}

	
	
	public static void createConf(Map<String,Object> args){
		
		
		String sck=args.get("sck")==null?"test":args.get("sck").toString();
		String tab=args.get("tab")==null?"test":args.get("tab").toString();
		String module=args.get("module")==null?"test":args.get("module").toString();
		String confKey=args.get("confKey")==null?"test":args.get("confKey").toString();
		
		List<String> autoZdms = args.get("autos")==null?new ArrayList():(List<String>)args.get("autos");
		
		
		
		String schema = sck;
		String tabName = tab;
		
		List<String> cols = BeanFactory.newDBMetaHelper().getTblCols(schema, tabName);
		
		Mx mx = new Mx();
		mx.setMz(schema+"."+tabName);
		mx.setBaseUrl("a");
		mx.setSplitKey("body");
		mx.setPath("");
		mx.setPagePath("");
		mx.setArgsLen(1);
		mx.setQqfs("get");
		final List<Zd> zds = new ArrayList<Zd>();
		cols.forEach(k->{
			boolean isAuto = false;
			Zd zd = null;
			if(!k.equals("id")){
				for(String autoZdm:autoZdms){
					if(k.equals(autoZdm))
						isAuto = true;
				}
				zd = new Zd();
				zd.setName(k);
				if(isAuto){
					zd.setAuto(true);
					isAuto = false;
				}else{
					zd.setPath("");
					zd.setJl(true);
				}
			}
			zds.add(zd);
		});
		
		mx.setZds(zds);
		
		String xmlStr = JaxbUtil.convertToXml(mx, Consts.UTF_8.displayName());
		
		System.out.println(xmlStr);
		String fileName = confKey+"-parse-sqls.xml";
		File file = new File("src/main/resources/conf/"+module+"/");
		
		if(!file.exists())
			try {
				file.mkdirs();
			} catch (Exception ignore) {}
		file = new File(file.getAbsoluteFile()+"/"+fileName);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException ignore) {}
		
		System.out.println(file.getAbsolutePath());
		
		FileUtil.nioWriteFile(file.getAbsolutePath(), xmlStr, false, false);
	}
	
	public static void createConf(String sck,String tab,String module,String confKey){
		Map<String,Object> arg = new HashMap<>();
		
		arg.put("sck", sck);
		arg.put("tab", tab);
		arg.put("module", module);
		arg.put("confKey", confKey);
		
		
		List<String> autos = new ArrayList<>();
		autos.add("url");
		autos.add("website");
		autos.add("crawlTime");
		
		arg.put("autos", autos);
		
		MakeConf.createConf(arg);
	}
}
