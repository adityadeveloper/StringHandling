package com.configurations;            

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigReader {
	
	private String DB_DRIVER;
	private String DB_CONNECTION_LBS;
	private String DB_USER_LBS;
	private String DB_PASSWORD_LBS;
	private String DB_CONNECTION_INTEGRATION;
	private String DB_USER_INTEGRATION;
	private String DB_PASSWORD_INTEGRATION;
	private String EQUIPMENT_TYPE;
	private String EQUIPMENT_STATUS;
	private String category;
	private String sub_category;
	private String radius;
	private String wifi_indoor;
	private String wifi_outdoor;
	private String image_reference;
	private String granite_file_path;
	private String cron_statement;
	private String header_structure;
	private String google_key;
	private int http_connection_timeout;
	private int http_socket_timeout;
	
	static Logger logger = Logger.getLogger(ConfigReader.class);
	
	public String getHeader_structure() {
		return header_structure;
	}

	public int getHttp_connection_timeout() {
		return http_connection_timeout;
	}

	public int getHttp_socket_timeout() {
		return http_socket_timeout;
	}

	public String getCron_statement() {
		return cron_statement;
	}

	public String getGoogle_key() {
		return google_key;
	}

	private static ConfigReader config = null;
		
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

	public String getDB_CONNECTION_LBS() {
		return DB_CONNECTION_LBS;
	}

	public String getDB_USER_LBS() {
		return DB_USER_LBS;
	}

	public String getDB_PASSWORD_LBS() {
		return DB_PASSWORD_LBS;
	}
	public String getDB_CONNECTION_INTEGRATION() {
		return DB_CONNECTION_INTEGRATION;
	}

	public String getDB_USER_INTEGRATION() {
		return DB_USER_INTEGRATION;
	}

	public String getDB_PASSWORD_INTEGRATION() {
		return DB_PASSWORD_INTEGRATION;
	}

	private ConfigReader(){                  
	Properties property = new Properties();
	InputStream input = null;
	
	try{
		
		input = new FileInputStream("E:/Java Development/CAV1/trunk/config.properties");
		property.load(input);
		DB_DRIVER = property.getProperty("db_driver").trim();
		DB_CONNECTION_LBS = property.getProperty("db_url_lbs").trim();
		DB_USER_LBS = property.getProperty("db_username_lbs").trim();
		DB_PASSWORD_LBS = property.getProperty("db_password_lbs").trim();
		DB_CONNECTION_INTEGRATION = property.getProperty("db_url_integration").trim();
		DB_USER_INTEGRATION = property.getProperty("db_username_integration").trim();
		DB_PASSWORD_INTEGRATION = property.getProperty("db_password_integration").trim();
		EQUIPMENT_TYPE = property.getProperty("equipment_type").trim();
		EQUIPMENT_STATUS = property.getProperty("equipment_status").trim();
		wifi_indoor = property.getProperty("wifi_indoor").trim();
		wifi_outdoor = property.getProperty("wifi_outdoor").trim();
		radius = property.getProperty("radius").trim();
		category = property.getProperty("category").trim();
		sub_category = property.getProperty("sub_category").trim();
		image_reference = property.getProperty("image_reference").trim();
		granite_file_path = property.getProperty("granite_file_path").trim();
		cron_statement = property.getProperty("cron_statement").replaceAll("[<>]", "").trim();
		header_structure = property.getProperty("header_structure").trim();
		google_key = property.getProperty("google_key").trim();
		http_connection_timeout = Integer.parseInt(property.getProperty("http_connection_timeout").trim());
		http_socket_timeout = Integer.parseInt(property.getProperty("http_socket_timeout").trim());
	}
	
	catch(IOException ex){
		//ex.printStackTrace();
		logger.error("Exception occured",ex);
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
	
	public static ConfigReader getInstance(){
		if (config == null){
			logger.info("Loading Configurations");
			config = new ConfigReader();
		}
		return config;
	}
	
	public static void main(String args[]){
		ConfigReader conf = new ConfigReader();
/*		System.out.println("1) "+conf.getDB_CONNECTION_LBS());
		System.out.println("2) "+conf.getDB_DRIVER());
		System.out.println("3) "+conf.getDB_PASSWORD_LBS());
		System.out.println("4) "+conf.getDB_USER_LBS());
		System.out.println("4) "+conf.getWifi_indoor());*/
		System.out.println("5) "+conf.getCron_statement());
		
	}
}
