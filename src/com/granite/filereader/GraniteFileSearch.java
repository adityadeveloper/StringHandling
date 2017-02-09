package com.granite.filereader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.configurations.ConfigReader;
import com.granite.model.GraniteFileVO;

public class GraniteFileSearch{
	

	private static final String FILE_PATH;
	private static final String DB_DRIVER;
	private static final String DB_CONNECTION_INTEGRATION;
	private static final String DB_USER_INTEGRATION;
	private static final String DB_PASSWORD_INTEGRATION;
	
	static Logger logger = Logger.getLogger(GraniteFileSearch.class);
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		FILE_PATH = config.getGranite_file_path();
		DB_DRIVER = config.getDB_DRIVER();
		DB_CONNECTION_INTEGRATION = config.getDB_CONNECTION_INTEGRATION();
		DB_USER_INTEGRATION = config.getDB_USER_INTEGRATION();
		DB_PASSWORD_INTEGRATION = config.getDB_PASSWORD_INTEGRATION();
	}

	public static List<GraniteFileVO> listofFilesInFolder() {
		File folder = new File(FILE_PATH);
		Pattern granitePattern = Pattern.compile("wifi_ap_[0-9]{12}.csv");
		List<GraniteFileVO> list = new ArrayList<GraniteFileVO>();
	    for (File fileEntry : folder.listFiles()) {
	        Matcher match = granitePattern.matcher(fileEntry.getName());
	        if(match.matches()){
	        	GraniteFileVO oneFile = new GraniteFileVO();
	           	oneFile.setFileName(fileEntry.getName());
	        	oneFile.setFileTimeStamp(new Timestamp(fileEntry.lastModified()));
	        	list.add(oneFile);
	        }
	    }
	    Collections.sort(list);
	    return list;
	}
	
	
	public static List<String> listOfDBFiles(){
		Connection dbConnection = null;
		Statement stmt = null;
		List<String> dbFileList = new ArrayList<>();
		try{
			dbConnection = getDBConnection(DB_CONNECTION_INTEGRATION,DB_USER_INTEGRATION,DB_PASSWORD_INTEGRATION);
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(fileQueryBuilder());
			
			while(rs.next()){
				dbFileList.add(rs.getString("file_name"));
			}
			return dbFileList;
		}
		catch(SQLException sqx){
			sqx.printStackTrace();
			return dbFileList;
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
	
	public static String fileQueryBuilder(){
		
		List<GraniteFileVO> dirFileList = GraniteFileSearch.listofFilesInFolder();
		StringBuilder query = new StringBuilder("SELECT file_name from integration_file_read_logger where file_name IN (");
		
		if (dirFileList.size() == 1){
			query.append("'"+ dirFileList.get(0).getFileName() +"',");
		}
		
		for (int i = 0; i<dirFileList.size(); i++){
			if (i<(dirFileList.size()-1))  query.append("'"+ dirFileList.get(i).getFileName() +"',");
			else query.append("'"+dirFileList.get(i).getFileName()+"'");
		}
		
		query.append(")");
		return query.toString();
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
	
	
	public String graniteFilePicker(){
		String fileName = null;
		List<String> dbFileList = GraniteFileSearch.listOfDBFiles();
		
		logger.info("Searching for Granite Wi_Fi AP files in : "+FILE_PATH);
		List<GraniteFileVO> dirFileList = GraniteFileSearch.listofFilesInFolder();
		for(GraniteFileVO oneFile : dirFileList){
			if (dbFileList.contains(oneFile.getFileName())){
				continue;
			}
			fileName = oneFile.getFileName();
		}
		return fileName;
	}
	
	public static void main(String args[]){
		GraniteFileSearch a = new GraniteFileSearch();
		System.out.println(a.graniteFilePicker());
	}
}
