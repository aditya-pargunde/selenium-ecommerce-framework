package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // ------------------ Data Providers ------------------

    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() throws Exception {
        ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
        Object[][] allData = excel.getAllRowsAsObjectArray();
        Object[][] filteredData = new Object[allData.length][2];
        for (int i = 0; i < allData.length; i++) {
            filteredData[i][0] = allData[i][1]; // Email
            filteredData[i][1] = allData[i][2]; // Password
        }
        return filteredData; 
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() throws Exception {
        ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "InvalidLoginUsers");
        Object[][] allData = excel.getAllRowsAsObjectArray();
        Object[][] filteredData = new Object[allData.length][2];
        for (int i = 0; i < allData.length; i++) {
            filteredData[i][0] = allData[i][1]; // Email
            filteredData[i][1] = allData[i][2]; // Password
        }
        return filteredData;
    }

    // ------------------ Tests ------------------

    @Test(dataProvider = "validLoginData")
    public void validUserLoginTest(String email, String password) {
        LoginPage loginPage = new LoginPage(getDriver(), wait);

        Object nextPage = loginPage.login(email, password);

        // Assert login succeeded
        if (nextPage instanceof HomePage) {
            System.out.println("Login successful for valid user: " + email);
            ((HomePage) nextPage).logout();
        } else {
            Assert.fail("Login failed for valid user: " + email);
        }
    }

    @Test(dataProvider = "invalidLoginData")
    public void invalidUserLoginTest(String email, String password) {
        LoginPage loginPage = new LoginPage(getDriver(), wait);

        Object nextPage = loginPage.login(email, password);

        // Assert login failed
        if (nextPage instanceof LoginPage) {
            String actualMessage = loginPage.getLoginErrorMessage();
            String expectedMessage = "Your email or password is incorrect!";
            Assert.assertEquals(actualMessage, expectedMessage,
                    "Incorrect error message for invalid user: " + email);
            System.out.println("Login failed for invalid user: " + email + " | Message: " + actualMessage);
        } else {
            Assert.fail("Login succeeded for invalid user: " + email);
        }
    }
  
}
