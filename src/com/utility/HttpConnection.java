package com.utility;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.configurations.ConfigReader;

public class HttpConnection {
	private static final int HTTP_CONN_TIMEOUT;
	private static final int HTTP_SOCKET_TIMEOUT;
	
	static{
		ConfigReader config = ConfigReader.getInstance();
		HTTP_CONN_TIMEOUT = config.getHttp_connection_timeout();
		HTTP_SOCKET_TIMEOUT = config.getHttp_socket_timeout();
	}
	
	static Logger logger = Logger.getLogger(HttpConnection.class);
	
	public static CloseableHttpClient getHttpConnection (){

	        RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(HTTP_CONN_TIMEOUT)
	                .setSocketTimeout(HTTP_SOCKET_TIMEOUT).build();
	        
	        CloseableHttpClient httpClient = HttpClients.custom()
	                .setDefaultRequestConfig(requestConfig).build();
	        
	        return httpClient;
	}
}
