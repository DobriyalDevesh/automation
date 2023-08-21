package com.company.automation.utils;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class RestAssuredUtils {
	public static String baseURI;

	public static void setBaseURI(String baseURI) {
		RestAssuredUtils.baseURI = baseURI;
	}

	public static CustomResponse get(String endPoint, String authType, String... authParam) {
		CustomResponse customResponse = new CustomResponse();
		URL url = null;
		try {
			url = new URL(baseURI + endPoint);
			RequestSpecBuilder builder = new RequestSpecBuilder();
			switch (authType.toUpperCase()) {
			case "BASIC":
				String credential = null;
				if (authParam != null) {
					credential = authParam[0]; // E.g. User:Token of Jira API
				} else {
					break;
				}
				String encoding = Base64.getEncoder().encodeToString(credential.getBytes(Charset.forName("UTF-8")));
				builder.addHeader("Authorization", "Basic " + encoding);
				break;
			case "TOKEN":
				String token = null;
				if (authParam != null) {
					token = authParam[0];
				} else {
					break;
				}
				builder.addHeader("Authorization", "Bearer " + token);
				break;
			case "NONE":
			default:
				break;
			}
			if (authParam != null && authParam.length >= 2 && StringUtils.isNotEmpty(authParam[1])) {
				builder.addHeader("Content-Type", authParam[1]);
			}
			final Response response = RestAssured.given(builder.build()).when().get(url);
			
			customResponse.setStatusCode(response.getStatusCode());
			customResponse.setResponse(response.body().asString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return customResponse;
	}

	public static CustomResponse post(String endPoint, String requestPayload, String authType, String... authParam) {
		CustomResponse customResponse = new CustomResponse();
		URL url = null;
		try {
			url = new URL(baseURI + endPoint);
			RequestSpecBuilder builder = new RequestSpecBuilder();
			switch (authType.toUpperCase()) {
			case "BASIC":
				String credential = null;
				if (authParam != null) {
					credential = authParam[0]; // E.g. User:Token of Jira API
				} else {
					break;
				}
				String encoding = Base64.getEncoder().encodeToString(credential.getBytes(Charset.forName("UTF-8")));
				builder.addHeader("Authorization", "Basic " + encoding);
				break;
			case "TOKEN":
				String token = null;
				if (authParam != null) {
					token = authParam[0];
				} else {
					break;
				}
				builder.addHeader("Authorization", "Bearer " + token);
				break;
			case "NONE":
			default:
				break;
			}

			if (authParam != null && authParam.length >= 2 && StringUtils.isNotEmpty(authParam[1])) {
				builder.addHeader("Content-Type", authParam[1]);
			}

			builder.addHeader("Accept", "application/json");
			builder.addHeader("Accept-Language", "en-US,en;q=0.8");
			builder.addHeader("User-Agent", "Mozilla/5.0");

			final Response response = RestAssured.given(builder.build()).body(requestPayload).when().post(url);
			customResponse.setStatusCode(response.getStatusCode());
			customResponse.setResponse(response.body().asString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return customResponse;
	}

	public static String getJsonPathVal(String responseString, String jsonPath) {
		try {
			return JsonPath.from(responseString).get(jsonPath);
		} catch (Exception e) {

		}
		return null;
	}

	public static List<Object> getJsonList(String responseString, String jsonPath) {
		try {
			return JsonPath.from(responseString).getList(jsonPath);
		} catch (Exception e) {

		}
		return null;
	}
}