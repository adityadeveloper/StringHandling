package com.granite.model;

public class GCResponseVO {
	private double lat;
	private double lon;
	private String formattedAddress;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	@Override
	public String toString() {
		return "GCResponseVO [lat=" + lat + ", lon=" + lon + ", formattedAddress=" + formattedAddress + "]";
	}
	
	
}
