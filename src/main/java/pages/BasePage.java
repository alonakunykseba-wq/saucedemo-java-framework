package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final By productPriceSelector = By.cssSelector(".inventory_item_price");
    protected final By productNameSelector = By.cssSelector(".inventory_item_name");
    protected final By contentsWrapperSelector = By.id("contents_wrapper");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
       wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void enterText(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
        driver.findElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected ArrayList<String> getTexts(By locator) {
        ArrayList<String> textList = new ArrayList<>();

        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        for (WebElement el : elements) {
            textList.add(el.getText());
        }
        return textList;
    }

    public ArrayList<String> getProductPricesWithCurrency() {
        return getTexts(productPriceSelector);
    }

    public List<Double> getProductPrices() {
        return getProductPricesWithCurrency().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePage> T waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(contentsWrapperSelector));
        return (T) this;
    }
}
