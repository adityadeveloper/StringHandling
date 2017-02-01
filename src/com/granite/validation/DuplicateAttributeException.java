package com.granite.validation;

public class DuplicateAttributeException extends Exception {
	DuplicateAttributeException(String duplicateAttributeName, String duplicateAttributeValue){
		System.out.println("Duplicate record " + duplicateAttributeName + " = " + duplicateAttributeValue + " in Granite data");
	}
}
