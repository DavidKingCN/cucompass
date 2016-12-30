package cn.com.davidking.persist.pojo;

public class Company {
	private String colName;
	private String orgCode;
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Override
	public String toString() {
		return "Company [colName=" + colName + ", orgCode=" + orgCode + "]";
	}
	
	public String toInsertSql(){
		return "insert into t_company(colName,orgCode) values('"+colName+"','"+orgCode+"')";
	}
}
