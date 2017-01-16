package com.landmarkfinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Keywords {
	private List<String> keywords;
	
	public List<String> getKeywords(){
		keywords = new ArrayList<String>();
		Properties property = new Properties();
		InputStream input = null;
		
		try{
			input = new FileInputStream("config.properties");
			property.load(input);
			String allKeywords =property.getProperty("keywords");
			String[] keywordArray = allKeywords.split(",");
				/*for(String oneKeyword : keywordArray){
					keywords.add(oneKeyword);
				}*/
				keywords = Arrays.asList(keywordArray);
				return keywords;
		}
		catch(IOException ex){
			ex.printStackTrace();
			return keywords;
		}
		finally{
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String args[]){
		Keywords key = new Keywords();
		List<String> keywords = key.getKeywords();
		for(String abc : keywords){
			System.out.println(abc);
		}
		
	}
}
