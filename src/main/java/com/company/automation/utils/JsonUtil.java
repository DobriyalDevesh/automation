package com.company.automation.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;


import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {
	private JSONObject jObj;
	
	public JsonUtil(String filename) throws ParseException,IOException {
		this.jObj=parserJsonFile(filename);
	}
	
	public JsonUtil(String filename,String nodeName) throws ParseException,IOException {
		this.jObj=parserJsonFile(filename);
		this.jObj=getObjectFromNodeName(jObj,nodeName);
	}
	
	private JSONObject parserJsonFile(String filename) throws ParseException,NullPointerException {
		JSONObject jo=null;	
		try {
			JSONParser jsonP=new JSONParser();
			Object gObj=jsonP.parse(new FileReader(filename));
			jo = (JSONObject) gObj;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jo;
	}
	public JSONObject getObjectFromNodeName(JSONObject jObject,String nodeN) throws ParseException {
		JSONObject jObj;
		if(jObject.get(nodeN) instanceof JSONObject) {
			jObj=(JSONObject) jObject.get(nodeN);
		}else {
			//String strR=new String(nodeN+":"+jObject.get(nodeN).toString());
			StringBuilder sb=new StringBuilder();
			try {
				sb.append("{\"").append(nodeN).append("\":\"").append(jObject.get(nodeN).toString()).append("\"}");		
			}catch(NullPointerException e) {
				sb.append("null").append("\"}");
			}
			jObj=(JSONObject) new JSONParser().parse(sb.toString());
			//jObj=(JSONObject) JSONValue.parse(sb);
		}
		return jObj;
	}
	
	public Map<String,Object> fetchJObjectValue(JSONObject jObject) {
		Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jObject.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jObject.get(key);
            if (value instanceof JSONArray) {
                value = fetchJArrayValue((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = fetchJObjectValue((JSONObject) value);
            }   
            map.put(key, value);
        }   return map;
	}
	
	public List<Object> fetchJArrayValue(JSONArray jObject) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < jObject.size(); i++) {
            Object value = jObject.get(i);
            if (value instanceof JSONArray) {
                value = fetchJArrayValue((JSONArray) value);
            }
            else if (value instanceof JSONObject) {
                value = fetchJObjectValue((JSONObject) value);
            }
            list.add(value);
        }   return list;
	}
	
	public JSONObject getJsonObject() {
		return jObj;
	}
	
	public void setJsonObject(JSONObject jObject) {
		this.jObj=jObject;
	}
	
}
