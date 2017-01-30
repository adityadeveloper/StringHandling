package com.granite.validation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import com.granite.model.GraniteVO;;

public class GraniteDataValidation {
	private List<GraniteVO> validGraniteList;
	
	public List<GraniteVO> graniteValidator(List<GraniteVO> graniteDBList){
		validGraniteList = new ArrayList<>();
		
		long startTime = System.currentTimeMillis();
		System.out.println("\nData validation process started at "+new Timestamp(startTime) + "\nTotal Granite records to be validated = "+graniteDBList.size());
		
		for(GraniteVO oneGraniteVO : graniteDBList){
			try{
				if(!(oneGraniteVO.getEquipment_status().trim().equalsIgnoreCase("PLANNED"))){
					throw new InvalidAttributeException("EQUIPMENT_STATUS",oneGraniteVO.getEquipment_status(),oneGraniteVO.getEquip_ref_name());
				}
				if((oneGraniteVO.getBssid().isEmpty()) || (oneGraniteVO.getBssid().trim().length() == 0)){
					throw new NullAttributeException("BSSID",oneGraniteVO);
				}
				validGraniteList.add(oneGraniteVO);
				}
				
			catch(NullAttributeException nae){
				//nae.printStackTrace();
			}
			catch(InvalidAttributeException nve){
				//nve.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Data validation process completed at "+new Timestamp(endTime) + "\nTotal time taken : " + (endTime-startTime) + " ms" + "\nTotal valid Granite records = "+validGraniteList.size()+"\n");
		return validGraniteList;
		
	}
	
	public static void main(String args[]){
		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();
		
		try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			List<GraniteVO> validatedData = validator.graniteValidator(dbData);
			
			try{
				dbf.insertIntoFinalGraniteTable(validatedData);
			}
			
			catch(SQLException sq){
				sq.getMessage();
				sq.printStackTrace();
			}
		}
		catch (SQLException sq){
			sq.printStackTrace();
		}
	}
}
