package com.mes.common;

public class JsonData {

	private boolean success=false;
	private Object result;
	private String message;
	
	public JsonData() {
		
	}

	public JsonData(boolean success) {
		this.success = success;
	}
	
	public JsonData(boolean success, Object result) {
		this.success = success;
		this.result = result;
	}
	
	public JsonData(boolean success, Object result, String message) {
		this.success = success;
		this.result = result;
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result){
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
