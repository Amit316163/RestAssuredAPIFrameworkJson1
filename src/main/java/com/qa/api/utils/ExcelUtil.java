package com.qa.api.utils;

import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

	
	public Object[][] getExcelDataMap(Map<String,HashMap<String,String>> sheetDataMap){
		
		int size=0;
		
		for(Map.Entry<String, HashMap<String, String>> entry:sheetDataMap.entrySet()) {
			
			if(entry.getValue().containsKey("Execute") && "N".equalsIgnoreCase(entry.getValue().get("Execute"))){
				continue;
			}
			size++;
		}
		
		Object[][] dataToUse=new Object[size][1];
		int i=0;
		
		for (Map.Entry<String, HashMap<String,String>> entry:sheetDataMap.entrySet()) {
			
			Map<String, HashMap<String, String>>localMap=new HashMap<>();
			
			if(entry.getValue().containsKey("Execute")&&"N".equalsIgnoreCase(entry.getValue().get("Execute"))) {
				continue;
			}
			localMap.put(entry.getKey(), entry.getValue());
			dataToUse[i++]=new Object[] {localMap};
		}
		return dataToUse;
	}
}
