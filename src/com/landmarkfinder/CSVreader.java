package com.landmarkfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVreader {

	public List<String> getCsvLine() {
		BufferedReader csvReader = null;
		List<String> csvList = new ArrayList<String>();
		String csvLine = null;
		try{
			
			csvReader = new BufferedReader(new FileReader("abc.csv"));
			int count = 0;
			while ((csvLine = csvReader.readLine()) != null){
				count ++;
				//System.out.println("CSV line no. "+count+") = "+csvLine);
				csvList.add(csvLine);
			}
			return csvList;
			
		}
		catch (IOException ex){
			ex.printStackTrace();
			return csvList;
		}
		finally{
			try {
				if (csvReader != null) csvReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			//return csvLine;
		}

	}
	
	public List<String> getCsvLineValues(String csvLine){
		List<String> CsvLineValues = new ArrayList <String>();
		String [] csvLineValueArray = csvLine.split("//s*,//s*");
		CsvLineValues = Arrays.asList(csvLineValueArray);
		return CsvLineValues;	
	}
	
	public static void main (String args[]){
		CSVreader cv = new CSVreader();
		List<String> list = cv.getCsvLine();
		for(String a : list) System.out.println(a);
	}
}