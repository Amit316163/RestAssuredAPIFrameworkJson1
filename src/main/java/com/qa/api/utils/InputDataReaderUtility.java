package com.qa.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InputDataReaderUtility {
	
	
	public Map<String,HashMap<String,String>> getTestInputDataFromExcel(String inputFile,String sheetName) throws IOException{
		
		Map<String,HashMap<String,String>> sheetDataMap=new LinkedHashMap<>();
		
	//	try(InputStream xlsStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(inputFile)){
		try(FileInputStream xlsStream=new FileInputStream(inputFile))	{
			XSSFWorkbook workbook=new XSSFWorkbook(xlsStream);
			
			XSSFSheet sheet=workbook.getSheet(sheetName);
			
			XSSFRow firstRow=sheet.getRow(0);
			
			int totalNoOfRows=sheet.getLastRowNum();
			int totalNoOfCols= firstRow.getLastCellNum();
			
			ArrayList<String> columnNameList=new ArrayList<String>();
			
			for(int col=0;col<totalNoOfCols;col++) {
				columnNameList.add(firstRow.getCell(col).getStringCellValue());
			}
			
			for(int rowNum=1;rowNum<=totalNoOfRows;rowNum++) {
				
				HashMap<String,String> rowDataMap=new HashMap<String,String>();
				String testCaseName="";
				
				try {
					testCaseName=sheet.getRow(rowNum).getCell(0).getStringCellValue();
				}catch(Exception ex) {
					continue;
				}
				
				for(int col=0;col<columnNameList.size();col++) {
					String columnName="";
					try {
						columnName=columnNameList.get(col).toString();
					}catch (Exception e) {
						System.out.println("Excel sheet formatting error"+e.getMessage());
						continue;
					}
				
				String rowValue;
				Cell cell=sheet.getRow(rowNum).getCell(col);
				
				switch (cell.getCellTypeEnum()) {
				case BOOLEAN:
					rowValue=String.valueOf(cell.getBooleanCellValue());
					break;
				case NUMERIC:
					rowValue=sheet.getRow(rowNum).getCell(col).getRawValue();
					break;
				default:
					rowValue=String.valueOf(cell.getStringCellValue());
					break;
				}
				
				rowDataMap.put(columnName, rowValue);
			}
				sheetDataMap.put(testCaseName, rowDataMap);
		}
			workbook.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sheetDataMap;
		}}
