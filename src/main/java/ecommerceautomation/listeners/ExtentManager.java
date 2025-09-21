package ecommerceautomation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	private static ExtentReports extent;

    public static ExtentReports getReporter() {
        if (extent == null) {
            // Report location
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("E-commerce Automation Suite");
            reporter.config().setDocumentTitle("E-Commerce Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // Add system info
            extent.setSystemInfo("Tester", "Aditya");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

}
