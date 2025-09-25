package ecommerceautomation.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.base.DriverManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentTestNGListener implements ITestListener {

	private static ExtentReports extent = ExtentManager.getReporter();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent.attachReporter(spark);

		// Ensure screenshots folder exists
		try {
			Files.createDirectories(Paths.get("test-output/screenshots"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, result.getThrowable());
	    System.out.println("Test failed: " + result.getMethod().getMethodName());

	    // Get WebDriver from BaseTest statically
	    WebDriver driver = BaseTest.getDriver(); // <-- static access

	    if (driver != null) {
	        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
	        test.get().addScreenCaptureFromPath(screenshotPath);
	    } else {
	        System.out.println("⚠️ Driver is null, screenshot cannot be captured");
	    }

        // Get WebDriver from BaseTest
        driver = BaseTest.getDriver();
        System.out.println("Driver in listener: " + driver);

        if (driver != null) {
            // Capture screenshot with ScreenshotUtils
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
            // Attach screenshot to report
			test.get().addScreenCaptureFromPath(screenshotPath);
        }
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
