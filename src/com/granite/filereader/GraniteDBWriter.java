package com.granite.filereader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import com.configurations.ConfigReader;
import com.granite.model.GraniteVO;

public class GraniteDBWriter {
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


	public void insertIntoGraniteTable(List<GraniteVO> graniteVO) throws SQLException {

		Connection dbConnection = null;
		Statement stmt = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL = "INSERT INTO granite"
				+ "(equipment_status,device_code,facility_id,modified_date_time,full_address,country,pincode,equip_ref_name,floor,bssid,timestamp,ap_group_name,equipment_id,rj_equipme_rjid,site_name,site_id,sap_id,site_location,gis_lat,gis_long,r4g_state,circle,city_code,city_name,city_rank,business_rank,area,equipment_type,equipment_model,equipment_device_code,access_point_make,access_point_model,equipement_hw_type)"
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();

			stmt.executeUpdate("TRUNCATE granite");
	
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			long startTimeStamp = System.currentTimeMillis();
			System.out.println("Data insertion started at "+ new Timestamp(startTimeStamp));
			
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
				preparedStatement.setString(12,oneGraniteVO.getAp_group_name());
				preparedStatement.setString(13,oneGraniteVO.getEquipment_id());
				preparedStatement.setString(14,oneGraniteVO.getRj_equipme_rjid());
				preparedStatement.setString(15,oneGraniteVO.getSite_name());
				preparedStatement.setString(16,oneGraniteVO.getSite_id());
				preparedStatement.setString(17,oneGraniteVO.getSap_id());
				preparedStatement.setString(18,oneGraniteVO.getSite_location());
				preparedStatement.setString(19,oneGraniteVO.getGis_lat());
				preparedStatement.setString(20,oneGraniteVO.getGis_long());
				preparedStatement.setString(21,oneGraniteVO.getR4g_state());
				preparedStatement.setString(22,oneGraniteVO.getCircle());
				preparedStatement.setString(23,oneGraniteVO.getCity_code());
				preparedStatement.setString(24,oneGraniteVO.getCity_name());
				preparedStatement.setString(25,oneGraniteVO.getCity_rank());
				preparedStatement.setString(26,oneGraniteVO.getBusiness_rank());
				preparedStatement.setString(27,oneGraniteVO.getArea());
				preparedStatement.setString(28,oneGraniteVO.getEquipment_type());
				preparedStatement.setString(29,oneGraniteVO.getEquipment_model());
				preparedStatement.setString(30,oneGraniteVO.getEquipment_device_code());
				preparedStatement.setString(31,oneGraniteVO.getAccess_point_make());
				preparedStatement.setString(32,oneGraniteVO.getAccess_point_model());
				preparedStatement.setString(33,oneGraniteVO.getEquipment_hw_type());
				
				
				// execute insert SQL stetement
				preparedStatement.executeUpdate();
	
				//System.out.println("Record is inserted into granite table for Equip_ref_name : "+oneGraniteVO.getEquip_ref_name());
			}
			long endTimeStamp = System.currentTimeMillis();
			System.out.println("Data insertion completed at "+ new Timestamp(endTimeStamp) + "\nTotal time taken : "+(endTimeStamp - startTimeStamp) + " ms\n");
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
