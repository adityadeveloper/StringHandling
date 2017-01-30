package com.granite.validation;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import com.configurations.ConfigReader;
import com.granite.model.GraniteVO;

public class GraniteFinalDBWriter {
	private static final String DB_DRIVER;
	private static final String DB_CONNECTION;
	private static final String DB_USER;
	private static final String DB_PASSWORD;
	
	static{
		ConfigReader conf = new ConfigReader();
		DB_DRIVER = conf.getDB_DRIVER();
		DB_CONNECTION = conf.getDB_CONNECTION();
		DB_USER = conf.getDB_USER();
		DB_PASSWORD = conf.getDB_PASSWORD();
	}


	public void insertIntoFinalGraniteTable(List<GraniteVO> graniteVO) throws SQLException {

		Connection dbConnection = null;
		Statement stmt = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL = "INSERT INTO granitefinal"
				+ "(equipment_status,device_code,facility_id,modified_date_time,full_address,country,pincode,equip_ref_name,floor,bssid,timestamp,is_active) "
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
		
			long startTimeStamp = System.currentTimeMillis();
			System.out.println("Final Data insertion started at "+ new Timestamp(startTimeStamp));
			
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
				preparedStatement.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setBoolean(12,false);
				
				preparedStatement.executeUpdate();
			}
			
			stmt = dbConnection.createStatement();
			stmt.execute("DELETE from granitefinal where is_active = true");
			stmt.execute("UPDATE granitefinal SET is_active = true");
			long endTimeStamp = System.currentTimeMillis();
			System.out.println("Fianl Data insertion completed at "+ new Timestamp(endTimeStamp) + "\nTotal time taken : "+(endTimeStamp - startTimeStamp) + " ms");
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
