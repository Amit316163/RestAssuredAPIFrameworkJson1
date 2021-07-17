package com.qa.api.handler;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.transform.EqualsAndHashCodeASTTransformation;

import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;

public class ResponseValidator {
	
	public Map<String,String> getExpectedResponse(Map<String,String> testCaseRecordValuesMap){
		
		Map<String,String> expectedDataMap=new HashMap<String,String>();
		
		for(Map.Entry<String, String> entry:testCaseRecordValuesMap.entrySet()) {
			if(entry.getKey().contains("exp_")&& entry.getValue() !="") {
				
			String strResponseBodyField=entry.getKey().replace("exp_", "");
			String strExpectedFieldValue=entry.getValue();
			
			expectedDataMap.put(strResponseBodyField, strExpectedFieldValue);
			}
		}
		return expectedDataMap;
	}
	
	public void validateResponse(Response resp,Map<String,String> testCaseRecordValuesMap,int statusCode ) {
		
	Map<String,String> expectedDataMap=	getExpectedResponse(testCaseRecordValuesMap);
	
	for(Map.Entry<String, String> expectedDataField :expectedDataMap.entrySet()) {
		
		if(expectedDataField.getKey().equalsIgnoreCase("httpStatusCode")) {
			continue;
		}
		
		try {
			resp.then().statusCode(statusCode).body(expectedDataField.getKey(),	equalTo(expectedDataField.getValue()));
		} catch (Exception e) {
			e.getMessage();
		}
	}
		
	}

}
