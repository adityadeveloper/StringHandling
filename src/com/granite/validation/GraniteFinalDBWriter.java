package com.granite.validation;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import com.configurations.ConfigReader;
import com.granite.model.*;

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


	public void insertIntoFinalGraniteTable(List<GraniteFinalVO> graniteVO) throws SQLException {

		Connection dbConnection = null;
		Statement stmt = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL = "INSERT INTO places"
				+ "(category,sub_category,ril_id,name,floor,full_address,building,street,locality,area,city,state,country,pincode,contact_number,email,website,radius,geom,custom_attributes,image_reference,equipmentneid,bssid,ap_group_name,is_active,custom_attributes_json) "
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ST_GeomFromText(?),?,?,?,?,?,?,cast(? as json))";
		
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
		
			long startTimeStamp = System.currentTimeMillis();
			System.out.println("Final Data insertion started at "+ new Timestamp(startTimeStamp));
			
			for (GraniteFinalVO oneFinalGraniteVO: graniteVO){
				preparedStatement.setString(1,oneFinalGraniteVO.getCategory());
				preparedStatement.setString(2,oneFinalGraniteVO.getSub_category());
				preparedStatement.setString(3,oneFinalGraniteVO.getRil_id());
				preparedStatement.setString(4,oneFinalGraniteVO.getName());
				preparedStatement.setString(5,oneFinalGraniteVO.getFloor());
				preparedStatement.setString(6,oneFinalGraniteVO.getFull_address());
				preparedStatement.setString(7,oneFinalGraniteVO.getBuilding());
				preparedStatement.setString(8,oneFinalGraniteVO.getStreet());
				preparedStatement.setString(9,oneFinalGraniteVO.getLocality());
				preparedStatement.setString(10,oneFinalGraniteVO.getArea());
				preparedStatement.setString(11,oneFinalGraniteVO.getCity());
				preparedStatement.setString(12,oneFinalGraniteVO.getState());
				preparedStatement.setString(13,oneFinalGraniteVO.getCountry());
				preparedStatement.setString(14,oneFinalGraniteVO.getPincode());
				preparedStatement.setString(15,oneFinalGraniteVO.getContact_number());
				preparedStatement.setString(16,oneFinalGraniteVO.getEmail());
				preparedStatement.setString(17,oneFinalGraniteVO.getWebsite());
				preparedStatement.setInt(18,oneFinalGraniteVO.getRadius());
				preparedStatement.setString(19, "POINT("+oneFinalGraniteVO.getGeom_long()+" "+oneFinalGraniteVO.getGeom_lat()+")");
			//	preparedStatement.setDouble(19, oneFinalGraniteVO.getGeom_long());
				//preparedStatement.setDouble(20, oneFinalGraniteVO.getGeom_lat());
				preparedStatement.setString(20,oneFinalGraniteVO.getCustom_attributes());			
				preparedStatement.setString(21,oneFinalGraniteVO.getImage_reference());
				preparedStatement.setString(22,oneFinalGraniteVO.getEquipmentneid());
				preparedStatement.setString(23,oneFinalGraniteVO.getBssid());
				preparedStatement.setString(24,oneFinalGraniteVO.getAp_group_name());	
				preparedStatement.setBoolean(25, false);				
				preparedStatement.setString(26,oneFinalGraniteVO.getCa_json());
				
		//		System.out.println(preparedStatement);
				
				preparedStatement.executeUpdate();
			}
			
			stmt = dbConnection.createStatement();
			stmt.execute("DELETE from places where is_active = true");
			stmt.execute("UPDATE places SET is_active = true");
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
