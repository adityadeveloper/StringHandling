package com.landmarkfinder;
import java.util.*;
import com.CSVutility.CSVReader;

public class AddressSplit {
	private Keywords key;
	private List<String> keywords;
	private int addressCount;
	private int landmarkCount;
	
	public int getAddressCount(){
		return addressCount;
	}
	
	public int getLandmarkCount(){
		return landmarkCount;
	}
	
	public AddressSplit(){
		key = new Keywords();
		keywords = key.getKeywords();
	}
	
	public String getLandmark(String address){	
		
		String landmark = null;
		System.out.println("\n"+(++addressCount)+") Given Address : "+address);
		if (address != null && !address.equals("")){
			String lowerCaseAddress = address.toLowerCase();
			for(String keyword : keywords){	
				if (lowerCaseAddress.contains(" "+keyword+" ") || lowerCaseAddress.contains(","+keyword+" ") || lowerCaseAddress.contains("."+keyword+" ") ){
					landmarkCount ++;
					int startIndex = lowerCaseAddress.indexOf(keyword);
					System.out.println("  Keyword found : "+keyword+" & Index of keyword : "+startIndex);
						
					String landmarkWithAddress = address.substring(startIndex+keyword.length());
			
						/*if (landmarkWithAddress.indexOf(",")>0){
							landmark = landmarkWithAddress.substring(0, landmarkWithAddress.indexOf(",")).trim();
							System.out.println("Landmark with commma :"+landmark);
							return landmark;
						}
						else{*/
							System.out.println("  Landmark without comma :"+landmarkWithAddress.trim());
							landmark = landmarkWithAddress.trim();
							return landmark;
						//}
				} //end of if condition for keyword check				
			} //end of for
			
			//System.out.println("    No Landmarks found in the given address");
			return landmark;
		}

		//System.out.println("   No Landmarks found in the given address");
		return landmark;
	}
	
	
	public static void main(String args[]){
		AddressSplit address = new AddressSplit();
		//String search = "B-002, Sukur Garden, opp. Nandibaba temple, Thane (W)";
		//address.getLandmark(search);
		
		List <String> csvList = new CSVReader().getCsvLine("abc.csv");
		//System.out.println(csvList.get(1));
		//System.out.println(address.getLandmark(csvList.get(1)));
		for(String csvAdd :csvList){
			address.getLandmark(csvAdd);
		}
		System.out.println("\nTotal number of addresses provided : "+csvList.size());
		System.out.println("Total Number of landmarks found : "+address.getLandmarkCount());	
		
	}
}