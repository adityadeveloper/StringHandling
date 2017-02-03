package com.granite.Exceptions;

import com.granite.model.GraniteVO;

public class NullAttributeException extends Exception {
	private static final long serialVersionUID = 1L;

	public NullAttributeException(String nullAttributeName,String EQUIPMENT_ID){
		System.out.println("Null attribute " + nullAttributeName+ " in Granite record with equipment_id : " + EQUIPMENT_ID);
	}
}
