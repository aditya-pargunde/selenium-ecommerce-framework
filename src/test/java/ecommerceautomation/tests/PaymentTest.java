package ecommerceautomation.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
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
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestNGListener.class)
public class PaymentTest extends BaseTest {
	@DataProvider(name = "UserAndPaymentData")
	public Object[][] getCombinedData() throws Exception {
		ExcelUtils loginExcel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		ExcelUtils cardExcel = new ExcelUtils(config.getProperty("user.data.path"), "CardDetails");

		List<String[]> loginRows = loginExcel.getAllRows();
		List<String[]> cardRows = cardExcel.getAllRows();

		// remove headers
		// loginRows.remove(0);
		cardRows.remove(0);

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

	@Test(dataProvider = "UserAndPaymentData", groups= {"regression"})
	public void completePaymentFlow(String name, String email, String password, String nameOnCard, String cardNumber,
			String cvc, String expiryMonth, String expiryYear) {

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
