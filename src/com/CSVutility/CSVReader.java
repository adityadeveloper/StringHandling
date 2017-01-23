package com.CSVutility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

	public List<String> getCsvLine(String csvFileName) {
		BufferedReader csvReader = null;
		List<String> csvList = new ArrayList<String>();
		String csvLine = null;
		
		try{	
			csvReader = new BufferedReader(new FileReader(csvFileName));
			while ((csvLine = csvReader.readLine()) != null){
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
		}
	}
	
	public List<String> getCsvLineValues(String csvLine){
		List<String> CsvLineValues = new ArrayList <String>();
		String [] csvLineValueArray = csvLine.split(",");
		CsvLineValues = Arrays.asList(csvLineValueArray);
		return CsvLineValues;	
	}
	
	public static void main (String args[]){
		CSVReader cv = new CSVReader();
		List<String> list = cv.getCsvLine("abc.csv");
		System.out.println(list.get(0));
		System.out.println(cv.getCsvLineValues(list.get(0)));
		//for(String a : list) System.out.println(a);
	}
}