package com.granite.model;

public class GraniteVO {
	private String equipment_status;
	private String device_code;	
	private String facility_id;	
	private String modified_date_time;	
	private String full_address;
	private String country;	
	private String pincode;	
	private String equip_ref_name;	
	private String floor;	
	private String bssid;
	
	public String getEquipment_status() {
		return equipment_status;
	}
	public void setEquipment_status(String equipment_status) {
		this.equipment_status = equipment_status;
	}
	public String getDevice_code() {
		return device_code;
	}
	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}
	public String getFacility_id() {
		return facility_id;
	}
	public void setFacility_id(String facility_id) {
		this.facility_id = facility_id;
	}
	public String getModified_date_time() {
		return modified_date_time;
	}
	public void setModified_date_time(String modified_date_time) {
		this.modified_date_time = modified_date_time;
	}
	public String getFull_address() {
		return full_address;
	}
	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getEquip_ref_name() {
		return equip_ref_name;
	}
	public void setEquip_ref_name(String equip_ref_name) {
		this.equip_ref_name = equip_ref_name;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	
	public String toString(){
		String thisObject = "equipment_status : " + this.getEquipment_status() + "\ndevice_code : " + this.getDevice_code() + "\nfacility_id : " + this.getFacility_id() + "\nmodified_date_time : " + this.getModified_date_time() + "\nfull_address : " + this.getFull_address() + "\ncountry : " + this.getCountry() + "\npincode : " + this.getPincode() + "\nequip_ref_name : " + this.getEquip_ref_name() + "\nfloor : " + this.getFloor() + "\nbssid : " + this.getBssid();
		return thisObject;
	}

}
