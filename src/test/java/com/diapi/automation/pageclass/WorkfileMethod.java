package com.diapi.automation.pageclass;

import java.util.List;

import com.diapi.automation.generic.AbstractBase;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class WorkfileMethod extends AbstractBase{
	
//================================================================================================================
/**
 * Verify workbook details
 * @throws Exception
 */
public void DI_API_001(Response resp2,ExtentTest testReporter1)  throws Exception{
	//If status code is matching then Verify responce in the body
	String customFieldFileLocation="src/test/test-data/workFilejsonData/workfilevalidation.json";
	String actaulworkFileId="";
	String actaulworkfileName="";
	try{
		System.out.println("========Enter to validate custom method ========================");
       List<String> fieldNameResponse = resp2.jsonPath().getList("workFileId");
       System.out.println(fieldNameResponse);
       if(fieldNameResponse.size()>0){
           for (int i = 0; i < fieldNameResponse.size(); i++) {
        	   actaulworkFileId=fieldNameResponse.get(i);
            }
       }
       List<String> fieldTagResponse = resp2.jsonPath().getList("workfileName");
       if(fieldTagResponse.size()>0){
           for (int i = 0; i < fieldTagResponse.size(); i++) {
        	   actaulworkfileName=fieldTagResponse.get(i);
            }
       }
       try{
       String expectedworkFileId=readDataFromJsonFile(customFieldFileLocation, "workFileId");
       String expectedworkfileName=readDataFromJsonFile(customFieldFileLocation, "workfileName");
       if(actaulworkFileId.equalsIgnoreCase(expectedworkFileId)&&actaulworkfileName.equalsIgnoreCase(expectedworkfileName)){
        	  testReporter1.log(LogStatus.INFO, "Expected workFileId and workfileName details is matching with actual details.");
        	  testReporter1.log(LogStatus.PASS, "PASS");
          }
       }
       catch(Exception e){
    	   e.printStackTrace();
    	   logger.info("Expected workFileId and workfileName details is not matching with actual details.");
       }
        System.out.println("========End to validate custom method ========================");
        
}
       catch (Exception e) {
    	   System.out.println(e);
       }
    
	}

}


