package ecommerceautomation.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.AccountInformationPage;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.CheckoutPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.OrderConfirmationPage;
import ecommerceautomation.pages.PaymentPage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;

//Test Case 16: Place Order: Login before Checkout
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click 'Signup / Login' button
//5. Fill email, password and click 'Login' button
//6. Verify 'Logged in as username' at top
//7. Add products to cart
//8. Click 'Cart' button
//9. Verify that cart page is displayed
//10. Click Proceed To Checkout
//11. Verify Address Details and Review Your Order
//12. Enter description in comment text area and click 'Place Order'
//13. Enter payment details: Name on Card, Card Number, CVC, Expiration date
//14. Click 'Pay and Confirm Order' button
//15. Verify success message 'Your order has been placed successfully!'

public class TestCaseSixteen extends BaseTest{
	@DataProvider(name = "userAndCardData")
	public Object[][] getUserAndCardDataforOrder() throws Exception {
		ExcelUtils loginExcel = new ExcelUtils(config.getProperty("user.data.path"), "UserLogout");
		ExcelUtils cardExcel = new ExcelUtils(config.getProperty("user.data.path"), "CardDetails");

		List<String[]> loginRows = loginExcel.getAllRows();
		List<String[]> cardRows = cardExcel.getAllRows();

		int numRows = Math.min(loginRows.size(), cardRows.size());
		Object[][] data = new Object[numRows][8];

		for (int i = 0; i < numRows; i++) {
			String[] login = loginRows.get(i);
			String[] card = cardRows.get(i);

			data[i][0] = login[0]; // name
			data[i][1] = login[1]; // email
			data[i][2] = login[2]; // password
			data[i][3] = card[0]; // nameOnCard
			data[i][4] = card[1]; // cardNumber
			data[i][5] = card[2]; // cvc
			data[i][6] = card[3]; // expiryMonth
			data[i][7] = card[4]; // expiryYear
		}
		return data;
	}
	@Test(dataProvider = "userAndCardData", groups= {"TestCases"})
	public void LoginBeforeCheckoutTestSixteen(String name, String email, String password, String nameOnCard,
			String cardNumber, String cvc, String expiryMonth, String expiryYear) {

		// Step 1,2,and 3 launch browser and go to home page.
		LoginPage loginPage = new LoginPage(getDriver(), wait);
		HomePage homePage = new HomePage(getDriver(), wait);
		CartPage cartPage = new CartPage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		CheckoutPage checkoutPage = new CheckoutPage(getDriver(), wait);
		PaymentPage paymentPage = new PaymentPage(getDriver(), wait);
		OrderConfirmationPage orderConfirmPage = new OrderConfirmationPage(getDriver(), wait);
		
		//Step 4,5,6: go to signup/login page, enter correct details and login
		loginPage.clickOnSignupLoginLink();
		loginPage.enterEmailForLogin(email);
		loginPage.enterPasswordForLogin(password);
		loginPage.clickOnLoginButton();
		
		//Step 7, 8, 9: search product, add to cart and go to cart page
		homePage.navigateToProductsPage();
		String productName = "Madame Top For Women";
		productPage.searchProduct(productName);
		
		//Step 10, 11: proceed to checkout and verify address details
		cartPage.proceedToCheckout();
		checkoutPage.getBillingAddressDetails();
		checkoutPage.getDeliveryAddressDetails();
		
		//Step 12: Enter decription in comment and place order
		WebElement descriptionBox = driver.findElement(By.cssSelector("textarea[name='message']"));
		descriptionBox.sendKeys("THIS IS TEST DESCRIPTION");
		checkoutPage.placeOrder();
		
		//Step 13: Enter payment details:
		paymentPage.enterCardDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);
		
		//Step 14: pay and confirm order
		paymentPage.confirmOrder();
		orderConfirmPage.getOrderConfirmationMessage();
		orderConfirmPage.downloadInvoice();
		orderConfirmPage.clickOnContinueButton();		
	}
}
