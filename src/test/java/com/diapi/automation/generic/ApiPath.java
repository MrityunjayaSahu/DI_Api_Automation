package com.diapi.automation.generic;

public class ApiPath {
	
	//API path for the Workbook services
	private String CREATE_WORKFILES;
	private String GET_WORKFILES_FOR_USER="/api/workfiles/PAT";
	
    
	//API path for the Custom Field.
	private String FETCH_CUSTOM_FIELD="/api/customfields/fetch";
	
	
	//API path for the Search
	private String SEARCH="/api/search";
	
	
	
	
	public String getFETCH_CUSTOM_FIELD() {
		return FETCH_CUSTOM_FIELD;
	}

	public String getSEARCH() {
		return SEARCH;
	}

	public String getGET_WORKFILES_FOR_USER() {
		return GET_WORKFILES_FOR_USER;
	}

	public String getCREATE_WORKFILES() {
		return CREATE_WORKFILES;
	}

	public void setCREATE_WORKFILES(String fETCH_ALL_WORKFILES_OF_USER) {
		CREATE_WORKFILES = fETCH_ALL_WORKFILES_OF_USER;
	}
	

}
