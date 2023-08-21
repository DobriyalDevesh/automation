package com.company.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SessionUtils {
	private static Logger LOGGER = Logger.getLogger(SessionUtils.class);

	public static boolean validateCookie(WebDriver driver, String cookieName, String expectedValue) {
		LOGGER.info("Validating cookie with name:" + cookieName + " against a value: " + expectedValue);

		String actualCookie = driver.manage().getCookieNamed(cookieName).toString();

		LOGGER.info("Cookie values is:" + actualCookie);
		return (actualCookie.contains(expectedValue));
	}
}
