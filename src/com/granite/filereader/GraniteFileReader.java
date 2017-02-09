package com.granite.filereader;

import com.granite.model.GraniteVO;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import org.apache.log4j.Logger;

import com.configurations.ConfigReader;

public class GraniteFileReader {
	private List<GraniteVO> GraniteList;
	private static final String HEADER_STRUCTURE;
	
	Logger logger = Logger.getLogger(GraniteFileReader.class);
	
	static{
		HEADER_STRUCTURE = ConfigReader.getInstance().getHeader_structure();
	}
	
	public List<GraniteVO> createGraniteList(String csvFileName){
		GraniteList = new ArrayList<GraniteVO>();

        CSVReader reader = null;
        try {
			long startTimeStamp = System.currentTimeMillis();
			logger.info("CSV file reading started");
            reader = new CSVReader(new FileReader(csvFileName));
            String[] firstLine = reader.readNext();
            if (headerCheck(firstLine)){
	            String[] line;
	            while ((line = reader.readNext()) != null) {
		            	GraniteVO graniteVO = new GraniteVO();
		    			graniteVO.setEquipment_status(line[0]);
		    			graniteVO.setDevice_code(line[1]);
		    			graniteVO.setFacility_id(line[2]);
		    			graniteVO.setModified_date_time(line[3]);
		    			graniteVO.setFull_address(line[4]);
		    			graniteVO.setCountry(line[5]);
		    			graniteVO.setPincode(line[6]);
		    			graniteVO.setEquip_ref_name(line[7]);
		    			graniteVO.setFloor(line[8]);
		    			graniteVO.setBssid(line[9]);
		    			graniteVO.setAp_group_name(line[10]);
		    			graniteVO.setEquipment_id(line[11]);
		    			graniteVO.setRj_equipme_rjid(line[12]);
		    			graniteVO.setSite_name(line[13]);
		    			graniteVO.setSite_id(line[14]);
		    			graniteVO.setSap_id(line[15]);
		    			graniteVO.setSite_location(line[16]);
		    			graniteVO.setGis_lat(line[17]);
		    			graniteVO.setGis_long(line[18]);
		    			graniteVO.setR4g_state(line[19]);
		    			graniteVO.setCircle(line[20]);
		    			graniteVO.setCity_code(line[21]);
		    			graniteVO.setCity_name(line[22]);
		    			graniteVO.setCity_rank(line[23]);
		    			graniteVO.setBusiness_rank(line[24]);
		    			graniteVO.setArea(line[25]);
		    			graniteVO.setEquipment_type(line[26]);
		    			graniteVO.setEquipment_model(line[27]);
		    			graniteVO.setEquipment_device_code(line[28]);
		    			graniteVO.setAccess_point_make(line[29]);
		    			graniteVO.setAccess_point_model(line[30]);
		    			graniteVO.setEquipment_hw_type(line[31]);
		    			
		    			GraniteList.add(graniteVO);
	            }
	            long endTimeStamp = System.currentTimeMillis();
				logger.info("CSV file reading completed.   Total time taken : "+(endTimeStamp - startTimeStamp) + " ms\n");
	            return GraniteList;
	            
	        } 
            else {
            	logger.info("Granite file is not in not in proper format");
            	return GraniteList;
            }
        }
        catch (IOException e) {
            logger.error("Exception Occured !!!", e);
            return GraniteList;
        }
        finally{
        		try{
	        		reader.close();
	        	}
	        	catch(IOException e){
	        		logger.error("Exception Occured !!!", e);
	        	}
        }
    }

	public static boolean headerCheck(String[] fileHeader){
		String[] validHeader = HEADER_STRUCTURE.split(":");
		if (fileHeader.length != validHeader.length) return false;
		else{
			for (int i=0; i<validHeader.length; i++){
				if (validHeader[i].trim().equals(fileHeader[i].trim())) continue;
				else return false;
			}
			return true;
		}
	}
	
	public static void main(String args[]){
		GraniteFileReader gr = new GraniteFileReader();
		GraniteDBWriter db = new GraniteDBWriter();
		List<GraniteVO> test = gr.createGraniteList("wifi_ap_301020161901.csv");
		
		//db.insertIntoGraniteTable(test, "wifi_ap_301020161900.csv");
	}
}
