package com.diapi.automation.reports;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ReportFactory {
	private static ExtentReports reporter;

	/**
	 * The {@code getReporter} method to initialize the ExtentReports objects which was used to generate reports.
	 *
	 */
	public static synchronized ExtentReports getReporter() {
		if (reporter == null) {
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd_HH-mm_z");
			df.setTimeZone(TimeZone.getDefault());
			String date_time = df.format(today);
			reporter = new ExtentReports("Reports/DI-API-AUTOMATION-TEST-REPORT_" + date_time + ".html", true,
					DisplayOrder.OLDEST_FIRST);
			
			reporter.loadConfig(new File("src/test/resources/extent.xml"));
			reporter.addSystemInfo("Rest Assured", "2.4.1");
			
		}
		return reporter;
	}
	
}
