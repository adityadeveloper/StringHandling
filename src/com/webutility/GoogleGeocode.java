package com.webutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.configurations.ConfigReader;
import com.granite.model.GCResponseVO;
import com.utility.HttpConnection;

public class GoogleGeocode {
	private static final String GOOGLE_KEY;
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		GOOGLE_KEY = config.getGoogle_key();
	}
	
	static Logger logger = Logger.getLogger(GoogleGeocode.class);
	
	public static GCResponseVO googleGeocode (String address){
		GCResponseVO responseVO = new GCResponseVO();
		CloseableHttpClient httpClient = null;;
		
		try{
			address = URLEncoder.encode(address, "UTF-8");
		}
		catch(UnsupportedEncodingException e){
			logger.error("Error occurred !!!", e);
		}
		
		try {
	        httpClient = HttpConnection.getHttpConnection();
	        
			String gcUrl = "https://maps.googleapis.com/maps/api/geocode/json?adress="+address+"&key="+GOOGLE_KEY;
			
			HttpGet getRequest = new HttpGet(gcUrl);
			getRequest.addHeader("accept", "application/json");
			
			logger.info("Calling Google Geocode service : "+ gcUrl);
			
			HttpResponse response = httpClient.execute(getRequest);
		
			if (response.getStatusLine().getStatusCode() != 200) {
					throw new InvalidStatusException(response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			StringBuilder output2 = new StringBuilder("");
			while ((output = br.readLine()) != null) {
				output2.append(output);	
			}
			
			httpClient.close();
			
			try{
				JSONObject googleResponse = (JSONObject)new JSONParser().parse(output2.toString());
				logger.info("JSONResponse : "+googleResponse);
				
				String status = (String)googleResponse.get("status");
				logger.info("Status : "+status);
				
				if(status.equals("OK")){
					JSONArray results = (JSONArray)googleResponse.get("results");
					JSONObject result0 = (JSONObject)results.get(0);
					String formatted_address = (String)result0.get("formatted_address");
					logger.info("formatted_address : "+formatted_address);
					responseVO.setFormattedAddress(formatted_address);
					
					JSONObject geometry = (JSONObject)result0.get("geometry");
					JSONObject location = (JSONObject)geometry.get("location");
					double lat = (double)location.get("lat");
					double lon = (double)location.get("lng");
					logger.info("Latitude : "+lat);
					logger.info("Longitude : "+lon);
					
					responseVO.setLat(lat);
					responseVO.setLon(lon);
				}
				else{
					logger.info("No result found");
				}
			}
			
			catch(ParseException pex){
				logger.error("Exception occurred while parsing JSON", pex);
			}
			return responseVO;
		 }
		
		 catch(InvalidStatusException e){
			logger.error("Exception occured !!!", e);
			return responseVO;
		 }
		
		 catch (ClientProtocolException e) {
			 logger.error("Exception Occurred !!!", e);
			 return responseVO;
		 }
		 
		 catch (IOException e) {
			logger.error("Exception Occurred !!!", e);
			return responseVO;
			
		 }
		
		finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("Exception occured while closing the HttpClient", e);
			}
		}
	}
	
	public static void main(String args[]){
			GCResponseVO gc = GoogleGeocode.googleGeocode("bhbhb,bhvghv,Thane");
			logger.info(gc.toString());
	}	
}
