package com.granite.Exceptions;

public class DuplicateAttributeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateAttributeException(String duplicateAttributeName, String duplicateAttributeValue){
		System.out.println("Duplicate record " + duplicateAttributeName + " = " + duplicateAttributeValue + " in Granite data");
	}
}
