package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ShoppingCartPage extends BasePage{

    private final By productPriceSelector = By.cssSelector(".inventory_item_price");
    private final By checkoutSelector = By.cssSelector("button[data-test='checkout']");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<Double> getProductPrices(){
        List<String> rawPrices = getTexts(productPriceSelector);
        return rawPrices.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }

    public CheckoutInformationPage clickCheckoutButton(){
        click(checkoutSelector);
        return new CheckoutInformationPage(driver);
    }
}

