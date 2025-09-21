package ecommerceautomation.listeners;

import ecommerceautomation.base.BaseTest; 
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("⚡ Test failed: " + result.getMethod().getMethodName());
        // Capture screenshot using your static method
        ScreenshotUtils.captureScreenshot(BaseTest.getDriver(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) { }
    @Override
    public void onTestSuccess(ITestResult result) { }
    @Override
    public void onTestSkipped(ITestResult result) { }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override
    public void onStart(ITestContext context) { }
    @Override
    public void onFinish(ITestContext context) { }
}
