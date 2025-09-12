package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.LoginPage;
import org.testng.annotations.Test;

public class QuickRunTest extends BaseTest {

    @Test(description = "Quick validation of setup")
    public void quickTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnSignupLogin();
        System.out.println("Signup/Login link clicked successfully!");
    }
}
