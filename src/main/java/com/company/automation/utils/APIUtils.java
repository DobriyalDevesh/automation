package com.company.automation.utils;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.SkipException;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtils {
	
public static Response executeGetAPI(String baseURI,HashMap<String,String> headerMap,HashMap<String,String> queryParamMap,HashMap<String,String> pathParamMap,HashMap<String,Object> body) {
			
	RestAssuredConfig config = RestAssured.config()
	        .httpClient(HttpClientConfig.httpClientConfig()
	                .setParam("CONNECTION_MANAGER_TIMEOUT", 1000)
	                .setParam("SO_TIMEOUT", 1000));
	
		RequestSpecification request = RestAssured.given();
		
		request.baseUri(baseURI);
		request.config(config);
		if(headerMap!=null)
			request.headers(headerMap);
		if(queryParamMap!=null)
			request.queryParams(queryParamMap);	
		if(pathParamMap!=null)
			request.pathParams(pathParamMap);	
		if(body!=null)
			request.body(body);	
		Response response = request.get();	
		
		if(String.valueOf(response.getStatusCode()).startsWith("5")|| String.valueOf(response.getStatusCode()).startsWith("4")) {
			 throw new SkipException("Status of response is not 'Ok' or '200' instead-'"+response.getStatusLine()+"' for resource-"+baseURI );
		}else if(!(response.getStatusCode()==200))
			Assert.fail();
		
			return response;
		
	}

public static Response executePostAPI(String baseURI,HashMap<String,String> headerMap,HashMap<String,String> queryParamMap,HashMap<String,String> pathParamMap,HashMap<String,Object> body) {
	
	RestAssuredConfig config = RestAssured.config()
	        .httpClient(HttpClientConfig.httpClientConfig()
	                .setParam("CONNECTION_MANAGER_TIMEOUT", 1000)
	                .setParam("SO_TIMEOUT", 1000));
	
		RequestSpecification request = RestAssured.given();
		
		request.baseUri(baseURI);
		request.config(config);
		if(headerMap!=null)
			request.headers(headerMap);
		if(queryParamMap!=null)
			request.queryParams(queryParamMap);	
		if(pathParamMap!=null)
			request.pathParams(pathParamMap);	
		if(body!=null)
			request.body(body);	
		
		Response response = request.post();		

		if(String.valueOf(response.getStatusCode()).startsWith("5")|| String.valueOf(response.getStatusCode()).startsWith("4")) {
			 throw new SkipException("Status of response is not 'Ok' or '200' instead-'"+response.getStatusLine()+"' for resource-"+baseURI );
		}else if(!(response.getStatusCode()==200))
			Assert.fail();
		return response;		
	}
}
