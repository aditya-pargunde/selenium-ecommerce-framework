package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.*;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class OrderConfirmationTest extends BaseTest {

	@DataProvider(name = "orderData")
	public Object[][] getOrderData() throws Exception {
		ExcelUtils loginExcel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		ExcelUtils cardExcel = new ExcelUtils(config.getProperty("user.data.path"), "CardDetails");

		List<String[]> loginRows = loginExcel.getAllRows();
		List<String[]> cardRows = cardExcel.getAllRows();

		// remove headers
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

	@Test(dataProvider = "orderData")
	public void verifyOrderConfirmation(String name, String email, String password, String nameOnCard,
			String cardNumber, String cvc, String expiryMonth, String expiryYear) {

		// Step 1: Start from the home page and add a product to the cart
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = homePage.navigateToProductsPage();
		CartPage cartPage = productPage.searchProduct("Blue Top");

		// Step 2: Proceed to checkout and place the order
		CheckoutPage checkoutPage = cartPage.proceedToCheckout();

		// Step 3: login into application
		LoginPage loginPage = new LoginPage(getDriver(), wait);
		loginPage.login(email, password);
		cartPage.cartPageLink();

		// Step 4: click on checkout to proceed
		cartPage.proceedToCheckout();

		// Step 5:Compare delivery and billing addresses and place order
		List<String> deliveryDetails = checkoutPage.getDeliveryAddressDetails();
		List<String> billingDetails = checkoutPage.getBillingAddressDetails();
		Assert.assertEquals(deliveryDetails, billingDetails, "Delivery and billing addresses should match.");
		PaymentPage paymentPage = checkoutPage.placeOrder();

		// Step 6: Enter payment details and confirm the order
		paymentPage.enterCardDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);
		OrderConfirmationPage orderConfirmationPage = paymentPage.confirmOrder();

		// Step 8: Verify the order confirmation message
		String actualMessage = orderConfirmationPage.getOrderConfirmationMessage();
		Assert.assertEquals(actualMessage, "Order placed!", "Order confirmation message is incorrect.");

		// Step 9: Download the invoice and continue
		orderConfirmationPage.downloadInvoice();
		orderConfirmationPage.clickOnContinueButton();

		// Step 10: Verify that we are redirected back to the home page
		Assert.assertTrue(getDriver().getCurrentUrl().contains(config.getProperty("baseURL")),
				"Not redirected to the home page after clicking 'Continue'.");
	}
}