package com.atm.exception;

public class AtmException extends Exception {

	private String exceptionMsg;

	public AtmException(String exceptionMsg) {
		super(exceptionMsg);
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

}
