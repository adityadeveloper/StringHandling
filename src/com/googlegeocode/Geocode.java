package com.googlegeocode;

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
import com.utility.HttpConnection;

public class Geocode {
	private static final String GOOGLE_KEY;
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		GOOGLE_KEY = config.getGoogle_key();
	}
	
	static Logger logger = Logger.getLogger(Geocode.class);
		
}
