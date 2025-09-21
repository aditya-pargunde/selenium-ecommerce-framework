package ecommerceautomation.base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import ecommerceautomation.utils.WaitUtils;
import java.lang.reflect.Method;

@Listeners(ecommerceautomation.listeners.ScreenshotListener.class)
public class BaseTest {

    // Make the config variable static so it's shared across all tests
    protected static Properties config;
    public WebDriver driver;
    protected WaitUtils wait;
    

    @BeforeSuite(alwaysRun = true)
    public void setupConfig() {
        if (config == null) {
            config = ConfigReader.getProperties();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
    	 String browser = config.getProperty("browser", "chrome");
    	    boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));

    	    WebDriver driverInstance = DriverManager.initDriver(browser, headless);
    	    this.driver = driverInstance;  // make sure instance variable points to ThreadLocal driver
    	    wait = new WaitUtils(driver);

    	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
    	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    	    driver.get(config.getProperty("baseURL"));
    	
//        String browser = config.getProperty("browser", "chrome");
//        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));
//
//        DriverManager.initDriver(browser, headless);
//        driver = DriverManager.getDriver();
//        wait = new WaitUtils(driver);
//
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.get(config.getProperty("baseURL"));
//
//        // Save driver in ITestContext so listener can access it
//        context.setAttribute("driver", driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // This method is already static, which is good
    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public WaitUtils getWait() {
        return wait;
    }
}