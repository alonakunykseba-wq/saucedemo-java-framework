package com.swaglabs.tests;

import com.swaglabs.base.LoggedInBaseTest;
import framework.pages.swagLabs.ShoppingCartPage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartTest extends LoggedInBaseTest {

    @Test (description ="TC-07: verifyHighestPriceItemCanBeAddedToCart")
    @Description("""
            Verifies that the system correctly identifies the highest-priced item in the catalog,
            successfully adds it to the shopping cart, and ensures the exact price is accurately reflected inside the cart.
            """)
    public void verifyHighestPriceItemCanBeAddedToCart(){
        double maxPrice = Collections.max(productsOverviewPage.getProductPrices());
        productsOverviewPage.addProductToTheCartByPrice(maxPrice);
        ShoppingCartPage shoppingCart = productsOverviewPage.clickShoppingCart();
        assertThat(shoppingCart.getProductPrices())
                .withFailMessage("The product price is not as expected")
                .containsExactly(maxPrice);
    }

    @Test (description = "TC-08: verifyCartBadgeUpdatesWhenProductIsAdded")
    @Description("""
            Verifies that the system correctly updates the shopping cart badge with the number of added items in real time
            and toggles button name from "Add to cart" to "Remove".
            """)
    public void verifyCartBadgeUpdatesWhenProductIsAdded(){
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

}
