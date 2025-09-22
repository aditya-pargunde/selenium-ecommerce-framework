package ecommerceautomation.listeners;

import ecommerceautomation.base.BaseTest; 
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

	
	   @Override
	    public void onTestStart(ITestResult result) {
	        System.out.println("🔹 Test Started: " + result.getMethod().getMethodName());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        System.out.println("✅ Test Passed: " + result.getMethod().getMethodName());
	    }
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test failed: " + result.getMethod().getMethodName());
        // Capture screenshot using your static method
        ScreenshotUtils.captureScreenshot(BaseTest.getDriver(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🚀 Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Test Suite Finished: " + context.getName());
    }
}
