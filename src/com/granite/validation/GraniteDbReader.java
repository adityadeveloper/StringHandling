package com.granite.validation;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import com.configurations.ConfigReader;
import com.granite.model.GraniteVO;

public class GraniteDBReader{
	private static final String DB_DRIVER;
	private static final String DB_CONNECTION;
	private static final String DB_USER;
	private static final String DB_PASSWORD;
	private List<GraniteVO> GraniteVOFromDB;
	
	static{
		ConfigReader conf = new ConfigReader();
		DB_DRIVER = conf.getDB_DRIVER();
		DB_CONNECTION = conf.getDB_CONNECTION();
		DB_USER = conf.getDB_USER();
		DB_PASSWORD = conf.getDB_PASSWORD();
	}
	

	public List<GraniteVO> readFromGraniteTable() throws SQLException {
	
		GraniteVOFromDB = new ArrayList<GraniteVO>();
		Connection dbConnection = null;
		Statement stmt = null;
		String graniteSelectQuery = "SELECT * from granite";
	
		try{
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();
			long startTime = System.currentTimeMillis();
			System.out.println("Granite DB read started at "+new Timestamp(startTime));
			ResultSet rs = stmt.executeQuery(graniteSelectQuery);
			
			while (rs.next()){
				GraniteVO oneGraniteDBRow = new GraniteVO();
				
				oneGraniteDBRow.setBssid(rs.getString("bssid"));
				oneGraniteDBRow.setCountry(rs.getString("country"));
				oneGraniteDBRow.setDevice_code(rs.getString("device_code"));
				oneGraniteDBRow.setEquip_ref_name(rs.getString("equip_ref_name"));
				oneGraniteDBRow.setEquipment_status(rs.getString("equipment_status"));
				oneGraniteDBRow.setFacility_id(rs.getString("facility_id"));
				oneGraniteDBRow.setFloor(rs.getString("floor"));
				oneGraniteDBRow.setFull_address(rs.getString("full_address"));
				oneGraniteDBRow.setModified_date_time(rs.getString("modified_date_time"));
				oneGraniteDBRow.setPincode(rs.getString("pincode"));
				
				GraniteVOFromDB.add(oneGraniteDBRow);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Granite DB read completed at " + new Timestamp(endTime)+ "\nTotal time taken : " + (endTime - startTime) + " ms");
			return GraniteVOFromDB;
		}
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return GraniteVOFromDB;
		} 
		
		finally {
			if (stmt != null) {
				stmt.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(DB_DRIVER);
		} 
		
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);                
			return dbConnection;
		} 
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
		
	}
	
	public static void main (String args[]){
		GraniteDBReader reader = new GraniteDBReader();
		
		try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			for(GraniteVO oneGraniteVO : dbData){
				System.out.println("\n\n" + oneGraniteVO);
			}
		}
		catch (SQLException sq){
			sq.printStackTrace();
		}
		
	}
}