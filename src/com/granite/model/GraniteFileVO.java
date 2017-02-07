package com.granite.model;

import java.sql.Timestamp;


public class GraniteFileVO implements Comparable<GraniteFileVO>{
	
	private String fileName;
	private Timestamp fileTimeStamp;
	
	@Override
	public int compareTo(GraniteFileVO gfs) {
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

}
