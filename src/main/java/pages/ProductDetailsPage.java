package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductDetailsPage extends BasePage {

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    private final By productNameSelector = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPriceSelector = By.cssSelector("[data-test='inventory-item-price']");

    public String getProductName() {
        return getText(productNameSelector);
    }

    public double getProductPrice(){
        return Double.parseDouble(getText(productPriceSelector).replace("$", ""));
    }
}
