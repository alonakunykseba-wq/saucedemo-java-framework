package com.swaglabs.tests;
import com.swaglabs.base.SwagLabsBase;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends SwagLabsBase {
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                { "locked_out_user",   "common_password", "Epic sadface: Sorry, this user has been locked out." },
                { "not_existing_user", "common_password", "Epic sadface: Username and password do not match any user in this service" },
                { "standard_user", "empty_password", "Epic sadface: Password is required" }
        };
    }


    @Test
    @Description("""
    Verifies that a standard user with valid credentials can successfully log in and
    is automatically redirected to the main Products Inventory page.
    """)
    public void verifySuccessfulLoginRedirectsToInventory(){
        loginAsStandardUser();
        assertThat(getProductsOverviewPage().getPageTitle())
                .withFailMessage("Page title is not as expected")
                .isEqualTo("Products");
    }

    @Test(dataProvider = "invalidLoginData", description = "Verify Login Error Messages")
    @Description("""
            Data-driven negative test suite.
            Verifies that the system securely intercepts invalid login attempts
            (including locked-out accounts, bad credentials, and empty fields)
            and correctly displays the expected error message without granting system access.
            """)
    public void verifyErrorMessageWhenLoginIsNotSuccessful(String login, String password, String expectedErrorMessage) {
        loginPage.logInToTheAccount(
                getProperty(login),
                getProperty(password)
        );
        assertThat(loginPage.getErrorText())
                .withFailMessage("Error text is not as expected")
                .isEqualTo(expectedErrorMessage);
    }
}