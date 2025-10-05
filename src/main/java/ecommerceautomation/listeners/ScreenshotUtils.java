package ecommerceautomation.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
//    	if (driver == null) {
//            System.out.println("⚠️ WebDriver is null. Screenshot not captured for: " + testName);
//            return null;
//        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String baseDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        String path = baseDir + testName + "_" + timestamp + ".png";

        try {
            Files.createDirectories(Paths.get(baseDir)); // create if not exists
            byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(path), src);
            System.out.println("✅ Screenshot saved at: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
