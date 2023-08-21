package com.company.automation.api.controller;

import static io.restassured.RestAssured.given;

import com.company.automation.api.reqres.in.ControllersHolder;
import com.company.automation.api.reqres.in.Endpoints;
import com.company.automation.api.setup.BaseController;

import io.restassured.response.Response;

public class AdEventsController extends BaseController {
	private ControllersHolder controllersHolder;
	private Endpoints endpoints;

	public AdEventsController(ControllersHolder holder) {
		controllersHolder = holder;
		endpoints = controllersHolder.endpoints();
	}

	/**
	 * Returns list of all notes of well
	 * 
	 * @return Response object
	 */
	public Response getadData() {

		Response response = 
				given()
				 .header("X-Correlation-Id", "test-2020-10-29")
				 .header("Content-Type", "application/json").header("X-Country", "123").header("X-AppName", "abc")
				 .header("X-business-group", "12").header("client-id", "3466bc431a3742aca2ffb043ec14b520")
				 .header("client-secret", "1c237D6106284082913AbFE3a64128A3").header("X-Region", "abc")
				 .header("X-txn-datetime", "2020-11-06T21:00:00").
				when()
				 .get(endpoints.getAdEventsController.getEdData()).
				then()
				 .extract().response();
		logResponse(response);
		return response;

	}

	public Response getReceiptData() {
		Response response = 
				given()
				 .header("User-Agent", "PostmanRuntime/7.26.8").header("Accept", "*/*")
				 .header("Host", "dev.pvdalext.jnj.com").header("Accept-Encoding", "gzip, deflate, br")
				 .header("Connection", "keep-alive")
				 .header("Cookie", "__cfduid=d29f18413ce066e4ce9b29501572730941610377647").
				when()
				 .get(endpoints.getReceiptDetailsController.getallReceiptsData()).
				then()
				 .extract().response();
		logResponse(response);
		return response;
	}

	public Response postprotoPatierntNoCOuntryData() {
		String payload = "{\r\n" + "\"STUDY_PATIENT_NO\":\"7fSzfJDBQV/h91HTJliDjcoqKMeFBPEJeUz9Bn2BKPQ=\",\r\n"
				+ "\"PRIMARY_SOURCE_COUNTRY\":\"028\",\r\n" + "\"PROTOCOL_NO\":\"RRA-5319\",\r\n"
				+ "\"RECEIPT_NO\":\"RCT8817015\",\r\n" + "\"AGX_WFA_ACTIVITY_NAME\":[\"Imported Cases\"]\r\n" + "}";
		
		Response response = 
				given()
				 .header("Content-Type", "application/json").body(payload).
				when()
				 .post(endpoints.postPPNoCountryController.postPPNcountryData()).
				then()
				 .extract().response();

		logResponse(response);
		return response;
	}

}
