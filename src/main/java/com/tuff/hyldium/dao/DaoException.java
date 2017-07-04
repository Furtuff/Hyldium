package com.tuff.hyldium.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = -3926103614233282282L;

	private int code;
	
	public DaoException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public DaoException(DaoErrors error) {
		super(error.getMessage());
		this.code = error.getCode();
	}
	
	public int getCode() {
		return code;
	}
}
