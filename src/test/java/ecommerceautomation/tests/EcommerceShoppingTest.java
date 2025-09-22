package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.*;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EcommerceShoppingTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
        return excel.getAllRowsAsObjectArray();
    }

    @Test(groups = { "regression" }, dataProvider = "loginData")
    public void fullEndToEndFlow(String email, String password, String expectedResult) {
        // Step 1: Login with valid credentials
        if (expectedResult.equalsIgnoreCase("success")) {
            LoginPage loginPage = new LoginPage(getDriver(), wait);
            Object nextPage = loginPage.login(email, password);
            Assert.assertTrue(nextPage instanceof HomePage, "Expected HomePage object on successful login.");

            HomePage homePage = (HomePage) nextPage;

            // Step 2: Add a product to the cart
            ProductPage productPage = homePage.navigateToProductsPage();
            String productName = "Blue Top";
            CartPage cartPage = productPage.searchProduct(productName);

            // Step 3: Verify the product is in the cart
            Assert.assertTrue(cartPage.isProductInCart(productName),
                    "Expected product to be present in the cart: " + productName);
            
            // Step 4: Proceed to checkout
            CheckoutPage checkoutPage = cartPage.proceedToCheckout();

            // Step 5: Place the order
            PaymentPage paymentPage = checkoutPage.placeOrder();

            // Step 6: Enter payment details (you would need a data provider for this)
            // For a complete test, you would get card details from a data provider
            String nameOnCard = "John Doe";
            String cardNumber = "4000 0000 0000";
            String cvc = "123";
            String expiryMonth = "12";
            String expiryYear = "2025";
            paymentPage.enterCardDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);
            OrderConfirmationPage orderConfirmationPage = paymentPage.confirmOrder();

            // Step 7: Assert order confirmation
            String actualMessage = orderConfirmationPage.getOrderConfirmationMessage();
            Assert.assertEquals(actualMessage, "Order Placed!", "Order confirmation message is incorrect.");

            // Step 8: Logout
            HomePage finalHomePage = orderConfirmationPage.clickOnContinueButton();
            finalHomePage.logout();

        } else {
            // Test for invalid login if needed
            System.out.println("Skipping end-to-end flow for invalid login credentials.");
        }
    }
}