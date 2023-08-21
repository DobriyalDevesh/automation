package com.company.automation.driver;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.company.automation.framework.ObjectRepository;
//import com.company.framework.ObjectRepository;
import com.company.automation.utils.Config;
//import com.company.utils.ExcelObject;
import com.company.automation.utils.ExcelObject;

public class TestConfig {
	static Logger LOGGER = Logger.getLogger(TestConfig.class);

	private static TestConfig testConfig;

	private static Config config;
	private static Config dataConfig;
	//private static Config emailBodyConfig;
	private static String configWorkbook;
	private static String testMappingWorkbook;
	private ObjectRepository objRep;
	private String appBaseURL;
	private String apiBaseURL;

	private String reportPath;
	private String screenShotPath;

	private boolean remoteExecution = false;
	private String gridURL;

	private String dbConnString;

	private String apiAuth;

	private String execEnvironment;

	private String browserName;
	
	private String deviceResolution;
	private String excelDateFormat;
	
	private TestConfig() {
	}

	static {
		try {
			config = new Config("Framework\\Test_Config\\config.properties");
			dataConfig = new Config("Framework\\Test_Data\\test.properties");
			//emailBodyConfig= new Config("Framework\\Test_Config\\emailBodyConfig.properties");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// System.setProperty("log4j.configurationFile", "log4j.properties");
		PropertyConfigurator.configure("log4j.properties");

		try {
			configWorkbook = new File("Framework\\Test_config\\Config.xlsx").getCanonicalPath();
		} catch (IOException e) {
			LOGGER.error("Unable to find Config workbook", e);
		}
	}

	public static TestConfig getInstance() {
		if (testConfig == null) {
			testConfig = new TestConfig();
		}
		return testConfig;
	}

	public static Config getConfig() {
		return config;
	}

	public static Config getDataConfig() {
		return dataConfig;
	}
	public static Config getTestMappingShet() {
		return dataConfig;
	}
//	public static Config getemailBodyConfig() {
//		return emailBodyConfig;
//	}
//	
	
	
	public String getDeviceResolution() {
		return deviceResolution;
	}

	public static String getConfigWorkbook() {
		return configWorkbook;
	}

	public ObjectRepository getObjRep() {
		return objRep;
	}

	public String getReportPath() {
		return reportPath;
	}

	public String getScreenShotPath() {
		return screenShotPath;
	}

	public String getAppBaseURL() {
		return appBaseURL;
	}

	public String getApiBaseURL() {
		return apiBaseURL;
	}

	public String getApiAuth() {
		return apiAuth;
	}

	public boolean isRemoteExecution() {
		return remoteExecution;
	}

	public String getGridURL() {
		return gridURL;
	}

	public String getDbConnString() {
		return dbConnString;
	}

	public String getExecEnvironment() {
		return execEnvironment;
	}
	
	public String getBrowserName() {
		return browserName;
	}
	
	public String getExcelDateFormat() {
		return excelDateFormat;
	}

	// Framework Initialization
	public void suiteSetup(String database) {
		try {
			frameworkSetup();
			databaseSetup(database);
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getClass().getName() + " caught from suite setup method", e);
		}
	}

	// Framework Initialization
	public void suiteSetup() {
		try {
			frameworkSetup();
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getClass().getName() + " caught from suite setup method", e);
		}
	}

	private void frameworkSetup() throws IOException {
		ExcelObject tcExcel = new ExcelObject(configWorkbook, "Config");

		objRep = new ObjectRepository("Framework\\OR\\ObjectRepository.xlsx");
		reportPath = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=ReportsPath")).trim();
		screenShotPath = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=ScreenshotPath")).trim();
		appBaseURL = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=AppBaseURL")).trim();
		browserName = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=Browser")).trim();
		deviceResolution = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=Resolution")).trim();
		

		if ("Yes".equalsIgnoreCase(
				String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=RemoteExecution")).trim())) {
			remoteExecution = true;
		}

		gridURL = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=GridURL")).trim();

		execEnvironment = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=Environment")).trim();

		tcExcel.closeWorkbook();
	}

	private void databaseSetup(String database) throws IOException {
		// Create Connection String
		ExcelObject tcExcel = new ExcelObject(configWorkbook, "Config");

		String dbHost = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBHost")).trim();
		String dbInstance = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBInstance")).trim();
		String dbSID = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBName")).trim();
		String dbAuth = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBAuthentication")).trim();

		switch (database.toUpperCase()) {
		case "SQL SERVER":
		case "SQLSERVER":
			if ("Trusted".equalsIgnoreCase(dbAuth)) {
				dbConnString = "jdbc:sqlserver://" + dbHost + ";instanceName=" + dbInstance + ";databaseName" + dbSID
						+ ";IntegratedSecurity=true;AuthenticationMethod=ntlm;";
			} else {
				String dbUser = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBUserName")).trim();
				String dbPwd = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBPassword")).trim();
				dbConnString = "jdbc:sqlserver://" + dbHost + ";instanceName=" + dbInstance + ";databaseName" + dbSID
						+ ";user=" + dbUser + ";password=" + dbPwd + ";";
			}
			break;
		case "MY SQL":
		case "MySQL":
			if ("Trusted".equalsIgnoreCase(dbAuth)) {
				dbConnString = "jdbc:mysql://" + dbHost + "/" + dbSID
						+ "?IntegratedSecurity=true&AuthenticationMethod=ntlm";
			} else {
				String dbUser = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBUserName")).trim();
				String dbPwd = String.valueOf(tcExcel.getCellValue("Config", "Value", "Key=DBPassword")).trim();
				dbConnString = "jdbc:mysql://" + dbHost + "/" + dbSID + "?user=" + dbUser + "&password=" + dbPwd;
			}
			break;
		default:
			break;
		}
		tcExcel.closeWorkbook();
	}
}