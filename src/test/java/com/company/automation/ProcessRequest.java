package com.company.automation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.company.automation.DTO.DataDTO;
import com.company.automation.api.reqres.in.ApiSetup;
import com.company.automation.utils.APIUtils;
import com.company.automation.DTO.DataDTO;
import com.company.automation.api.controller.AdEventsController;
import com.company.automation.api.reqres.in.ApiSetup;
import com.company.automation.framework.LogMe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.restassured.response.Response;

public class ProcessRequest {

	static Logger LOGGER = Logger.getLogger(ProcessRequest.class);
	private AdEventsController adEventCtrl;
	public ITestResult result;
	public static int retryCount;

	
	public static void setRetryCount(int retryCnt) {
		retryCount=retryCnt;
	}
	
	public ProcessRequest() {

	}

	public List<String> GetRecordsToProcess() throws ClassNotFoundException, SQLException {
		return null;

	}

	public ArrayList<DataDTO> FetchFullData(LogMe LOGGER) throws ClassNotFoundException, SQLException {
		ArrayList<DataDTO> fulldata = new ArrayList<DataDTO>();
		boolean result = true;
		try {
			ApiSetup receiptRequest = new ApiSetup("listUsers");
			receiptRequest.replaceRequestParams("queryParam", "page", "2");

			Response resReceiptData = APIUtils.executeGetAPI(receiptRequest.endPoint, receiptRequest.headers,
					receiptRequest.queryParam, receiptRequest.pathParam, receiptRequest.body);
			LOGGER.logInfo("Reponse Code for Advere event is " + resReceiptData.getStatusCode());
			LOGGER.logInfo("Reponse for Advere event is " + resReceiptData.getBody().asString());

			/* Deserializing the JSON Object to java object */
			Gson gson1 = new Gson();

			fulldata = new ArrayList<>(
					Arrays.asList(new GsonBuilder().create().fromJson(resReceiptData.asString(), DataDTO.class)));

		} catch (RuntimeException e) {
			if (!(e instanceof JsonSyntaxException))
				throw new SkipException("Exception caught in Api Request with below\n" + e.getMessage(), (Throwable) e);
			else
				e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fulldata;
	}

}
