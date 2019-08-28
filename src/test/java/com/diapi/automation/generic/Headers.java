package com.diapi.automation.generic;

public class Headers {
 //Headers for the Search feature
	private String PATENT_SEARCH_HEADERS="content-type=application/x-www-form-urlencoded";
	
 //Headers for the Workbook file
	private String WORKBOOK_CUSTOM_HEADER="Accept=application/json";
	
	
	
	

	public String getPATENT_SEARCH_HEADERS() {
		return PATENT_SEARCH_HEADERS;
	}

	public String getWORKBOOK_CUSTOM_HEADER() {
		return WORKBOOK_CUSTOM_HEADER;
	}
	
	
	
	
}
