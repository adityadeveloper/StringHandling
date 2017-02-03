package com.granite.Exceptions;

public class DuplicateAttributeException extends Exception {
	
	public DuplicateAttributeException(String duplicateAttributeName, String duplicateAttributeValue){
		System.out.println("Duplicate record " + duplicateAttributeName + " = " + duplicateAttributeValue + " in Granite data");
	}
}
