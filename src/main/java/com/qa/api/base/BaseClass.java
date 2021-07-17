package com.qa.api.base;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.qa.api.handler.RequestPayloadGenerator;
import com.qa.api.utils.Configuration;
import com.qa.api.utils.Reusable;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	Configuration config=new Configuration();
	Reusable resuable=new Reusable();
	
	public String URI;
	
	public  BaseClass() {
		URI=config.envSetup().get("BASEURI");
	}


	@Step("Post call with {0},{1},{2},{3},{4},{5},{6},{7}")
	public Response doPost(String baseURI,String basePath,Map<String,String> params,String token,boolean log,String contentType,
			Map<String,HashMap<String,String>> sheetDataMap,String requestTemplate) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {

		if(setBaseURI(baseURI)) {
			RequestSpecification request= createRequest(log,params,token,contentType);
			request.body(addRequestPayload(sheetDataMap,requestTemplate));
			return getResponse("POST",request,basePath);
		}
		return null;
	}

	public Response doPut(String baseURI,String basePath,Map<String,String> params,String token,boolean log,String contentType,
			Map<String,HashMap<String,String>> sheetDataMap,String requestTemplate) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {

		if(setBaseURI(baseURI)) {
			RequestSpecification request= createRequest(log,params,token,contentType);
			request.body(addRequestPayload(sheetDataMap,requestTemplate));
			return getResponse("PUT",request,basePath);
		}
		return null;
	}

	public Response doGet(String baseURI,String basePath,Map<String,String> params,String token,boolean log,String contentType) {

		if(setBaseURI(baseURI)) {
			RequestSpecification request= createRequest(log,params,token,contentType);
			return getResponse("GET",request,basePath);
		}
		return null;
	}

	public Response doDelete(String baseURI,String basePath,Map<String,String> params,String token,boolean log,String contentType) {

		if(setBaseURI(baseURI)) {
			RequestSpecification request= createRequest(log,params,token,contentType);
			return getResponse("DELETE",request,basePath);
		}
		return null;
	}



	private boolean setBaseURI(String baseURI) {

		if(baseURI==null||baseURI.isEmpty()) {
			System.out.println("Please assign the baseURI");
			return false;
		}

		try {
			RestAssured.baseURI = baseURI;
			return true;
		} catch (Exception e) {
			System.out.println("some exception got occured while assigning the base URI");
			return false;
		}
	}

	private RequestSpecification createRequest(boolean log,Map<String,String> params,String token,String contentType) {

		RequestSpecification request=null;

		if(log) {
			request=RestAssured.given().relaxedHTTPSValidation().log().all();
		}else {
			request=RestAssured.given().relaxedHTTPSValidation();

		}

		if(!(params==null)) {
			request.queryParams(params);
		}

		if(token!=null) {
			request.headers("Authorization",token);
		}

		if(contentType.equalsIgnoreCase("JSON")) {
			request.contentType(ContentType.JSON);
		}else if (contentType.equalsIgnoreCase("XML")){
			request.contentType(ContentType.XML);
		}else if (contentType.equalsIgnoreCase("TEXT")){
			request.contentType(ContentType.TEXT);
		}else {
			System.out.println("Please provide the content type");
		}
		return request;
	}

	private Response getResponse(String httpMethod,RequestSpecification request,String basePath) {

		Response response=null;

		if(httpMethod.equalsIgnoreCase("POST")) {
			request.post(basePath);
		}else if(httpMethod.equalsIgnoreCase("PUT")) {
			request.put(basePath);
		}else if(httpMethod.equalsIgnoreCase("GET")) {
			request.get(basePath);
		}else if(httpMethod.equalsIgnoreCase("DELETE")) {
			request.delete(basePath);
		}else {
			System.out.println("please path correct httpMethod");
		}
		return response;
	}

	private String addRequestPayload(Map<String,HashMap<String,String>> sheetDataMap,String requestTemplate) throws JsonMappingException, JsonProcessingException, JsonIOException, JsonSyntaxException, FileNotFoundException {

		RequestPayloadGenerator requestGenerator=new RequestPayloadGenerator();

		Map<String,String> testCaseRecordValuesMap=resuable.getTestCaseRecordValuesMap(sheetDataMap);

		String requestJsonValues=requestGenerator.generatePayloadRequest(requestTemplate,testCaseRecordValuesMap);

		//requestJsonValues.replaceAll("\"bBlank_Number\"", "");
		//System.out.println("Request json body -"+requestJsonValues);
		return requestJsonValues;




	}

}
