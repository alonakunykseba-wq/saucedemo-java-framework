package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BasePage{
    private final By firstNameFieldSelector = By.cssSelector("input[data-test='firstName']");
    private final By lastNameFieldSelector = By.cssSelector("input[data-test='lastName']");
    private final By postalCodeSelector = By.cssSelector("input[data-test='postalCode']");
    private final By continueButtonSelector = By.cssSelector("input[data-test='continue']");
    private final By errorSelector = By.cssSelector("h3[data-test = 'error']");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutInformationPage fillTheForm(String firstName,String lastName, String postalCode){
        enterText(firstNameFieldSelector, firstName);
        enterText(lastNameFieldSelector, lastName);
        enterText(postalCodeSelector, postalCode);
        return this;
    }

    public CheckoutOverviewPage proceed(){
        click(continueButtonSelector);
        return new CheckoutOverviewPage(driver).waitForPageLoad();
    }

    public String getErrorText(){
        return getText(errorSelector);
    }
}
