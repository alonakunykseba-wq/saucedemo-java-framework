package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    private final By completeHeaderSelector = By.cssSelector("h2[data-test='complete-header']");
    private final By completeTextSelector = By.cssSelector("div[data-test='complete-text']");

    public String getCompleteOrderHeader(){
        return getText(completeHeaderSelector);
    }

    public String getCompleteOrderText(){
        return getText(completeTextSelector);
    }
}
