package com.webutility;

public class InvalidStatusException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	
	public InvalidStatusException(int statusCode){
		this.statusCode = statusCode;
	}
	
	public String getMessage(){
		return "Failed : HTTP error code : "+statusCode;
	}
}
