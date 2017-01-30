package com.granite.validation;

import com.granite.model.GraniteVO;

public class NullAttributeException extends Exception {
	private static final long serialVersionUID = 1L;

	NullAttributeException(String nullAttributeName,GraniteVO invalidGraniteVO){
		System.out.println("Null attribute " + nullAttributeName+ " in Granite record with equip_ref_name : " + invalidGraniteVO.getEquip_ref_name());
	}
}
