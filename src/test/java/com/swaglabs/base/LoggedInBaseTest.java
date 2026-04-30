package com.swaglabs.base;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import static org.assertj.core.api.Assertions.assertThat;

public class LoggedInBaseTest extends SwagLabsBase{
    protected SoftAssertions softly;

    @BeforeMethod
    public void setupTestState() {
        softly = new SoftAssertions();
        loginAsStandardUser();

        assertThat(getProductsOverviewPage().getPageTitle())
                .withFailMessage("Page title is not as expected")
                .isEqualTo("Products");
    }
}
