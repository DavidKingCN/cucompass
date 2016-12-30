/*
 *    功能名称   ： 扒取实现1.0
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Zd.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "zd")
public class Zd {
	
	/** The name. */
	@XmlAttribute(name="name")
	private String name;	//字段名称
	
	/** The path. */
	@XmlAttribute(name="path")
	private String path;	//字段的路径 规定为json的叶子节点[a-zA-Z$_]+
	
	/** The num. */
	@XmlAttribute(name="num")
	private String num;
	
	/** The jl. */
	@XmlAttribute(name="jl")
	private boolean jl;		//级联默认为 false 指定为true 则 模型的path.字段mz 得到该jsonpath的路径
	
	/** The auto. */
	@XmlAttribute(name="auto")
	private boolean auto;	//自动填充值默认为false 指定true 则值有外部带入，组对象均为该值
	
	@XmlAttribute(name="attr")
	private String attr;


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets the num.
	 *
	 * @return the num
	 */
	public String getNum() {
		return num;
	}


	/**
	 * Sets the num.
	 *
	 * @param num the num
	 */
	public void setNum(String num) {
		this.num = num;
	}


	/**
	 * Checks if is jl.
	 *
	 * @return true, if checks if is jl
	 */
	public boolean isJl() {
		return jl;
	}

	/**
	 * Sets the jl.
	 *
	 * @param jl the jl
	 */
	public void setJl(boolean jl) {
		this.jl = jl;
	}


	/**
	 * Checks if is auto.
	 *
	 * @return true, if checks if is auto
	 */
	public boolean isAuto() {
		return auto;
	}

	/**
	 * Sets the auto.
	 *
	 * @param auto the auto
	 */
	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Zd [name=" + name + ", path=" + path + /*", mr=" + mr +*/ ", jl=" + jl + ", auto=" + auto + "]";
	}

}
