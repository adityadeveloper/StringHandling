package com.granite.validation;

public class InvalidAttributeException extends Exception {

	private static final long serialVersionUID = 1L;

	InvalidAttributeException(String invalidAttributeName, String invalidAttributeValue, String equipmentRefName){
		//System.out.println("Invalid attribute " + invalidAttributeName + " = " + invalidAttributeValue + " in Granite record with equip_ref_name : " + equipmentRefName);
	}
}
