/*
 *    功能名称   ： xpath数据提取实现1.1
 *    
 *    (C) Copyright DavidKing 2016
 *    All Rights Reserved.
 *	  
 *    注意： 有问题联系作者13621151569@yeah.net
 */
package cn.com.davidking.html.parse;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

// TODO: Auto-generated Javadoc
/**
 * The Class PickAgent.
 */
public class PickAgent {
	
	/** The cleaner. */
	private HtmlCleaner cleaner;
	
	/** The node. */
	private TagNode node;
	
	/** The path. */
	private String path;
	
	/** The obey rule. */
	private boolean obeyRule;
	
	/**
	 * Inits the.
	 *
	 * @param cleaner the cleaner
	 * @param node the node
	 * @param path the path
	 */
	public void init(HtmlCleaner cleaner, TagNode node, String path) {
		this.cleaner = cleaner;	this.node = node; this.path = path;	this.obeyRule = false;
	}
	
	/**
	 * Gets the cleaner.
	 *
	 * @return the cleaner
	 */
	public HtmlCleaner getCleaner() {
		return cleaner;
	}
	
	/**
	 * Sets the cleaner.
	 *
	 * @param cleaner the cleaner
	 */
	public void setCleaner(HtmlCleaner cleaner) {
		this.cleaner = cleaner;
	}
	
	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public TagNode getNode() {
		return node;
	}
	
	/**
	 * Sets the node.
	 *
	 * @param node the node
	 */
	public void setNode(TagNode node) {
		this.node = node;
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
	 * Checks if is obey rule.
	 *
	 * @return true, if checks if is obey rule
	 */
	public boolean isObeyRule() {
		return obeyRule;
	}
	
	/**
	 * Sets the obey rule.
	 *
	 * @param obeyRule the obey rule
	 */
	public void setObeyRule(boolean obeyRule) {
		this.obeyRule = obeyRule;
	}
	
	
}

