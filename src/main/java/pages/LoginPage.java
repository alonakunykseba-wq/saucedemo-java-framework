package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By usernameField = By.cssSelector("[placeholder=Username]");
    private final By passwordField = By.cssSelector("[placeholder=Password]");
    private final By loginButton = By.cssSelector("#login-button");
    private final By errorMessage = By.cssSelector("[data-test=\"error\"]");

    public ProductsOverviewPage logInSuccessfully(String username, String password) {
        enterText(usernameField, username);
        enterText(passwordField, password);
        click(loginButton);
        return new ProductsOverviewPage(driver).waitForPageLoad();
    }

    public LoginPage loginUnsuccessfully(String username, String password) {
        enterText(usernameField, username);
        enterText(passwordField, password);
        click(loginButton);
        return this;
    }

    public String getErrorText() {
        return getText(errorMessage);
    }

}
