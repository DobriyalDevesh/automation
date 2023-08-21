package com.company.automation.api.setup;

import org.testng.asserts.SoftAssert;

import   com.company.automation.TestBase;
//import com.jnj.irt.automation.api.setup.TestBase;
import com.company.automation.api.reqres.in.ControllersHolder;

/**
 * Testbase that can be used by test methods that execute API calls or test
 * methods that need a web-driver created.
 */
public class ApiTestBase extends TestBase {
	private SoftAssert softAssert;
	private ControllersHolder pvdalControllers;

	public ApiTestBase() {
		softAssert = new SoftAssert();
		pvdalControllers = new ControllersHolder(getHostNames());
	}

	protected SoftAssert softAssert() {
		return this.softAssert;
	}

	protected ControllersHolder pvdalControllers() {
		return this.pvdalControllers;
	}
}