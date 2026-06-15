package tests.base;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggedInBaseTest extends BaseTest {
    protected SoftAssertions softly;


    @BeforeMethod(alwaysRun = true)
    public void setupTestState() {
        softly = new SoftAssertions();
        productsOverviewPage = loginAsStandardUser();
        assertThat(productsOverviewPage.getPageTitle())
                .withFailMessage("Page title is not as expected")
                .isEqualTo("Products");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTestState() {
        if (softly != null) {
            softly.assertAll();
        }
    }

}
