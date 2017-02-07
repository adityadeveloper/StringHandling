package com.granite.validation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import org.json.simple.JSONObject;
import com.configurations.ConfigReader;
import com.granite.Exceptions.*;
import com.granite.model.*;;

public class GraniteDataValidation {

	private static final String CATEGORY;
	private static final String SUB_CATEGORY;
	private static final int RADIUS;
	private static final String[] WIFI_INDOOR;
	private static final String[] WIFI_OUTDOOR;
	private static final String IMAGE_REFERENCE;
	private List<GraniteVO> validGraniteList;
	private List<GraniteFinalVO> finalGraniteList;
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		CATEGORY = config.getCategory();
		SUB_CATEGORY = config.getSub_category();
		RADIUS = Integer.parseInt(config.getRadius());
		WIFI_INDOOR = config.getWifi_indoor().split(":");
		WIFI_OUTDOOR = config.getWifi_outdoor().split(":");
		IMAGE_REFERENCE = config.getImage_reference();
	}
	
	public List<GraniteVO> graniteValidator(List<GraniteVO> graniteDBList){
		HashSet<String> uniqueEquipmentId = new HashSet<>();
		List<GraniteVO> duplicateGraniteVO = new ArrayList<>();
		
		long startTime = System.currentTimeMillis(); System.out.println("\nData validation process started at "+new Timestamp(startTime) + "\nTotal Granite records to be validated = "+graniteDBList.size());
		
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
		if ((toBeValidated.isEmpty()) || (toBeValidated.trim().length() == 0)) return true;
		else return false;
	}
	
	
	public static boolean locationValidator(String toBeValidated){
		try{
			Double.parseDouble(toBeValidated);
			return false;
		}
		catch(NumberFormatException nfx){
			return true;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GraniteFinalVO> graniteFinalListMaker(List<GraniteVO> validatedGraniteList){
		finalGraniteList = new ArrayList<>();
		for(GraniteVO oneGraniteVO : validatedGraniteList){
			GraniteFinalVO oneFinalGraniteVO = new GraniteFinalVO();
			JSONObject ca_json = new JSONObject();
			
			oneFinalGraniteVO.setCategory(CATEGORY);
			oneFinalGraniteVO.setSub_category(SUB_CATEGORY);
			oneFinalGraniteVO.setRil_id(oneGraniteVO.getRj_equipme_rjid());
			oneFinalGraniteVO.setName(oneGraniteVO.getSite_location());
			oneFinalGraniteVO.setFloor(oneGraniteVO.getFloor());
			oneFinalGraniteVO.setFull_address(oneGraniteVO.getFull_address());
			oneFinalGraniteVO.setBuilding("");
			oneFinalGraniteVO.setStreet("");
			oneFinalGraniteVO.setLocality(oneGraniteVO.getArea());
			oneFinalGraniteVO.setArea("");
			oneFinalGraniteVO.setCity(oneGraniteVO.getCity_name());
			oneFinalGraniteVO.setState(oneGraniteVO.getR4g_state());
			oneFinalGraniteVO.setCountry(oneGraniteVO.getCountry());
			oneFinalGraniteVO.setPincode(oneGraniteVO.getPincode());
			oneFinalGraniteVO.setContact_number("");
			oneFinalGraniteVO.setEmail("");
			oneFinalGraniteVO.setWebsite("");
			oneFinalGraniteVO.setRadius(RADIUS);
			oneFinalGraniteVO.setGeom_lat(Double.parseDouble(oneGraniteVO.getGis_lat()));
			oneFinalGraniteVO.setGeom_long(Double.parseDouble(oneGraniteVO.getGis_long()));
			oneFinalGraniteVO.setCustom_attributes("");			
			oneFinalGraniteVO.setImage_reference(IMAGE_REFERENCE);
			oneFinalGraniteVO.setEquipmentneid(oneGraniteVO.getEquipment_id());
			oneFinalGraniteVO.setBssid(oneGraniteVO.getBssid());
			oneFinalGraniteVO.setAp_group_name(oneGraniteVO.getAp_group_name());		
			ca_json.put("site_neid", oneGraniteVO.getSite_id());
			ca_json.put("equip_rjid", oneGraniteVO.getRj_equipme_rjid());
			ca_json.put("equip_neid", oneGraniteVO.getEquipment_id());
			ca_json.put("equip_range", "");
			ca_json.put("site_name", oneGraniteVO.getSite_location());
			ca_json.put("equip_status", oneGraniteVO.getEquipment_status());
			ca_json.put("indoor_flag", "");
			
			for(String wifi_indoor : WIFI_INDOOR){
				if (oneGraniteVO.getEquipment_device_code().equalsIgnoreCase(wifi_indoor)) {
					ca_json.put("indoor_flag", "INDOOR");
				}
			}
			for(String wifi_outdoor : WIFI_OUTDOOR){
				if (oneGraniteVO.getEquipment_device_code().equalsIgnoreCase(wifi_outdoor)) {
					ca_json.put("indoor_flag", "OUTDOOR");
				}
			}
			
			ca_json.put("timestamp", oneGraniteVO.getModified_date_time());
			ca_json.put("floor", oneGraniteVO.getFloor());
			ca_json.put("coverage_shape", "");
			ca_json.put("equip_mfg", oneGraniteVO.getAccess_point_make());
			ca_json.put("feat_info", "");
			ca_json.put("equip_ref_name", oneGraniteVO.getEquip_ref_name());
			ca_json.put("trai_circle", oneGraniteVO.getCircle());
			ca_json.put("site_rjid", oneGraniteVO.getSite_name());
			
			oneFinalGraniteVO.setCa_json(ca_json.toJSONString());
			
			finalGraniteList.add(oneFinalGraniteVO);
			
/*			oneFinalGraniteVO.setCa_site_neid(oneGraniteVO.getSite_id());
			oneFinalGraniteVO.setCa_equip_rjid(oneGraniteVO.getRj_equipme_rjid());
			oneFinalGraniteVO.setCa_equip_neid(oneGraniteVO.getEquipment_id());
			oneFinalGraniteVO.setCa_equip_range("");
			oneFinalGraniteVO.setCa_site_name(oneGraniteVO.getSite_location());
			oneFinalGraniteVO.setCa_equip_status(oneGraniteVO.getEquipment_status());
			oneFinalGraniteVO.setCa_indoor_flag("");
			
			for(String wifi_indoor : WIFI_INDOOR){
				if (oneGraniteVO.getEquipment_device_code().equalsIgnoreCase(wifi_indoor)) {
					oneFinalGraniteVO.setCa_indoor_flag("INDOOR");
				}
			}
			for(String wifi_outdoor : WIFI_OUTDOOR){
				if (oneGraniteVO.getEquipment_device_code().equalsIgnoreCase(wifi_outdoor)) {
					oneFinalGraniteVO.setCa_indoor_flag("OUTDOOR");
				}
			}
			
			oneFinalGraniteVO.setCa_timestamp(oneGraniteVO.getModified_date_time());
			oneFinalGraniteVO.setCa_floor(oneGraniteVO.getFloor());
			oneFinalGraniteVO.setCa_coverage_shape("");
			oneFinalGraniteVO.setCa_equip_mfg(oneGraniteVO.getAccess_point_make());
			oneFinalGraniteVO.setCa_feat_info("");
			oneFinalGraniteVO.setCa_equip_ref_name(oneGraniteVO.getEquip_ref_name());
			oneFinalGraniteVO.setCa_trai_circle(oneGraniteVO.getCircle());
			oneFinalGraniteVO.setCa_site_rjid(oneGraniteVO.getSite_name());
*/			
		}
		return finalGraniteList;
	}
	
	
	public static void main(String args[]){
		
		GraniteDBReader reader = new GraniteDBReader();
		GraniteDataValidation validator = new GraniteDataValidation();
		GraniteFinalDBWriter dbf = new GraniteFinalDBWriter();
		
		try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			List<GraniteVO> validatedData = validator.graniteValidator(dbData);
			List<GraniteFinalVO> finalData = validator.graniteFinalListMaker(validatedData);
	//		System.out.println(finalData.get(0));
			
			try{
				dbf.insertIntoFinalGraniteTable(finalData);
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
