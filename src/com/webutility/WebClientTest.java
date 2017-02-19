package com.webutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.configurations.ConfigReader;
import com.granite.model.GCResponseVO;

public class WebClientTest {
	private static final String GOOGLE_KEY;
	private static final int HTTP_CONN_TIMEOUT;
	private static final int HTTP_SOCKET_TIMEOUT;
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		GOOGLE_KEY = config.getGoogle_key();
		HTTP_CONN_TIMEOUT = config.getHttp_connection_timeout();
		HTTP_SOCKET_TIMEOUT = config.getHttp_socket_timeout();
	}
	
	static Logger logger = Logger.getLogger(WebClientTest.class);
	
	public static GCResponseVO googleGeocode (String address){
		GCResponseVO responseVO = new GCResponseVO();
		
		try{
			address = URLEncoder.encode(address, "UTF-8");
		}
		catch(UnsupportedEncodingException e){
			logger.error("Error occurred !!!", e);
		}
		
		try {
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(HTTP_CONN_TIMEOUT)
	                .setSocketTimeout(HTTP_SOCKET_TIMEOUT).build();
	        
	        CloseableHttpClient httpClient = HttpClients.custom()
	                .setDefaultRequestConfig(requestConfig).build();
	        
			String gcUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+GOOGLE_KEY;
			
			HttpGet getRequest = new HttpGet(gcUrl);
			getRequest.addHeader("accept", "application/json");
			
			logger.info("Calling Google Geocode service : "+ gcUrl);
			
			HttpResponse response = httpClient.execute(getRequest);
		
			if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			StringBuilder output2 = new StringBuilder("");
			while ((output = br.readLine()) != null) {
				output2.append(output);
			}

			httpClient.close();
			
			try{
				responseVO = new GCResponseVO();
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
				return responseVO;
			}
			
			catch(ParseException pex){
				logger.error("Exception occurred while parsing JSON", pex);
				return responseVO;
			}
		 }
		
	
		 
		 catch (ClientProtocolException e) {
			 logger.error("Exception Occurred !!!", e);
			 return responseVO;
		 }
		 
		 catch (IOException e) {
			logger.error("Exception Occurred !!!", e);
			return responseVO;
		 }
	}
	
	
	public static void main(String args[]){
			GCResponseVO gc = WebClientTest.googleGeocode("Thane");
			System.out.println(gc.toString());
			
	}	
}
