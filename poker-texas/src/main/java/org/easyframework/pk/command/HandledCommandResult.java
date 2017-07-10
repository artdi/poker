package org.easyframework.pk.command;

import java.util.Map;

public class HandledCommandResult {
	private boolean result=false;
	private String resultMsg="";
	
	private Map resultMap;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Map getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map resultMap) {
		this.resultMap = resultMap;
	}
	
	

}
