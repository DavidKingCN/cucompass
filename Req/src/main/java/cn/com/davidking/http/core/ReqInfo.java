package cn.com.davidking.http.core;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

/**
 * 请求包裹的参数信息
 * @author daikai
 *
 */
public class ReqInfo {
	private String url;						//url
	private String charSet = "utf-8"; 		//default 字符集 也可以指定
	List<Header> reqheaders;				//请求头信息
	private Map<String,String> mapArgs ;	//map参数
	private List<NameValuePair> pairArgs;	//name-value 参数
	private String jsonArgs;				//json参数
	private String textArgs;				//文本参数
	private InputStream steamArgs;			//字节流
	List<Header> respheaders;				//相应头
	
	private ReqArgType regArgType;			//请求类型
	
	//没有请求头和相应头也不用传递参数，字符集也是默认的
	public ReqInfo(String url) {
		super();
		this.url = url;
	}

	//没有参数但要指定字符集
	public ReqInfo(String url, String charSet) {
		super();
		this.url = url;
		this.charSet = charSet;
	}

	//默认的字符集但是需要传递参数
	public ReqInfo(String url, ReqArgType regArgType) {
		super();
		this.url = url;
		this.regArgType = regArgType;
	}

	//没有请求头、相应头 但是要指定字符集及参数类型
	public ReqInfo(String url, String charSet, ReqArgType regArgType) {
		super();
		this.url = url;
		this.charSet = charSet;
		this.regArgType = regArgType;
	}

	public ReqInfo(String url, String charSet, List<Header> reqheaders, List<Header> respheaders,
			ReqArgType regArgType) {
		super();
		this.url = url;
		this.charSet = charSet;
		this.reqheaders = reqheaders;
		this.respheaders = respheaders;
		this.regArgType = regArgType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public List<Header> getReqheaders() {
		return reqheaders;
	}

	public void setReqheaders(List<Header> reqheaders) {
		this.reqheaders = reqheaders;
	}

	public Map<String, String> getMapArgs() {
		return mapArgs;
	}

	public void setMapArgs(Map<String, String> mapArgs) {
		this.mapArgs = mapArgs;
	}

	public List<NameValuePair> getPairArgs() {
		return pairArgs;
	}

	public void setPairArgs(List<NameValuePair> pairArgs) {
		this.pairArgs = pairArgs;
	}

	public String getJsonArgs() {
		return jsonArgs;
	}

	public void setJsonArgs(String jsonArgs) {
		this.jsonArgs = jsonArgs;
	}

	public String getTextArgs() {
		return textArgs;
	}

	public void setTextArgs(String textArgs) {
		this.textArgs = textArgs;
	}

	public InputStream getSteamArgs() {
		return steamArgs;
	}

	public void setSteamArgs(InputStream steamArgs) {
		this.steamArgs = steamArgs;
	}

	public List<Header> getRespheaders() {
		return respheaders;
	}

	public void setRespheaders(List<Header> respheaders) {
		this.respheaders = respheaders;
	}

	public ReqArgType getRegArgType() {
		return regArgType;
	}

	public void setRegArgType(ReqArgType regArgType) {
		this.regArgType = regArgType;
	}
	//请求参数类型
	public static enum ReqArgType{
		none(-1),
		mapArgs(0),
		pairArgs(1),
		jsonArgs(2),
		textArgs(3),
		steamArgs(4);
		private int slot;

		private ReqArgType(int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return slot;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}
		
		
	}
}
