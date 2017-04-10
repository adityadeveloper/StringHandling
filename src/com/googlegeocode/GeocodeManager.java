package com.googlegeocode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.configurations.ConfigReader;
import com.opencsv.CSVReader;
import com.utility.HttpConnection;

public class GeocodeManager {
	static Logger logger = Logger.getLogger(GeocodeManager.class);
	private GeocodeDAO geocodeDAO = new GeocodeDAO();
	private static final String GOOGLE_KEY; 
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		GOOGLE_KEY = config.getGoogle_key();
	}
	
	public void saveGeocodeResponse(GeocodeResponseVO responseVO){
		geocodeDAO.insertOrUpdate(responseVO);
	}

	
	public List<GeocodeResponseVO> getAddressList(String jobId){
		return geocodeDAO.getList(jobId);
	}
	
	
	public List<GeocodeResponseVO> createAddressList(String csvFileName, String jobId){
		List<GeocodeResponseVO> list = new ArrayList<GeocodeResponseVO>();
        CSVReader reader = null;
        try {
			logger.info("CSV file reading started");
            reader = new CSVReader(new FileReader(csvFileName));
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
            	GeocodeResponseVO oneVO = new GeocodeResponseVO();
            	oneVO.setInput_address(line[2]);
            	oneVO.setJob_id(jobId);
            	list.add(oneVO);
            }
            return list;
        }    
        catch(Exception e){
        	logger.error("Exception occurred !!!", e);
        	return list;
        }
        finally{
        	try {
				reader.close();
			} catch (IOException e) {
				logger.error("Exception occurred !!!", e);
			}
        }
	}

	
	public static ResponseVO googleGeocode (String address){
		ResponseVO responseVO = new ResponseVO();
		CloseableHttpClient httpClient = null;;
		try {
			Thread.sleep(1000);
			address = URLEncoder.encode(address, "UTF-8");
	        httpClient = HttpConnection.getHttpConnection();
			String gcUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+GOOGLE_KEY;
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
					responseVO.setFormattedAddress(null);
					responseVO.setLat(0.0);
					responseVO.setLon(0.0);
					return responseVO;
				}
			}
			catch(ParseException pex){
				logger.error("Exception occurred while parsing JSON", pex);
				responseVO.setFormattedAddress(null);
				responseVO.setLat(0.0);
				responseVO.setLon(0.0);
			}
			return responseVO;
		 }	
		 catch(Exception e){
			 logger.error("Exception Occurred !!!", e);
				responseVO.setFormattedAddress(null);
				responseVO.setLat(0.0);
				responseVO.setLon(0.0);
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
	
	public GeocodeResponseVO GeocodeMain(GeocodeResponseVO addressVO){
		String inputAddress = addressVO.getInput_address().trim();
		String[] addressArray = inputAddress.split(",");
		String inputAddress2 = "";
		ResponseVO responseVO = new ResponseVO();
		
		responseVO = googleGeocode(inputAddress);
		if(isLatLonWithinIndia(responseVO.getLat(),responseVO.getLon())){
			addressVO.setLat(responseVO.getLat());
			addressVO.setLon(responseVO.getLon());
			addressVO.setFormattedAddress(responseVO.getFormattedAddress());
			return addressVO;
		}
		
		for(int i=0; i<addressArray.length; i++){
			inputAddress2 = "";
			for(int j=i; j<addressArray.length; j++){
				inputAddress2 = inputAddress2 + "," + addressArray[j];
			}
			inputAddress2 = inputAddress2.substring(1);
			responseVO = googleGeocode(inputAddress2);
			if(isLatLonWithinIndia(responseVO.getLat(),responseVO.getLon())){
				addressVO.setLat(responseVO.getLat());
				addressVO.setLon(responseVO.getLon());
				addressVO.setFormattedAddress(responseVO.getFormattedAddress());
				return addressVO;
			}	
		}
		
		addressVO.setLat(0.0);
		addressVO.setLon(0.0);
		addressVO.setFormattedAddress(null);
		return addressVO;
	}
	
	
	
    public Boolean isLatLonWithinIndia(Double lat, Double lon)
    {
    	Boolean isWithinIndia = false;
    	try
    	{
    		if (lat >= 6.75981999 && lat <= 37.08520002 && lon >= 68.17082508 && lon <= 97.40479284)
    		{
    			isWithinIndia = true;
    		}
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return isWithinIndia;
    }
    
    
    
	public static void main(String args[]){
/*		GeocodeResponseVO gc = Geocode.googleGeocode("Sukur Garden, Dhokali Naka,Thane");
		GeocodeResponseVO gc2 = Geocode.googleGeocode("Dhokali Naka,Thane");
		//gc.setId(1);
*/
		//GeocodeManager manager = new GeocodeManager();
/*		manager.saveGeocodeResponse(gc);
		manager.saveGeocodeResponse(gc2);*/
		
/*		List<GeocodeResponseVO> myList = manager.createAddressList("gc.csv","1234");
		int a = 1;
		for (GeocodeResponseVO one : myList){
			logger.info(a + ")" + one.getInput_address());
			manager.saveGeocodeResponse(one);
			a++;
		}*/
		
		//logger.info("count of records : " + manager.getAddressList("1234").size());
		String address = "Sukur Garden, Dhokali Naka,Thane";
		String[] addressArray = address.split(",");
		String inputAddress2 = "";
		
		for(int i=0; i<addressArray.length; i++){
			inputAddress2 = "";
			for(int j=i; j<addressArray.length; j++){
				inputAddress2 = inputAddress2 + "," + addressArray[j];
			}
			inputAddress2 = inputAddress2.substring(1);
			logger.info(inputAddress2);
		}	

		
	}
}
