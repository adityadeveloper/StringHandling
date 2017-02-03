package com.granite.validation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.granite.Exceptions.DuplicateAttributeException;
import com.granite.Exceptions.InvalidAttributeException;
import com.granite.Exceptions.NullAttributeException;
import com.granite.model.GraniteVO;;

public class GraniteDataValidation {
	private List<GraniteVO> validGraniteList;
	
	public List<GraniteVO> graniteValidator(List<GraniteVO> graniteDBList){
		HashSet<String> uniqueEquipmentId = new HashSet<>();
		List<GraniteVO> duplicateGraniteVO = new ArrayList<>();
		for(GraniteVO oneGraniteVO : graniteDBList){
			try{
				if(!uniqueEquipmentId.add(oneGraniteVO.getEquipment_id())){
					duplicateGraniteVO.add(oneGraniteVO);
					throw new DuplicateAttributeException("equipment_id",oneGraniteVO.getEquipment_id()); 
				}
			}
			catch(DuplicateAttributeException dex){
				//dex.printStackTrace();
			}
		}
		graniteDBList.removeAll(duplicateGraniteVO);
		
		validGraniteList = new ArrayList<>();
		
		long startTime = System.currentTimeMillis(); System.out.println("\nData validation process started at "+new Timestamp(startTime) + "\nTotal Granite records to be validated = "+graniteDBList.size());
		
		for(GraniteVO oneGraniteVO : graniteDBList){
			try{
				if(nullValidator(oneGraniteVO.getRj_equipme_rjid())){
					throw new NullAttributeException("RJ_EQUIPME_RJID",oneGraniteVO.getEquipment_id());
				}
				if(nullValidator(oneGraniteVO.getBssid())){
					throw new NullAttributeException("BSSID",oneGraniteVO.getEquipment_id());
				}
				if(nullValidator(oneGraniteVO.getSite_location())){
					throw new NullAttributeException("SITE_LOCATION",oneGraniteVO.getEquipment_id());
				}
				if(nullValidator(oneGraniteVO.getCity_name())){
					throw new NullAttributeException("CITY_NAME",oneGraniteVO.getEquipment_id());
				}
				if(nullValidator(oneGraniteVO.getR4g_state())){
					throw new NullAttributeException("R4G_STATE",oneGraniteVO.getEquipment_id());
				}
				if(nullValidator(oneGraniteVO.getCountry())){
					throw new NullAttributeException("COUNTRY",oneGraniteVO.getEquipment_id());
				}
				if(locationValidator(oneGraniteVO.getGis_lat())){
					throw new InvalidAttributeException("GIS_LAT",oneGraniteVO.getGis_lat(),oneGraniteVO.getEquipment_id());
				}
				if(locationValidator(oneGraniteVO.getGis_long())){
					throw new InvalidAttributeException("GIS_LONG",oneGraniteVO.getGis_long(),oneGraniteVO.getEquipment_id());
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
		
		long endTime = System.currentTimeMillis(); System.out.println("Data validation process completed at "+new Timestamp(endTime) + "\nTotal time taken : " + (endTime-startTime) + " ms" + "\nTotal valid Granite records = "+validGraniteList.size()+"\n");
		return validGraniteList;
	}
	
	public static boolean nullValidator(String toBeValidated){
		if ((toBeValidated.isEmpty()) || (toBeValidated.trim().length() == 0)) return false;
		else return true;
	}
	
	public static boolean locationValidator(String toBeValidated){
		try{
			Double.parseDouble(toBeValidated);
			return true;
		}
		catch(NumberFormatException nfx){
			return false;
		}
	}
	
	
	public static void main(String args[]){
/*		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();*/
		
		System.out.println(locationValidator("a"));
		
	/*	try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			List<GraniteVO> validatedData = validator.graniteValidator(dbData);
			*/
			/*try{
				dbf.insertIntoFinalGraniteTable(validatedData);
			}
			
			catch(SQLException sq){
				sq.getMessage();
				sq.printStackTrace();
			}*/
/*		}
		catch (SQLException sq){
			sq.printStackTrace();
		}*/
	}
}
