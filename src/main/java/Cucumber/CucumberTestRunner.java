package Cucumber;
import org.junit.AfterClass;
import org.testng.annotations.AfterSuite;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.base.DriverManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ecommerceautomation.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
	 @AfterSuite(alwaysRun = true)
	    public void tearDownSuite() {
	        // Properly quit the driver and clear ThreadLocal
	        DriverManager.quitDriver();
	    }
}
