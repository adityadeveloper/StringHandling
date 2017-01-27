package com.granite.filereader;

import com.granite.model.GraniteVO;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class GraniteFileReader {
	private List<GraniteVO> GraniteList;

	public List<GraniteVO> createGraniteList(String csvFileName){
		GraniteList = new ArrayList<GraniteVO>();

        CSVReader reader = null;
        try {
			long startTimeStamp = System.currentTimeMillis();
			System.out.println("CSV file reading started at "+ new Timestamp(startTimeStamp));
            reader = new CSVReader(new FileReader(csvFileName));
            long endTimeStamp = System.currentTimeMillis();
			System.out.println("CSV file reading completed at "+ new Timestamp(endTimeStamp) + "\nTotal time taken : "+(endTimeStamp - startTimeStamp) + " ms");
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
    			
    			GraniteList.add(graniteVO);
            }
            GraniteList.remove(0);
            return GraniteList;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return GraniteList;
        }
        finally{
        		try{
	        		reader.close();
	        	}
	        	catch(IOException e){
	        		e.printStackTrace();
	        	}
        }
    }

	public static void main(String args[]){
		GraniteFileReader gr = new GraniteFileReader();
		GraniteDB db = new GraniteDB();
		List<GraniteVO> test = gr.createGraniteList("wifi_ap_141020161848.csv");
		
		try{
			db.insertIntoGraniteTable(test);
		}
		
		catch(SQLException sq){
			sq.getMessage();
			sq.printStackTrace();
		}
	}
}
