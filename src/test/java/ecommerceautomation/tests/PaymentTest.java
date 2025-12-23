package ecommerceautomation.tests;

import org.testng.annotations.Test;
import ecommerceautomation.base.BaseTest;
import ecommerceautomation.listeners.ExtentTestNGListener;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.CheckoutPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.OrderConfirmationPage;
import ecommerceautomation.pages.PaymentPage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.utils.ExcelUtils;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(ExtentTestNGListener.class)
public class PaymentTest extends BaseTest {
	@DataProvider(name = "UserAndPaymentData")
	public Object[][] getCombinedData() throws Exception {
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

	@Test(dataProvider = "UserAndPaymentData", groups = { "regression" })
	public void completePaymentFlowTest(String name, String email, String password, String title, String day, String month,
			String year, String firstName, String lastName, String company, String address, String country,
			String state, String city, String zipcode, String mobile, String subject, String message, String nameOnCard,
			String cardNumber, String cvc, String expiryMonth, String expiryYear) {

		// Step 1: Start from home and add product
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = homePage.navigateToProductsPage();
		CartPage cartPage = productPage.searchProduct("Blue Top");

		// Step 2: Checkout
		CheckoutPage checkoutPage = cartPage.proceedToCheckout();
		LoginPage loginPage = new LoginPage(getDriver(), wait);

		// Step 3: Login
		loginPage.login(email, password);
		cartPage.cartPageLink();
		cartPage.proceedToCheckout();

		// Step 4: Compare delivery and billing addresses
		List<String> deliveryDetails = checkoutPage.getDeliveryAddressDetails();
		List<String> billingDetails = checkoutPage.getBillingAddressDetails();
		Assert.assertEquals(deliveryDetails, billingDetails, "Delivery and billing addresses should match.");

		// Step 5: Enter card details
		PaymentPage paymentPage = checkoutPage.placeOrder();
		paymentPage.enterCardDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);

		// Step 6: Confirm order
		OrderConfirmationPage orderConfirmationPage = paymentPage.confirmOrder();
		String confirmationMessage = orderConfirmationPage.getOrderConfirmationMessage();
		Assert.assertEquals(confirmationMessage, "ORDER PLACED!", "The order confirmation message is incorrect.");
	}
}
