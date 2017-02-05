package com.granite.Exceptions;

public class InvalidAttributeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAttributeException(String invalidAttributeName, String invalidAttributeValue, String equipment_id){
		System.out.println("Invalid attribute " + invalidAttributeName + " = " + invalidAttributeValue + " in Granite record with equipment_id : " + equipment_id);
	}
}
