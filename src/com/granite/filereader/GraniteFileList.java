package com.granite.filereader;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraniteFileList implements Comparable<GraniteFileList>{
	
	private String fileName;
	private Timestamp fileTimeStamp;
	
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

	Pattern uName = Pattern.compile("wifi_ap_[0-9]{11}.csv");
	public List<GraniteFileList> listFilesForFolder(File folder) {
		List<GraniteFileList> list = new ArrayList<GraniteFileList>();
	    for (File fileEntry : folder.listFiles()) {
	        Matcher mUname = uName.matcher(fileEntry.getName());
	        if(mUname.matches()){
	        	GraniteFileList oneFile = new GraniteFileList();
	        //	System.out.println(fileEntry.getName()+"\t"+new Timestamp(fileEntry.lastModified()));
	        	oneFile.setFileName(fileEntry.getName());
	        	oneFile.setFileTimeStamp(new Timestamp(fileEntry.lastModified()));
	        	list.add(oneFile);
	        }
	    }
	    Collections.sort(list);
	    return list;
	}

	
	
	public static void main(String args[]){
		
	//	System.out.println(Pattern.matches(".*s", "mas"));//true (3rd char is s)  
		GraniteFileList a = new GraniteFileList();
		File folder = new File("E:/Java Development/CAV1/trunk");
		List<GraniteFileList> abc = a.listFilesForFolder(folder);
		for (GraniteFileList oneFile : abc){
			System.out.println(oneFile.getFileName()+"\t"+oneFile.getFileTimeStamp());
		}
	}
}
