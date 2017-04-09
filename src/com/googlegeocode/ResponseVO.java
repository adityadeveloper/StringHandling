package com.googlegeocode;

public class ResponseVO {

	private Double lat;
	private Double lon;
	private String formattedAddress;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formatted_address) {
		this.formattedAddress = formatted_address;
	}
	
}
