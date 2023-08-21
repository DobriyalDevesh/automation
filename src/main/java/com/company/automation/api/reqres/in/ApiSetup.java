package com.company.automation.api.reqres.in;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.company.automation.utils.JsonUtil;
import com.company.automation.driver.TestConfig;

public class ApiSetup {
	
	private String filename=TestConfig.getConfig().getPropertyValue("ApiConfigFilePath");
	public static String baseUri="";
	public static String HOST="";
	public static String CLIENTID="";
	public static String CLIENTSECRET="";
	public String endPoint;
	public HashMap<String,String> headers;
	public HashMap<String,String> queryParam;
	public HashMap<String,String> pathParam;
	public HashMap<String,Object> body;
	public JsonUtil jsUtil;
	
	public ApiSetup(String resourceName) {
		try {
			
			jsUtil=new JsonUtil(filename,resourceName);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		setRequestEndpoint("endpoint");
		setRequestHeaders("Header");
		setRequestBody("Body");
		setReqQueryParam("QueryParam");
		setReqPathParam("PathParam");
	}
	
	
	
	private void setRequestEndpoint(String nodeName) {
		
		JSONObject endPointJO;
		try {
			endPointJO = jsUtil.getObjectFromNodeName(jsUtil.getJsonObject(), nodeName);
			Map<String,Object> endpointVal=jsUtil.fetchJObjectValue(endPointJO);
			if (endpointVal.containsKey(nodeName) )
				endPoint=baseUri+"/"+(String) endpointVal.get(nodeName);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		private void setRequestHeaders(String nodeName) {
			HashMap<String,String> heaederStrVal=new HashMap<String,String>();
			JSONObject headerJO;
			try {
				headerJO = jsUtil.getObjectFromNodeName(jsUtil.getJsonObject(), nodeName);
				Map<String,Object> heaederVal=jsUtil.fetchJObjectValue(headerJO);
				//Iterator<String> keys = heaederVal.keySet().iterator();
				if (!(heaederVal.isEmpty()) ) {
						for(String keyname:heaederVal.keySet())
						{
							if(!keyname.equalsIgnoreCase("Host") && !keyname.equalsIgnoreCase("client-id") && !keyname.equalsIgnoreCase("client-secret"))
								heaederStrVal.put(keyname, (String)heaederVal.get(keyname));
						}
						heaederStrVal.put("Host",HOST);
						heaederStrVal.put("client-id",CLIENTID);
						heaederStrVal.put("client-secret",CLIENTSECRET);
						headers=heaederStrVal;
					}else
						headers=null;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
		private void setRequestBody(String nodeName) {
			//Map<String,String> bodyStrVal=new HashMap<String,String>();
			JSONObject bodyJO;
			try {
				bodyJO = jsUtil.getObjectFromNodeName(jsUtil.getJsonObject(), nodeName);
				Map<String,Object> bodyVal=jsUtil.fetchJObjectValue(bodyJO);
				if(bodyJO.toString().contains("null") || bodyJO==null||bodyJO.isEmpty()) {
					body=null;
				}else {
					body=(HashMap<String,Object>)bodyVal;
					}
				}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
							
		}
	
		private void setReqQueryParam(String nodeName) {
			HashMap<String,String> queryStrVal=new HashMap<String,String>();
			JSONObject queryJO;
			try {
				queryJO = jsUtil.getObjectFromNodeName(jsUtil.getJsonObject(), nodeName);
				Map<String,Object> queryVal=jsUtil.fetchJObjectValue(queryJO);
				if(queryVal.toString().contains("null") || queryJO==null||queryJO.isEmpty()) {
					queryParam=null;
				}else {
					for(String keyname:queryVal.keySet())
					{
						queryStrVal.put(keyname, (String)queryVal.get(keyname));
					}
					queryParam=queryStrVal;
					}
				}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
							
		}
		
		private void setReqPathParam(String nodeName) {
			HashMap<String,String> pathStrVal=new HashMap<String,String>();
			JSONObject pathJO;
			try {
				pathJO = jsUtil.getObjectFromNodeName(jsUtil.getJsonObject(), nodeName);
				Map<String,Object> pathVal=jsUtil.fetchJObjectValue(pathJO);
				if(pathVal.toString().contains("null") || pathJO==null||pathJO.isEmpty()) {
					pathParam=null;
				}else {
					for(String keyname:pathVal.keySet())
					{
						pathStrVal.put(keyname, (String)pathVal.get(keyname));
					}
					pathParam=pathStrVal;
					}
				}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
							
		}
		
		public void replaceRequestParams(String paramType,String Key,String value) {
			boolean isKeyRep=false;
			Iterator<String> keys;
			switch (paramType.toLowerCase()){
			case "endpoint":
				this.endPoint=baseUri+"/"+value;
			case "header":
				keys=this.headers.keySet().iterator();
				 while(keys.hasNext()) {
			       String mapkey = keys.next();
					if (Key.equalsIgnoreCase(mapkey)) {
						this.headers.replace(mapkey, value);
						isKeyRep=true;
						break;}
				 	}
				 	if(!isKeyRep)
				 		this.headers.put(Key, value);
				 break;
			case "queryparam":
				keys=this.queryParam.keySet().iterator();
				 while(keys.hasNext()) {
			       String mapkey = keys.next();
					if (Key.equalsIgnoreCase(mapkey)) {
						this.queryParam.replace(mapkey, value);
						isKeyRep=true;
						break;}
				 	}
				 	if(!isKeyRep)
				 		this.queryParam.put(Key, value);
				 break;
			case "pathparam":
				keys=this.pathParam.keySet().iterator();
				 while(keys.hasNext()) {
			       String mapkey = keys.next();
					if (Key.equalsIgnoreCase(mapkey)) {
						this.pathParam.replace(mapkey, value);
						isKeyRep=true;
						break;}
				 	}
				 	if(!isKeyRep)
				 		this.pathParam.put(Key, value);
				 break;
			case "body":
				keys=this.body.keySet().iterator();
				 while(keys.hasNext()) {
			       String mapkey = keys.next();
					if (Key.equalsIgnoreCase(mapkey)) {
						this.body.replace(mapkey, value);
						isKeyRep=true;
						break;}
				 	}
				 	if(!isKeyRep)
				 		this.body.put(Key, value);
				 break;
			}
		}
		
		public void replaceRequestParamsInt(String paramType,String Key,int value) {
			boolean isKeyRep=false;
			Iterator<String> keys;
			switch (paramType.toLowerCase()){
			case "body":
				keys=this.body.keySet().iterator();
				 while(keys.hasNext()) {
			       String mapkey = keys.next();
					if (Key.equalsIgnoreCase(mapkey)) {
						this.body.replace(mapkey, value);
						isKeyRep=true;
						break;}
				 	}
				 	if(!isKeyRep)
				 		this.body.put(Key, value);
				 break;
			}
		}
		

}

