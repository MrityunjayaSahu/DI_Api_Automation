package com.diapi.automation.generic;

public class RowData {
	
	private String testName;
	private String featureType;
	private String description;
	private String method;
	private String body;
	private String runmode;
	private String userType;
	private String entitlements;
	
	
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getEntitlements() {
		return entitlements;
	}
	public void setEntitlements(String entitlements) {
		this.entitlements = entitlements;
	}
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}
	public void setRunmode(String runmode) {
		this.runmode = runmode;
	}
	public String getFeatureType() {
		return featureType;
	}
	public String getRunmode() {
		return runmode;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getrunmode() {
		return runmode;
	}
	public void setrunmode(String runmode) {
		this.runmode = runmode;
	}

	@Override
	public String toString() {
		return "RowData [testName=" + testName + ", featureType=" + featureType + ", description=" + description + ", userType="
				+ userType + ", method=" + method + ", body=" + body +  ", entitlements=" + entitlements + ", runmode=" + runmode +"]";
	}


}
