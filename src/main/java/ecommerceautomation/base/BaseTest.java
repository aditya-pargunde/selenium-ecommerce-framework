package ecommerceautomation.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest: All test classes will extend this
 */
public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Get browser and headless config
        String browser = ConfigReader.get("browser", "chrome");
        boolean headless = ConfigReader.getBoolean("headless", false);

        // Initialize driver
        DriverManager.initDriver(browser, headless);

        // Navigate to base URL
        getDriver().get(ConfigReader.get("baseURL"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Quit driver
        DriverManager.quitDriver();
    }

    // Getter for driver
    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
