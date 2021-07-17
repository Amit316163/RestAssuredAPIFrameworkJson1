package com.qa.api.utils;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;


public class Configuration {
	
	public static Map<String,String> map=new HashedMap<String,String>();
	//public static Map<String,String> map1=new HashedMap<String,String>();
	PropertyReader prop=new PropertyReader() ;
	
	
	public Map<String,String> envSetup(){
		String environment =System.getProperty("env");
		
		
		
		try {
		if(environment.equalsIgnoreCase("dev")) {
			map.put("BASEURI", prop.readProperty("dev.BASEURI"));
			
		}else if (environment.equalsIgnoreCase("qa")) {
			map.put("BASEURI", prop.readProperty("qa.BASEURI"));
		}else {
			System.out.println("Please define the config env");
		}
		
	} catch(Exception e) {
		
	}
		return map;

}
	
	public Map<String,String> getConfigReader(){
		if(map==null) {
			map=envSetup();
		}
		return map;
	}
}