package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.utils.ExcelUtils;

import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

	@Test(groups = { "smoke" })
	public void loginWithInvalidCredentials() {
		LoginPage login = new LoginPage(getDriver());
		try {
            ExcelUtils excel = new ExcelUtils("src/test/resources/users.xlsx", "InvalidLoginUsers");
            String[] invalidUser = excel.getFirstRow();

            login.login(invalidUser[0], invalidUser[1]); // email, password
            // Optional: Assert error message displayed
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Test(groups = { "smoke" })
	public void loginWithValidCredentials() {
		LoginPage login = new LoginPage(getDriver());
		try {
            ExcelUtils excel = new ExcelUtils("src/test/resources/users.xlsx", "ValidLoginUsers");
            String[] validUser = excel.getFirstRow();

            login.login(validUser[0], validUser[1]); // email, password
            // Optional: Assert user is logged in
        } catch (Exception e) {
            e.printStackTrace();
        }
		login.logout();
	}
}
