package framework.pages.swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BasePage{
    private final By firstNameFieldSelector = By.cssSelector("input[data-test='firstName']");
    private final By lastNameFieldSelector = By.cssSelector("input[data-test='lastName']");
    private final By postalCodeSelector = By.cssSelector("input[data-test='postalCode']");
    private final By continueButtonSelector = By.cssSelector("input[data-test='continue']");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    public void typeFirstName(String firstName){
        enterText(firstNameFieldSelector, firstName);
    }

    public void typeLastName(String lastName){
        enterText(lastNameFieldSelector, lastName);
    }

    public void typePostalCode(String postalCode){
        enterText(postalCodeSelector, postalCode);
    }

    public CheckoutOverviewPage clickContinueButton(){
        click(continueButtonSelector);
        return new CheckoutOverviewPage(driver);
    }
}
