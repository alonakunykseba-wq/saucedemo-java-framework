package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ShoppingCartPage extends BasePage{

    private final By productPriceSelector = By.cssSelector(".inventory_item_price");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<Double> getProductPrices(){
        List<String> rawPrices = getTexts(productPriceSelector);
        return rawPrices.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }
}

