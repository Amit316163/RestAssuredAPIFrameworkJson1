package com.qa.api.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Reusable {
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getTestCaseRecordValuesMap(Map<String,HashMap<String,String>> sheetDataMap){
		
		
		Entry<String, HashMap<String,String>> testCaseRecordMap=(Entry<String, HashMap<String,String>>) sheetDataMap.entrySet().toArray()[0];
		Map<String, String> testCaseRecordValuesMap=testCaseRecordMap.getValue();
		return testCaseRecordValuesMap;
	}
	
	public Map<String,HashMap<String,String>> getTestInputDataMap(String testInputFileNameKey,String testSheetName) throws IOException{
		
		String testInputFileName= PropertyReader.readProperty(testInputFileNameKey);
		InputDataReaderUtility oInputDataReaderUtility=new InputDataReaderUtility();
		return oInputDataReaderUtility.getTestInputDataFromExcel(testInputFileName, testSheetName);
	}
	
	
	@SuppressWarnings("static-access")
	public String getTestJsonPath(String requestTemplate) {
		
		PropertyReader properties=new PropertyReader();
		String jsonTemplate=properties.readProperty(requestTemplate);
		return jsonTemplate;
	}
	
	
	public String randomNumber() {
		
		Random rand=new Random();
		int num=rand.nextInt(99999);
		String number=Integer.toString(num);
		return number;
	}
	
	public int parseStringToInt(String stringValue) {
		int num=Integer.parseInt(stringValue.substring(0,stringValue.indexOf(".")));
		return num;
	}

}
