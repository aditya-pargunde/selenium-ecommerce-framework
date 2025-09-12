package ecommerceautomation.listeners;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.utils.ActionsUtils;
import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExtentListener implements ITestListener {
	
	private WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private static ExtentReports extent;
    private static ExtentTest test;
   

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, result.getThrowable());

        // Take screenshot
        File srcFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
        String destPath = "test-output/screenshots/" + result.getMethod().getMethodName() + ".png";
        try {
            Files.createDirectories(new File("test-output/screenshots/").toPath());
            Files.copy(srcFile.toPath(), new File(destPath).toPath());
            test.addScreenCaptureFromPath(destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
