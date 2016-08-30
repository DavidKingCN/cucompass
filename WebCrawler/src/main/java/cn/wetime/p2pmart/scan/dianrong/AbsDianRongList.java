/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.scan.dianrong;

import java.util.ArrayList;
import java.util.List;

import cn.wetime.p2pmart.WebCrawler;
@SuppressWarnings("all")
public abstract class AbsDianRongList implements WebCrawler {

	
}
/**
 * 团团赚 plan tab页的数据类型
 * @author daikai
 *
 */
class DianRongPlanList{
	private PlanContent content = new PlanContent();
	private String result;
	
	private String[] errors;
	private String[] messages;
	
	private Object metadata;

	public PlanContent getContent() {
		return content;
	}

	public void setContent(PlanContent content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	
}
/**
 * 点融
 * @author daikai
 *
 */
class DianRongBidList{
	private Content content=new Content();
	
	private String result;
	
	private String[] errors;
	private String[] messages;
	
	private Object metadata;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	
}
/**
 * 团团赚 plan tab页的数据类型
 * @author daikai
 *
 */
class PlanContent{
	private List<PlanObj> loans = new ArrayList<PlanObj>();
	private int totalRecords;
	public List<PlanObj> getLoans() {
		return loans;
	}
	public void setLoans(List<PlanObj> loans) {
		this.loans = loans;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	
}
class Content{
	private List<DianRongBidObj> loans = new ArrayList<DianRongBidObj>();
	private List<SecuritizedLoan> securitizedLoans = new ArrayList<SecuritizedLoan>();
	
	
	
	private int totalRecords;

	public List<DianRongBidObj> getLoans() {
		return loans;
	}

	public void setLoans(List<DianRongBidObj> loans) {
		this.loans = loans;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<SecuritizedLoan> getSecuritizedLoans() {
		return securitizedLoans;
	}

	public void setSecuritizedLoans(List<SecuritizedLoan> securitizedLoans) {
		this.securitizedLoans = securitizedLoans;
	}
	
	
}
/**
 * 团团赚 plan tab页的数据类型
 * @author daikai
 *
 */
class PlanObj{
	private double id;//产品ID
	private String name;//产品名称
	private double interstFixedRate;//商品利率
	
	private double amount;//已投金额
	private double appAmount;//投资额度，总金额
	private double minInvestAmount;//起投最小金额
	private String investPeriod="不清楚";//投资期限
	private String repaymentMethod;//获利方式
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getInterstFixedRate() {
		return interstFixedRate;
	}
	public void setInterstFixedRate(double interstFixedRate) {
		this.interstFixedRate = interstFixedRate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAppAmount() {
		return appAmount;
	}
	public void setAppAmount(double appAmount) {
		this.appAmount = appAmount;
	}
	public double getMinInvestAmount() {
		return minInvestAmount;
	}
	public void setMinInvestAmount(double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}
	public String getInvestPeriod() {
		return investPeriod;
	}
	public void setInvestPeriod(String investPeriod) {
		this.investPeriod = investPeriod;
	}
	public String getRepaymentMethod() {
		return repaymentMethod;
	}
	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}
	
}
/***
 * 
 * @author daikai
 *
 */
class DianRongBidObj{
	private String loanGUID;//产品ID
	private String title;//产品名称
	private String loanRate;//商品利率
	private double loanLength;//投资期限
	private String repaymentMethod;//获利方式
	private double loanAmtRemaining;
	private double loanAmt;//投资额度
	private String loan_status;//INFUNDING正在交易
	//平台名称
	public String getLoanGUID() {
		return loanGUID;
	}
	public void setLoanGUID(String loanGUID) {
		this.loanGUID = loanGUID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public double getLoanLength() {
		return loanLength;
	}
	public void setLoanLength(double loanLength) {
		this.loanLength = loanLength;
	}
	public String getRepaymentMethod() {
		return repaymentMethod;
	}
	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}
	public double getLoanAmtRemaining() {
		return loanAmtRemaining;
	}
	public void setLoanAmtRemaining(double loanAmtRemaining) {
		this.loanAmtRemaining = loanAmtRemaining;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoan_status() {
		return loan_status;
	}
	public void setLoan_status(String loan_status) {
		this.loan_status = loan_status;
	}
	
}
/**
 * 
 * @author daikai
 *
 */
class SecuritizedLoan{
	//产品ID
	//产品名称
	//商品利率
	//投资期限
	//获利方式
	//投资额度
	//交易状态
}