package com.yara.agro.exception;

@SuppressWarnings("serial")
public class AgroMonitorException extends Exception {
	
	String message = "";
	Integer errorCode = 0;
	
	public AgroMonitorException(String message){
		this.message = message;
	}
	public AgroMonitorException(String message, Integer code){
		errorCode = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
