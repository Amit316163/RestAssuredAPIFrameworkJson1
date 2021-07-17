package com.qa.api.handler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RequestPayloadGenerator {

	Map<String,String> jsonMap=null;

	public String generatePayloadRequest(String testRequestTemplate,Map<String,String> testCaseRecordMap) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {

		//InputStreamReader jsonStream= new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(testRequestTemplate));

		//Fetch the requestTemplate and converted into String
			//	Object parse=JsonParser.parseReader(jsonStream);
		//without jar we can use this one also but when we want to generate a jar-it will throw error  
		Object parse=JsonParser.parseReader(new FileReader(testRequestTemplate));

		
		String requestTemplateJsonString=parse.toString();

		for(Map.Entry<String, String>entry:testCaseRecordMap.entrySet()) {
			jsonMap=new HashMap<String,String>();

			//it impleaments Serializable and used to configure and construct reader and writer
			JsonFactory factory=new JsonFactory();

			//this also impleamnets serializable ,and provide funcationality for reading and writing json either to or from POJO
			ObjectMapper mapper=new ObjectMapper(factory);

			JsonNode rootNode=	mapper.readTree(requestTemplateJsonString);
			jsonParseRecur(rootNode);
			
			for(Map.Entry<String, String> jsonMapEntry:jsonMap.entrySet()) {
				String jsonValue=jsonMapEntry.getValue().toString();
				
				if(jsonValue.contains("b{{") && jsonValue.contains(entry.getKey())&& entry.getValue().equalsIgnoreCase("Blank_Number")) {
					requestTemplateJsonString=requestTemplateJsonString.replace(jsonValue, entry.getValue());
				}else if(jsonValue.contains(entry.getKey())) {
					requestTemplateJsonString=requestTemplateJsonString.replace("{{"+ entry.getKey()+ "}}",entry.getValue());
				}
			}

		}
		return requestTemplateJsonString;
	}

	public void jsonParseRecur(JsonNode rootNode) {

		Iterator<Map.Entry<String, JsonNode>> fieldsIterator=rootNode.fields();

		while(fieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> field=fieldsIterator.next();
			Iterator<Map.Entry<String, JsonNode>> fieldsIterator1=field.getValue().fields();
			if(fieldsIterator1.hasNext()) {
				jsonParseRecur(field.getValue());
			}else {
				jsonMap.put(field.getKey(), field.getValue().toString());
			}
					}

	}



}
