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

import com.configurations.ConfigReader;

public class GraniteFileList implements Comparable<GraniteFileList>{
	
	private String fileName;
	private Timestamp fileTimeStamp;
	private static final String FILE_PATH;
	private static final String DB_DRIVER;
	private static final String DB_CONNECTION;
	private static final String DB_USER;
	private static final String DB_PASSWORD;
	
	static{
		ConfigReader config = new ConfigReader();
		FILE_PATH = config.getGranite_file_path();
		DB_DRIVER = config.getDB_DRIVER();
		DB_CONNECTION = config.getDB_CONNECTION();
		DB_USER = config.getDB_USER();
		DB_PASSWORD = config.getDB_PASSWORD();
	}
	
	@Override
	public int compareTo(GraniteFileList gfs) {
		return this.getFileTimeStamp().compareTo(gfs.getFileTimeStamp());
	}
	
    public String getFileName() {
		return fileName;
	}
    
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Timestamp getFileTimeStamp() {
		return fileTimeStamp;
	}
	
	public void setFileTimeStamp(Timestamp fileTimeStamp) {
		this.fileTimeStamp = fileTimeStamp;
	}

	
	public static List<GraniteFileList> listofFilesInFolder() {
		File folder = new File(FILE_PATH);
		Pattern uName = Pattern.compile("wifi_ap_[0-9]{12}.csv");
		List<GraniteFileList> list = new ArrayList<GraniteFileList>();
	    for (File fileEntry : folder.listFiles()) {
	        Matcher mUname = uName.matcher(fileEntry.getName());
	        if(mUname.matches()){
	        	GraniteFileList oneFile = new GraniteFileList();
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
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();
			System.out.println(fileQueryBuilder());
			ResultSet rs = stmt.executeQuery(fileQueryBuilder());
			
			while(rs.next()){
				dbFileList.add(rs.getString("file_name"));
			}
			return dbFileList;
		}
		catch(SQLException sqx){
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
		//GraniteFileList gfl = new GraniteFileList();
		List<GraniteFileList> dirFileList = GraniteFileList.listofFilesInFolder();
		StringBuilder query = new StringBuilder("SELECT file_name from integration_file_read_logger where file_name NOT IN (");
		
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
	
	
	public static void main(String args[]){
		
		//GraniteFileList a = new GraniteFileList();
		List<String> b = GraniteFileList.listOfDBFiles();
		System.out.println(b);
/*		List<GraniteFileList> abc = a.listFilesForFolder();
		for (GraniteFileList oneFile : abc){
			System.out.println(oneFile.getFileName()+"\t"+oneFile.getFileTimeStamp());
		}*/
		//System.out.println(GraniteFileList.fileQueryBuilder());
		
		
	}
}
