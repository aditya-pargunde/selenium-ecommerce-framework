package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.AccountInformationPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AccountInformationPageTest extends BaseTest {

    @DataProvider(name = "registerUserData")
    public Object[][] getRegisterUserData() throws Exception {
        ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "RegisterUsers");
        return excel.getAllRowsAsObjectArray();
    }

    @Test(dataProvider = "registerUserData")
    public void registerNewUserTest(String name, String email, String password, String title,
                                    String day, String month, String year, String firstname,
                                    String lastname, String company, String address, String country,
                                    String state, String city, String zip, String mobileNo) {

        // Step 1: Start the signup process on the SignupPage
        SignupPage signupPage = new SignupPage(getDriver(), wait);
        signupPage.clickOnSignupLoginLink();
        signupPage.enterNameAtSignUp(name);
        signupPage.enterEmailAtSignUp(email);

        // Step 2: Continue to the account information page
        AccountInformationPage accountInfoPage = signupPage.clickOnSignupButton();

        // Step 3: Fill out account and address information on the AccountInformationPage
        accountInfoPage.enterAccountInformation(title, password, day, month, year);
        accountInfoPage.enterAddressInformation(firstname, lastname, company, address, country, state, city, zip, mobileNo);

        // Step 4: Click create account and navigate to the home page
        HomePage homePage = accountInfoPage.clickOnCreateAccountButton();

        // Step 5: Assert that account creation was successful
        Assert.assertTrue(homePage.isUserLoggedIn(), "Account creation failed or user not logged in.");
    }
}