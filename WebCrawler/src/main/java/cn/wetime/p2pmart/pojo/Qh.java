/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 区划表
 * @author daikai
 *
 */

@Entity
@Table(name = "t_qh", catalog = "test")
public class Qh {
	//qh_code
	private String qhCode;			//行政区划代码
	
	//qh_name
	private String qhName;			//行政区划名称
	
	//qh_lng
	private String qhLng;			//行政区划经度
	
	//qh_lat
	private String qhLat;			//行政区划纬度
	//qh_parent_code
	private String qhParentCode;	//行政区划的上级区划代码
	//qh_layer
	private int qhLayer;			//行政区划的层级
	
	
	
	
	@Id
	@Column(name="qh_code")
	@GeneratedValue(generator = "qhGenerator")
	@GenericGenerator(name = "qhGenerator", strategy = "assigned")
	public String getQhCode() {
		return qhCode;
	}
	public void setQhCode(String qhCode) {
		this.qhCode = qhCode;
	}
	
	@Column(name="qh_name")
	public String getQhName() {
		return qhName;
	}
	public void setQhName(String qhName) {
		this.qhName = qhName;
	}
	
	@Column(name="qh_lng")
	public String getQhLng() {
		return qhLng;
	}
	public void setQhLng(String qhLng) {
		this.qhLng = qhLng;
	}
	
	@Column(name="qh_lat")
	public String getQhLat() {
		return qhLat;
	}
	public void setQhLat(String qhLat) {
		this.qhLat = qhLat;
	}
	
	@Column(name="qh_parent_code")
	public String getQhParentCode() {
		return qhParentCode;
	}
	public void setQhParentCode(String qhParentCode) {
		this.qhParentCode = qhParentCode;
	}
	
	@Column(name="qh_layer")
	public int getQhLayer() {
		return qhLayer;
	}
	public void setQhLayer(int qhLayer) {
		this.qhLayer = qhLayer;
	}
	
	
	
}
