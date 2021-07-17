package com.qa.api.listeners;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class AllureReportListener implements ITestListener {
	
	private static String getTestMethodName(ITestResult iTestResult) {
		
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	private ByteArrayOutputStream request=new ByteArrayOutputStream();
	private ByteArrayOutputStream response=new ByteArrayOutputStream();
	
	private PrintStream requestVar=new PrintStream(request,true);
	private PrintStream responseVar=new PrintStream(request,true);
	

	@Override
	public void onStart(final ITestContext ITestContext) {
		System.out.println("OnStart method "+ITestContext.getName());	
		
		RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL,responseVar),new RequestLoggingFilter(LogDetail.ALL,requestVar));
	}
	
	@Override
	public void onTestSuccess(final ITestResult ITestResult) {
		logRequest(request);
		logResponse(response);
		System.out.println("OnTestSuccess method "+getTestMethodName(ITestResult)+" succeed");
		
	}
	
	@Override
	public void onTestFailure(final ITestResult ITestResult) {
		logRequest(request);
		logResponse(response);
		System.out.println("OnTestFailure method "+getTestMethodName(ITestResult)+" failed");		
	}
	
	@Override
	public void onFinish(final ITestContext ITestContext) {
		System.out.println("OnFinish method "+ITestContext.getName());		
	}
	
	@Override
	public void onTestStart(final ITestResult ITestResult) {
		System.out.println("OnTestStart method "+ITestResult.getName()+ " start");			
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(final ITestResult ITestResult) {
		System.out.println("Test failed but it is in defined success ratio "+getTestMethodName(ITestResult));		
	}

	

	@Override
	public void onTestSkipped(final ITestResult ITestResult) {
		System.out.println("OnTestSkipped method "+ITestResult.getName()+ " skipped");		
	}

	@Attachment(value="request")
	public byte[] logRequest(final ByteArrayOutputStream stream) {
		
		return attach(stream);
	}
	
	@Attachment(value="response")
	public byte[] logResponse(final ByteArrayOutputStream stream) {
		
		return attach(stream);
	}
	
	public byte[] attach(final ByteArrayOutputStream log) {
		
	final byte[] array=	log.toByteArray();
	log.reset();
	return array;
	}

	
	


}
