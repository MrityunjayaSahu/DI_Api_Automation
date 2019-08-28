package com.diapi.automation.pageclass;

import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;

import com.diapi.automation.generic.AbstractBase;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CustomeMethod extends AbstractBase{
	
	public void fetchCustomFields(Response resp2,ExtentTest testReporter1) throws IOException, ParseException{
		String customFieldFileLocation="src/test/test-data/customFieldJsonData/customfield.json";
		String actaulFieldName="";
		String actaulFieldTag="";
		try{
			System.out.println("========Enter to validate custom method ========================");
	       List<String> fieldNameResponse = resp2.jsonPath().getList("fieldName");
	       if(fieldNameResponse.size()>0){
	           for (int i = 0; i < fieldNameResponse.size(); i++) {
	        	   actaulFieldName=fieldNameResponse.get(i);
	            }
	       }
	       List<String> fieldTagResponse = resp2.jsonPath().getList("fieldTag");
	       if(fieldTagResponse.size()>0){
	           for (int i = 0; i < fieldTagResponse.size(); i++) {
	        	   actaulFieldTag=fieldTagResponse.get(i);
	            }
	       }
	       try{
	       String expectedFileName=readDataFromJsonFile(customFieldFileLocation, "fieldName");
	       String expectedFileTag=readDataFromJsonFile(customFieldFileLocation, "fieldTag");
	       if(actaulFieldName.equalsIgnoreCase(expectedFileName)&&actaulFieldTag.equalsIgnoreCase(expectedFileTag)){
	        	  logger.info("Expected file name and field Tag details is matching with actual details.");
	        	  testReporter1.log(LogStatus.INFO, "Expected file name and field Tag details is matching with actual details.");
	        	  testReporter1.log(LogStatus.PASS, "PASS");
	          }
	       }
	       catch(Exception e){
	    	   e.printStackTrace();
	    	   logger.info("Expected file name and field Tag details is not matching actual details.");
	       }
	        System.out.println("========End to validate custom method ========================");
	        
	}
	       catch (Exception e) {
	    	   System.out.println(e);
	       }
	}

}
