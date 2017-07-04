package com.tuff.hyldium.dao;

public enum DaoErrors {
	USER_UNKNOWN (2101, "User unknown");
	
	private int code;
	private String message;
	
	private DaoErrors(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
