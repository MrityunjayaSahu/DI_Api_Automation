package com.diapi.automation.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.diapi.automation.reports.ReportFactory;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * The {@code AbstractBase} class is the common setup class for all the test
 * classes. The class {@code AbstractBase} is support to test RestFull web
 * services.
 */
public class AbstractBase {
	protected ExtentReports reporter = ReportFactory.getReporter();
	protected ExtentTest testReporter = null;
	protected static final Logger logger = LogManager.getLogger();
	protected RequestSpecification respec = RestAssured.given();
	public String cookiesid;
	public Response resp;
	protected static String methodName = null;
	protected static final String GET = "GET";
	protected static final String POST = "POST";
	protected static final String PUT = "PUT";
	protected static final String DELETE = "DELETE";
	protected static final String FAIL = "FAIL";
	private static final int TESTDATA_COLUMN_COUNT = 10;
	private static final String EMPTY_STRING = "";
	protected static final String STATUS = "status";
	protected static final String TOKENIZER_DOUBLE_PIPE = "||";
	protected static final String TOKENIZER_EQUALTO = "=";
	protected static final String NOT_EMPTY = "NOTEMPTY";
	private static final String TEST_OUTPUT_FOLDER_PATH = "src/test/test-responses";
	private static Path TEST_OUTPUT_ROOT_FOLDER_PATH = null;
	protected static final String TESTOUTPUT_FOLDER_DATEFORMAT = "ddMMMyyyy_HHmmss";
	protected final String filePath = "Reports/test_report.html";
	protected static String strDateTime = null;
	protected String setEndPoint = null;
	protected String getApiPath = null;
	protected String requestMethod = null;
	protected String appName = null;
	protected RowData rowData = null;
	protected boolean isTestFail = false;
	protected String isTestFailDescroption = null;
	protected String jsoncookies = null;
	protected String EnvironmentUrl;
	protected String username;
	protected String password;
	protected String requesturl;
	protected String userType;
	protected String headers;
	protected int statusCode;
	protected String entitlements;
	protected String testDataExcelPath = "src/test/test-data/TestData.xlsx";

	Xls_Reader xlar = new Xls_Reader(testDataExcelPath);
	HashMap<String, String> header = new HashMap<String, String>();
	String resultsetIdForser = "";
	String requesturlNew = "";
	String resultId1 = "";
	protected String RESULTSET_ID = "";
	String resultId2 = "";
	String SelMarkedlist = "N";
	int statusCodeDependency = 0;
	protected Map<String, String> dataStore = new HashMap<String, String>();
	protected Map<String, String> payload = new HashMap<String, String>();
	protected static Map<String, RowData> testcase = null;
	protected ApiPath apath = new ApiPath();
	protected Headers headCall = new Headers();

	// ==============================================================================================================================
	/**
	 * Creates root directory to store all the test responses.
	 * 
	 * @exception Exception
	 *                On folder creation error
	 * @see Exception
	 */
	@BeforeSuite
	public void beforeSuite() throws Exception {
		strDateTime = new SimpleDateFormat(TESTOUTPUT_FOLDER_DATEFORMAT).format(new Date());
		TEST_OUTPUT_ROOT_FOLDER_PATH = Paths.get(TEST_OUTPUT_FOLDER_PATH, strDateTime);
		Files.createDirectories(TEST_OUTPUT_ROOT_FOLDER_PATH);
	}

