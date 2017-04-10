package com.googlegeocode;

import java.util.List;

import org.apache.log4j.Logger;

public class GeocodeUtil {

	static Logger logger = Logger.getLogger(GeocodeUtil.class);
	
	
	public static void main(String[] args){
		GeocodeManager manager = new GeocodeManager();
		
/*	  List<GeocodeResponseVO> myList1 = manager.createAddressList("gc.csv","1234");
		
		int a = 1;
		for (GeocodeResponseVO one : myList1){
			logger.info(a + ")" + one.getInput_address());
			manager.saveGeocodeResponse(one);
			a++;
		}
		
		logger.info("Done!!");*/
		
		List<GeocodeResponseVO> myList2 = manager.getAddressList("1234");
		
		for (GeocodeResponseVO inputVO : myList2){
			inputVO = manager.GeocodeMain(inputVO);
			manager.saveGeocodeResponse(inputVO);
		}
	}
}
