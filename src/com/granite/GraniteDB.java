package com.granite;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class GraniteDB {
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/granite";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "password";


	public void insertIntoGraniteTable(List<GraniteVO> graniteVO) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO granite"
				+ "(equipment_status,device_code,facility_id,modified_date_time,full_address,country,pincode,equip_ref_name,floor,bssid) "
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			for (GraniteVO oneGraniteVO: graniteVO){
				preparedStatement.setString(1,oneGraniteVO.getEquipment_status());
				preparedStatement.setString(2,oneGraniteVO.getDevice_code());
				preparedStatement.setString(3,oneGraniteVO.getFacility_id());
				preparedStatement.setString(4,oneGraniteVO.getModified_date_time());
				preparedStatement.setString(5,oneGraniteVO.getFull_address());
				preparedStatement.setString(6,oneGraniteVO.getCountry());
				preparedStatement.setString(7,oneGraniteVO.getPincode());
				preparedStatement.setString(8,oneGraniteVO.getEquip_ref_name());
				preparedStatement.setString(9,oneGraniteVO.getFloor());
				preparedStatement.setString(10,oneGraniteVO.getBssid());
			
				// execute insert SQL stetement
				preparedStatement.executeUpdate();
	
				System.out.println("Record is inserted into granite table for Equip_ref_name : "+oneGraniteVO.getEquip_ref_name());
			}
		} 
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
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
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);                
			return dbConnection;
		} 
		
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
		
	}
}
