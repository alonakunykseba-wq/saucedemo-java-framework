package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductDetailsPage extends BasePage {

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    private final By productNameLocator = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPriceLocator = By.cssSelector("[data-test='inventory-item-price']");

    public String getProductName() {
        return getText(productNameLocator);
    }

    public double getProductPrice(){
        return Double.parseDouble(getText(productPriceLocator).replace("$", ""));
    }
}
