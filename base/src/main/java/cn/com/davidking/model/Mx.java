/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
// TODO: Auto-generated Javadoc

/**
 * The Class Mx.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mx")
public class Mx {
	
	/** The mz. */
	@XmlAttribute(name="mz")
	private String mz;			//类的完整路径([a-zA-Z$_]+\\.?)*
	
	/** The base url. */
	@XmlAttribute(name="baseUrl")
	private String baseUrl;		//基准的url 参数格式 $page$ $arg1$
	
	/** The split key. */
	@XmlAttribute(name="splitKey")
	private String splitKey;	//分割的键名
	
	/** The path. */
	@XmlAttribute(name="path")
	private String path;		//jsonpath的路径，一般是$.*.aList[*]
	
	/** The pagesize. */
	@XmlAttribute(name="pagesize")
	private int pagesize=10 ;   //分页的每页的页码数，正整书 填充一页的条目数
	
	/** The page. */
	@XmlAttribute(name="page")
	private int page=1;		//当前页码，正整数 填充当前页码
	
	/** The args len. */
	@XmlAttribute(name="argsLen")
	private int argsLen=0;		//参数个数 参数以此为$arg0$,$arg1$,$arg2$,$arg3$,$arg4$...
	
	/** The sffy. */
	@XmlAttribute(name="sffy")
	private boolean sffy;
	
	/** The qqfs. */
	@XmlAttribute(name="qqfs")
	private String qqfs;
	
	/** The page path. */
	@XmlAttribute(name="pagePath")
	private String pagePath;	//分页的jsonPath路径，格式$.[a-zA-Z$_]+
	
	/** The zds. */
	@XmlElementWrapper(name = "zds")
	@XmlElement(name = "zd")
	private List<Zd> zds =new ArrayList<Zd>();

	/**
	 * Gets the mz.
	 *
	 * @return the mz
	 */
	public String getMz() {
		return mz;
	}
	
	/**
	 * Sets the mz.
	 *
	 * @param mz the mz
	 */
	public void setMz(String mz) {
		this.mz = mz;
	}

	/**
	 * Gets the base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Sets the base url.
	 *
	 * @param baseUrl the base url
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the split key.
	 *
	 * @return the split key
	 */
	public String getSplitKey() {
		return splitKey;
	}
	
	/**
	 * Sets the split key.
	 *
	 * @param splitKey the split key
	 */
	public void setSplitKey(String splitKey) {
		this.splitKey = splitKey;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param path the path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the pagesize.
	 *
	 * @return the pagesize
	 */
	public int getPagesize() {
		return pagesize;
	}
	
	/**
	 * Sets the pagesize.
	 *
	 * @param pagesize the pagesize
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * Sets the page.
	 *
	 * @param page the page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Gets the page path.
	 *
	 * @return the page path
	 */
	public String getPagePath() {
		return pagePath;
	}
	
	/**
	 * Sets the page path.
	 *
	 * @param pagePath the page path
	 */
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	
	/**
	 * Gets the zds.
	 *
	 * @return the zds
	 */
	public List<Zd> getZds() {
		return zds;
	}
	
	/**
	 * Sets the zds.
	 *
	 * @param zds the zds
	 */
	public void setZds(List<Zd> zds) {
		this.zds = zds;
	}
	
	/**
	 * Gets the args len.
	 *
	 * @return the args len
	 */
	public int getArgsLen() {
		return argsLen;
	}

	/**
	 * Sets the args len.
	 *
	 * @param argsLen the args len
	 */
	public void setArgsLen(int argsLen) {
		this.argsLen = argsLen;
	}

	/**
	 * Checks if is sffy.
	 *
	 * @return true, if checks if is sffy
	 */
	public boolean isSffy() {
		return sffy;
	}

	/**
	 * Sets the sffy.
	 *
	 * @param sffy the sffy
	 */
	public void setSffy(boolean sffy) {
		this.sffy = sffy;
	}
	
	

	/**
	 * Gets the qqfs.
	 *
	 * @return the qqfs
	 */
	public String getQqfs() {
		return qqfs;
	}

	/**
	 * Sets the qqfs.
	 *
	 * @param qqfs the qqfs
	 */
	public void setQqfs(String qqfs) {
		this.qqfs = qqfs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mx [mz=" + mz + ", baseUrl=" + baseUrl + ", splitKey=" + splitKey + ", path=" + path + ", pagesize="
				+ pagesize + ", page=" + page + ", argsLen=" + argsLen + ", sffy=" + sffy + ", pagePath=" + pagePath
				+ ", zds=" + zds + "]";
	}

	
	
	
}
