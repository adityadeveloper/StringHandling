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
	private static final String DB_CONNECTION_LBS;
	private static final String DB_USER_LBS;
	private static final String DB_PASSWORD_LBS;
	private static final String DB_CONNECTION_INTEGRATION;
	private static final String DB_USER_INTEGRATION;
	private static final String DB_PASSWORD_INTEGRATION;
	
	static{
		ConfigReader conf = new ConfigReader();
		DB_DRIVER = conf.getDB_DRIVER();
		DB_CONNECTION_LBS = conf.getDB_CONNECTION_LBS();
		DB_USER_LBS = conf.getDB_USER_LBS();
		DB_PASSWORD_LBS = conf.getDB_PASSWORD_LBS();
		DB_CONNECTION_INTEGRATION = conf.getDB_CONNECTION_INTEGRATION();
		DB_USER_INTEGRATION = conf.getDB_USER_INTEGRATION();
		DB_PASSWORD_INTEGRATION = conf.getDB_PASSWORD_INTEGRATION();
	}


	public void insertIntoGraniteTable(List<GraniteVO> graniteVO, String csvFileName) throws SQLException {
		Connection dbConnection1 = null;
		Statement stmt1 = null;
		PreparedStatement preparedStatement1 = null;		
		
		try {
			dbConnection1 = getDBConnection(DB_CONNECTION_INTEGRATION,DB_USER_INTEGRATION,DB_PASSWORD_INTEGRATION);
			stmt1 = dbConnection1.createStatement();
			String query = "INSERT INTO integration_file_read_logger"
					+ "(file_name, type, timestamp, is_success, comment) VALUES "
					+"(?,?,?,?,?)";
			preparedStatement1 = dbConnection1.prepareStatement(query);
			
			preparedStatement1.setString(1, csvFileName);
			preparedStatement1.setString(2, "WIFI_AP_FILE");
			preparedStatement1.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatement1.setBoolean(4, true);
			preparedStatement1.setString(5, "SUCCESS");
			
			preparedStatement1.executeUpdate(query);

		} 
		
		catch (SQLException /*| InterruptedException*/ e) {
			System.out.println(e.getMessage());
		} 
		
		finally {
			if (preparedStatement1 != null) {
				preparedStatement1.close();
			}
			if (dbConnection1 != null) {
				dbConnection1.close();
			}
		}
		
		Connection dbConnection = null;
		Statement stmt = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL = "INSERT INTO granite"
				+ "(equipment_status,device_code,facility_id,modified_date_time,full_address,country,pincode,equip_ref_name,floor,bssid,timestamp,ap_group_name,equipment_id,rj_equipme_rjid,site_name,site_id,sap_id,site_location,gis_lat,gis_long,r4g_state,circle,city_code,city_name,city_rank,business_rank,area,equipment_type,equipment_model,equipment_device_code,access_point_make,access_point_model,equipement_hw_type)"
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";		
	
		try {
			dbConnection = getDBConnection(DB_CONNECTION_LBS,DB_USER_LBS,DB_PASSWORD_LBS);
			dbConnection.setAutoCommit(false);
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
			//System.out.println("before commit");
			//Thread.sleep(15000);
			dbConnection.commit();
			long endTimeStamp = System.currentTimeMillis();
			System.out.println("Data insertion completed at "+ new Timestamp(endTimeStamp) + "\nTotal time taken : "+(endTimeStamp - startTimeStamp) + " ms\n");
		} 
		
		catch (SQLException /*| InterruptedException*/ e) {
			dbConnection.rollback();
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

	private static Connection getDBConnection(String dbUrl, String dbUserName, String dbPassword) {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} 
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(dbUrl, dbUserName,dbPassword);                
			return dbConnection;
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}
