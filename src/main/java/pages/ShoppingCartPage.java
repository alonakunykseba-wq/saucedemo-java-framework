package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ShoppingCartPage extends BasePage{

    private final By checkoutSelector = By.cssSelector("button[data-test='checkout']");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getProductNames(){
        return getTexts(productNameSelector);
    }

    public CheckoutInformationPage checkout(){
        click(checkoutSelector);
        return new CheckoutInformationPage(driver).waitForPageLoad();
    }
}

