package ecommerceautomation.tests;

import java.util.List;

import org.testng.Assert;
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

//Test Case 14: Place Order: Register while Checkout
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Add products to cart
//5. Click 'Cart' button
//6. Verify that cart page is displayed
//7. Click Proceed To Checkout
//8. Click 'Register / Login' button
//9. Fill all details in Signup and create account
//10. Verify 'ACCOUNT CREATED!' and click 'Continue' button
//11. Verify ' Logged in as username' at top
//12. Click 'Cart' button
//13. Click 'Proceed To Checkout' button
//14. Verify Address Details and Review Your Order
//15. Enter description in comment text area and click 'Place Order'
//16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
//17. Click 'Pay and Confirm Order' button
//18. Verify success message 'Your order has been placed successfully!'
//19. Click 'Delete Account' button
//20. Verify 'ACCOUNT DELETED!' and click 'Continue' button

public class TestCaseFourteen extends BaseTest{
	@DataProvider(name = "placeOrderData")
	public Object[][] userAndCardData() throws Exception {
		ExcelUtils loginExcel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		ExcelUtils cardExcel = new ExcelUtils(config.getProperty("user.data.path"), "CardDetails");

		List<String[]> loginRows = loginExcel.getAllRows();
		List<String[]> cardRows = cardExcel.getAllRows();

		int numRows = Math.min(loginRows.size(), cardRows.size());
		Object[][] data = new Object[numRows][23];

		for (int i = 0; i < numRows; i++) {
			String[] login = loginRows.get(i);
			String[] card = cardRows.get(i);

			data[i][0] = login[0]; // name
			data[i][1] = login[1]; // email
			data[i][2] = login[2]; // password
			data[i][3] = login[3]; // title
			data[i][4] = login[4]; // day
			data[i][5] = login[5]; // month
			data[i][6] = login[6]; // year
			data[i][7] = login[7]; // first name
			data[i][8] = login[8]; // last name
			data[i][9] = login[9]; // company
			data[i][10] = login[10]; // address
			data[i][11] = login[11]; // country
			data[i][12] = login[12]; // state
			data[i][13] = login[13]; // city
			data[i][14] = login[14]; // zipcode
			data[i][15] = login[15]; // mobile
			data[i][16] = login[16]; // subject
			data[i][17] = login[17]; // message
			data[i][18] = card[0]; // nameOnCard
			data[i][19] = card[1]; // cardNumber
			data[i][20] = card[2]; // cvc
			data[i][21] = card[3]; // expiryMonth
			data[i][22] = card[4]; // expiryYear
		}
		return data;
	}
	@Test(dataProvider = "placeOrderData", groups= {"TestCases"})
	public void RegisterWhileCheckoutTestFourteen(String name, String email, String password, String title, String day, String month,
			String year, String firstname, String lastname, String company, String address, String country,
			String state, String city, String zip, String mobileNo, String subject, String message, String nameOnCard,
			String cardNumber, String cvc, String expiryMonth, String expiryYear) {

		// Step 1,2,and 3 launch browser and go to home page.
		SignupPage signupPage = new SignupPage(getDriver(), wait);
		HomePage homePage = new HomePage(getDriver(), wait);
		CartPage cartPage = new CartPage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		AccountInformationPage acntInfoPage = new AccountInformationPage(getDriver(), wait);
		CheckoutPage checkoutPage = new CheckoutPage(getDriver(), wait);
		PaymentPage paymentPage = new PaymentPage(getDriver(), wait);
		OrderConfirmationPage orderConfirmPage = new OrderConfirmationPage(getDriver(), wait);
		
		//Step 4,5,6: search and add product to cart and go to cart page
		homePage.navigateToProductsPage();
		String productName="Fancy Green Top";
		productPage.searchProduct(productName);
		cartPage.isProductInCart(productName);
		
		//Step 7, 8: proceed to checkout and click on register / login button at modal
		cartPage.proceedToCheckout();
		
		//Step 9, 10, 11: fill details to signup, verify account creation
		signupPage.enterNameAtSignUp(name);
		signupPage.enterEmailAtSignUp(email);
		signupPage.clickOnSignupButton();
		acntInfoPage.enterAccountInformation(title, password, day, month, year);
		acntInfoPage.enterAddressInformation(firstname, lastname, company, address, country, state, city, zip, mobileNo);
		acntInfoPage.clickOnCreateAccountButton();
		acntInfoPage.accountCreationConfirmation();
		
		//Step 12, 13: click on cart and proceed to checkout
		cartPage.cartPageLink();
		cartPage.proceedToCheckout();
		
		//Step 14: verify address details
		checkoutPage.getBillingAddressDetails();
		checkoutPage.getDeliveryAddressDetails();
		
		//Step 15. Enter description in comment text area and click 'Place Order'
		checkoutPage.placeOrder();
		
		//Step 16. Enter payment details:
		paymentPage.enterCardDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);
		
		//Step 17: pay and confirm order
		paymentPage.confirmOrder();
		
		//Step 18. verify order success message
		orderConfirmPage.getOrderConfirmationMessage();
		orderConfirmPage.downloadInvoice();
		orderConfirmPage.clickOnContinueButton();
		
		//Step 19, 20: delete account and confirm deletion and click on continue
		homePage.deleteAccount();
		homePage.verifyAccountDeletedMessage();
		homePage.clickOnContinueAfterDeletion();	
	}
}
