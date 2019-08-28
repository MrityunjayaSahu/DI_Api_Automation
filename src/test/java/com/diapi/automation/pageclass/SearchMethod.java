package com.diapi.automation.pageclass;

import com.diapi.automation.generic.AbstractBase;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentTest;

public class SearchMethod extends AbstractBase{
	
public void patSearch(Response resp2,ExtentTest testReporter1) {
        
        try {
            System.out.println("Start Search !!");
            String jsonSingleData = resp2.getBody().asString(); 
            System.out.println("===================Json Data value is :-======================= " + jsonSingleData);
           // testReporter1.log(LogStatus.INFO, "Expected file name and field Tag details is matching with actual details.");     
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
