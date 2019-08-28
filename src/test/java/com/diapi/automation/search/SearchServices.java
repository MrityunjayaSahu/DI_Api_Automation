package com.diapi.automation.search;


import org.testng.annotations.Test;

import com.diapi.automation.pageclass.SearchMethod;

public class SearchServices extends SearchMethod{
	SearchMethod srch = new SearchMethod();
	
    @Test(priority=1)
    public void FieldedSearch() throws Exception {
        dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT, strDateTime);
        appName = "PatentSeatch";
        methodName=new Throwable().getStackTrace()[0].getMethodName(); 
        getApiPath=apath.getSEARCH();
        headers=headCall.getPATENT_SEARCH_HEADERS();
        runTests("Search");
        try {
            patSearch(resp,testReporter);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//===============================================================================
   /* @Test(priority=2)
    public void DWPISearch() throws Exception {
        dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT, strDateTime);
        appName = "PatentSeatch";
        methodName=new Throwable().getStackTrace()[0].getMethodName(); 
        getApiPath=apath.getSEARCH();
        headers=headCall.getPATENT_SEARCH_HEADERS();
        runTests("Search");
        try {
            patSearch(resp,testReporter);
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
//===============================================================================
   /* @Test(priority=3)
    public void IntegratedSearch() throws Exception {
        dataStore.put(TESTOUTPUT_FOLDER_DATEFORMAT, strDateTime);
        methodName=new Throwable().getStackTrace()[0].getMethodName(); 
        appName = "Patent Seatch";
        getApiPath=apath.getSEARCH();
        runTests("Search");
        try {
            patSearch(resp,testReporter);
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
}
