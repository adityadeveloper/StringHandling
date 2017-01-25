package com.granite;

import com.CSVutility.*;
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
		
        String csvFile = csvFileName;

        CSVReader reader = null;
        try {
           	System.out.println("CSV file reading started : "+ new Timestamp(System.currentTimeMillis()));
            reader = new CSVReader(new FileReader(csvFile));
            System.out.println("CSV file reading completed : "+ new Timestamp(System.currentTimeMillis()));
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
            return GraniteList;
        } catch (IOException e) {
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
			
/*		
		CSVReader cv = new CSVReader();
		List<String> allGraniteRows = cv.getCsvLine();
		for(String oneGraniteRow : allGraniteRows){
			List<String> CsvLineValues = cv.getCsvLineValues(oneGraniteRow);
		
			GraniteVO graniteVO = new GraniteVO();
			graniteVO.setEquipment_status(CsvLineValues.get(0));
			graniteVO.setDevice_code(CsvLineValues.get(1));
			graniteVO.setFacility_id(CsvLineValues.get(2));
			graniteVO.setModified_date_time(CsvLineValues.get(3));
			graniteVO.setFull_address(CsvLineValues.get(4));
			graniteVO.setCountry(CsvLineValues.get(5));
			graniteVO.setPincode(CsvLineValues.get(6));
			graniteVO.setEquip_ref_name(CsvLineValues.get(7));
			graniteVO.setFloor(CsvLineValues.get(8));
			graniteVO.setBssid(CsvLineValues.get(9));
			
			GraniteList.add(graniteVO);
		}
		
		return GraniteList;
	}*/
	
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
		
		//System.out.println("\n\n"+test.get(0));
		//System.out.println("\n\n"+test.get(1));
	}
}
