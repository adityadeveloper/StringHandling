package com.configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class ConnectionManager {
	private static final String DB_DRIVER;
	
	static Logger logger = Logger.getLogger(ConnectionManager.class);
	
	static {
		ConfigReader config = ConfigReader.getInstance();
		DB_DRIVER = config.getDB_DRIVER();
	}
	
	public static Connection getDBConnection(String dbUrl, String dbUserName, String dbPassword) {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} 	
		catch (ClassNotFoundException e) {
			logger.error("Exception occurred !!!", e);
		}
		try {
			dbConnection = DriverManager.getConnection(dbUrl, dbUserName,dbPassword);                
			return dbConnection;
		} 
		catch (SQLException e) {
			logger.error("Exception occurred !!!", e);
		}
		return dbConnection;	
	}
}
