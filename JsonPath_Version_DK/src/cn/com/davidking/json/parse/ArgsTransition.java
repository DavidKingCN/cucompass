package cn.com.davidking.json.parse;

import java.util.List;

/**
 * json 流转对象
 * @author dk
 *
 */
public class ArgsTransition{
	private String targetJson;
	private List<String> results;
	private boolean error;
	private boolean isArrAll;
	private boolean isArrOne;
	private int arrIdx;
	
	
	public void initArgs(String targetJson){
		this.targetJson = targetJson;
		this.results = null;
		this.error = false;
		this.isArrAll = false;
		this.isArrAll = false;
		this.arrIdx = 0;
	}
	public String getTargetJson() {
		return targetJson;
	}
	public void setTargetJson(String targetJson) {
		this.targetJson = targetJson;
	}
	public List<String> getResults() {
		return results;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public boolean isArrAll() {
		return isArrAll;
	}
	public void setArrAll(boolean isArrAll) {
		this.isArrAll = isArrAll;
	}
	public boolean isArrOne() {
		return isArrOne;
	}
	public void setArrOne(boolean isArrOne) {
		this.isArrOne = isArrOne;
	}
	public int getArrIdx() {
		return arrIdx;
	}
	public void setArrIdx(int arrIdx) {
		this.arrIdx = arrIdx;
	}
	
}