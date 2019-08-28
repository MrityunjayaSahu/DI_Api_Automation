package com.diapi.automation.workfile;

import org.testng.annotations.Test;

import com.diapi.automation.pageclass.WorkfileMethod;


public class WorkfileService extends WorkfileMethod {

//========================================================================================
 @Test
  public void FetchWorkfile() throws Exception {
	  dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT,strDateTime);
	  appName = "WorkfileService";
	  methodName=new Throwable().getStackTrace()[0].getMethodName(); 
	  getApiPath=apath.getGET_WORKFILES_FOR_USER();
	  headers=headCall.getWORKBOOK_CUSTOM_HEADER();
	  runTests("WorkFile");
	  //If status code is pass then Verify responce in the body
	  try{
		  DI_API_001(resp,testReporter);
	  }catch (Exception e) {
		System.out.println(e);
	}
  }
//==========================================================================================
  @Test
  public void CreateWorkFile() throws Exception{
	  
	  dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT,strDateTime);
	  appName = "WorkfileService";
	  methodName=new Throwable().getStackTrace()[0].getMethodName(); 
	  //Create Random workbook name for 10 digits
	  String generatedString = getAlphaNumericString(10);
	  
	   //Enter Category
	   String selCategorie="PAT";
	   
	  //Create End point with the help of workbook name,Searched Result set Id and Category
	  setEndPoint="/api/workfiles/create?workFileName="+generatedString+"&"+"categorie="+selCategorie+"&"+"resultsetId="+RESULTSET_ID+"";
	  apath.setCREATE_WORKFILES(setEndPoint);
	  getApiPath=apath.getCREATE_WORKFILES();
	  headers=headCall.getWORKBOOK_CUSTOM_HEADER();
	  runTests("WorkFile"); 	  
  }
}
