package tests.base;

import driver.DriverFactory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import pages.ProductsOverviewPage;
import utils.ConfigurationManager;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductsOverviewPage productsOverviewPage;

    public ProductsOverviewPage loginAsStandardUser() {
        return loginPage.logInSuccessfully(
                getProperty("standard_user"),
                getProperty("common_password")
        );
    }

    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browserName) {
        driver = DriverFactory.getDriver(browserName);
        driver.manage().window().maximize();
        driver.get(getProperty("sauce_url"));
        loginPage = new LoginPage(driver);
    }

    public String getProperty(String key) {
        return ConfigurationManager.getProperty(key);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
