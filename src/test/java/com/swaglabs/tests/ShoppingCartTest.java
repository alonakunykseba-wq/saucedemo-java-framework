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
            Verifies that the system correctly identifies the highest-priced item in the catalog,\s
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

}
