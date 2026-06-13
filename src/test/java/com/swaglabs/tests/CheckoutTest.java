package com.swaglabs.tests;

import com.swaglabs.base.LoggedInBaseTest;
import framework.pages.swagLabs.CheckoutCompletePage;
import framework.pages.swagLabs.CheckoutInformationPage;
import framework.pages.swagLabs.CheckoutOverviewPage;
import io.qameta.allure.Description;
import org.assertj.core.data.Offset;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTest extends LoggedInBaseTest {
    private static final String TEST_FIRST_NAME = "Antuan";
    private static final String TEST_LAST_NAME = "Muller";
    private static final String TEST_ZIP = "12-456";

    public record CheckoutData(String firstName, String lastName, String postalCode, String errorMessage ){}
    @DataProvider(name = "checkoutInformation")
    public static Object[][] checkoutInformation() {
        return new Object[][]{
                {new CheckoutData("", TEST_LAST_NAME, TEST_ZIP, "Error: First Name is required")},
                {new CheckoutData(TEST_FIRST_NAME, "", TEST_ZIP, "Error: Last Name is required")},
                {new CheckoutData (TEST_FIRST_NAME, TEST_LAST_NAME, "", "Error: Postal Code is required")}
        };
    }

    private CheckoutInformationPage navigateToCheckoutForm(int amount) {
        return productsOverviewPage
                .addProductsToTheCart(amount)
                .navigateToTheCart()
                .checkout();
    }

    @Test(description = "TC-09: shouldCalculateAccurateTaxesAndTotals_duringCheckout")
    @Description("""
             Verifies that the system calculates cart totals and taxes accurately by ensuring the sum
             of individual items matches the displayed subtotal, and that the final total correctly includes the applied tax amount.
            """)
    public void shouldCalculateAccurateTaxesAndTotals_duringCheckout() {
        int amount = 3;
        CheckoutOverviewPage checkoutOverview = navigateToCheckoutForm(amount)
                .fillTheForm(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ZIP)
                .proceed();
        List<Double> prices = checkoutOverview.getProductPrices();
        softly.assertThat(checkoutOverview.getItemTotalPrice())
                .withFailMessage("The item total differs from sum of product prices without tax")
                .isEqualTo(checkoutOverview.getSumOfProductPricesWithoutTax(prices));
        softly.assertThat(checkoutOverview.getTotalWithTax())
                .withFailMessage("Total amount is not equal to the sum of item total price and tax ptice")
                .isCloseTo(checkoutOverview.getSumOfTaxAndItemTotal(), Offset.offset(0.01));
        softly.assertAll();
    }

    @Test(groups = {"smoke", "e2e"},description = "TC-10: shouldCompletePurchase_whenValidShippingDetailsAreProvided")
    @Description("""  
            Verifies the end-to-end Happy Path purchase flow.
            Ensures that a user can successfully add an item to the cart, provide valid checkout information,
            submit the final order, and receive the correct order confirmation message.
    """)
    public void shouldCompletePurchase_whenValidShippingDetailsAreProvided(){
        int amount = 3;
        CheckoutCompletePage checkoutComplete = navigateToCheckoutForm(amount)
                .fillTheForm(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ZIP)
                .proceed()
                .finish();
        softly.assertThat(checkoutComplete.getCompleteOrderHeader())
                .withFailMessage("The complete order header is not as expected")
                .isEqualTo("Thank you for your order!");
        softly.assertThat(checkoutComplete.getCompleteOrderText())
                .withFailMessage("The complete order text is not as expected")
                .isEqualTo("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        softly.assertAll();
    }

    @Test(dataProvider = "checkoutInformation", description = "TC-11: shouldDisplayValidationErrors_whenShippingFieldsAreMissing")
    @Description("""
            Verifies that the system handles field validation on the checkout information page correctly:
            appropriate error messages appears when First Name, Last Name, or Postal Code are omitted.
            """)

    public void shouldDisplayValidationErrors_whenShippingFieldsAreMissing(CheckoutData data) {
        int amount = 1;
        CheckoutInformationPage buyerInformation = navigateToCheckoutForm(amount)
                .fillTheForm(data.firstName, data.lastName, data.postalCode);
        buyerInformation.proceed();
        softly.assertThat(buyerInformation.getErrorText())
                .withFailMessage("Error message is not as expected")
                .isEqualTo(data.errorMessage);
        softly.assertAll();
    }

    @Test(description = "TC-12: shouldPersistCartItems_whenUserLogsOutAndLogsBackIn")
    @Description("""
            Verifies shopping cart session persistence.
            Ensures that if a user adds items to their cart, logs out, and subsequently logs back in,
            the system successfully retains the state of the cart and restores all previously added items.
            """)

    public void shouldPersistCartItems_whenUserLogsOutAndLogsBackIn(){
        productsOverviewPage.addProductsToTheCart(2);
        List <String> expectedProductList = productsOverviewPage.navigateToTheCart().getProductNames();
        productsOverviewPage.logout();
        loginAsStandardUser();
        List <String> actualProductList = productsOverviewPage.navigateToTheCart().getProductNames();
        assertThat(actualProductList)
                .withFailMessage("The list of products in the shopping cart is not as expected one")
                .containsExactlyInAnyOrderElementsOf(expectedProductList);
    }
}
