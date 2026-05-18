package com.swaglabs.base;

import com.framework.core.BaseTest;
import framework.pages.swagLabs.ProductsOverviewPage;
import org.testng.annotations.BeforeMethod;

public class SwagLabsBase extends BaseTest {
    protected ProductsOverviewPage productsOverviewPage;

    @BeforeMethod(dependsOnMethods = "setup")
    public void navigate() {
        driver.get(getProperty("sauce_url"));
    }

    public ProductsOverviewPage getProductsOverviewPage() {
        return this.productsOverviewPage;
    }
    // A common method to handle the repetitive login steps
    public void loginAsStandardUser() {
        productsOverviewPage = loginPage.logInSuccessfully(
                getProperty("standard_user"),
                getProperty("common_password")
        );
    }
}
