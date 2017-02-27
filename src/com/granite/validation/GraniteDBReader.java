package com.granite.validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;

import com.configurations.ConfigReader;
import com.granite.model.GraniteVO;
import com.utility.DbConnection;

public class GraniteDBReader{
	private static final String DB_CONNECTION_INTEGRATION;
	private static final String DB_USER_INTEGRATION;
	private static final String DB_PASSWORD_INTEGRATION;
	private static final String[] EQUIPMENT_TYPE;
	private static final String[] EQUIPMENT_STATUS;
	private List<GraniteVO> GraniteVOFromDB;
	
	static Logger logger = Logger.getLogger(GraniteDBReader.class);
	
	static{
		ConfigReader conf = ConfigReader.getInstance();
		DB_CONNECTION_INTEGRATION = conf.getDB_CONNECTION_INTEGRATION();
		DB_USER_INTEGRATION = conf.getDB_USER_INTEGRATION();
		DB_PASSWORD_INTEGRATION = conf.getDB_PASSWORD_INTEGRATION();
		EQUIPMENT_TYPE = conf.getEQUIPMENT_TYPE();
		EQUIPMENT_STATUS = conf.getEQUIPMENT_STATUS();
	}
	

	public List<GraniteVO> readFromGraniteTable() throws SQLException {
	
		GraniteVOFromDB = new ArrayList<GraniteVO>();
		Connection dbConnection = null;
		Statement stmt = null;
		String graniteSelectQuery = selectQueryBuilder();
	
		try{
			dbConnection = DbConnection.getDBConnection(DB_CONNECTION_INTEGRATION,DB_USER_INTEGRATION,DB_PASSWORD_INTEGRATION);
			stmt = dbConnection.createStatement();
			long startTime = System.currentTimeMillis();
			logger.info("Started reading data from Granite table");
			ResultSet rs = stmt.executeQuery(graniteSelectQuery);
			
			while (rs.next()){
				GraniteVO oneGraniteDBRow = new GraniteVO();
				
				oneGraniteDBRow.setBssid(rs.getString("bssid"));
				oneGraniteDBRow.setCountry(rs.getString("country"));
				oneGraniteDBRow.setEquip_ref_name(rs.getString("equip_ref_name"));
				oneGraniteDBRow.setEquipment_status(rs.getString("equipment_status"));
				oneGraniteDBRow.setFloor(rs.getString("floor"));
				oneGraniteDBRow.setFull_address(rs.getString("full_address"));
				oneGraniteDBRow.setModified_date_time(rs.getString("modified_date_time"));
				oneGraniteDBRow.setPincode(rs.getString("pincode"));
				oneGraniteDBRow.setAp_group_name(rs.getString("ap_group_name"));
				oneGraniteDBRow.setEquipment_id(rs.getString("equipment_id"));
				oneGraniteDBRow.setRj_equipme_rjid(rs.getString("rj_equipme_rjid"));
				oneGraniteDBRow.setSite_name(rs.getString("site_name"));
				oneGraniteDBRow.setSite_id(rs.getString("site_id"));
				oneGraniteDBRow.setSite_location(rs.getString("site_location"));
				oneGraniteDBRow.setGis_lat(rs.getString("gis_lat"));
				oneGraniteDBRow.setGis_long(rs.getString("gis_long"));
				oneGraniteDBRow.setR4g_state(rs.getString("r4g_state"));
				oneGraniteDBRow.setCircle(rs.getString("circle"));
				oneGraniteDBRow.setCity_code(rs.getString("city_code"));
				oneGraniteDBRow.setCity_name(rs.getString("city_name"));
				oneGraniteDBRow.setEquipment_type(rs.getString("equipment_type"));
				oneGraniteDBRow.setEquipment_device_code(rs.getString("equipment_device_code"));
				oneGraniteDBRow.setAccess_point_make(rs.getString("access_point_make"));
				
				GraniteVOFromDB.add(oneGraniteDBRow);
			}
			long endTime = System.currentTimeMillis();
			logger.info("Reading from Granite DB completed.  Total time taken : " + (endTime - startTime) + " ms"+"\nNumber of records read : "+GraniteVOFromDB.size());
			return GraniteVOFromDB;
		}
		
		catch (SQLException e) {
			logger.error("Exception occurred !!!", e);
			return GraniteVOFromDB;
		} 
		
		finally {
			try{
				if (stmt != null) {
					stmt.close();
				}
				if (dbConnection != null) {
					dbConnection.close();
				}
			}
			catch(SQLException sqx){
				
			}
		}
	}
	
	
	private static String selectQueryBuilder(){
		StringBuilder query = new StringBuilder("SELECT equipment_status, modified_date_time, full_address, country, pincode, equip_ref_name, floor, bssid, ap_group_name, equipment_id, rj_equipme_rjid, site_name, site_id, site_location, gis_lat, gis_long, r4g_state, circle, city_code, city_name, equipment_type, equipment_device_code, access_point_make from granite where equipment_status in (");
		if(EQUIPMENT_STATUS.length==1)  query.append("'"+EQUIPMENT_STATUS[0]+"'"+")");
		
		else{
			for(int i=0; i<EQUIPMENT_STATUS.length; i++){
				if(i < EQUIPMENT_STATUS.length-1)  query.append("'"+EQUIPMENT_STATUS[i]+"',");
				else query.append("'"+EQUIPMENT_STATUS[i]+"')");
			}	
		}
		
		query.append(" and equipment_type in (");
		
		if(EQUIPMENT_TYPE.length==1)  query.append("'"+EQUIPMENT_TYPE[0]+"'"+")");
		
		else{
			for(int i=0; i<EQUIPMENT_TYPE.length; i++){
				if(i < EQUIPMENT_TYPE.length-1)  query.append("'"+EQUIPMENT_TYPE[i]+"',");
				else query.append("'"+EQUIPMENT_TYPE[i]+"')");
			}
		}
		return query.toString();
	}
	
	
/*	public static void main (String args[]){
		GraniteDBReader reader = new GraniteDBReader();
		//System.out.println(reader.selectQueryBuilder());
		try{
			List<GraniteVO> dbData =  reader.readFromGraniteTable();
			for(GraniteVO oneGraniteVO : dbData){
				System.out.println("\n\n" + oneGraniteVO);
			}
		}
		catch (SQLException sq){
			sq.printStackTrace();
		}
		
	}*/
}