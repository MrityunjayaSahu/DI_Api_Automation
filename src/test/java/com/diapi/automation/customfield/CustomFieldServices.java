package com.diapi.automation.customfield;

import org.testng.annotations.Test;
import com.diapi.automation.pageclass.CustomeMethod;



public class CustomFieldServices extends CustomeMethod{	
  @Test
  public void FetchCustomField() throws Exception {
	  dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT,strDateTime);
	  appName = "customFiled";
	  methodName=new Throwable().getStackTrace()[0].getMethodName();
	  getApiPath=apath.getFETCH_CUSTOM_FIELD();
	  headers=headCall.getWORKBOOK_CUSTOM_HEADER();
	  runTests("CustomField");	  
	  try{
		  fetchCustomFields(resp,testReporter);
	  }catch (Exception e) {
		System.out.println(e);
	}
	 
  }

}
