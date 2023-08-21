package com.company.automation.api.reqres.in;

import com.company.automation.api.controller.AdEventsController;
import com.company.automation.api.setup.HostNames;
import com.company.automation.api.reqres.in.Endpoints.GetReceiptDetailsControllerEndpoints;

import io.restassured.specification.RequestSpecification;
import lombok.Setter;

@Setter
public class ControllersHolder {
	private RequestSpecification authSpec;
	private HostNames hostNames;
	private Endpoints endpoints;

	private AdEventsController adeventsController;
	
	public ControllersHolder(HostNames hostNames) {
		this.hostNames = hostNames;
		endpoints = new Endpoints(hostNames);
		initialize();
	}

	/**
	 * Gets the authorize header in Request Specification form then re-initializes
	 * the controllers
	 * 
	 * @param user     username
	 * @param password password
	 */
	/*
	 * public void authorize(String user, String password) { authSpec =
	 * WellDataAuth.getToken(user, password, hostNames); initialize(); }
	 */

	private void initialize() {

		adeventsController = new AdEventsController(this);
	}

	// getters
	public RequestSpecification authSpec() {
		return authSpec;
	}

	public HostNames hostNames() {
		return hostNames;
	}

	public Endpoints endpoints() {
		return endpoints;
	}

	// controller getters

	public AdEventsController adverseevent() {
		return adeventsController;
	}
}
