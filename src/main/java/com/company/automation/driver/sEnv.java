package com.company.automation.driver;

public enum sEnv {
	
	DEV("devUrl","devUrl_SSO","DEV.apibaseURL",ApiHostAuthorization.HostDEV),
	QA("qaUrl","qaUrl_SSO","QA.apibaseURL",ApiHostAuthorization.HostQA),
	PROD("prodUrl","prodUrl_SSO","PROD.apibaseURL",ApiHostAuthorization.HostUAT);
	
	private final String sArisgURL;
	private final String sArisgURL_SSO;
	private final String sApiURL;
	private final ApiHostAuthorization sHostAuth;
	
	private sEnv(String sArisgURL,String sArisgURL_SSO,String sApiURL,ApiHostAuthorization sHostAuth){
		this.sArisgURL=TestConfig.getConfig().getPropertyValue(sArisgURL);
		this.sApiURL=TestConfig.getConfig().getPropertyValue(sApiURL);
		this.sHostAuth=sHostAuth;
		this.sArisgURL_SSO=TestConfig.getConfig().getPropertyValue(sArisgURL_SSO);
		}
	
	public String getArisgURL() {
		return this.sArisgURL;
	}
	public String getArisgURL_SSO() {
		return this.sArisgURL_SSO;
	}
	
	public String getApiURL() {
		return this.sApiURL;
	}
	
	public ApiHostAuthorization getHostAuth() {
		return this.sHostAuth;
	}
	 
	public enum ApiHostAuthorization {
		
		HostDEV("DEV.Host","DEV.client-id","DEV.client-secret"),
		HostQA("QA.Host","QA.client-id","QA.client-secret"),
		HostUAT("PROD.Host","PROD.client-id","PROD.client-secret");
		
		private final String host;
		private final String clientId;
		private final String clientSecret;
		
		private ApiHostAuthorization(String host,String clientid,String clientsecret){
			this.host=TestConfig.getConfig().getPropertyValue(host);
			this.clientId=TestConfig.getConfig().getPropertyValue(clientid);
			this.clientSecret=TestConfig.getConfig().getPropertyValue(clientsecret);
			}
		
		public String getHost() {
			return this.host;
		}
		
		public String getClientId() {
			return this.clientId;
		}
		 
		public String getClientSecret() {
			return this.clientSecret;
		}
		 

	}

}
