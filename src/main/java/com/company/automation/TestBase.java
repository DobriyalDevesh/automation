package com.company.automation;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.google.common.base.Enums;
import com.company.automation.api.setup.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import lombok.Setter;


@Setter
@Listeners(TestBase.class)
public class TestBase implements IExecutionListener {
	private HostNames hostNames;
	private String cycleVersionId = System.getProperty("cycleVersionId");

	
	public ExtentReports extent = new ExtentReports(
			System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
	public ExtentTest logger;
	public static final Properties props = new Properties();
	public static final Properties envProps = new Properties();

	public TestBase() {
		loadProperties();
		loadEnvironmentProperties();
		hostNames = new HostNames(envProps);
	}

	/**
	 * TestNG onExecutionStart() method
	 */
	@Override
	public void onExecutionStart() {
		setLogPath();
	}

	/**
	 * The annotated method will be run before each test method.
	 * 
	 * @param ctx    (ITestContext) test context to retrieve parameters from
	 * @param method the test method to be run
	 * @throws MalformedURLException - Bad URL
	 */
	@BeforeMethod
	public void beforeMethod(ITestContext ctx, Method method) throws MalformedURLException {
		
	}

	/**
	 * The annotated method will be run after each test method.
	 *
	 * @param result records the test method name that is tearing down
	 * @throws IOException IO exception
	 */
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		
	}

	@BeforeTest
	public void beforeTest(ITestContext ctx) {
		
	}

	/**
	 * The annotated method will be run after all the test methods belonging to the
	 * classes inside the {@code <test>} tag have run.
	 *
	 */
	@AfterTest()
	public void afterTest() {
		
	}

	/**
	 * TestNG tests are complete
	 */
	@Override
	public void onExecutionFinish() {
		generateLogs();
	}

	// region properties
	/**
	 * This method is to load the static properties in one place
	 */
	private void loadProperties() {
		
	}

	/**
	 * This method is to load the environment properties in one place
	 */
	private void loadEnvironmentProperties() {
		
	}
	// endregion

	/**
	 * Executes after each test in suite. Report.
	 *
	 * @param result Result of the test run
	 */
	private void reportTearDownMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
			testFail(result);
		} else if (result.getStatus() == ITestResult.SKIP) {
			testSkip(result);
		} else {
			testPass(result);
		}
	}

	/**
	 * Adds a starting log record to visually separate each test in the log file.
	 *
	 * @param method The test method to be run
	 */
	private void logDividerSetup(Method method) {
		
	}

	/**
	 * Adds an ending log record to visually separate each test in the log file.
	 *
	 * @param result Result of the test run
	 */
	private void logDividerTearDown(ITestResult result) {
		
	}

	/**
	 * Custom outcome for a 'Passed' test result. Writes the result to the console,
	 * and log file.
	 *
	 * @param result Record the test passed
	 */
	private void testPass(ITestResult result) {
		
	}

	/**
	 * Custom outcome for a 'Failed' test result. Writes the result to the console,
	 * and log file.
	 *
	 * @param result Record the test failed
	 */
	private void testFail(ITestResult result) {
		
	}

	/**
	 * Custom outcome for a 'Skipped' test. Writes the result to the console, and
	 * log file.
	 *
	 * @param result Record the test was skipped
	 */
	private void testSkip(ITestResult result) {
		
	}

	/**
	 * This method uses a testng listener to merge all the log files at the end of a
	 * test run into one sorted log
	 */
	private void generateLogs() {
		
	}

	/**
	 * Sets system property for log path
	 */
	private void setLogPath() {
		
	}

	/**
	 * Parses the Suite 'name' property from the testng.xml file provided at runtime
	 * via the -DsuiteXmlFile property to capture the cycle ID that needs to be
	 * cloned to the new release. Expected format "{name}:{cycleID}"
	 * 
	 * @param xmlSuiteName'name' property from the testng.xml file
	 * @return Cycle ID to be cloned
	 */
	private String getCloneCycleId(String xmlSuiteName) {
		return xmlSuiteName.split(":")[1];
	}

	// getters
	public HostNames getHostNames() {
		return hostNames;
	}

	// region Extent Report Methods
	private void startReport() {
		extent.addSystemInfo("Host Name", "SoftwareTestingMaterial").addSystemInfo("Environment", "Automation Testing");
		// loading the external xml file (i.e., extent-config.xml) which was placed
		// under the base directory
		// You could find the xml file below. Create xml file in your project and copy
		// past the code mentioned below
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}

	private void getResult(ITestResult result) throws IOException {
		
	}

	public static String capture(EventFiringWebDriver driver) throws IOException {
		File scrFile = driver.getScreenshotAs(OutputType.FILE);
		File dest = new File(String.format("src/../test-output/%s.png", System.currentTimeMillis()));
		String errflpath = dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, dest);
		return errflpath;
	}
	// endregion
}