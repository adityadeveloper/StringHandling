package com.custompoi;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class CustomPoiUtil {
	
	public static int addPoi(CustomPoiVO customPoiVO){
		
		Gson gson = new Gson();
		JSONObject poiJson;
		JSONObject poiJson1 = new JSONObject();
		try {
			poiJson = (JSONObject)(new JSONParser().parse(gson.toJson(customPoiVO)));
			poiJson1.put("ab", poiJson);
			System.out.println(poiJson);
			System.out.println(poiJson1.toJSONString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 1;
	}
	
	public static void main(String args[]){
	
		CustomPoiVO customPoiVO = new CustomPoiVO();
		customPoiVO.setLat(10.555);
		customPoiVO.setLon(22.55);
		customPoiVO.setName("abc");
		
		CustomPoiUtil.addPoi(customPoiVO);
	}
}
