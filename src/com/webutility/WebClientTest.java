package com.webutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebClientTest {
	public static void main (String args[]){
		 try {
			 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					"https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDyjlp97xwjxcy5bmiBvbJC1CflIYDtbZc");
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(
		                         new InputStreamReader((response.getEntity().getContent())));

			String output;
			StringBuilder output2 = new StringBuilder("");
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				//System.out.print(output);
				output2.append(output);
			}
			
			System.out.println(output2.toString());

			httpClient.getConnectionManager().shutdown();

		 }
		 
		 catch (ClientProtocolException e) {
			e.printStackTrace();
		 }
		 
		 catch (IOException e) {
			e.printStackTrace();
		 }
	}
}
