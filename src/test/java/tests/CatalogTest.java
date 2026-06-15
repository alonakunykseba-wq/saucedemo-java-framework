package tests;

import tests.base.LoggedInBaseTest;
import pages.ProductDetailsPage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest extends LoggedInBaseTest {

    // Test is currently disabled because the "Test.allTheThings() T-Shirt"
    // violates the Sauce Labs branding rules. This is a known bug on the site.
    @Test(enabled = false, groups ={"smoke"}, description = "TC-05: shouldDisplaySixProductsWithCorrectBranding_onInitialLoad")
    @Description("""
            Verifies the integrity of the product catalog by ensuring that exactly 6 products are loaded on the page,
            all product names strictly contain the 'Sauce Labs' branding,
            and all product prices are correctly formatted with the USD ($) currency symbol.
            """)
    public void shouldDisplaySixProductsWithCorrectBranding_onInitialLoad() {

        List<String> productNamesList = productsOverviewPage.getProductNames();
        List<String> badNames = productNamesList.stream()
                .filter(name -> !name.toLowerCase().startsWith("sauce") && !name.toLowerCase().contains("labs"))
                .toList();

        softly.assertThat(badNames)
                .withFailMessage("Products which don't contain 'Sauce Labs' are: " + badNames)
                .isEmpty();
        softly.assertThat(productNamesList.size())
                .withFailMessage("The products page contains less products than expected")
                .isEqualTo(6);
        softly.assertThat(productsOverviewPage.getProductPricesWithCurrency())
                .withFailMessage("Product price currency is not USD")
                .allMatch(price -> price.startsWith("$"));
        softly.assertAll();
    }

    @Test (groups ={"smoke"}, description ="TC-06: shouldSortProductsByPriceDescending_whenHighToLowFilterIsApplied")
    @Description("""
            Verifies that the catalog sorting mechanism successfully applies the 'Price (high to low)' filter,
                        and correctly rearranges all product prices on the page into strictly descending order.
            """)
    public void shouldSortProductsByPriceDescending_whenHighToLowFilterIsApplied(){
        productsOverviewPage.applySortingFilter("Price (high to low)");
        assertThat(productsOverviewPage.getProductPrices())
                .withFailMessage("The prices are not sorted in descending order")
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test (description = "TC-13: shouldMatchCatalogInformation_whenProductDetailsAreOpened")
    @Description("""
            Verifies data synchronization between the high-level catalog and individual item pages.
            Ensures that when a specific product is clicked,
            the resulting Product Details page displays the exact same product name and price as the catalog cache.
            """)
    public void shouldMatchCatalogInformation_whenProductDetailsAreOpened(){
        String expectedName = productsOverviewPage.getRandomProductName();
        double expectedPrice = productsOverviewPage.getProductPriceByName(expectedName);
        ProductDetailsPage details = productsOverviewPage.clickRandomProductLink(expectedName);
        softly.assertThat(details.getProductName())
                .withFailMessage("Product name is not the same as one in the overview page")
                .isEqualTo(expectedName);
        softly.assertThat(details.getProductPrice())
                .withFailMessage("Product price is not the same as one in the overview page")
                .isEqualTo(expectedPrice);
        softly.assertAll();
    }
}
