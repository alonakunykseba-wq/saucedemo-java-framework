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
    public record CheckoutData(String firstName, String lastName, String postalCode, String errorMessage ){}
    @DataProvider(name = "checkoutInformation")
    public static Object[][] checkoutInformation() {
        return new Object[][]{
                {new CheckoutData("", "Muller", "12456", "Error: First Name is required")},
                {new CheckoutData("Antuan", "", "12345", "Error: Last Name is required")},
                {new CheckoutData ("Antuan", "Muller", "", "Error: Postal Code is required")}
        };
    }

    private CheckoutInformationPage navigateToCheckoutForm(int amount) {
        productsOverviewPage.addProductsToTheCart(amount);
        return productsOverviewPage.clickShoppingCart().clickCheckoutButton();
    }

    @Test(description = "TC-09: verifyCheckoutTotalsAndTaxCalculationsAreAccurate")
    @Description("""
             Verifies that the system calculates cart totals and taxes accurately by ensuring the sum
             of individual items matches the displayed subtotal, and that the final total correctly includes the applied tax amount.
            """)
    public void verifyCheckoutTotalsAndTaxCalculationsAreAccurate() {
        int amount = 3;
        CheckoutInformationPage buyerInformation = navigateToCheckoutForm(amount);
        buyerInformation.fillTheForm("Antuan", "Muller","12-456");
        CheckoutOverviewPage checkoutOverview = buyerInformation.clickContinueButton();
        List<Double> prices = checkoutOverview.getProductPrices();
        softly.assertThat(checkoutOverview.getItemTotalPrice())
                .withFailMessage("The item total differs from sum of product prices without tax")
                .isEqualTo(checkoutOverview.getSumOfProductPricesWithoutTax(prices));
        softly.assertThat(checkoutOverview.getTotalWithTax())
                .withFailMessage("Total amount is not equal to the sum of item total price and tax ptice")
                .isCloseTo(checkoutOverview.getSumOfTaxAndItemTotal(), Offset.offset(0.01));
        softly.assertAll();
    }

    @Test(description = "TC-10: verifyCheckoutIsSuccessfullyFinished")
    @Description("""  
            Verifies the end-to-end Happy Path purchase flow.
            Ensures that a user can successfully add an item to the cart, provide valid checkout information, 
            submit the final order, and receive the correct order confirmation message.
    """)
    public void verifyCheckoutIsSuccessfullyFinished(){
        int amount = 3;
        CheckoutInformationPage buyerInformation = navigateToCheckoutForm(amount);
        buyerInformation.fillTheForm("Antuan", "Muller","12-456");
        CheckoutOverviewPage checkoutOverview = buyerInformation.clickContinueButton();
        CheckoutCompletePage checkoutComplete = checkoutOverview.clickFinish();
        softly.assertThat(checkoutComplete.getCompleteOrderHeader())
                .withFailMessage("The complete order header is not as expected")
                .isEqualTo("Thank you for your order!");
        softly.assertThat(checkoutComplete.getCompleteOrderText())
                .withFailMessage("The complete order text is not as expected")
                .isEqualTo("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        softly.assertAll();
    }

    @Test(dataProvider = "checkoutInformation", description = "TC-11: verifyCheckoutFormValidationMessagesForMissingFields")
    @Description("""
            Verifies that the system handles field validation on the checkout information page correctly:
            appropriate error messages appears when First Name, Last Name, or Postal Code are omitted.
            """)

    public void verifyCheckoutFormValidationMessagesForMissingFields(CheckoutData data) {
        int amount = 1;
        CheckoutInformationPage buyerInformation = navigateToCheckoutForm(amount);
        buyerInformation.fillTheForm(data.firstName, data.lastName, data.postalCode);
        buyerInformation.clickContinueButton();
        softly.assertThat(buyerInformation.getError())
                .withFailMessage("Error message is not as expected")
                .isEqualTo(data.errorMessage);
        softly.assertAll();
    }

    @Test(description = "TC-12: verifyCartItemsPersistAfterRelogin")
    @Description("""
            Verifies shopping cart session persistence.
            Ensures that if a user adds items to their cart, logs out, and subsequently logs back in,
            the system successfully retains the state of the cart and restores all previously added items.
            """)

    public void verifyCartItemsPersistAfterRelogin(){
        productsOverviewPage.addProductsToTheCart(2);
        List <String> expectedProductList = productsOverviewPage.clickShoppingCart().getProductNames();
        productsOverviewPage.logout();
        loginAsStandardUser();
        List <String> actualProductList = productsOverviewPage.clickShoppingCart().getProductNames();
        assertThat(actualProductList)
                .withFailMessage("The list of products in the shopping cart is not as expected one")
                .containsExactlyInAnyOrderElementsOf(expectedProductList);
    }
}
