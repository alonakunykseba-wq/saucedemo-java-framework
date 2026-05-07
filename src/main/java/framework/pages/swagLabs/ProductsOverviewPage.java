package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ProductsOverviewPage extends BasePage {

    private final By title = By.cssSelector(".title");
    private final By productNameSelector = By.cssSelector(".inventory_item_name");

    private final By sortingDropdownSelector = By.className("product_sort_container");
    private final By shoppingCartSelector = By.cssSelector("a[data-test='shopping-cart-link']");
    private final By shoppingCartBadgeSelector = By.cssSelector(".shopping_cart_badge");
    private final By removeButtonSelector = By.xpath("//button[contains(text(), 'Remove')]");
    private final By addButtonSelector = By.xpath("//button[contains(text(), 'Add to cart')]");

    public ProductsOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(title);
    }

    public ArrayList<String> getProductNames() {
        return getTexts(productNameSelector);
    }

    public String selectRandomProductName() {
        List<String> products = getProductNames();
        int randomIndex = new Random().nextInt(products.size());
        return products.get(randomIndex);
    }

    public ProductDetailsPage clickRandomProductLink(String randomProduct) {
        String randomProductSelector = String.format("//div[@data-test='inventory-item-name' and text()='%s']", randomProduct);
        click(By.xpath(randomProductSelector));
        return new ProductDetailsPage(driver);
    }

    public double getProductPriceByName(String productName) {
        String priceLocator = String.format(
                "//div[text()='%s']/ancestor::div[@class='inventory_item_description']//div[@data-test='inventory-item-price']",
                productName
        );
        return Double.parseDouble(getText(By.xpath(priceLocator)).replace("$", ""));
    }

    public void applySortingFilter(String sortingMethodName) {
        WebElement sortingDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sortingDropdownSelector));
        Select dropdown = new Select(sortingDropdownElement);
        dropdown.selectByVisibleText(sortingMethodName);
    }

    public void addProductToTheCartByPrice(double price) {
        By addToCartLocator = By.xpath(
                String.format(Locale.US, "//div[@class='inventory_item_price' and contains(.,'$%.2f')]/following-sibling::button", price));
        click(addToCartLocator);
    }

    public void addProductsToTheCart(int amount) {
        for (int x = 0; x < amount; x++) {
           click(addButtonSelector);
        }
    }

    public boolean areRemoveButtonsDisplayed() {
        return !driver.findElements(removeButtonSelector).isEmpty();
    }

    public void clickRemoveButton(){
        click(removeButtonSelector);
    }

    public boolean isShoppingCartBadgeDisplayed() {
        return !driver.findElements(shoppingCartBadgeSelector).isEmpty();
    }

    public int getProductsAmountInTheCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadgeSelector));
        return Integer.parseInt(getText(shoppingCartBadgeSelector));
    }

    public ShoppingCartPage clickShoppingCart() {
        click(shoppingCartSelector);
        return new ShoppingCartPage(driver);
    }
}
