package com.qa.api.handler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.qa.api.base.BaseClass;

import io.restassured.response.Response;

public class TestHandler extends BaseClass {
	Response resp;
	ResponseValidator assertion=new ResponseValidator();
	
	public TestHandler() {
		super();
	}
	
	
	
	public void  validatePostCall(String basePath,Map<String,HashMap<String,String>> excelDataMap,
		String requestTemplate,String token,Map<String,String> params,Map<String,String> testCaseRecordValuesMap) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {
	
	resp=doPost(URI, basePath, params, token, true, "JSON", excelDataMap, requestTemplate);
	
	//System.out.println("Response payload "+resp.asPrettyString());
	System.out.println("Status code is "+resp.getStatusCode());
	assertion.validateResponse(resp, testCaseRecordValuesMap, Integer.parseInt(testCaseRecordValuesMap.get("exp_httpStatusCode")));
}
	
	public void  validatePutCall(String basePath,Map<String,HashMap<String,String>> excelDataMap,
			String requestTemplate,String token,Map<String,String> params,Map<String,String> testCaseRecordValuesMap) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {
		
		resp=doPut(URI, basePath, params, token, true, "JSON", excelDataMap, requestTemplate);
		
		System.out.println("Response payload "+resp.asPrettyString());
		System.out.println("Status code is "+resp.getStatusCode());
		assertion.validateResponse(resp, testCaseRecordValuesMap, Integer.parseInt(testCaseRecordValuesMap.get("exp_httpStatusCode")));
	}
	
	public void  validateGetCall(String basePath,String token,Map<String,String> params,Map<String,String> testCaseRecordValuesMap) {
		
		resp=doGet(URI, basePath, params, token, true, "JSON");
		
		System.out.println("Response payload "+resp.asPrettyString());
		System.out.println("Status code is "+resp.getStatusCode());
		assertion.validateResponse(resp, testCaseRecordValuesMap, Integer.parseInt(testCaseRecordValuesMap.get("exp_httpStatusCode")));
	}
	
	public void  validateDeleteCall(String basePath,String token,Map<String,String> params,Map<String,String> testCaseRecordValuesMap) {
		
		resp=doDelete(URI, basePath, params, token, true, "JSON");
		
		System.out.println("Response payload "+resp.asPrettyString());
		System.out.println("Status code is "+resp.getStatusCode());
		assertion.validateResponse(resp, testCaseRecordValuesMap, Integer.parseInt(testCaseRecordValuesMap.get("exp_httpStatusCode")));
	}
}