	/**
	 * Support to stores the input parameters and Host/IP names
	 * 
	 * @exception Exception
	 *                On storing parameters.
	 * @see Exception
	 */
	@Parameters({ "entitleMents", "userType", "userName", "passWord" })
	@BeforeClass
	public String getUserSelectionResultSetId(String entitleMents, String usertype, String username, String password)
			throws Exception {
		boolean success = true;
		String validationsToken = null;
		String jsonNameKey = null;
		String expectedValue = null;
		entitlements = entitleMents;
		userType = usertype;

		logger.info("@BeforeSuite - any initialization / activity to perform before starting your test suite");
		EnvironmentUrl = System.getProperty("EnvironmentUrl");

		if (EnvironmentUrl != null && username != null && password != null) {
			logger.info("environment = " + EnvironmentUrl);
			logger.info("entitleMents = " + entitleMents);
			logger.info("usertype = " + usertype);
			logger.info("username = " + username);
			logger.info("password = " + password);
		} else {
			logger.info("Stop Execution as System varibale is not set for the current Excution.");
		}
		try {
			resp = respec.when().post("" + EnvironmentUrl + "/j_spring_security_check?username=" + username
					+ "&password=" + password + "&localeValue=en");
			statusCode = resp.getStatusCode();
			logger.info("status code for cookies query request " + ":" + statusCode);
			if (statusCode == 200 || statusCode == 302) {
				cookiesid = resp.cookies().get("JSESSIONID");
				logger.info("json cookies is " + "  :" + cookiesid);
			}
		} catch (Throwable t) {
			testReporter.log(LogStatus.FAIL, "Store cookies api call failed");// extent
		}

		String endpoint = apath.getSEARCH();
		requesturlNew = EnvironmentUrl + endpoint;

		// Get Header Value
		String headerString = "content-type=application/x-www-form-urlencoded";
		String bodyString = xlar.getCellData("GenericSearch", 4, 2);

		// ====================================================================================================
		if (headerString.trim().equals("content-type=application/x-www-form-urlencoded")) {

			if (StringUtils.isNotBlank(bodyString))
				System.out.println("BodyString is ==>>" + bodyString);

			org.json.JSONObject jsonString = new org.json.JSONObject(bodyString.trim());
			Iterator<?> keys = jsonString.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = jsonString.get(key).toString();

				respec.formParam(key, value);
			}
		}
		// String tokenizer for header string
		StringTokenizer validationsTokenizer = new StringTokenizer(headerString, TOKENIZER_DOUBLE_PIPE);
		while (validationsTokenizer.hasMoreTokens()) {
			if (success == false)
				break;
			validationsToken = validationsTokenizer.nextToken();
			StringTokenizer validationTokenizer = new StringTokenizer(validationsToken, TOKENIZER_EQUALTO);

			while (validationTokenizer.hasMoreTokens()) {
				jsonNameKey = validationTokenizer.nextToken();
				// Get next token to get value
				if (validationTokenizer.hasMoreTokens()) {
					expectedValue = validationTokenizer.nextToken();
					// add headers in the request
					header.put(jsonNameKey, expectedValue);
					respec.headers(header);
				}
			}

			resp = respec.cookie("JSESSIONID=" + cookiesid).when().post(requesturlNew).then().extract().response();
			// String jsonSingleData = resp.getBody().asString();

			resultsetIdForser = resp.jsonPath().getString("resultsetId");

			List<String> lstresultId = resp.jsonPath().getList("body.id");
			for (int i = 0; i < lstresultId.size() - 2; i++) {
				resultId1 = lstresultId.get(0).trim();
				resultId2 = lstresultId.get(1).trim();
			}
			// Make Request For the User selection ResultsetId
			requesturlNew = EnvironmentUrl + "/api/userSelection/rsId?resultsetId=" + resultsetIdForser + "&markedlist="
					+ SelMarkedlist + "";
			respec.formParam(resultId1, resultId2);
			// Request for get result set id
			resp = respec.contentType("application/json").cookie("JSESSIONID=" + cookiesid).when().post(requesturlNew);
			statusCodeDependency = resp.getStatusCode();
			RESULTSET_ID = resp.jsonPath().getString("RESULTSET_ID");
		}
		return RESULTSET_ID;
	}

	// ===============================================================================================================================
	/**
	 * Execute all the test cases defined in the excel sheet.
	 * 
	 * @throws Exception
	 */
	protected void runTests(String sheetName) throws Exception {
		logger.info("Entered the process method...");

		XSSFWorkbook workBook = null;
		FileInputStream inputStream = null;

		try {
			int sheetRowCount;
			XSSFSheet sheet = null;
			XSSFRow row = null;

			// Read Excel file
			File myxl = new File(testDataExcelPath);
			inputStream = new FileInputStream(myxl);
			workBook = new XSSFWorkbook(inputStream);
			// Loop through each sheet in the Excel
			logger.info("========================================================================");
			sheet = workBook.getSheet(sheetName);
			sheetRowCount = sheet.getLastRowNum();
			logger.debug("total number of rows:" + sheetRowCount);

			for (int i = 1; i <= sheetRowCount; i++) {
				row = sheet.getRow(i);
				rowData = getRowData(row);
				if (rowData.getFeatureType().equalsIgnoreCase(methodName)) {

					logger.info("Test case id " + rowData.getFeatureType());
					logger.debug("row data=" + rowData.toString());
					try {
						process(row, sheetName);
					} catch (Exception e) {
						logger.error("Exception while executing the test: " + rowData.getTestName() + e);
						e.printStackTrace();
						testReporter.log(LogStatus.ERROR, e.toString());
						reporter.endTest(testReporter);
						isTestFail = true;
						isTestFailDescroption = "Testcase Failed due to " + e.toString();
					}
				}
			}
			logger.info("========================================================================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

	}

	// ================================================================================================================================
	/**
	 * {@code process} method to check run mode is yes or No if set Yes then
	 * process the test cases based on the Excel request and validates response
	 * json and updates status(PASS/FAIL/DEPFAIL) back into excel file.
	 * 
	 * @param row
	 * @param sheetName
	 */
	protected void process(XSSFRow row, String sheetName) {

		/*
		 * At first check the test case Run mode is set Y or N if set Y then run
		 * the test cases otherwise Skipped test cases.
		 */

		boolean testRunmode = getTestRunMode(rowData.getrunmode());
		boolean testUsertypeEntitl = getUserAndEntitlementType(rowData.getUserType(), rowData.getEntitlements());
		if (testRunmode) {
			if (testUsertypeEntitl) {
				if (StringUtils.isNotBlank(rowData.getTestName()) && StringUtils.isNotBlank(rowData.getFeatureType())
						&& (StringUtils.isNotBlank(rowData.getDescription()))
						&& StringUtils.isNotBlank(rowData.getMethod())) {
					testReporter = reporter.startTest(rowData.getTestName(), rowData.getDescription()).assignCategory(appName);
					testReporter.log(LogStatus.INFO, "Test case id  - " + rowData.getTestName());
					testReporter.log(LogStatus.INFO, "Feature Type  - " + rowData.getFeatureType());
					testReporter.log(LogStatus.INFO, "User Type  - " + rowData.getUserType());
					testReporter.log(LogStatus.INFO, "Entitlements for the User  - " + rowData.getEntitlements());
					testReporter.log(LogStatus.INFO, "Test case Method  - " + rowData.getMethod());

					// data
					boolean success = true;
					String validationsToken = null;
					String jsonNameKey = null;
					String expectedValue = "";
					int actualStatuscode = 0;
					int expectedStatusCode = 200;

					String bodyString = rowData.getBody();

					requesturl = EnvironmentUrl + getApiPath;
					testReporter.log(LogStatus.INFO, "Requested URL  - " + requesturl);
					// ====================================================================================================

					if (headers.trim().equals("content-type=application/x-www-form-urlencoded")) {
						if (StringUtils.isNotBlank(bodyString))
							testReporter.log(LogStatus.INFO, "Request Body - " + bodyString);
						org.json.JSONObject jsonString = new org.json.JSONObject(bodyString.trim());
						Iterator<?> keys = jsonString.keys();
						while (keys.hasNext()) {
							String key = (String) keys.next();
							String value = jsonString.get(key).toString();
							respec.formParam(key, value);
						}
					} else if (headers.trim().equals("Accept=application/json")) {
						testReporter.log(LogStatus.INFO, "Request API doesn't have Body ");
					}

					if (StringUtils.isNotBlank(requesturl)) {
						logger.info("Request URL for  " + rowData.getFeatureType() + " is :" + requesturl);
						logger.info("spilit headers into string");
						// String tokenizer for header string
						testReporter.log(LogStatus.INFO, "Request Header - " + headers);
						StringTokenizer validationsTokenizer = new StringTokenizer(headers, TOKENIZER_DOUBLE_PIPE);
						while (validationsTokenizer.hasMoreTokens()) {
							if (success == false)
								break;
							validationsToken = validationsTokenizer.nextToken();
							StringTokenizer validationTokenizer = new StringTokenizer(validationsToken,
									TOKENIZER_EQUALTO);

							while (validationTokenizer.hasMoreTokens()) {
								jsonNameKey = validationTokenizer.nextToken();
								// Get next token to get value
								if (validationTokenizer.hasMoreTokens()) {
									expectedValue = validationTokenizer.nextToken();
									logger.info("Headers of the API is " + jsonNameKey + "and" + expectedValue);
									// add headers in the request
									dataStore.put(jsonNameKey, expectedValue);
									respec.headers(dataStore);
								}
							}
						}
					}
					// =========================================================================================================================
					/**
					 * For URL encoding Get and set headers to request
					 */
					if (rowData.getMethod().equalsIgnoreCase(GET)) {
						logger.debug("Entered into GET Method");
						// Call the Rest API and get the response
						resp = respec.cookie("JSESSIONID=" + cookiesid).when().get(requesturl);
					} else if (rowData.getMethod().equalsIgnoreCase(POST)) {
						logger.debug("Entered into POST Method");
						// Call the Rest API and get the response
						resp = respec.cookie("JSESSIONID=" + cookiesid).when().post(requesturl);
					} else if (rowData.getMethod().equalsIgnoreCase(PUT)) {
						logger.debug("Entered into PUT Method");
						// Call the Rest API and get the response
						resp = respec.contentType("application/json").cookie("JSESSIONID=" + cookiesid).when()
								.put(requesturl);
					} else if (rowData.getMethod().equalsIgnoreCase(DELETE)) {
						logger.debug("Entered into Delete Method");
						// Call the Rest API and get the response
						resp = respec.contentType("application/json").cookie("JSESSIONID=" + cookiesid).when()
								.delete(requesturl);
					}

					// Now Compare status code
					actualStatuscode = resp.getStatusCode();
					testReporter.log(LogStatus.INFO, "ActualStatus code  - " + actualStatuscode);
					if (actualStatuscode == expectedStatusCode) {

						logger.info("Actual stauscode: " + actualStatuscode + " " + "expected statuscode "
								+ expectedStatusCode + " is matching as expected");
						testReporter.log(LogStatus.INFO, "Expected and Actual status code is matching");
						testReporter.log(LogStatus.PASS, rowData.getFeatureType() + " " + "Status code Matched");
						testReporter.log(LogStatus.PASS, "PASS");

					} else {
						testReporter.log(LogStatus.FAIL,
								"Status Code not matching - " + "test failed as status code not matching");
						logger.info("Actual stauscode: " + actualStatuscode + " expected statuscode "
								+ expectedStatusCode + " is not matching as expected value");
						testReporter.log(LogStatus.FAIL, "FAIL");
						Assert.fail("test failed as status code not matching");
					}

				}

				else {
					testReporter = reporter.startTest(rowData.getTestName(), rowData.getDescription())
							.assignCategory(appName);
					logger.info(
							"Mandatory information like test name, feature type, Description, Method,usertype or entitlements not provided.");
					testReporter.log(LogStatus.SKIP,
							"Mandatory information like test name, feature type, Description, Method,usertype or entitlements not provided.");
				}
			}
		}

		else {
			testReporter = reporter.startTest(rowData.getTestName(), rowData.getDescription()).assignCategory(appName);
			logger.info("Test case Run Mode is set N hence skipping this test.");
			testReporter.log(LogStatus.SKIP, "Test case Run Mode is set N hence skipping this test.");
		}
	}

	// ========================================================================================================
	/**
	 * Verify Test Run mode if set "Y" then Run Test and If Run Mode is "N" then
	 * Skip test cases.
	 * 
	 * @param testRunmode
	 * @return
	 */
	protected boolean getTestRunMode(String testRunmode) {
		// String testRunmode = testcase.get(simpleName).getTestcaseRunmode();
		if ("Y".equals(testRunmode)) {
			return true;
		} else {
			return false;
		}
	}

	// ====================================================================================================================================
	/**
	 * Capture current excel row data in an object and return
	 * 
	 * @param excel
	 *            row
	 * @return RowData object contains excel row data
	 * @throws Exception
	 */
	protected RowData getRowData(XSSFRow row) throws Exception {
		RowData rowData = new RowData();
		String currentCellData = null;
		for (int currentCell = 0; currentCell < TESTDATA_COLUMN_COUNT; currentCell++) {
			currentCellData = getCellData(row.getCell(currentCell, Row.CREATE_NULL_AS_BLANK));
			switch (currentCell) {
			case 0:
				rowData.setTestName(currentCellData);
			case 1:
				rowData.setFeatureType(currentCellData);
			case 2:
				rowData.setDescription(currentCellData);
			case 3:
				rowData.setMethod(currentCellData);
			case 4:
				rowData.setBody(currentCellData);
			case 5:
				rowData.setrunmode(currentCellData);
			case 6:
				rowData.setUserType(currentCellData);
			case 7:
				rowData.setEntitlements(currentCellData);

			}
		}
		return rowData;
	}

	// ================================================================================================================
	/**
	 * This function will convert an object of type excel cell to a string value
	 * 
	 * @param cell
	 *            excel cell
	 * @return the cell value
	 */
	protected String getCellData(XSSFCell cell) {
		int type = cell.getCellType();
		Object result;
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			throw new RuntimeException("We can't evaluate formulas in Java");
		case Cell.CELL_TYPE_BLANK:
			result = EMPTY_STRING;
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_ERROR:
			throw new RuntimeException("This cell has an error");
		default:
			throw new RuntimeException("We don't support this cell type: " + type);
		}
		return result.toString();
	}

	// ===============================================================================================================================
	/**
	 * Write and commit the changes to excel file.
	 * 
	 * @param workBook
	 *            current excel from where tests run
	 * @throws Exception
	 */
	protected void writeUpdatestoExcel(XSSFWorkbook workBook) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(testDataExcelPath));
		workBook.write(fos);
		fos.close();
	}

	// ====================================================================================================================================
	protected boolean getUserAndEntitlementType(String testUserType, String testUserEntitlments) {

		if (entitlements.equalsIgnoreCase("DWPI")) {
			if (entitlements.equalsIgnoreCase(testUserEntitlments) && userType.equalsIgnoreCase(testUserType) || testUserType.equalsIgnoreCase("ALL")) {
				isTestFail = true;
			} else {
				isTestFail = false;
			}
		} else if (entitlements.equalsIgnoreCase("NDWPI")) {
			if (entitlements.equalsIgnoreCase(testUserEntitlments) || testUserEntitlments.equalsIgnoreCase("DWPI") && userType.equalsIgnoreCase(testUserType) || testUserType.equalsIgnoreCase("ALL")) {
				isTestFail = true;
			} else {
				isTestFail = false;
			}
		} else {
			isTestFail = false;
		}

		return isTestFail;
	}

	// ===================================================================================================================================
	/**
	 * Read data from Json File by passing arguments
	 * 
	 * @param jsonpathLocation
	 * @param filesearch
	 */
	protected String readDataFromJsonFile(String jsonpathLocation, String findText) {
		// JSON read json file from local repo and compare with the actual value
		JSONParser jsonParser = new JSONParser();
		JSONArray a = null;
		String expectedFileName = "";
		try {
			a = (JSONArray) jsonParser.parse(new FileReader(jsonpathLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (Object o : a) {
			JSONObject person = (JSONObject) o;

			expectedFileName = (String) person.get(findText);

		}
		return expectedFileName;
	}

	// =================================================================================================================================
	public String getAlphaNumericString(int n) {
		// lower limit for LowerCase Letters
		int lowerLimit = 97;
		// lower limit for LowerCase Letters
		int upperLimit = 122;
		Random random = new Random();
		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer(n);
		for (int i = 0; i < n; i++) {
			// take a random value between 97 and 122
			int nextRandomChar = lowerLimit + (int) (random.nextFloat() * (upperLimit - lowerLimit + 1));

			// append a character at the end of bs
			r.append((char) nextRandomChar);
		}

		// return the resultant string
		return r.toString();
	}

	// ==================================================================================================================================
	@AfterClass
	public void afterClass() {
		try {
			logger.info("@AfterClass - Start - Logged out of Application.");
			resp = respec.when().get("" + EnvironmentUrl + "/j_spring_security_logout");
			statusCode = resp.getStatusCode();
			logger.info("status code for cookies query request " + ":" + statusCode);
			if (statusCode == 200) {
				logger.info("Logged out Successfully. ");
			}
		} catch (Throwable t) {
			testReporter.log(LogStatus.FAIL, "Failed to Logged out.");// extent
		}
		logger.info("@AfterClass - End - Logged out of Application.");
	}

	// ==================================================================================================================================
	@AfterSuite
	public void afterSuite() {
		reporter.endTest(testReporter);
		reporter.flush();

	}
}
