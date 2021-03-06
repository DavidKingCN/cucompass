/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author DavidKing
 *
 */
@Entity
@Table(name = "t_product", catalog = "test")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1752876839177600622L;
	
	private String productId;	//

	private String productName;

	private String rate;
	
	private String investPeriod;
	
	private String profitMode;
	
	private String investAmount;
	
	private String progress;
	
	private String platformName;

	public Product() {
	}



	@Id
	@Column
	@GeneratedValue(generator = "productGenerator")
	@GenericGenerator(name = "productGenerator", strategy = "assigned")
	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	


	public String getProfitMode() {
		return profitMode;
	}



	public void setProfitMode(String profitMode) {
		this.profitMode = profitMode;
	}




	public String getPlatformName() {
		return platformName;
	}



	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}



	public String getRate() {
		return rate;
	}



	public void setRate(String rate) {
		this.rate = rate;
	}



	public String getInvestPeriod() {
		return investPeriod;
	}



	public void setInvestPeriod(String investPeriod) {
		this.investPeriod = investPeriod;
	}



	public String getInvestAmount() {
		return investAmount;
	}



	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}



	public String getProgress() {
		return progress;
	}



	public void setProgress(String progress) {
		this.progress = progress;
	}



	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName="
				+ productName + ", rate=" + rate + ", investPeriod="
				+ investPeriod + ", profitMode=" + profitMode
				+ ", investAmount=" + investAmount + ", progress=" + progress
				+ ", platformName=" + platformName + "]";
	}
	
	

}
