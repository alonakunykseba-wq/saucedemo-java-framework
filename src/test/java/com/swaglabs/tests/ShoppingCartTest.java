package com.swaglabs.tests;

import com.swaglabs.base.LoggedInBaseTest;
import framework.pages.swagLabs.CheckoutInformationPage;
import framework.pages.swagLabs.CheckoutOverviewPage;
import framework.pages.swagLabs.ShoppingCartPage;
import io.qameta.allure.Description;
import org.assertj.core.data.Offset;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartTest extends LoggedInBaseTest {

    @Test(description = "TC-07: verifyHighestPriceItemCanBeAddedToCart")
    @Description("""
            Verifies that the system correctly identifies the highest-priced item in the catalog,
            successfully adds it to the shopping cart, and ensures the exact price is accurately reflected inside the cart.
            """)
    public void verifyHighestPriceItemCanBeAddedToCart() {
        double maxPrice = Collections.max(productsOverviewPage.getProductPrices());
        productsOverviewPage.addProductToTheCartByPrice(maxPrice);
        ShoppingCartPage shoppingCart = productsOverviewPage.clickShoppingCart();
        assertThat(shoppingCart.getProductPrices())
                .withFailMessage("The product price is not as expected")
                .containsExactly(maxPrice);
    }

    @Test(description = "TC-08: verifyCartBadgeUpdatesWhenProductIsAdded")
    @Description("""
            Verifies that the system correctly updates the shopping cart badge with the number of added items in real time
            and toggles button name from "Add to cart" to "Remove".
            """)
    public void verifyCartBadgeUpdatesWhenProductIsAdded() {
        int amount = 3;
        softly.assertThat(productsOverviewPage.isShoppingCartBadgeDisplayed())
                .withFailMessage("The shopping cart is not empty")
                .isFalse();
        productsOverviewPage.addProductsToTheCart(amount);
        softly.assertThat(productsOverviewPage.getProductsAmountInTheCart())
                .withFailMessage("The amount of added product in the cart is not correct")
                .isEqualTo(amount);
        softly.assertThat(productsOverviewPage.areRemoveButtonsDisplayed())
                .withFailMessage("The remove button is not found")
                .isTrue();
        productsOverviewPage.clickRemoveButton();
        softly.assertThat(productsOverviewPage.getProductsAmountInTheCart())
                .withFailMessage("The products amount in the shopping cart is not updated correctly")
                .isEqualTo(amount - 1);
        softly.assertAll();
    }

    @Test(description = "TC-09: verifyCheckoutTotalsAndTaxCalculationsAreAccurate")
    @Description("""
             Verifies that the system calculates cart totals and taxes accurately by ensuring the sum\s
             of individual items matches the displayed subtotal, and that the final total correctly includes the applied tax amount.
            """)
    public void verifyCheckoutTotalsAndTaxCalculationsAreAccurate() {
        int amount = 3;
        productsOverviewPage.addProductsToTheCart(amount);
        ShoppingCartPage shoppingCart = productsOverviewPage.clickShoppingCart();
        CheckoutInformationPage buyerInformation = shoppingCart.clickCheckoutButton();
        buyerInformation.typeFirstName("Antuan");
        buyerInformation.typeLastName("Muller");
        buyerInformation.typePostalCode("12-456");
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
}
