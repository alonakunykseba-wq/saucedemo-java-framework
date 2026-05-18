package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutOverviewPage extends BasePage{
    private final By itemTotalSelector = By.cssSelector("div[data-test='subtotal-label']");
    private final By taxPriceSelector = By.cssSelector("div[data-test='tax-label']");
    private final By totalSelector = By.cssSelector("div[data-test='total-label']");
    private final By finishButton = By.cssSelector("button[data-test='finish']");


    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public double getSumOfProductPricesWithoutTax(List<Double> prices){
        return prices.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getItemTotalPrice(){
        return Double.parseDouble(getText(itemTotalSelector).replaceAll("[^0-9.]", ""));
    }

    public double getTaxPrice(){
        String taxPriceRaw= getText(taxPriceSelector);
        return Double.parseDouble(taxPriceRaw.replaceAll("[^0-9.]", ""));
    }

    public double getSumOfTaxAndItemTotal(){
        return getItemTotalPrice() + getTaxPrice();
    }

    public double getTotalWithTax(){
        return Double.parseDouble(getText(totalSelector).replaceAll("[^0-9.]", ""));
    }

    public CheckoutCompletePage finish(){
        click(finishButton);
        return new CheckoutCompletePage(driver).waitForPageLoad();
    }

}
