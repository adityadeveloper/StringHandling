package com.googlegeocode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "googlegeocode")
public class GeocodeResponseVO {
	
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "jobid")
	private String job_id;	

	@Column(name = "input_address")
	private String input_address;
	
	@Column(name = "latitude")
	private Double lat;
	
	@Column(name = "longitude")
	private Double lon;
	
	@Column(name = "formatted_address")
	private String formattedAddress;
	
	
	public double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInput_address() {
		return input_address;
	}
	public void setInput_address(String input_address) {
		this.input_address = input_address;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	@Override
	public String toString() {
		return "GCResponseVO [lat=" + lat + ", lon=" + lon + ", formattedAddress=" + formattedAddress + "]";
	}
	
	
}
