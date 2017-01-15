package com.landmarkfinder;
import java.util.*;

public class AddressSplit {
	Keywords key;
	ArrayList<String> keywords;
	AddressSplit(){
		key = new Keywords();
		keywords = key.getKeywords();
	}
	
	public String getLandmark(String address){	
		String landmark = null;
		System.out.println("Given Address : "+address);
		for(String keyword : keywords){	
			int startIndex = address.toLowerCase().indexOf(" "+keyword.toLowerCase()+" ");
			if (startIndex>0){
				System.out.println("Keyword found : "+keyword+" & Index of keyword : "+startIndex);
				
				String landmarkWithAddress = address.substring(startIndex+keyword.length()+1);
				
				if (landmarkWithAddress.indexOf(",")>0){
					landmark = landmarkWithAddress.substring(0, landmarkWithAddress.indexOf(",")).trim();
					System.out.println("Landmark with commma :"+landmark);
					return landmark;
				}
				else{
					System.out.println("Landmark without comma :"+landmarkWithAddress.trim());
					landmark = landmarkWithAddress.trim();
					return landmark;
				}
			}					
		}
		System.out.println("No Landmarks found in the given address");
		return landmark;
	}
	
	
	public static void main(String args[]){
		AddressSplit address = new AddressSplit();
		String search = "B-002, Sukur Garden, near Nandibaba temple, Thane (W)";
		address.getLandmark(search);
	}
}
