package com.configurations;            

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private String DB_DRIVER;
	private String DB_CONNECTION;
	private String DB_USER;
	private String DB_PASSWORD;
	private String EQUIPMENT_TYPE;
	private String EQUIPMENT_STATUS;
	private String category;
	private String sub_category;
	private String radius;
	private String wifi_indoor;
	private String wifi_outdoor;
	private String image_reference;
	private String granite_file_path;
	
		
	public String getGranite_file_path() {
		return granite_file_path;
	}

	public String getImage_reference() {
		return image_reference;
	}

	public String getCategory() {
		return category;
	}

	public String getSub_category() {
		return sub_category;
	}

	public String getRadius() {
		return radius;
	}

	public String getWifi_indoor() {
		return wifi_indoor;
	}

	public String getWifi_outdoor() {
		return wifi_outdoor;
	}

	public String[] getEQUIPMENT_TYPE() {
		String[] EQUIPMENT_TYPE_ARRAY = EQUIPMENT_TYPE.split(":");
		return EQUIPMENT_TYPE_ARRAY;
	}

	public String[] getEQUIPMENT_STATUS() {
		String[] EQUIPMENT_STATUS_ARRAY = EQUIPMENT_STATUS.split(":");
		return EQUIPMENT_STATUS_ARRAY;
	}

	public String getDB_DRIVER() {
		return DB_DRIVER;
	}

	public String getDB_CONNECTION() {
		return DB_CONNECTION;
	}

	public String getDB_USER() {
		return DB_USER;
	}

	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public ConfigReader(){                  
	Properties property = new Properties();
	InputStream input = null;
	
	try{
		input = new FileInputStream("config.properties");
		property.load(input);
		DB_DRIVER = property.getProperty("db_driver").trim();
		DB_CONNECTION = property.getProperty("db_url").trim();
		DB_USER = property.getProperty("db_username").trim();
		DB_PASSWORD = property.getProperty("db_password").trim();
		EQUIPMENT_TYPE = property.getProperty("equipment_type").trim();
		EQUIPMENT_STATUS = property.getProperty("equipment_status").trim();
		wifi_indoor = property.getProperty("wifi_indoor").trim();
		wifi_outdoor = property.getProperty("wifi_outdoor").trim();
		radius = property.getProperty("radius").trim();
		category = property.getProperty("category").trim();
		sub_category = property.getProperty("sub_category").trim();
		image_reference = property.getProperty("image_reference").trim();
		granite_file_path = property.getProperty("granite_file_path").trim();
	}
	
	catch(IOException ex){
		ex.printStackTrace();
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
		ConfigReader conf = new ConfigReader();
		System.out.println("1) "+conf.getDB_CONNECTION());
		System.out.println("2) "+conf.getDB_DRIVER());
		System.out.println("3) "+conf.getDB_PASSWORD());
		System.out.println("4) "+conf.getDB_USER());
		System.out.println("4) "+conf.getWifi_indoor());
	}
}
