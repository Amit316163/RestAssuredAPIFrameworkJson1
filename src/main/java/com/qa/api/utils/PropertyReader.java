package com.qa.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.collections4.MapUtils;

public class PropertyReader {
	
	
	static Properties properties=new Properties();
	static String environment=null;
	
	public PropertyReader() {
		
		if(MapUtils.isEmpty(properties)) {
			loadProperties();
		}
	}
	
	private void loadProperties() {
		String pathPrefix="";
		
		try (InputStream propStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(pathPrefix+"testconfig.properties")){
			properties.load(propStream);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	
	
	Properties prop=new Properties();
	try (InputStream propStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(pathPrefix+"global.properties")){
		prop.load(propStream);
		properties.putAll(prop);;
		
	}catch(IOException e) {
		e.printStackTrace();
	}

}
	public static String readProperty(String key) {
		return properties.getProperty(key);
	}
}
