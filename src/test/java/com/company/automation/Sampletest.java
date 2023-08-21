package com.company.automation;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import com.company.automation.TestResultRunControllerList;
import com.company.automation.driver.sEnv;
import com.company.automation.DTO.DataDTO;
//
//import com.birlasoft.TypeClasses.DTOexception;
//import com.birlasoft.TypeClasses.MappingExcelData;
//import com.birlasoft.TypeClasses.ProcessingSummary;
//import com.birlasoft.automation.DataSearch.OutputData;
//import com.birlasoft.automation.DataSearch.ProcessDuplicateReceipts;
//import com.birlasoft.automation.DataSearch.ProcessDuplicateReceipts_AL;
//import com.birlasoft.automation.api.setup.ApiTestBase;
//import com.birlasoft.automation.pages.ArisG.FollowUpArisG;
//import com.birlasoft.automation.pages.ArisG.HomePageArisG;
//import com.birlasoft.automation.pages.ArisG.LoginPageArisG;
//import com.birlasoft.utils.DataLoaders;
//import com.birlasoft.utils.ExcelMappingOperations;
//import com.birlasoft.utils.ExceptionDBUtil;
//import com.birlasoft.utils.UIUtils;
import com.company.automation.api.reqres.in.ApiSetup;
import com.company.automation.driver.TestConfig;
import com.company.automation.framework.LogMe;
import com.company.automation.utils.UIUtils;

@Listeners(com.company.automation.TestResultRunControllerList.class)
public class Sampletest extends ApiTestBase {

	public LogMe LOGGER;
	public String excelName;
	String data;

	@BeforeTest
	public void setUp(ITestContext context) throws IOException {
		try {
			TestConfig.getInstance().suiteSetup();
			env_setUp(context);
			LOGGER = new LogMe(Sampletest.class);
			LOGGER.logInfo("*********EXECUTION STARTED**********\n\n");
			ApiSetup.baseUri = context.getAttribute("ApiUrl").toString();
			ApiSetup.HOST = context.getAttribute("ApiHost").toString();
//			ApiSetup.CLIENTID = context.getAttribute("ApiClientID").toString();
//			ApiSetup.CLIENTSECRET = context.getAttribute("ApiClientSecret").toString();
		} catch (Exception e) {
			LOGGER.logError("Exception " + e.getClass().getName() + " caught from suite setup method", e);
		}
	}

	@Test(description = "Sample tests", priority = 1, retryAnalyzer = TestResultRunControllerList.class, enabled = true)
	public void getUserID() throws Exception {
		ProcessRequest.setRetryCount(TestResultRunControllerList.getRetryCount());
		try {
			ProcessRequest obj = new ProcessRequest();
			ArrayList<DataDTO> dataObj = new ArrayList<DataDTO>();
			dataObj = obj.FetchFullData(LOGGER);
			System.out.println(dataObj.get(0).getData().get(0).getAvatar().toString());
			System.out.println(dataObj.get(0).getData().get(0).getEmail().toString());
			System.out.println(dataObj.get(0).getData().get(0).getFirstName().toString());
			Assert.assertEquals(dataObj.get(0).getData().get(0).getFirstName().toString(),"Michael");

		} catch (Exception e) {
			LOGGER.logError("Exception Found in the @Test Block" + e);
		}
		ITestContext context = Reporter.getCurrentTestResult().getTestContext();
	}
	
	public void env_setUp(ITestContext context) {

		System.out.println("Reading Controller parameters");
//		String dbConnection = TestConfig.getConfig().getPropertyValue("dbConnection");
		String AppUrl = TestConfig.getConfig().getPropertyValue("applicationUrl");
//		String attachment = TestConfig.getConfig().getPropertyValue("attach_AutomationLogPath");
//		String SSO = TestConfig.getConfig().getPropertyValue("SSO_Login");
//		String SSO_Server = TestConfig.getConfig().getPropertyValue("SSO_Login_Server");
		String Url = "", Url_SSO = "", ApiUrl = "", ApiHost = "", ApiClientId = "", ApiCLientSecret = "";
//		context.setAttribute("dbConnection", dbConnection);
//		context.setAttribute("attachment", attachment);
//		context.setAttribute("SSO", SSO);
//		context.setAttribute("SSO_Server", SSO_Server);

		switch (AppUrl.toLowerCase()) {
		case "dev":
//			Url = sEnv.DEV.getArisgURL();
//			Url_SSO = sEnv.DEV.getArisgURL_SSO();
			ApiUrl = sEnv.DEV.getApiURL();
			ApiHost = sEnv.DEV.getHostAuth().getHost();
//			ApiClientId = sEnv.DEV.getHostAuth().getClientId();
//			ApiCLientSecret = sEnv.DEV.getHostAuth().getClientSecret();
			break;
		case "qa":
//			Url = sEnv.QA.getArisgURL();
//			Url_SSO = sEnv.QA.getArisgURL_SSO();
			ApiUrl = sEnv.QA.getApiURL();
			ApiHost = sEnv.QA.getHostAuth().getHost();
//			ApiClientId = sEnv.QA.getHostAuth().getClientId();
//			ApiCLientSecret = sEnv.QA.getHostAuth().getClientSecret();
			break;
		case "prod":
//			Url = sEnv.PROD.getArisgURL();
//			Url_SSO = sEnv.PROD.getArisgURL_SSO();
			ApiUrl = sEnv.PROD.getApiURL();
			ApiHost = sEnv.PROD.getHostAuth().getHost();
//			ApiClientId = sEnv.PROD.getHostAuth().getClientId();
//			ApiCLientSecret = sEnv.PROD.getHostAuth().getClientSecret();
			break;
		}
//		context.setAttribute("Url", Url);
//		context.setAttribute("Url_SSO", Url_SSO);
		context.setAttribute("ApiUrl", ApiUrl);
		context.setAttribute("ApiHost", ApiHost);
//		context.setAttribute("ApiClientID", ApiClientId);
//		context.setAttribute("ApiClientSecret", ApiCLientSecret);
	}

	
	@AfterTest(enabled = true)
	public void tearDown(ITestContext context) throws Exception {
		for (ITestResult a : context.getFailedTests().getAllResults()) {
			System.out.println(a.getThrowable().getMessage());
		}
		// ExcelMappingOperations.closeWorkbook();
	}
	
}
