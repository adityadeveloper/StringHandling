package com.granite.model;

import java.sql.Timestamp;

import com.granite.filereader.GraniteFileSearch;

public class GraniteFileVO implements Comparable<GraniteFileVO>{
	
	private String fileName;
	private Timestamp fileTimeStamp;
	
	@Override
	public int compareTo(GraniteFileVO gfs) {
		return gfs.getFileTimeStamp().compareTo(this.getFileTimeStamp());
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
