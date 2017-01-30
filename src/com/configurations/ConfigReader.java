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
		DB_DRIVER = property.getProperty("db_driver");
		DB_CONNECTION = property.getProperty("db_url");
		DB_USER = property.getProperty("db_username");
		DB_PASSWORD = property.getProperty("db_password");
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
	}
}
